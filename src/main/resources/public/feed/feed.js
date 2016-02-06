(function() {


'use strict';

angular
    .module('angularFeedApp')
    .controller('feedController', feedController);

feedController.$inject = ['$window', '$routeParams', '$location', 'apiAccess', '$sanitize'];


function feedController($window, $routeParams, $location, apiAccess, $sanitize) {
	
	var self = this;
	self.entries = [];
	self.entry="";

	apiAccess.feed.entries.query( {id: $routeParams.id}, function(data){
		self.entries=data;
	});

	self.openEntry = function(entry,event){
		switch (event.which) {
			case 1:
				console.log("LEFT CLICK");
				break;
			case 2:
				console.log("MIDDLE CLICK");
				break;
		}
		apiAccess.feed.entries.cont.query( {id: entry.idEntry}, function(data){
			self.entry = $sanitize(data.content);
		});
	};
}

})();
