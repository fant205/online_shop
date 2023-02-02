angular.module('app').controller('cartController', function ($scope, $rootScope, $http, $localStorage) {


    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

//    const contextPath = 'http://localhost:5555/core/api/v1/products';
    var currentPage = 0;
    var size = 5;
    var totalPages = null;


    $scope.loadCart = function () {
        $http.get('http://localhost:5555/carts/api/v1/cart').then(function successCallback(response) {
            $scope.cart = response.data;
        }, function errorCallback(response) {
        });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:5555/carts/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.increment = function (productId, count) {
        $http.get('http://localhost:5555/carts/api/v1/cart/increment/' + productId + '?count=' + count).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.delete = function (productId) {
        $http.delete('http://localhost:5555/carts/api/v1/cart/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.createOrder = function (productId) {
        $http.post('http://localhost:5555/core/api/v1/orders').then(function (response) {
            $scope.clearCart();
            console.log("Order created!");
        });
    };

    $scope.loadCart();

});