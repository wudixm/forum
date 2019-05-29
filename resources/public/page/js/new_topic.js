define(['app'], function (app) {
  app.controller('newTopicCtrl', function($scope, $http, $rootScope, $location) {
    console.log("in newTopic Ctrl");
    var user_name = $rootScope.uname;
    console.log(user_name);
    if (user_name === undefined || user_name === ""){
      alert("请先登录");
      $location.path( "/" );
      return ;
    }

    $scope.submit = function() {
      var title = $scope.topic_title;
      var content = $scope.topic_content;
      var data= $.param({"title":title, "content":content});
      console.log(data);
      $http({
        method:"post",
        url:"/new_topic",
        data:data,
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      }).then(function(response) {
        $rootScope.register_show = false;
        console.log(response);
        // $location.path( "/" );
      });

    };

  });
});


