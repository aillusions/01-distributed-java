/**
 *
 */
var djWsApp = angular.module('djWebsocketFrontApp', []);

/**
 *
 */
djWsApp.config(function ($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});

