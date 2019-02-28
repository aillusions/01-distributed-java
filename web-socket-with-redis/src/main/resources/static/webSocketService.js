/**
 * See http://jmesnil.net/stomp-websocket/doc/
 */
djWsApp.service('webSocketService', ['$rootScope', '$timeout', '$q', 'eventsService', function ($rootScope, $timeout, $q, eventsService) {
    var srv = this;

    var RECONNECT_TIMEOUT_MS = _.random(5 * 1000, 100 * 1000);
    var DISCONNECT_ACKNOWLEDGE_TIMEOUT_MS = 5 * 1000;

    var SOCK_JS_CONNECT_URI = "/stomp-endpoint";

    var subscribedDestinations = [];

    srv.WebSocketDest = {
        SERVER_AVAILABILITY_EVT: "SERVER_AVAILABILITY_EVT",
        WebSocketTopics: {
            GREETINGS_TOPIC: '/topic/greetings'
        },
        /*WebSocketQueues: {
            MESSAGING_THREAD_UPDATE_QUEUE: '/mktWsUser/queue/threadUpdated'
        },*/
        WebSocketMessage: {
            GREETING_MESSAGE: '/ignWsApp/hello'
        }
    };

    var stompClient = null;
    var isStompConnected = false;
    var sessionUserName = null;

    srv.initializeWebSocketConnection = function () {
        stompConnect();
    };

    srv.addWebSocketConnectionListener = function (callback) {
        var topicName = srv.WebSocketDest.SERVER_AVAILABILITY_EVT;
        eventsService.addAppEventListener(topicName, callback);
        console.info("addWebSocketConnectionListener: subscribed.");
    };

    srv.removeWebSocketConnectionListener = function (callback) {
        var topicName = srv.WebSocketDest.SERVER_AVAILABILITY_EVT;
        eventsService.removeAppEventListener(topicName, callback);
        console.info("removeWebSocketConnectionListener: unSubscribed");
    };

    srv.addWebSocketTopicListener = function (topicName, callback) {
        eventsService.addAppEventListener(topicName, callback);
        console.info("addWebSocketTopicListener: subscribed for: " + topicName);

        if (!_.includes(subscribedDestinations, topicName)) {
            stompClient.subscribe(topicName, function (webSockMessage) {
                var payloadBodyDto = JSON.parse(webSockMessage['body']);
                fireWebSocketDest(topicName, payloadBodyDto)
            });
        }
    };

    srv.removeWebSocketTopicListener = function (topicName, callback) {
        eventsService.removeAppEventListener(topicName, callback);
        console.info("removeWebSocketTopicListener: unSubscribed for: " + topicName);
    };

    srv.publishWebSocketMessage = function (destination, dto) {
        stompClient.send(destination, {}, JSON.stringify(dto));
    };

    $(window).bind('beforeunload', function (e) {
        disconnectStompClient();
    });

    //
    //
    //

    function stompConnect() {
        var socket = new SockJS(SOCK_JS_CONNECT_URI);
        stompClient = Stomp.over(socket);
        stompClient.debug = function (msg) {
            // Uncomment for debugging
            // console.info(msg);
        };

        stompClient.heartbeat.outgoing = 0; // client will send heartbeats every 20000ms
        stompClient.heartbeat.incoming = 0; // Set 0 if client does not want to receive heartbeats from the server

        stompClient.connect({}, connectCallback, errorCallback);
    }

    function connectCallback(frame) {
        isStompConnected = true;
        var mappedFrame = mapConnectionFrame(frame);
        sessionUserName = mappedFrame.wsConnectedAsUserName;

        notifyConnected();
        console.info("spaWebSocketService: connected: " + sessionUserName);
    }

    function disconnectStompClient() {
        if (stompClient != null) {
            stompClient.disconnect();
        }
    }

    function errorCallback(error) {
        console.warn("spaWebSocketService: communication error: " + error);
        var origStompConnected = isStompConnected;
        isStompConnected = false;
        sessionUserName = null;

        // If went from connected to disconnected
        if (origStompConnected && !isStompConnected) {
            notifyDisconnected();
        }

        $timeout(function () {
            console.info("spaWebSocketService: attempting to reconnect... ");
            stompConnect();
        }, RECONNECT_TIMEOUT_MS);
    }

    function mapConnectionFrame(frame) {
        return {
            wsConnectedAsUserName: frame['headers']['user-name']
        };
    }

    function notifyConnected() {
        fireWebSocketDest(srv.WebSocketDest.SERVER_AVAILABILITY_EVT, {
            isServerOnline: true
        });
    }

    function notifyDisconnected() {
        $timeout(function () {
            if (!isStompConnected) {
                fireWebSocketDest(srv.WebSocketDest.SERVER_AVAILABILITY_EVT, {
                    isServerOnline: false
                });
            }
        }, DISCONNECT_ACKNOWLEDGE_TIMEOUT_MS);
    }

    function fireWebSocketDest(topicName, payload) {
        eventsService.fireAppEvent(topicName, payload);
        $timeout(function () {
            $rootScope.$apply();
        });
    }

}]);
