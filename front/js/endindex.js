/** 判断添加信息是否为空  **/
function IsInputProblemInfo(problemname,problemauthor,problemcontent){
  if(problemname.length==0){
      alert("题目名称不能为空");
      return false;
  }else if(problemauthor.length==0){
      alert("题目作者不能为空");
      return false;
  }else if(problemContent.length==0){
      alert("题目内容不能为空");
      return false;
  }else {
      return true;
  }
}
/** 获取当前时间 */
function CurentTime()
{
    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
    var ss = now.getSeconds();           //秒

    var clock = year + "-";

    if(month < 10)
        clock += "0";

    clock += month + "-";

    if(day < 10)
        clock += "0";

    clock += day + " ";

    if(hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";

    if (ss < 10) clock += '0';
    clock += ss;
    return(clock);
}
