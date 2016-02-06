(function() {


'use strict';

angular
    .module('angularFeedApp')
    .controller('menuController', menuController);

menuController.$inject = ['$window', '$location', 'apiAccess'];

function menuController($window, $location, apiAccess) {

	var self = this;
	self.categories = [];

	apiAccess.category.query(function (data) {
		self.categories = data;
		for (var i = self.categories.length - 1; i >= 0; i--) {
				wrap(apiAccess, self.categories[i]);
		}
	});

	self.unfold = function(category) {
		if(category.display==null){
			category.display=true;
		}else {
			category.display=!category.display;
		}
		
	};

	self.openFeed = function(feed) {
		$location.path('feed/'+feed.idFeed);
	};
}

function wrap(apiAccess, i) {
	apiAccess.category.feeds.query({id: i.idCategory}, function(data){
		i.feeds = data;
	});
}


})();
