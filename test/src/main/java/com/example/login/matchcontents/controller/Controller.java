package com.example.login.matchcontents.controller;

import com.example.login.match.dao.entity.Matchinfo;
import com.example.login.match.dao.mapper.MatchinfoMapper;
import com.example.login.matchcontents.dao.entity.Matchcontents;
import com.example.login.matchcontents.dao.mapper.MatchcontentsMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping(value = "matchcontents/")
public class Controller {

    @Autowired
    private MatchcontentsMapper matchcontentsMapper;
    @Autowired
    private MatchinfoMapper matchinfoMapper;

    @PostMapping(value = "addSets")
    public Boolean addSets(HttpServletRequest request, HttpServletResponse response, @Param("contestid") Integer contestid,@Param("addtime") String addtime){
        String[]  sets= request.getParameterValues("checkID");
        Matchcontents matchcontents=new Matchcontents();
        /**  添加到题库 */
        for(int i=0;i<sets.length;i++){
            matchcontents.setContestid(contestid);
            matchcontents.setAddtime(addtime);
            matchcontents.setProblemid( Integer.parseInt(sets[i]));
            matchcontentsMapper.insertSelective(matchcontents);
        }
        /**  锁定添加题库 */
        Matchinfo matchinfo=new Matchinfo();
        matchinfo.setContestcontent("1");
        matchinfo.setContestid(contestid);
        matchinfoMapper.updateByPrimaryKeySelective(matchinfo);
        return true;
    }

    @GetMapping(value = "queryByContestId")
    public List<Matchcontents> queryByContestId(@Param("contestid") Integer contestid){
       List<Matchcontents> matchcontents= matchcontentsMapper.selectByContestId(contestid);
       if(matchcontents.size()>0)
           return matchcontents;
       return null;
    }
}
