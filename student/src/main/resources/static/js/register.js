function checkForm(){
    //alert("aa");
    /**校验用户名*/
    //1.获取用户输入的数据
    var uValue = document.getElementById("username").value;
    // alert(uValue);
    if(uValue==""){
        //2.给出错误提示信息
        alert("用户名不能为空!");
        return false;
    }


    /*校验密码*/
    var pValue = document.getElementById("password").value;
    // alert(pValue);
    if(pValue==""){
        alert("密码不能为空!");
        return false;
    }

    /**校验确认密码*/
    var rpValue = document.getElementById("password2").value;
    // alert(rpValue);
    if(rpValue!=pValue){
        document.getElementById("password2")
        document.getElementById("after-password").innerText = "两次输入密码不同!"
        return false;
    }

    /*校验邮箱*/
    var eValue = document.getElementById("email").value;
    if(eValue==""){
        alert("邮箱不能为空");
        return false;
    }
}
