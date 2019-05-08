package com.example.login.algorithm.service;

import com.example.login.submit.dao.entity.Submitinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface SubmitWordMapper {

    @PostConstruct
    public String writeWordIntoFile(String submitcontent,int userid,String wordresult) throws IOException;

    @PostConstruct
    public Boolean preHandleWordFile(String fileurl,String filename) throws IOException;

    @PostConstruct
    public String  judgeIserror(Integer userid) throws IOException;

}