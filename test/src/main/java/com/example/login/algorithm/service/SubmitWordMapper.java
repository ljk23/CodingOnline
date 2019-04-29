package com.example.login.algorithm.service;

import com.example.login.submit.dao.entity.Submitinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface SubmitWordMapper {

    @PostConstruct
    public Boolean writeWordIntoFile(String submitcontent) throws IOException;

    @PostConstruct
    public Boolean preHandleWordFile(String fileurl,String filename) throws IOException;

}