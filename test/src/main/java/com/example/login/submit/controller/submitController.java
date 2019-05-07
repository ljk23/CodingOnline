package com.example.login.submit.controller;

import com.example.login.submit.dao.entity.Submitinfo;
import com.example.login.submit.dao.mapper.SubmitinfoMapper;
import com.example.login.user.dao.entity.User;
import com.example.login.user.dao.entity.Userinfo;
import com.example.login.user.dao.mapper.UserinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/getsubmit")
public class submitController {

    @Autowired
    private SubmitinfoMapper submitinfoMapper;
    @Autowired
    private UserinfoMapper userinfoMapper;

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
}
