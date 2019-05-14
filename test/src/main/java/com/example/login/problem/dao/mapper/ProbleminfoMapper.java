package com.example.login.problem.dao.mapper;

import com.example.login.problem.dao.entity.Probleminfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProbleminfoMapper {
    int deleteByPrimaryKey(Integer problemid);

    int insert(Probleminfo record);

    int insertSelective(Probleminfo record);

    Probleminfo selectByPrimaryKey(Integer problemid);

    @Select("Select * from problemInfo where problemClassfy=#{problemclassfy}")
    List<Probleminfo> selectByProblemClassfy(@Param("problemclassfy") String problemclassfy);

    @Select("Select * from problemInfo where problemName=#{problemname}")
    Probleminfo selectByProblemName(@Param("problemname")String problemname);

    @Select("Select * from problemInfo")
    List<Probleminfo> selectAll();

    int updateByPrimaryKeySelective(Probleminfo record);

    int updateByPrimaryKey(Probleminfo record);

    List<Probleminfo> selectByQuery(Probleminfo record);
}