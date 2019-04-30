package com.example.login.submit.controller;

import com.example.login.problem.dao.entity.Probleminfo;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.algorithm.service.SubmitWordMapper;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import com.example.login.user.dao.entity.Userinfo;
import com.example.login.user.dao.mapper.UserinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/submit")
public class wordHandleController {
    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private SubmitWordMapper submitWordMapper;

    @PostMapping("wordsubmit")
    public List<Integer> wordSubmit(@RequestBody Submitinfo submitinfo) throws IOException {

        System.out.println(submitinfo.getSubmitcontent());
       /** 将提交信息存入数据库 */
       submitinfoMapper.insertSelective(submitinfo);
        /** 将代码写入文件  */
        submitWordMapper.writeWordIntoFile(submitinfo.getSubmitcontent(),submitinfo.getSubmituserid());
        /**  判断是否出错 */
        String errorcause=submitWordMapper.judgeIserror(submitinfo.getSubmituserid());
       if(errorcause==null){
           /**  更新数据库表 */
            submitinfo.setIssuccess(true);
            submitinfo.setSubmitsuccess(1);
            System.out.println(submitinfo.toString());
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            /**   */
            List<Integer> list=new ArrayList<>();
            list.add(1);
            list.add(submitinfo.getSubmituserid());
           return list;
       }
       else{
           /**  更新数据库表 */
             submitinfo.setErrorcause(errorcause);
             submitinfo.setIssuccess(false);
             submitinfo.setSubmitsuccess(0);
             System.out.println(submitinfo.getErrorcause()+" "+submitinfo.getSubmitid());
             submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
             List<Integer> list=new ArrayList<>();
             list.add(0);
             list.add(submitinfo.getSubmituserid());
            return list;
       }
    }

    @GetMapping("query")
    public List<Submitinfo> queryBysubmituserId(@Param("submituserid") Integer submituserid){
        System.out.println(submituserid);
        List<Submitinfo> submitinfos = submitinfoMapper.selectBysubmituserId(submituserid);
        if (submitinfos.size() > 0) {
            return submitinfos;
        }
        return null;
    }
    @GetMapping("queryBysubmitId")
    public Submitinfo queryBysubmitId(@Param("submitid") Integer submitid){
        Submitinfo submitinfo=submitinfoMapper.selectByPrimaryKey(submitid);
        if(submitinfo!=null)
            return submitinfo;
        return null;
    }
}
