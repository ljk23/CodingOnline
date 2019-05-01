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
/** 获取当前时间 */
function CurentTime() {
    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();           //秒

    var clock = year + "-";

    if (month < 10)
        clock += "0";

    clock += month + "-";

    if (day < 10)
        clock += "0";

    clock += day + " ";

    if (hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";

    if (ss < 10) clock += '0';
    clock += ss;
    return (clock);
}
