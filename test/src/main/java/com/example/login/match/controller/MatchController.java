package com.example.login.match.controller;

import com.example.login.match.dao.entity.Matchinfo;
import com.example.login.match.dao.mapper.MatchinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="matchAdmin/")
public class MatchController {
    @Autowired
    private MatchinfoMapper matchinfoMapper;

    @PostMapping("insert")
    public Boolean insertInfo(@RequestBody Matchinfo matchinfo){
        matchinfo.setConteststate(true);
        System.out.println(matchinfo.toString());
        matchinfoMapper.insertSelective(matchinfo);
        if (matchinfo.getContestid()!= null) {
            return true;
        }
        return false;
    }

    @PostMapping("query")
    public List<Matchinfo> query(@RequestBody  Matchinfo matchinfo){
      //  matchinfo.toString();
        List<Matchinfo> matchinfos=matchinfoMapper.selectByQuery(matchinfo);
        if(matchinfos.size()>0)
            return matchinfos;
        return null;
    }

    @GetMapping("queryAll")
    public List<Matchinfo> queryAll(){
        List<Matchinfo> matchinfos=matchinfoMapper.selectAll();
        if(matchinfos.size()>0)
            return matchinfos;
        return null;
    }

    @GetMapping("queryAllOnline")
    public List<Matchinfo> queryAllOnline(){
        List<Matchinfo> matchinfos=matchinfoMapper.queryAllOnline();
        if(matchinfos.size()>0)
            return matchinfos;
        return null;
    }

    @GetMapping("delete")
    public Boolean deleteMatch(@Param("contestid") Integer contestid){
        if(matchinfoMapper.deleteByPrimaryKey(contestid)==1){
            return true;
        }
        else return false;
    }
}
