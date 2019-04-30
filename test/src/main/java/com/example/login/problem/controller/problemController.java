package com.example.login.problem.controller;

import com.example.login.problem.dao.entity.Probleminfo;
import com.example.login.problem.dao.mapper.ProbleminfoMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="problemAdmin/")
public class problemController {

    @Autowired
    private ProbleminfoMapper probleminfoMapper;

    @PostMapping("/insert")
    public String register(@RequestBody Probleminfo probleminfo) {
        probleminfoMapper.insertSelective(probleminfo);
        if (probleminfo.getProblemname() != null) {
            return probleminfo.getProblemname();
        }
        return null;
    }

    @PostMapping("query")
    public List<Probleminfo> query(@RequestBody Probleminfo probleminfo) {
        List<Probleminfo> problemInfos = probleminfoMapper.selectByQuery(probleminfo);
        if (problemInfos.size() > 0) {
            return problemInfos;
        }
        return null;
    }

    @GetMapping("queryById")
    public Probleminfo queryById(@RequestParam("problemId") Integer problemId) {
        System.out.println(problemId);
        Probleminfo problemInfos = probleminfoMapper.selectByPrimaryKey(problemId);
        if (problemInfos !=null) {
            return problemInfos;
        }
        return null;
    }

    @GetMapping("queryByClassfy")
    public List<Probleminfo> queryByClassfy(@RequestParam("problemclassfy") String problemclassfy) {
        List<Probleminfo> problemInfos = probleminfoMapper.selectByProblemClassfy(problemclassfy);
        if (problemInfos !=null) {
            return problemInfos;
        }
        return null;
    }

    @GetMapping("delete")
    public Boolean delete(@RequestParam("problemid") Integer problemid){
        if(problemid!=0 && probleminfoMapper.deleteByPrimaryKey(problemid)>0)
            return true;
        return false;
    }

    @PostMapping("editor")
    public Boolean editor(@RequestBody Probleminfo probleminfo){
        System.out.println(probleminfo.getProblemclassfy()+" "+probleminfo.getProblemstate());
        if(probleminfoMapper.updateByPrimaryKeySelective(probleminfo)==1)
            return true;
        return false;
    }
}
