/**  更新题库信息 */
function updateInfo(data) {
    /** 删除原有表格的数据 */
    var table = document.getElementById("end_pro_table");
    var infos = document.getElementById("pro_tbody");
    table.removeChild(infos);
    /**此处必须创建tbody，否则无法加入到table中  */
    infos = document.createElement("tbody");
    /**  生成新的table数据元素并添加到table中 */
    $.each(data, function (i, n) {
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");
        var td7 = document.createElement("td");

        td.innerHTML = i + 1;
        td1.innerHTML = n.problemid;
        td2.innerHTML = n.problemname;
        td3.innerHTML = n.problemauthor;
        if (n.problemstate == false) td4.innerHTML = "下线";
        else td4.innerHTML = "在线";
        if (n.problemstate == false) td5.innerHTML = "<a style='color: #2D93CA;cursor: pointer' onclick='loadtests("+n.problemid+")'>未载入</a>";
        else td5.innerHTML ="已载入";
        td6.innerHTML = n.uploadtime;

        td7.innerHTML = "<a class='pro_btn pro_btn_editor' onclick='editorPro(" + n.problemid + ")'><img src='../../images/edit.png'></a>&nbsp;&nbsp;&nbsp;" +
            "<a class='pro_btn pro_btn_delete' onclick='deletePro(" + n.problemid + "," + (i + 1) + ")'><img src='../../images/delete-2.png'></a>";

        tr.appendChild(td);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.setAttribute("id", i + 1);
        //document.getElementById("end_pro_table tbody tr").setAttribute("id",i+1);
        infos.appendChild(tr);
    })
    table.appendChild(infos);
}

/**  更新提交信息 */
function updateSubmitInfo(data){
    /** 局部刷新  */
    /** 删除原有表格的数据 */
    var table = document.getElementById("pro_table");
    var infos = document.getElementById("pro_tbody");
    table.removeChild(infos);
    /**此处必须创建tbody，否则无法加入到table中  */
    var infos = document.createElement("tbody");
    /**  生成新的table数据元素并添加到table中 */
    $.each(data, function (i, n) {
        var tr = document.createElement("tr");
        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");
        var td7 = document.createElement("td");
        var td8 = document.createElement("td");
        var td9 = document.createElement("td");
        var td10 = document.createElement("td");

        td1.innerHTML = n.submitid;
        td2.innerHTML = n.submituserid;
        td3.innerHTML = n.submitname;
        if(n.submitsuccess==0)  {
            td4.innerHTML="<div onclick='errorPage("+n.submitid+")' class='daanstate' style='background: #D9534F;cursor: pointer'>Compile Error</div>";
        }
        else if(n.submitsuccess==1){
            td4.innerHTML="<div class='daanstate' style='background: #5CB85C'>ACCEPT</div>";
        }else if(n.submitsuccess==2){
            td4.innerHTML="<div onclick='errorPage("+n.submitid+")' class='daanstate' style='background: orange;cursor: pointer'>Answer Error</div>";
        }
        else if(n.submitsuccess==4){
            td4.innerHTML="<div onclick='errorPage("+n.submitid+")' class='daanstate' style='background: yellow ;cursor: pointer'>TimeOut</div>";
        }
        else{
            td4.innerHTML="<div onclick='errorPage("+n.submitid+")' class='daanstate' style='background: aquamarine;cursor: pointer'>MemoryOut</div>";
        }
        td5.innerHTML = n.wordresult+"ms";
        td6.innerHTML = n.synaxresult+"kb";
        td7.innerHTML = n.codinglanguage;
        td8.innerHTML = n.submitnameclassfy;
        td9.innerHTML = n.submitupdatetime;
        td10.innerHTML="127.0.0.1";

        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.appendChild(td10);
        tr.setAttribute("id", i + 1);
        infos.appendChild(tr);
    })
    table.appendChild(infos);
}

