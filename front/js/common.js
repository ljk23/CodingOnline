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