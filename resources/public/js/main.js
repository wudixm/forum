require.config({
  paths : {
    "jquery" : ["jquery.min"],
    "angular": ["angular"],
    "angular-route": ["angular-route"],
    "angularAMD": ["angularAMD.min"],
    // "scripts" : ["scripts"]
  },
  shim: { 'angularAMD': ['angular'], 'angular-route': ['angular'] },
  deps: ['app']

});
