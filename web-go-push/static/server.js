function ServerCtrl($scope, $http) {
  $scope.working = false;

  var logError = function(data, status) {
    console.log('code '+status+': '+data);
    $scope.working = false;
  };

  $scope.sendPush = function() {
    $scope.working = true;
    $http.post('/server/', {Message: $scope.pushMessage, APIKey: $scope.apiKey, RegId: $scope.regId}).
      error(logError).
      success(function(data) {
          $scope.working = false;
          $scope.result = data.Message;
      });
  };

  $scope.result = 'Result will be here...';
}