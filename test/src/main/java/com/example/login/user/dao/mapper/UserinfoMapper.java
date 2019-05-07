package com.example.login.user.dao.mapper;

import com.example.login.user.dao.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserinfoMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);

    @Select("select * from userInfo")
    List<Userinfo> selectAllUsers();

    @Select("Select * from UserInfo where userName=#{username}")
    List<Userinfo> selectUserByUsername(@Param("username") String username);

    @Select("Select * from UserInfo where userEmail=#{userEmail}")
    List<Userinfo> selectUserByEmail(@Param(("userEmail")) String userEmail);

}