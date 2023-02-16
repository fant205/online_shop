angular.module('app').controller('registrationController', function ($scope, $rootScope, $http, $localStorage, $location) {

    $scope.functionRegistration = function () {
        $http.post('http://localhost:5555/auth/registration', $scope.reguser).then(function successCallback(response) {
            console.log("Register Success! Please login");
            $localStorage.reguser = null;
            $location.path("/");
        }, function errorCallback(response) {

        });
    };

});