/**
 *
 */
djWsApp.service('eventsService', ['$rootScope', function ($rootScope) {
    var srv = this;

    var eventListeners = [];

    srv.addAppEventListener = function (evtName, callback) {
        eventListeners.push({
            eventName: evtName,
            eventCallback: callback
        });
    };

    srv.removeAppEventListener = function (evtName, callback) {
        _.remove(eventListeners, function (elem) {
            return elem.eventName === evtName && elem.eventCallback === callback;
        });
    };

    srv.fireAppEvent = function (evtName, data) {
        _.forEach(eventListeners, function (listener) {
            if (listener.eventName === evtName) {
                listener.eventCallback(data);
            }
        });
    };

}]);
