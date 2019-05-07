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

        td.innerHTML = i + 1;
        td1.innerHTML = n.problemid;
        td2.innerHTML = n.problemname;
        td3.innerHTML = n.problemauthor;
        if (n.problemstate == false) td4.innerHTML = "下线";
        else td4.innerHTML = "在线";
        td5.innerHTML = n.uploadtime;

        td6.innerHTML = "<a class='pro_btn pro_btn_editor' onclick='editorPro(" + n.problemid + ")'><img src='../../images/edit.png'></a>&nbsp;&nbsp;&nbsp;" +
            "<a class='pro_btn pro_btn_delete' onclick='deletePro(" + n.problemid + "," + (i + 1) + ")'><img src='../../images/delete-2.png'></a>";

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
        if(n.issuccess==true)  {
            td4.innerHTML="<div class='daanstate' style='background: #5CB85C;'>ACCEPT</div>";
        }
        else{
            td4.innerHTML="<div onclick='errorPage("+n.submitid+")' class='daanstate' style='background: #D9534F;cursor: pointer;width: 150px'>Compile Error</div>";
        }
        td5.innerHTML = "5KB";
        td6.innerHTML = "8s";
        td7.innerHTML = "C";
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