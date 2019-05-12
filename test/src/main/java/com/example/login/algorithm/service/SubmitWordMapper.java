package com.example.login.algorithm.service;

import com.example.login.submit.dao.entity.Submitinfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @PostConstruct
    public String uploadWordCode(MultipartFile file);

    @PostConstruct
    public Boolean UnZip(String filename,String filepath) throws IOException;

    @PostConstruct
    public Boolean Compiler(String filepath) throws IOException;

    @PostConstruct
    public Boolean CompilerCpp(String filepath) throws IOException;

    @PostConstruct
    public Boolean CompilerFiles(String filepath) throws IOException;

    @PostConstruct
    public void deleteCode(String filename);

    @PostConstruct
    public void deleteFiles(String filename) throws Exception;

    @PostConstruct
    public Boolean Answer(Integer problemid,String filename) throws IOException;

    @PostConstruct
    public String WriteCideIntoFile(String contents,String codingLanguage) throws IOException;

    @PostConstruct
    public Integer JudgeResult(String codingFile) throws IOException;

}