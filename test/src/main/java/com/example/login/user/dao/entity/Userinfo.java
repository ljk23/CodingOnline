package com.example.login.user.dao.entity;

public class Userinfo {
    private Integer userid;

    private String username;

    private String password;

    private String useremail;

    private String userschool;

    private Boolean usergender;

    private Integer userlevel;

    private String usergrader;

    private String registertime;

    private String useridentity;

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

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail == null ? null : useremail.trim();
    }

    public String getUserschool() {
        return userschool;
    }

    public void setUserschool(String userschool) {
        this.userschool = userschool == null ? null : userschool.trim();
    }

    public Boolean getUsergender() {
        return usergender;
    }

    public void setUsergender(Boolean usergender) {
        this.usergender = usergender;
    }

    public Integer getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(Integer userlevel) {
        this.userlevel = userlevel;
    }

    public String getUsergrader() {
        return usergrader;
    }

    public void setUsergrader(String usergrader) {
        this.usergrader = usergrader == null ? null : usergrader.trim();
    }

    public String getRegistertime() {
        return registertime;
    }

    public void setRegistertime(String registertime) {
        this.registertime = registertime == null ? null : registertime.trim();
    }

    public String getUseridentity() {
        return useridentity;
    }

    public void setUseridentity(String useridentity) {
        this.useridentity = useridentity == null ? null : useridentity.trim();
    }
}