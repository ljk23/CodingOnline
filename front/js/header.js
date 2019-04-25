/**  login  */
function login() {
    $('.operatebackgroud').toggle();
    $('#loginclass').toggle();
}

/**  register */
function register() {
    $('.operatebackgroud').toggle();
    $('#registerclass').toggle();
}

/**  login implement */
function Implement(obj) {
    var user_register = "user_register";
    var user_submit = "user_submit";
    document.getElementById("ui-right").setAttribute("class", "ui-right");
    document.getElementById("ui-right").style.marginTop = '-3px';
    document.getElementById("ui-right").innerHTML = "<ul onmousemove='sub_login_over()' onmouseout='sub_login_out()'><li style='list-style-type:none;'><img src='../images/loginuser.png' width='32' height='32'><a class='loginitem'>" + obj + "<img src='../images/user_xiala.png' width='20' height='10'></a>" +
        "<ul id='sub_login' class='sub_login'>" +
        "<li><a href='../UserMoudle/userInfo.html?userInfo=" + user_register + "' style='text-decoration: none;color: white'>注册信息</a></li>" +
        "<li><a href='../UserMoudle/perProlemSetlInfo.html' style='text-decoration: none;color: white'>用户信息</a></li>" +
        "<li><a href='../UserMoudle/userInfo.html?userInfo=" + user_submit + "' style='text-decoration: none;color: white'>我的提交</a></li>" +
        "<li><a>我的竞赛</a></li>" +
        "<li><a onclick='exitLogin()'>注销</a></li>" +
        "</ul></li></ul>";
}

/** admin login implement  */

/** exit login  */
function exitLogin() {
    alert("您已退出登陆！！！");
    sessionStorage.clear();
    window.location.href = '../IndexMoudle/index.html';
}

/** personal menu  */
function sub_login_over() {
    $("#sub_login").css('display', 'block');
}

function sub_login_out() {
    $("#sub_login").css('display', 'none');
}

/**  check username*/
function checkUserName(username, flag) {
    var reg = /^(?=.*?[a-zA-Z])(?=.*?\d).*$/;
    if (username.length < 8) {
        alert("用户名长度不能小于8");
        return false;
    } else if (username.length > 15) {
        alert("用户名长度不能大于15");
        return false;
    } else if (!(('A' < username.charAt(0) && username.charAt(0) < 'Z') || ('a' < username.charAt(0) && username.charAt(0) < 'z'))) {
        alert("用户名首字母必须为字母");
        return false;
    } else if (!reg.test(username)) {
        alert("用户名必须同时包含字母、数字");
        return false;
    } else return true;
}

/**  check password */
function checkUserPassword(password, repassword, flag) {
    var reg = /^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?\d)(?=.*?[#@*&.]).*$/;
    if (password != repassword) {
        alert("两次密码输入不一致");
        return false;
    } else if (password.length < 8) {
        alert("密码长度不能小于8");
        return false;
    } else if (password.length > 15) {
        alert("密码长度不能大于15");
        return false;
    } else if (!reg.test(password)) {
        alert("密码必须同时包含大小写字母、数字和特殊字符");
        return false;
    } else return true;
}

/**  check email */
function checkUserEmail(email, flag) {
    var pattern = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (!pattern.test(email)) {
        alert("邮箱格式不正确");
        return false;
    }
    return true;
}

