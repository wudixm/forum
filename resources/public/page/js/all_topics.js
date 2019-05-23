define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('allTopicsCtrl', function($scope, $http, $rootScope) {
    console.log("$rootScope.uname");
    console.log($rootScope.uname);
    if ($rootScope.uname === '' ||$rootScope.uname === undefined ) {
      $rootScope.uname = "";
    }
    $http.get("/all_post")
      .then(function(response) {
        console.log(response);
        data = response.data;
        pinned = data['pinned'];
        latest = data['latest'];
        result = []
        for (var i= 0, len = pinned.length; i < len; i++) {
          result[i] = pinned[i];
        }
        console.log($scope.pinned);
        $scope.pinned = result;
        result = []
        for (var i= 0, len = latest.length; i < len; i++) {
          result[i] = latest[i];
        }
        console.log($scope.latest);
        $scope.latest = result;
        // $scope.myWelcome = response.data;
      });
    $scope.toggle = function() {
      $scope.topics = [];
    }
    $scope.user_info = function(uid) {
      console.log(uid);
    }
    console.log("$scope.link_id");
    console.log($scope.link_id);
  });
});

