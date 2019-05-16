package com.example.login.submit.dao.entity;

import org.springframework.web.multipart.MultipartFile;

public class Submitmeg extends Submitinfo{

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "Submitmeg{" +
                "file=" + file +
                '}';
    }

    private MultipartFile file;
}
