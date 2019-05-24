define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('loginCtrl', function($scope, $http, $rootScope, $location) {
    console.log("in login Ctrl");
    console.log($scope.email);
    $scope.submit = function() {
      var pass = $scope.password1;
      var email = $scope.email;
      var data= $.param({"email":email, "password":pass});
      $http.get("/login?" + data).then(
        function(response){
          console.log(response);
          $location.path( "/" );
        }
      );

    };
  });
});

