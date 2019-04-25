package com.example.login.user.dao.entity;

import java.util.Date;

public class Userinfo {
    private Integer userid;

    private String username;

    private String password;

    private String useremail;

    private String userschool;

    private Byte usergender;

    private Integer userlevel;

    private Integer usergrader;

    private Date registertime;


    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserschool() {
        return userschool;
    }

    public void setUserschool(String userschool) {
        this.userschool = userschool == null ? null : userschool.trim();
    }

    public Byte getUsergender() {
        return usergender;
    }

    public void setUsergender(Byte usergender) {
        this.usergender = usergender;
    }

    public Integer getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }

    public Integer getUsergrader() {
        return usergrader;
    }

    public void setUsergrader(Integer usergrader) {
        this.usergrader = usergrader;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail == null ? null : useremail.trim();
    }
}