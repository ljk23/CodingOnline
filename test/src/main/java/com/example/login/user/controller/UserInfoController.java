package com.example.login.user.controller;

import com.example.login.user.dao.entity.Userinfo;
import com.example.login.user.dao.mapper.UserinfoMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="userInfo/")
public class UserInfoController {

    @Autowired
    private UserinfoMapper userinfoMapper;  /**   */

    @PostMapping("login")    /** Post接受传值的方式是Post */
    public Userinfo login(@RequestBody Userinfo userinfo){
        /**  通过@requestBody可以将请求体中的JSON字符串绑定到相应的bean上  */
        if(userinfo.getUseremail()!=null){
            /**  email login */
            System.out.print(userinfo.getUseremail());
            List<Userinfo> userinfoList=userinfoMapper.selectUserByEmail(userinfo.getUseremail());
            if(userinfoList.size()==0 || !userinfoList.get(0).getPassword().equals(userinfo.getPassword()))
                return  null;
            return userinfoList.get(0);
        }
        if(userinfo.getUsername()!=null){
            /**  username login */
            List<Userinfo> userinfoList=userinfoMapper.selectUserByUsername(userinfo.getUsername());
            if(userinfoList.size()==0 || !userinfoList.get(0).getPassword().equals(userinfo.getPassword()))
                return  null;
            return userinfoList.get(0);
        }
            return null;
    }
    /**  IsUserExist */
    @GetMapping("/judgeIsUserExist")
    public Boolean judgeIsUserExist(@Param("username") String username){
        if(userinfoMapper.selectUserByUsername(username).size()>0)
            return true;
        return false;
    }
    /**  Regisrer */
    @PostMapping("/register")
    public Integer register(@RequestBody Userinfo userinfo){
        userinfoMapper.insertSelective(userinfo);
        if(userinfo.getUserid()!=null)
            return userinfo.getUserid();
        return 0;
    }

    @GetMapping("/hello")
    public String helloword(){
        return "helloword";
    }
}
