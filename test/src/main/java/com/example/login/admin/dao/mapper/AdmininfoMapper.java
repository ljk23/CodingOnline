package com.example.login.admin.dao.mapper;

import com.example.login.admin.dao.entity.Admininfo;
import com.example.login.user.dao.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdmininfoMapper {
    int deleteByPrimaryKey(Integer adminid);

    int insert(Admininfo record);

    int insertSelective(Admininfo record);

    Admininfo selectByPrimaryKey(Integer adminid);

    int updateByPrimaryKeySelective(Admininfo record);

    int updateByPrimaryKey(Admininfo record);

    @Select("Select * from adminInfo where adminName=#{adminName}")
    List<Admininfo> selectByAdminName(@Param(("adminName")) String adminName);
}