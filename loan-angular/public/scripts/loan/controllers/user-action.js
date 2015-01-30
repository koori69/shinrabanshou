/**
 * Created by KJ on 2015/1/30.
 */
function UserAction($scope,$http,$rootScope){
    $scope.username="qwe";
    $scope.pwd="123";
$http.post("/use/login?userName="+$scope.username+"&pwd="+$scope.pwd).success(function(res){
if(res.suc){
    $scope.data=res.data.userName;
    $rootScope.user=JSON.parse(res.data);
}
})
}