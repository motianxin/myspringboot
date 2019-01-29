var app = angular.module('bossApp.snmpCtrl', []);
app.controller('snmpCtrl', function ($scope, $http) {
    var strPath = window.document.location.pathname;
    var appName = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    var serverUrl = "http://" + window.location.host + appName;
    console.log("serverUrl:", serverUrl);
    $scope.ip = "";
    $scope.listenerPort = "";
    $scope.snmpResult = "";
    $scope.startSnmp = function () {
        var url = serverUrl + "/startReceiverSnmpTrap?ip=" + $scope.ip + "&port=" + $scope.listenerPort;
        $http.get(url).then(function (response) {
            console.log(response.data);
            $scope.snmpResult = response.data.msg;
        }, function (response) {
            console.log(response.data);
            $scope.snmpResult = response.data.msg;
        });
    }

    $scope.stopSnmp = function () {
        var url = serverUrl + "/closeReceiverSnmpTrap";
        $http.get(url).then(function (response) {
            console.log(response.data);
            $scope.snmpResult = response.data.msg;
        }, function (response) {
            console.log(response.data);
            $scope.snmpResult = response.data.msg;
        });
    }
});