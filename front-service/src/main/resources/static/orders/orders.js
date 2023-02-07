angular.module('app').controller('ordersController', function ($scope, $rootScope, $http, $localStorage) {

    $scope.loadOrders = function () {
        $http.get('http://localhost:5555/core/api/v1/orders').then(function successCallback(response) {
            $scope.orders = response.data;
        }, function errorCallback(response) {

        });
    };

    $scope.loadOrders();
});