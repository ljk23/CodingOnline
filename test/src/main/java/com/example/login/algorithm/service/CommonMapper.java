package com.example.login.algorithm.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface CommonMapper {

    @PostConstruct
    public String readFile(String filepath,String value) throws IOException;

    @PostConstruct
    public void reWriteFile(String filepath,String value) throws IOException;

}