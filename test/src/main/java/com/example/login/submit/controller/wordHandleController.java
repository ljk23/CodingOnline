package com.example.login.submit.controller;

import com.example.login.algorithm.service.CommonMapper;
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
    @Autowired
    private CommonMapper commonMapper;



    @PostMapping("wordsubmit")
    public List<String> wordSubmit(@RequestBody Submitinfo submitinfo) throws IOException {
        /** 将提交信息存入数据库 */
       submitinfoMapper.insertSelective(submitinfo);

       System.out.println(submitinfo.getCodinglanguage());
        /** 将代码写入----------main.java//main.cpp-----------文件  */
       submitWordMapper.WriteCideIntoFile(submitinfo.getSubmitcontent(),submitinfo.getCodinglanguage());

       /**  重新定位到控制台 */
        PrintStream printStream=System.out;
        System.setOut(printStream);
       /**   判断是否有错误 */
       /**  判断题目的分类 */
        int result=0;

           if(submitinfo.getCodinglanguage().equals("java"))
               result=submitWordMapper.JudgeResult("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.java",submitinfo);
           else
               result=submitWordMapper.JudgeResult("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/main.cpp",submitinfo);

           switch (result){
               case 1: System.out.println("编译成功"); break;
               case 2: System.out.println("用例错误"); break;
               case 3: System.out.println("编译错误"); break;
               case 4: System.out.println("超时"); break;
               case 5: System.out.println("超过内存"); break;
           }
          List<String> list=new ArrayList<>();
           /**  更新数据库  */
           if(result==1){   /**  成功通过 */
               submitinfo.setIssuccess(true);
               submitinfo.setSubmitsuccess(1);
               submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
               list.add(String.valueOf(result));
           }else if(result==2){
               /** 用例不通过  */
               submitinfo.setIssuccess(false);
               submitinfo.setSubmitsuccess(2);
               submitinfo.setErrorcause("用例不通过");
               submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
               list.add(String.valueOf(result));
               list.add("用例不通过");
           }else if(result==3){
               /**   得到错误信息 */
               String value="";
               value=commonMapper.readFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt",value);
               submitinfo.setIssuccess(false);
               submitinfo.setSubmitsuccess(0);
               submitinfo.setErrorcause(value);
               submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
               list.add(String.valueOf(result));
               list.add(value);
           }
           else if(result==4){
               /** 用例不通过  */
               submitinfo.setIssuccess(false);
               submitinfo.setSubmitsuccess(4);
               submitinfo.setErrorcause("超时");
               submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
               list.add(String.valueOf(result));
               list.add("超时");
           }else if(result==5){
               /** 用例不通过  */
               submitinfo.setIssuccess(false);
               submitinfo.setSubmitsuccess(5);
               submitinfo.setErrorcause("超过内存");
               submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
               list.add(String.valueOf(result));
               list.add("超过内存");
           }
           /**  返回结果 */
           return list;

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
