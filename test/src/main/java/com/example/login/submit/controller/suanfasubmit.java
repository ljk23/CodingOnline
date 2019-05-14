package com.example.login.submit.controller;

import com.example.login.algorithm.service.SubmitWordMapper;
import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value="/submit")
public class suanfasubmit {
    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private SubmitWordMapper submitWordMapper;

    @PostMapping("suanfasubmit")
    public List<String> suanfaSubmit(@RequestBody Submitinfo submitinfo) throws IOException {
        List<String> list=new ArrayList<>();/** 返回答案 */
        /** 将提交信息存入数据库 */
        submitinfoMapper.insertSelective(submitinfo);
        System.out.println(submitinfo.getCodinglanguage());
        /** 将代码写入----------main.java//main.cpp-----------文件  */
        submitWordMapper.WriteCideIntoFile(submitinfo.getSubmitcontent(),submitinfo.getCodinglanguage());
        /**   判断是否有错误 */


        return list;
    }
}
