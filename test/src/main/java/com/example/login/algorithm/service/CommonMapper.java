package com.example.login.algorithm.service;

import com.example.login.submit.dao.entity.Submitinfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public interface CommonMapper {

    @PostConstruct
    public String readFile(String filepath,String value) throws IOException;

    @PostConstruct
    public void reWriteFile(String filepath,String value) throws IOException;

    @PostMapping
    public List<String> getResult(Submitinfo submitinfo,Integer result,List<String> list,String value);

    @PostMapping
    public void clearFile(String filepath);
}