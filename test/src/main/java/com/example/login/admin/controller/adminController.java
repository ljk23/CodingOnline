package com.example.login.admin.controller;

import com.example.login.admin.dao.entity.Admininfo;
import com.example.login.admin.dao.mapper.AdmininfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="adminInfo/")
public class adminController {
     @Autowired
    private AdmininfoMapper admininfoMapper;

    @PostMapping("login")
    public Admininfo login(@RequestBody Admininfo admininfo){
        System.out.println(admininfo.getAdminname());
        List<Admininfo> admininfoList=admininfoMapper.selectByAdminName(admininfo.getAdminname());
        if(admininfoList.size()==0 || !admininfoList.get(0).getPassword().equals(admininfo.getPassword()))
            return  null;
        return admininfoList.get(0);
    }
}
