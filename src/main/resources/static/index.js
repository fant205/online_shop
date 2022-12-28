angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $rootScope, $http, $localStorage) {
//angular.module('app', []).controller('indexController', function ($scope, $http) {

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    const contextPath = 'http://localhost:8189/app/api/v1/products';
    var currentPage = 0;
    var size = 5;
    var totalPages = null;

    $scope.nextPage = function (i) {
        if (currentPage + i == totalPages || currentPage + i == -1) {
            return;
        }
        currentPage = currentPage + i;
        $scope.loadProducts(currentPage);
    };

    $scope.loadProducts = function (page) {
        if (page == null) {
            page = currentPage;
        }
        console.log("page: " + page);
        var id = null;
        var title = null;
        var min = null;
        var max = null;
        if ($scope.newFilterJson != null) {
            id = $scope.newFilterJson.id;
            title = $scope.newFilterJson.title;
            min = $scope.newFilterJson.min;
            max = $scope.newFilterJson.max;
        }

        $http({
            url: contextPath, method: 'GET', params: {
                id: id, title: title, min: min, max: max, page: page, size: size,
            }
        }).then(function (response) {
            $scope.productList = response.data.list;
            var array = [];
            for (let index = 0; index < response.data.pagesCount; index++) {
                array.push({
                    id: index + 1
                })
            }
            totalPages = response.data.pagesCount;
            $scope.pagesCount = array;
            console.log($scope.pagesCount);
            $scope.recordsTotal = response.data.recordsTotal;
        });

    };

    $scope.changeScore = function (s, cost) {
        s.cost = s.cost + cost;
        $http.put(contextPath, s)
            .then(function (response) {
                $scope.loadProducts()
            });
    };

    $scope.delete = function (id) {
        $http({
            url: contextPath + '/' + id, method: 'DELETE',
        }).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.add = function () {
        console.log("add");
        $scope.newProductJson = null;
//            $scope.showMe = true;
//            $scope.disableMe = true;
    };

    $scope.edit = function (s) {
        console.log(s);
        $scope.newProductJson = s;
//        $scope.showMe = true;
        $scope.disableMe = true;
    };

    $scope.createProductJson = function () {
        console.log($scope.newProductJson);
        if ($scope.newProductJson.id == null) {
            //create
            $http.post(contextPath, $scope.newProductJson)
                .then(function (response) {
                    $scope.loadProducts()
                });
        } else {
            //update
            $http.put(contextPath, $scope.newProductJson)
                .then(function (response) {
                    $scope.loadProducts()
                });
        }
    };

    //-------------------------------------------------------------------------------------

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/app/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.loadProducts();
                }
            }, function errorCallback(response) {

            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.addToCart = function (productId) {
        $http.get('http://localhost:8189/app/api/v1/cart/add/' + productId).then(function successCallback(response) {
            $scope.loadCart();
        }, function errorCallback(response) {
        });
    };

    $scope.loadCart = function () {
        $http.get('http://localhost:8189/app/api/v1/cart').then(function successCallback(response) {
            $scope.cart = response.data;
        }, function errorCallback(response) {
        });
    };

    $scope.clearCart = function () {
        $http.get('http://localhost:8189/app/api/v1/cart/clear').then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.increment = function (productId, count) {
        $http.get('http://localhost:8189/app/api/v1/cart/increment/' + productId + '?count=' + count).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.delete = function (productId) {
        $http.delete('http://localhost:8189/app/api/v1/cart/' + productId).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.createOrder = function (productId) {
        $http.post('http://localhost:8189/app/api/v1/orders').then(function (response) {

        });
    };


    $scope.loadProducts(0);
    $scope.loadCart();

});