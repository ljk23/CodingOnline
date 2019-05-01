
/**  判断创建比赛信息是否输入为空 */
function judgeCreateContestInfo(name,host){
    if(name==''){
        layer.alert('比赛名称为空', {
            icon: 5,
            time: 2000
        })
        return null;
    }
    else if(host==''){
        layer.alert('比赛主办方为空', {
            icon: 5,
            time: 2000
        })
        return null;
    }
    else return true;
}