'use strict';

angular.module('myApp.view2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'view2/view2.html',
    controller: 'View2Ctrl'
  });
}])

    .controller('View2Ctrl', ['$scope', '$routeParams', '$http','$sce', function($scope, $routeParams, $http,$sce) {
      var selectedUser = "marco";
      $http.get("/rest/access/").success(function (data) {
        $scope.profile = data;
        $scope.certifications = $sce.trustAsHtml(data.certifications);
        $scope.opensource = $sce.trustAsHtml(data.opensource);
      });
    }]);