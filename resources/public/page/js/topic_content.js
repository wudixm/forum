define(['app'], function (app) {

  app.controller('topicContentCtrl', ['$routeParams', '$http', '$scope', function($routeParams, $http, $scope){
    console.log($routeParams);
    console.log($routeParams['tid']);
    $http.get("/topic?topic_id=" + $routeParams['tid']).then(
      function(response){
        console.log("get topic content from server");
        console.log(response);
        data = response.data;
        $scope.content = data;

      }
    );
  }]);
});


