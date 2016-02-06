(function() {


'use strict';

angular
    .module('angularFeedApp')
    .controller('entryController', entryController);

entryController.$inject = ['$window', '$routeParams', '$location', 'apiAccess'];

function entryController($window, $routeParams, $location, apiAccess) {
	
	var self = this;
	self.cont = [];

	apiAccess.feed.entries.cont.query( {id: $routeParams.id}, function(data){
		console.log(data);
		self.cont=data.content;
	});
}

})();