define(['angularAMD', 'angular-route'], function (angularAMD) {
  var app = angular.module("app", ['ngRoute']);
  app.config(function ($routeProvider) {
    $routeProvider.when("/", angularAMD.route({
      templateUrl: '/template/test.html',
      controller: 'testCtrl',
      controllerUrl: '/template/test.js'
    }))
  });
  return angularAMD.bootstrap(app);
});
