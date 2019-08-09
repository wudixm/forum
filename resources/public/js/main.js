require.config({
  paths : {
    "jquery" : ["jquery.min"],
    "angular": ["angular.min"],
    "angular-route": ["angular-route"],
    "angularAMD": ["angularAMD.min"],
//    "popper": ["popper.min"],
    "bootstrap": ["bootstrap.min"],
//    "bootstrap.popper": ["popper.min"],
//    "initBootstrap" : "...wotever..."


    // "scripts" : ["scripts"]
  },
  shim: {
//    'popper':['jquery'],
    'angularAMD':['angular'],
    'angular-route': ['angular'],
//    "bootstrap": ['jquery', '/js/popper.min.js'],
    "bootstrap": ['jquery'],
//    "bootstrap.popper": ['jquery', 'popper.min'],

//    "bootstrap.popper": ['popper']
    },
  deps: ['app']

});
//define("initBootstrap", ["popper"], function(popper) {
//    // set popper as required by Bootstrap
//    window.Popper = popper;
//    require(["bootstrap"], function(bootstrap) {
//        // do nothing - just let Bootstrap initialise itself
//    });
//});
//require(["/js/popper.min.js"], function(popper) {
//    window.Popper = popper;
//    require(["bootstrap"]);
//});
//
// require(["module/name", ...], function(params){ ... });
//require(["jquery", "bootstrap", "popper.min"], function ($) {
//  console.log('in require');
//    $('#bs-example-navbar-collapse-1').show();
//  console.log('in require');
//
//});

require(["jquery", "bootstrap"], function ($) {
  console.log('in require');
    $('#bsexamplenavbarcollapse1').show();
  console.log('in require');
});
