'use strict';

angular.module('myApp.view2', ['ngRoute','ngSanitize'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/stats', {
    templateUrl: '/views/stats/main.html',
    controller: 'StatsCtrl'
  });
}])

    .controller('StatsCtrl', ['$scope', '$routeParams', '$http','$sce', function($scope, $routeParams, $http,$sce) {
      $http.get("/rest/stats/weekly").success(function (data) {
        $scope.articles = data;
        $scope.certifications = $sce.trustAsHtml(data.certifications);
        $scope.opensource = $sce.trustAsHtml(data.opensource);
          console.log($scope.articles);

      });
    }]);