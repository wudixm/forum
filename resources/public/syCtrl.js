(function(){
    define(['controllerModel'],function(controllers){
        controllers.controller('syCtrl',function($scope,$location){
            $scope.data = "我是sy";
            $scope.goLogin = function(){
                $location.path("/login")
            }
        })
    })
})();
