package com.example.login.submit.controller;

import com.example.login.algorithm.service.CommonMapper;
import com.example.login.algorithm.service.Impl.CommonHandle;
import com.example.login.algorithm.service.Impl.SubmitWordHandle;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.entity.Submitmeg;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import com.example.login.user.dao.entity.User;
import com.example.login.user.dao.entity.Userinfo;
import com.example.login.user.dao.mapper.UserinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/getsubmit")
public class submitController {

    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private SubmitWordHandle submitWordHandle;

    @Autowired
    private CommonMapper commonMapper;

    @GetMapping(value = "/queryRankInfo")
    public List<Userinfo> queryAllUsers(){
        List<Userinfo> userinfos=userinfoMapper.selectAllUsers();
        if(userinfos.size()>0)
            return userinfos;
        return null;
    }

    @GetMapping(value ="/queryRank")
    public List<Submitinfo> queryRank(){
        List<Submitinfo> submitinfos=submitinfoMapper.queryRankUsers();
        if(submitinfos.size()>0)
            return submitinfos;
        return null;
    }

    @PostMapping(value = "/infosubmit")
    public Boolean infosubmit(@RequestBody Submitinfo submitinfo){
        Integer submitid=submitinfoMapper.insertSelective(submitinfo);
        if(submitid!=0)
            return true;
        return false;
    }

    @PostMapping(value = "/wordsubmit")
    public List<String> uploadCode(@Param("file") MultipartFile file) throws IOException {

        /**  找出提交记录的最后一条，也就是最新的一条 */
        Submitinfo submitinfo=submitinfoMapper.selectLastRecord();
        System.out.println(submitinfo.toString());
        /** 获取编程语言  */
        String CodingLanguage=submitinfo.getCodinglanguage();

        /**  判断上传的是单个文件还是文件夹 */
        String fileName=file.getOriginalFilename();
        /**  获得文件的名称 */
        String filePreName=fileName.substring(0,fileName.lastIndexOf("."));
        /**  获得文件的后缀名 */
        String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());

        System.out.println(fileTyle);

        /**  上传到指定目录下,并返回路径 */
        String filepath=submitWordHandle.uploadWordCode(file);
        System.out.println(file.getOriginalFilename());

        /**  判断是否编译运行 */

        List list=new ArrayList();   /** 保存最后返回的结果值  */
        String value="";
        /**  单文件处理 */
        /*if(CodingLanguage.equals("java")) {
                *//**  第一步-----进行编译-----   *//*
            if(submitWordHandle.Compiler(filepath)){
                System.out.println("编译成功");
                *//** 第二步-----用例测试----  *//*
                *//** first ---通过 problemId 拿到输入测试集-----  **//*
                int result=submitWordHandle.Answer(1, fileName,submitinfo);
                *//**  删除编译文件 *//*
                submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                *//**  更新数据库  *//*
                list=commonMapper.getResult(submitinfo,result,list,value);
            }else{
                *//**   得到错误信息 *//*
                value=commonMapper.readFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt",value);
                list=commonMapper.getResult(submitinfo,3,list,value);
            }
            *//**   返回结果  *//*
            return list;
        }*/
        /**  多文件处理 */
        if(fileTyle.equals("zip")){
            /**  第一步---------解压缩----------   */
            String zipPath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+file.getOriginalFilename();
            String outPath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/";
            /** 解压缩文件  */
            submitWordHandle.UnZip(zipPath,outPath);
            String compilepath="/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode/"+filePreName;
            /** java文件编译 */
            if(CodingLanguage.equals("java")){    /**    */
                if(submitWordHandle.CompilerFiles(compilepath)){
                    System.out.println("编译成功");
                    /**  进行运行 */
                    /** 第二步-----用例测试----  */
                    /** first ---通过 problemId 拿到输入测试集-----  **/
                    /**  重新定位到控制台 */
                    int result=submitWordHandle.Answer(1,file.getOriginalFilename(),submitinfo);
                    /**  删除编译文件 */
                    submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                    /**  更新数据库  */
                    list=commonMapper.getResult(submitinfo,result,list,value);
                }
                else{
                    /**  删除编译文件 */
                    submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                    System.out.println("编译错误");
                    /**  编译错误  */
                    value=commonMapper.readFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt",value);
                    list=commonMapper.getResult(submitinfo,3,list,value);
                }
                /**   返回结果  */
                return list;
            }
            /**  Cpp文件夹 */
            else{
                if(submitWordHandle.CompilerAppFiles(compilepath)) {
                    System.out.println("编译成功");
                    int result=submitWordHandle.AnswerCpp(1,filePreName+"/",submitinfo);
                    /**  删除编译文件 */
                    submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                    /**  更新数据库  */
                    list=commonMapper.getResult(submitinfo,result,list,value);
                }else{
                    /**  删除编译文件 */
                    submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                    System.out.println("编译错误");
                    /**  编译错误  */
                    value=commonMapper.readFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt",value);
                    list=commonMapper.getResult(submitinfo,3,list,value);
                }
                return list;
            }
        }
        /**  如果上传的是cpp或者c文件 */
       /* else if(CodingLanguage.equals("cpp") || CodingLanguage.equals("c")) {
            *//**  第一步-----进行编译-----   *//*
            if(submitWordHandle.CompilerCpp(filepath)) {
                System.out.println("编译成功");
                PrintStream printStream=System.out;
                System.setOut(printStream);
                fileName="";
                int result=submitWordHandle.AnswerCpp(1,fileName,submitinfo);
                *//**  删除编译文件 *//*
                submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
                *//**  更新数据库  *//*
                list=commonMapper.getResult(submitinfo,result,list,value);
            }
            else{
                    System.out.println("编译失败");
                *//**  删除编译文件 *//*
                *//**  编译错误  *//*
                value=commonMapper.readFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/testset/isaccept/error.txt",value);
                list=commonMapper.getResult(submitinfo,3,list,value);
            }
            return list;
        }*/
        /**   上传文件格式不对 */
        else{
            submitWordHandle.delAllFile("/Users/tp5admin/Desktop/CodingOnline/test/src/main/java/com/example/login/code/wordcode");
            /**  更新数据库  */
            list=commonMapper.getResult(submitinfo,6,list,value);
            return list;
        }
        /**  创建线程池,将提交的任务加入队列 */
    }
}
