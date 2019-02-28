/**
 *
 */
djWsApp.controller('djWebsocketFrontMainCtrl', ['$scope', '$interval', 'webSocketService', function ($scope, $interval, webSocketService) {
    var vm = this;


    webSocketService.initializeWebSocketConnection();

    webSocketService.addWebSocketConnectionListener(function (payload) {
        console.info("Server available: " + payload.isServerOnline);

        if (payload.isServerOnline) {

            webSocketService.addWebSocketTopicListener(webSocketService.WebSocketDest.WebSocketTopics.GREETINGS_TOPIC, function (payload) {
                console.info("Greeting ws notification: " + (payload.content || payload.testField));
            });

            $interval(function () {
                webSocketService.publishWebSocketMessage(webSocketService.WebSocketDest.WebSocketMessage.GREETING_MESSAGE, {
                    name: "Vasia"
                });
            }, 2000);
        }

    });


}]);