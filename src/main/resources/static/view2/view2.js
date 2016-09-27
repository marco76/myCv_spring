'use strict';

angular.module('myApp.view2', ['ngRoute','ngSanitize'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/tips', {
    templateUrl: 'view2/view2.html',
    controller: 'View2Ctrl'
  });
}])

    .controller('View2Ctrl', ['$scope', '$routeParams', '$http','$sce', function($scope, $routeParams, $http,$sce) {
      $http.get("/rest/article/latest").success(function (data) {
        $scope.articles = data;
        $scope.certifications = $sce.trustAsHtml(data.certifications);
        $scope.opensource = $sce.trustAsHtml(data.opensource);
          console.log($scope.articles);

      });
    }]);