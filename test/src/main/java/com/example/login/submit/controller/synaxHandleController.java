package com.example.login.submit.controller;

import com.example.login.algorithm.service.SubmitSynaxMapper;
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
@RequestMapping(value="/synaxsubmit")
public class synaxHandleController {

    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private SubmitSynaxMapper submitSynaxMapper;

    @PostMapping("synaxsubmit")
    public List<Integer> wordSubmit(@RequestBody Submitinfo submitinfo) throws IOException {

        System.out.println(submitinfo.getSubmitcontent());
        /** 将提交信息存入数据库 */
        submitinfoMapper.insertSelective(submitinfo);
        /** 语法分析  */
        submitSynaxMapper.IsSynax(submitinfo.getSubmitcontent());
        /**  判断是否出错 */

        List<Integer> list=new ArrayList<>();
        list.add(0);
        list.add(1);
        return list;
    }
}
