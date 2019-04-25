package com.example.login.user.controller;

import com.example.login.user.dao.entity.User;
import com.example.login.user.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value="user/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @CrossOrigin
    @PostMapping("login")
    public User login(@RequestBody User user){
        List<User> userList=  userMapper.selectUserByUsername(user.getUsername());
        if(userList.size()==0|| !userList.get(0).getPassword().equals(user.getPassword()))  return null;

       return  userList.get(0);
    }

    @PostMapping("/register")
    public Integer register(@RequestBody User user){
        userMapper.insertSelective(user);
        if(user.getId()!=null)
            return user.getId();
        return 0;
    }

    @GetMapping("/delete")
    public Boolean delete(@RequestParam("id") Integer id){
        if(id!=null && userMapper.deleteByPrimaryKey(id)>0)
            return true;
        return false;
    }

    @GetMapping("/list")
    public List<User> list(){
        List<User> userlist=userMapper.selectAllUser();
        return userlist;
    }

    @GetMapping("/judgeIsUserExist")
    public Boolean judgeIsUserExist(@RequestParam("username") String username){
        if(userMapper.selectUserByUsername(username).size()>0)
            return true;
          return false;
    }
}
