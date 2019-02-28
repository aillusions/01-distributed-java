/**
 *
 */
var djWsApp = angular.module('djWebsocketFrontApp', ['pascalprecht.translate']);

/**
 *
 */
djWsApp.config(function ($locationProvider) {
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});

