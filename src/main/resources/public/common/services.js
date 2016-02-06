(function() {
    
'use strict';

angular
    .module('angularFeedApp')
    .service('tokenManager', tokenManager)
    .service('apiAccess', apiAccess)



function tokenManager($localStorage){

  var self=this;

  self.getToken = function(){
    return 'cb0951b22de3ed836f28b1b07085fe09';
    //return $localStorage.accountToken;
  }

  self.setToken = function(value) {
    $localStorage.accountToken = value;
  }

  self.isLogged = function (){
    return $localStorage.accountToken !== 'unlogged';
  }

  if (self.getToken() === undefined) {
        self.setToken('unlogged');
  }
}

function apiAccess($resource, tokenManager) {
    var self = this;

    self.baseUrl = 'http://127.0.0.1:8088';

     self.category = $resource(self.baseUrl+'/categories/:id'
        ,{ id : '@id' }
        ,{ 
           query : {
               method : 'GET',
               isArray : true,
               headers : {
                   'AUTH':  tokenManager.getToken()
               },
           }
        });

     self.category.feeds = $resource(self.baseUrl+'/categories/:id/feeds'
      ,{id : '@id'}
      ,{
        query : {
          method  : 'GET',
          isArray : true,
          headers : {
              'AUTH':  tokenManager.getToken()
          },
        }
      });

     self.recentEntries = $resource(self.baseUrl+'/entries/recent', {}, {
      query : {
        method : 'GET',
        isArray : true,
        headers : {
          'AUTH' : tokenManager.getToken()
        },
      }
     });

    self.feed = $resource(self.baseUrl+'/feeds/:id'
      ,{id : '@id'}
      , {
           query : {
               method : 'GET',
               isArray : true,
               headers : {
                   'AUTH':  tokenManager.getToken()
               },
           }
        });
    self.feed.entries = $resource(self.baseUrl+'/feeds/:id/entries'
      ,{id : '@id'}
      , {
           query : {
               method : 'GET',
               isArray : true,
               headers : {
                   'AUTH':  tokenManager.getToken()
               },
           }
        });

    self.feed.entries.cont = $resource(self.baseUrl+'/entries/:id/content'
      ,{id : '@id'}
      , {
           query : {
               method : 'GET',
               isArray : false,
               headers : {
                   'AUTH':  tokenManager.getToken()
               },
           }
        });
}

})();
