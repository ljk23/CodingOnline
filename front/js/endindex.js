/** 判断添加信息是否为空  **/
function IsInputProblemInfo(problemname, problemauthor, problemcontent) {
    if (problemname.length == 0) {
        alert("题目名称不能为空");
        return false;
    } else if (problemauthor.length == 0) {
        alert("题目作者不能为空");
        return false;
    } else if (problemContent.length == 0) {
        alert("题目内容不能为空");
        return false;
    } else {
        return true;
    }
}


