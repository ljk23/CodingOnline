package com.example.login.algorithm.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public interface SubmitSynaxMapper {

    @PostConstruct
    public String IsSynax(String str);

}