define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('topicsCtrl', function($scope, $http) {
    $http.get("/all_post")
      .then(function(response) {
        console.log(response);
        data = response.data;
        result = []
        for (var i= 0, len = data.length; i < len; i++) {
          result[i] = data[i];
        }
        console.log(typeof(data));
        console.log($scope.topics);
        $scope.topics = result;
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

