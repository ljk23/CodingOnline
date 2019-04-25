package com.example.login.problem.dao.entity;

public class Probleminfo {
    private Integer problemid;

    private String problemname;

    private String problemclassfy;

    private String problemauthor;

    private String problemcontent;

    private Boolean problemstate;

    private String probleminput;

    private String problemoutput;

    private String problemlevel;

    private String uploadtime;

    public Integer getProblemid() {
        return problemid;
    }

    public void setProblemid(Integer problemid) {
        this.problemid = problemid;
    }

    public String getProblemname() {
        return problemname;
    }

    public void setProblemname(String problemname) {
        this.problemname = problemname == null ? null : problemname.trim();
    }

    public String getProblemclassfy() {
        return problemclassfy;
    }

    public void setProblemclassfy(String problemclassfy) {
        this.problemclassfy = problemclassfy == null ? null : problemclassfy.trim();
    }

    public String getProblemauthor() {
        return problemauthor;
    }

    public void setProblemauthor(String problemauthor) {
        this.problemauthor = problemauthor == null ? null : problemauthor.trim();
    }

    public String getProblemcontent() {
        return problemcontent;
    }

    public void setProblemcontent(String problemcontent) {
        this.problemcontent = problemcontent == null ? null : problemcontent.trim();
    }

    public Boolean getProblemstate() {
        return problemstate;
    }

    public void setProblemstate(Boolean problemstate) {
        this.problemstate = problemstate;
    }

    public String getProbleminput() {
        return probleminput;
    }

    public void setProbleminput(String probleminput) {
        this.probleminput = probleminput == null ? null : probleminput.trim();
    }

    public String getProblemoutput() {
        return problemoutput;
    }

    public void setProblemoutput(String problemoutput) {
        this.problemoutput = problemoutput == null ? null : problemoutput.trim();
    }

    public String getProblemlevel() {
        return problemlevel;
    }

    public void setProblemlevel(String problemlevel) {
        this.problemlevel = problemlevel == null ? null : problemlevel.trim();
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime == null ? null : uploadtime.trim();
    }
}