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
        List<Probleminfo> probleminfos = probleminfoMapper.selectByQuery(probleminfo);
        if (probleminfos.size() > 0) {
            return probleminfos;
        }
        return null;
    }

    @GetMapping("querybyId")
    public Probleminfo queryById(@RequestParam("problemid") Integer problemid) {
        Probleminfo probleminfos = probleminfoMapper.selectByPrimaryKey(problemid);
        if (probleminfos !=null) {
            return probleminfos;
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
        if(probleminfoMapper.updateByPrimaryKeySelective(probleminfo)==1)
            return true;
        return false;
    }
}
