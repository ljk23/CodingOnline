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
import java.io.PrintStream;
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
        /** 将提交信息存入数据库 */
       submitinfoMapper.insertSelective(submitinfo);


        /** 将代码写入----------main.java//main.cpp-----------文件  */
       submitWordMapper.WriteCideIntoFile(submitinfo.getSubmitcontent(),submitinfo.getCodinglanguage());

       /**  重新定位到控制台 */
        PrintStream printStream=System.out;
        System.setOut(printStream);
       /**   判断是否有错误 */
        int result=0;
       if(submitinfo.getCodinglanguage().equals("java"))
            result=submitWordMapper.JudgeResult("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.java");
       else
            result=submitWordMapper.JudgeResult("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.cpp");
        switch (result){
           case 1: System.out.println("编译成功"); break;
           case 2: System.out.println("用例错误"); break;
           case 3: System.out.println("编译错误"); break;
       }

        /*String wordresult=""; *//** 接受词法分析结果  *//*
        wordresult=submitWordMapper.writeWordIntoFile(submitinfo.getSubmitcontent(),submitinfo.getSubmituserid(),wordresult);
        *//**  判断是否出错 *//*
        String errorcause=submitWordMapper.judgeIserror(submitinfo.getSubmituserid());
        System.out.println("wordresult="+wordresult);
       if(errorcause==null){
           *//**  更新数据库表 *//*
            submitinfo.setWordresult(wordresult);
            submitinfo.setIssuccess(true);
            submitinfo.setSubmitsuccess(1);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            *//**   *//*
            List<Integer> list=new ArrayList<>();
            list.add(1);
            list.add(submitinfo.getSubmituserid());*/
       /*    return list;
       }
       else{
           *//**  更新数据库表 *//*
             submitinfo.setWordresult(wordresult);
             submitinfo.setErrorcause(errorcause);
             submitinfo.setIssuccess(false);
             submitinfo.setSubmitsuccess(0);
             submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
             List<Integer> list=new ArrayList<>();
             list.add(0);
             list.add(submitinfo.getSubmituserid());*/
            return null;
      // }
    }

    @GetMapping("query")
    public List<Submitinfo> queryBysubmituserId(@Param("submituserid") Integer submituserid){
        List<Submitinfo> submitinfos = submitinfoMapper.selectBysubmituserId(submituserid);
        if (submitinfos.size() > 0) {
            return submitinfos;
        }
        return null;
    }

    @GetMapping("queryAll")
    public List<Submitinfo> queryAll(){
        List<Submitinfo> submitinfos=submitinfoMapper.selectAll();
        if(submitinfos.size()>0)
            return submitinfos;
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
