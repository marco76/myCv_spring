'use strict';

angular.module('myApp.view1', ['ngRoute', 'ngSanitize'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/marco', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl',
    css: ['view1/reset-font-grids.css','view1/resume.css']
  }).otherwise({
    redirectTo: '/marco'
  });
}])

.controller('View1Ctrl', ['$scope', '$routeParams', '$http','$sce', function($scope, $routeParams, $http,$sce) {
   var selectedUser = "marco";
    $http.get("/rest/cv/user/"+selectedUser).success(function (data) {
      $scope.profile = data;

  });
}]);

