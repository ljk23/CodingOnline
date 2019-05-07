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