(function() {

'use strict';

angular.module('angularFeedApp', ['ngRoute','ngSanitize', 'ngResource','ngStorage' ]).config(angularFeedAppConfig);

function angularFeedAppConfig($routeProvider) {
    $routeProvider   
        .when('/', {
        	templateUrl : 'feed/feed.html',
        	controller : 'recentController',
        	controllerAs : 'feed'
        })
        .when('/feed/:id', {
            templateUrl: 'feed/feed.html',
            controller: 'feedController',
            controllerAs: 'feed'
        })
        .when('/entry/:id', {
            templateUrl: 'entries/entry.html',
            controller: 'entryController',
            controllerAs: 'entry'
        })
        .otherwise({
            redirectTo: '/'
        });
    }
})();
