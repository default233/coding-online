const baseUrl = "/coding-online/admin";

var code;
var user;

$("#login-form").submit(function(){
    let params = {};
    params.username = $('#username').val().toString();
    params.password = $('#password').val().toString();
    params.rememberMe = $('#customCheck1').prop("checked");

    $.ajax({
        url: baseUrl + '/login',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(params),
        dataType: "json",
        success: function (res) {
            swal("登陆成功！","提示", "success").then(function () {
                    $(location).attr('href', baseUrl + '/index.html');
                }
            );
        },
        error: function (res) {
            let data = jQuery.parseJSON(res.responseText);
            swal(data.message, "登录失败", "error");
        }
    });

    return false;
});


$("#register-form").submit(function(){
    let params = {};
    params.username = $('#username').val().toString();
    params.password = $('#password').val().toString();
    params.email = $('#email').val().toString();

    var reg= /^(?=.*[a-zA-Z])(?=.*\d)[^]{8,16}$/;

    if(!reg.test(params.password)){
        swal("密码格式不正确！", "要求至少8位且必须包含数字和字母", "warning");
        return false;
    }

    $.ajax({
        url: baseUrl + '/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(params),
        dataType: "json",
        success: function (res) {
            swal("注册成功！", "请登录", "success").then(function () {
                        $(location).attr('href', baseUrl + '/pages-login');
                    }
                );
        },
        error: function (res) {
            let data = jQuery.parseJSON(res.responseText);
            swal(data.message, "注册失败", "error");
        }
    });

    return false;
});

$("#recoverpw-form").submit(function(){
    let params = {};
    params.email = $('#email').val();
    swal({
        title: "请等待",
        text: "正在发送中！！",
        icon: "success",
        button: "确定",
    }).then(function () {
        $.ajax({
            url: baseUrl + '/sendEmail',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(params),
            dataType: "json",
            success: function (res) {
                code = res.verificationCode;
                user = res.sysUser;
            },
            error: function (res) {
                let data = jQuery.parseJSON(res.responseText);
                swal(data.message, "发送失败", "error");
            }
        });
    }
);
    return false;
});


$('#verification-code').click(function () {
    swal("验证码", {
        content: "input",
    }).then((value) => {
        if (code === value) {
            newPassword();
        } else {
            swal("警告", "验证码错误", "error");
        }
    })
})

function newPassword() {
    $("#notation").empty().append("请输入新的密码！");
    let recoverpw = $("#recoverpw-form");
    recoverpw.empty().append("<div class=\"form-group\">\n" +
        "                                    <div class=\"col-12\">\n" +
        "                                        <label for=\"username\">用户名</label>\n" +
        "                                        <input class=\"form-control\" type=\"text\" readonly=\"readonly\" id=\"username\" name=\"username\" value="+ user.username +">\n" +
        "                                    </div>\n" +
        "                                </div>")
        .append("<div class=\"form-group\">\n" +
            "                                    <div class=\"col-12\">\n" +
            "                                        <label for=\"password\">新密码</label>\n" +
            "                                        <input class=\"form-control\" type=\"password\" required=\"\" id=\"new-password\" name=\"password\" placeholder=\"请输入密码\">\n" +
            "                                    </div>\n" +
            "                                </div>")
        .append("<div class=\"form-group text-center mt-3\">\n" +
            "                                    <div class=\"col-12\">\n" +
            "                                        <button  id='recover' class=\"btn btn-primary btn-block waves-effect waves-light\" type=\"button\">修改</button>\n" +
            "                                    </div>\n" +
            "                                </div>")
}
$("#recoverpw-form").on('click', '#recover', function () {
    let newPassword = $('#recoverpw-form').find('#new-password').val();
    var reg= /^(?=.*[a-zA-Z])(?=.*\d)[^]{8,16}$/;

    if(!reg.test(newPassword)){
        swal("密码格式不正确！", "要求至少8位且必须包含数字和字母", "warning");
        return false;
    }

    let params = JSON.stringify({
        'newPassword': newPassword,
        'sysUser': user
    });
    $.ajax({
        url: baseUrl + '/recover-password',
        type: 'POST',
        data: params,
        dataType: "json",
        contentType: 'application/json',
        success: function (res) {
            swal("修改成功！", "请重新登录", "success");
        },
        error: function (res) {
            let data = jQuery.parseJSON(res.responseText);
            swal(data.message, "修改密码失败", "error");
        }
    })

})

