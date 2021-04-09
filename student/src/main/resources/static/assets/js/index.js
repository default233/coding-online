const baseUrl = "/coding-online/student";
var currentUser = {
    username: ""
};
$(document).ready(function () {
    $.ajax({
        url: baseUrl + '/getCurrentUser',
        type: 'Get',
        contentType: 'application/json',
        dataType: "json",
        success: function (res) {
            currentUser.username = res.username;
            $('#current-user-name').val(res.username)
        },
        error: function (res) {

        }
    });
})

