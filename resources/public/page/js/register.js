define(['app'], function (app) {
  // app.controller('HomeCtrl', function ($scope) {
  // $scope.message = "Message from HomeCtrl";
  // });
  app.controller('registerCtrl', function($scope, $http) {
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
    };
    $scope.selected = function(){
      if ($scope.boxchecked){
        $scope.boxchecked = false;
      } else{
        $scope.boxchecked = true;
      }
    }
  });
});
