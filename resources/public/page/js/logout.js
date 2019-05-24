define(['app'], function (app) {
  app.controller('logoutCtrl', function($scope, $http, $rootScope, $location) {
    console.log("in logout Ctrl");
    $http.get("/logout").then(
      function(response){
        console.log(response);
        $rootScope.uname=undefined;
        $location.path( "/" );
      }
    );
  });
});


