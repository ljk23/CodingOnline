package com.example.login.algorithm.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface SubmitSynaxMapper {

    @PostConstruct
    public String Synax(String str,Integer filenameid);

    @PostConstruct
    public String IsError(Integer filenameid) throws Exception;
}