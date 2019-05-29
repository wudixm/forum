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
      when("/new_topic", angularAMD.route({
        templateUrl: '/page/html/new_topic.html',
        controller: 'newTopicCtrl',
        controllerUrl: '/page/js/new_topic.js'
      })).
      when("/login", angularAMD.route({
        templateUrl: '/page/html/login.html',
        controller: 'loginCtrl',
        controllerUrl: '/page/js/login.js'
      })).
      when("/logout", angularAMD.route({
        template: {template:"fdafdsa"},
        controller: 'logoutCtrl',
        controllerUrl: '/page/js/logout.js'
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
