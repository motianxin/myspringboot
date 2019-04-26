angular.module('bossApp', [
    'ngRoute',
    'bossApp.snmpCtrl'
        ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/', {
                template: '这是首页页面'
            })
            .when('/table', {
                templateUrl: 'view/table.html'
            })
            .when('/snmp', {
                templateUrl: 'view/snmp.html',
                controller:'snmpCtrl'
            })
            .when('/flash', {
                templateUrl: 'view/flash.html'
            })
            .when('/gobang', {
                templateUrl: 'view/game/gobang.html'
            })
            .when('/game', {
                templateUrl: 'view/game/game.html'
            })
            .when('/mypage', {
                templateUrl: 'view/mypage.html'
            })
            .when('/upload', {
                templateUrl: 'view/upload.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);