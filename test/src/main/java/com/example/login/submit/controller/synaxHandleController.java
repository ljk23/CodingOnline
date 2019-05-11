package com.example.login.submit.controller;

import com.example.login.algorithm.service.SubmitSynaxMapper;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/synaxsubmit")
public class synaxHandleController {

    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private SubmitSynaxMapper submitSynaxMapper;

    @PostMapping("synaxsubmit")
    public List<Integer> wordSubmit(@RequestBody Submitinfo submitinfo) throws Exception {
        String synaxresult="";

        /** 将提交信息存入数据库 */
        submitinfoMapper.insertSelective(submitinfo);
        /** 语法分析  */
        String resultInfo=submitSynaxMapper.Synax(submitinfo.getSubmitcontent(),submitinfo.getSubmituserid());
        /**  判断是否出错 */
        String errorcause=submitSynaxMapper.IsError(submitinfo.getSubmituserid());
        if(errorcause==null){   /**  编译正确 */
              /**  更新数据表 */
            submitinfo.setIssuccess(true);
            submitinfo.setSubmitsuccess(1);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            List<Integer> list=new ArrayList<>();
            list.add(1);
            list.add(submitinfo.getSubmituserid());
            return list;
        }
        else{
            /**  编译错误 */
            /**  更新数据表 */
            submitinfo.setErrorcause(errorcause);
            submitinfo.setIssuccess(false);
            submitinfo.setSubmitsuccess(0);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            List<Integer> list=new ArrayList<>();
            list.add(0);
            list.add(submitinfo.getSubmituserid());
            return list;
        }

    }

    @PostMapping("synaxcall")
    public Submitinfo synaxcall(@RequestBody Submitinfo submitinfo) throws Exception{
        System.out.println(submitinfo.getSubmitcontent());
        /** 语法分析  */
        String resultInfo=submitSynaxMapper.Synax(submitinfo.getSubmitcontent(),submitinfo.getSubmituserid());
        /**  判断是否出错 */
        System.out.println("长度是"+resultInfo.length());
        if(resultInfo!=null) {   /**  编译正确 */
            submitinfo.setCentralresult(resultInfo);
            submitinfoMapper.updateByPrimaryKeySelective(submitinfo);
            return submitinfo;
        }
        else{
            return null;
        }
    }
}