/**  更新后台比赛信息 */
function updateMatchInfo(data) {
    /** 删除原有表格的数据 */
    var table = document.getElementById("contest_pro_table");
    var infos = document.getElementById("contest_tbody");
    table.removeChild(infos);
    /**此处必须创建tbody，否则无法加入到table中  */
    infos = document.createElement("tbody");
    /**  生成新的table数据元素并添加到table中 */
    $.each(data, function (i, n) {
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");
        var td7 = document.createElement("td");
        var td8 = document.createElement("td");
        var td9 = document.createElement("td");

        td.innerHTML = i + 1;
        td1.innerHTML = n.contestname;
        td2.innerHTML = n.contesthost;
        td3.innerHTML = n.contestform;
        td4.innerHTML=n.conteststarttime+"---"+n.contestendtime;
        if(n.contestcontent!=1)  {
            td5.innerHTML="<a onclick='LoadSets("+n.contestid+")'>未载入</a>";
            td5.style="color: #6FB3E0;cursor:pointer";
        }
        else  td5.innerHTML="已载入"
        if (n.problemstate == false) td6.innerHTML = "下线";
        else td6.innerHTML = "在线";
        td7.innerHTML = n.contestlanguage;
        td8.innerHTML=n.contestcreatetime;
        td9.innerHTML="<a class='pro_btn pro_btn_editor' onclick='editorCon(" + n.contestid + ")'><img src='../../images/edit.png'></a>&nbsp;&nbsp;&nbsp;" +
            "<a class='pro_btn pro_btn_delete' onclick='deleteCon(" + n.contestid + ")'><img src='../../images/delete-2.png'></a>";

        tr.appendChild(td);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.appendChild(td7);
        tr.appendChild(td8);
        tr.appendChild(td9);
        tr.setAttribute("id", i + 1);
        //document.getElementById("end_pro_table tbody tr").setAttribute("id",i+1);
        infos.appendChild(tr);
    })
    table.appendChild(infos);
}

/** 更新前台比赛信息 */
function updatefronttMatchInfo(data) {
    /** 删除原有表格的数据 */
    var table = document.getElementById("qu_table");
    var infos = document.getElementById("qu_tbody");
    table.removeChild(infos);
    /**此处必须创建tbody，否则无法加入到table中  */
    infos = document.createElement("tbody");
    /**  生成新的table数据元素并添加到table中 */
    $.each(data, function (i, n) {
        var tr = document.createElement("tr");
        var td = document.createElement("td");
        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");

        td.innerHTML = i + 1;
        td1.innerHTML = "<a style='text-decoration: none; cursor: pointer;color: #1890FF' onclick='matchInfo("+n.contestid+")'>"+n.contestname+"</a>";
        td2.innerHTML = n.contesthost;
        n.conteststarttime=n.conteststarttime.replace(/\T/g," ");
        td3.innerHTML=n.conteststarttime;
        n.conteststarttime=new Date(n.conteststarttime.replace(/\-/g, "/"));
        n.contestendtime=n.contestendtime.replace(/\T/g," ");
        n.contestendtime=new Date(n.contestendtime.replace(/\-/g, "/"));
        td4.innerHTML = (parseInt(n.contestendtime-n.conteststarttime)/1000/60/60)+"小时";
        td5.innerHTML = n.contestform;
        if (n.conteststate == false) {
            td6.innerHTML ="过期";
            td6.style="color:red";
        }
        else td6.innerHTML = "在线";

        tr.appendChild(td);
        tr.appendChild(td1);
        tr.appendChild(td2);
        tr.appendChild(td3);
        tr.appendChild(td4);
        tr.appendChild(td5);
        tr.appendChild(td6);
        tr.setAttribute("id", i + 1);
        //document.getElementById("end_pro_table tbody tr").setAttribute("id",i+1);
        infos.appendChild(tr);
    })
    table.appendChild(infos);
}
