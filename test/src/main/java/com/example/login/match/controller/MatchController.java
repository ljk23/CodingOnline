package com.example.login.match.controller;

import com.example.login.match.dao.entity.Matchinfo;
import com.example.login.match.dao.mapper.MatchinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="matchAdmin/")
public class MatchController {
    @Autowired
    private MatchinfoMapper matchinfoMapper;

    @PostMapping("insert")
    public Boolean insertInfo(@RequestBody Matchinfo matchinfo){
        System.out.println(matchinfo.toString());
        matchinfoMapper.insertSelective(matchinfo);
        if (matchinfo.getContestid()!= null) {
            return true;
        }
        return false;
    }

}
