// Empty JS for your own code to be here
$(function(){
  $("#registera").on('click', function(){
    console.log('register is clicked');
    $.get("register.html",
      function(data){
        //这里是回调方法。返回data数据。这里想怎么处理就怎么处理了。
        console.log(data);
        $('.container-content').html(data);

      });
  });

});
var app = angular.module('myApp', []);
app.controller('myCtrl', function($scope, $http, $stateProvider) {
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
//设置angularJS路由
app.config(function($routeProvider,$locationProvider){
  $routeProvider.when("/",{
    templateUrl : "register.html",
    controller : "syCtrl"
  }).when("/login",{
    templateUrl : "tpl/login.html",
    controller : "loginCtrl"
  });
  $locationProvider.html5Mode(false).hashPrefix("!");
});
