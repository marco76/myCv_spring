'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/marco', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  }).otherwise({
    redirectTo: '/marco'
  });
}])

.controller('View1Ctrl', ['$scope', '$routeParams', '$http','$sce', function($scope, $routeParams, $http,$sce) {
  $http.get("view1/"+"marco.json").success(function (data) {
      $scope.profile = data;
      $scope.certifications = $sce.trustAsHtml(data.certifications);
      $scope.opensource = $sce.trustAsHtml(data.opensource);

  });

    var utc = new Date().toJSON().slice(0,10);


            var myFirebaseRef = new Firebase("https://marcv.firebaseio.com/");
            var visitRef = myFirebaseRef.child("visit").push();
            visitRef.set({
                connectedAt: Firebase.ServerValue.TIMESTAMP,
                cv: "marco",
                date: utc
            });


}]);

