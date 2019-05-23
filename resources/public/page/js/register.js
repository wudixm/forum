define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('registerCtrl', function($scope, $http, $rootScope, $location) {
    console.log("in register Ctrl");
    console.log($scope.email);
    $scope.boxchecked = false;
    $scope.submit = function() {
      var pass1 = $scope.password1;
      var pass2 = $scope.password2;
      console.log("in submit function");
      console.log(pass1);
      console.log(pass2);
      if (pass1 !== pass2){
        alert("两次密码不同");
        return;
      }
      var checkbox = $scope.checkboxagree;
      var checked = $scope.boxchecked;
      if (!checked){
        alert("请先同意协定");
        return;
      }
      var email = $scope.email;
      console.log($scope.link_id);

      register($rootScope, $scope, $location, email, pass1);
    };
    $scope.selected = function(){
      if ($scope.boxchecked){
        $scope.boxchecked = false;
      } else{
        $scope.boxchecked = true;
      }
    };
    register = function($rootScope, $scope, $location, email, pass){
      data= $.param({"email":email, "password":pass});
      // data = $.param({"id":"1","name":"jyy"})
      $http({
        method:"post",
        url:"/register",
        data:data,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }).then(function(response) {
        console.log(response);
        data= $.param({"key_name":"username"});
        $http({
          method:"get",
          url:"/session_info",
          data:data
        }).then(function(response) {
          console.log(response);
          $rootScope.uname = response.data;
          console.log($scope.link_id);
          $location.path( "/" );
        });
      });

    };
  });
});
