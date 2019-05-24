define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('allTopicsCtrl', function($scope, $http, $rootScope) {
    console.log("$rootScope.uname in all topics");
    console.log($rootScope.uname);
    data= $.param({"key_name":"username"});
    if ($rootScope.uname === '' || $rootScope.uname === undefined){
      $http.get("/session_info?key_name=username").then(
        function(response){
          console.log(response);
          $rootScope.uname = response.data;
          if ($rootScope.uname === '' || $rootScope.uname === undefined){
            $rootScope.register_show = true;
            $rootScope.login_show = true;
            $rootScope.logout_show = false;
          } else{
            $rootScope.register_show = false;
            $rootScope.login_show = false;
            $rootScope.logout_show = true;
          }
        }
      );
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
  });
});

