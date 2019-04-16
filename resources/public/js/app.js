define(['angularAMD', 'angular-route'], function (angularAMD) {
  var app = angular.module("app", ['ngRoute']);
  app.config(function ($routeProvider) {
    $routeProvider.
      when("/", angularAMD.route({
      templateUrl: '/html/topics.html',
      controller: 'topicsCtrl',
      controllerUrl: '/js/topics.js'
    })).
      when("/test", {template:"fdafdsafdsafdsaf"}).
      when("/u/:uid", angularAMD.route({
      templateUrl: '/html/u.html',
      controller: 'uCtrl',
      controllerUrl: '/js/u.js'
    }))
  });
  return angularAMD.bootstrap(app);
});
