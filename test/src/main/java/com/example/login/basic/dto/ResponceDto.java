package com.example.login.basic.dto;

public class ResponceDto<T> {
    private T t;
    private int code;
    private String message;

    public ResponceDto(T t){
        this.t=t;
        this.code=1;
        this.message="success";
    }
    public ResponceDto(){
        this.code=1;
        this.message="success";
    }

}
