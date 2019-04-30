/** 获得get的第一个传值  */
function getUserTransferValue() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        theRequest[strs[0].split("=")[0]] = decodeURI(strs[0].split("=")[1]);
        return theRequest[strs[0].split("=")[0]];
    }
}
/**  页面加载  */
function onload(){
    alert("124");
}
/** 查找用户id  */
function  SelectUserIdByUsername(username) {
    var submituserid;
    $.ajax({
        url: "http://localhost:8081/userInfo/queryByUsername",
        type: 'GET',
        data: "username=" + username,
        async: false,
        success: function (data) {
            submituserid=eval(data).userid;
        }
    })
    return submituserid;
}
/** 得到网址url的参数  */
function GetQueryString(name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}