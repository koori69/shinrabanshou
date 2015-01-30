/**
 * Created by KJ on 2015/1/30.
 */
var app = angular.module('app', ['ngRoute']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/', {templateUrl: '/views/home.html', controller: 'UserAction'})
        .when('/wel', {templateUrl: '/views/user/login.html', controller: 'UserAction'})
        .otherwise({redirectTo: '/'});
    $locationProvider.html5Mode(true);
}]);

