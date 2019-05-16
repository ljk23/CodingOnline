package com.example.login.testset.controller;

import com.example.login.match.dao.entity.Matchinfo;
import com.example.login.problem.dao.entity.Probleminfo;
import com.example.login.problem.dao.mapper.ProbleminfoMapper;
import com.example.login.testset.dao.entity.Testinfo;
import com.example.login.testset.dao.mapper.TestinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "testsets/")
public class Testcontroller {

    @Autowired
    private TestinfoMapper testinfoMapper;
    @Autowired
    private ProbleminfoMapper probleminfoMapper;

    @PostMapping(value = "addTests")
    public Boolean addTests(HttpServletRequest request, HttpServletResponse response, @Param("problemid") Integer problemid, @Param("addtime") String addtime){
        String[]  sets= request.getParameterValues("checkID");
       //
        Testinfo testInfo=new Testinfo();
        for(int i=0;i+1<sets.length;i++){
            testInfo.setProblemid(problemid);
            testInfo.setAddtime(addtime);
            testInfo.setTestinput(sets[i]);
            testInfo.setTestoutput(sets[i+1]);
            testinfoMapper.insertSelective(testInfo);
        }
        Probleminfo probleminfo=new Probleminfo();
        probleminfo.setProblemstate(true);
        probleminfo.setProblemid(problemid);
        probleminfoMapper.updateByPrimaryKeySelective(probleminfo);
        return true;
    }
}
