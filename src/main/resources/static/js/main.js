function checkPassword() {
    let password, confirm;
    password = document.getElementById('password').value;
    confirm = document.getElementById('confirm').value;
    if (password === confirm){
        document.getElementById('btn').removeAttribute('disabled');
        return;
    }
    document.getElementById('btn').setAttribute('disabled', 'disabled');
}

let app = angular.module('mainModule', []);

app.controller('mainController', function($scope, $http){
    $scope.nome = 'Valor Inicial';
    $scope.reset = function()
    {
        $scope.nome = '';
    }
});
