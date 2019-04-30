package com.example.login.algorithm.service;

import com.example.login.submit.dao.entity.Submitinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface SubmitWordMapper {

    @PostConstruct
    public void writeWordIntoFile(String submitcontent,int userid) throws IOException;

    @PostConstruct
    public Boolean preHandleWordFile(String fileurl,String filename) throws IOException;

    @PostConstruct
    public String  judgeIserror(Integer userid) throws IOException;

}