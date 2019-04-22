define(['angularAMD', 'angular-route'], function (angularAMD) {
  var app = angular.module("app", ['ngRoute']);
  app.config(function ($routeProvider) {
    $routeProvider.
      when("/", angularAMD.route({
      templateUrl: '/page/html/all_topics.html',
      controller: 'allTopicsCtrl',
      controllerUrl: '/page/js/all_topics.js'
    })).
      when("/topic/:tid", angularAMD.route({
      templateUrl: '/page/html/topic_content.html',
      controller: 'topicContentCtrl',
      controllerUrl: '/page/js/topic_content.js'
    })).
      when("/register", angularAMD.route({
      templateUrl: '/page/html/register.html',
      controller: 'registerCtrl',
      controllerUrl: '/page/js/register.js'
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
