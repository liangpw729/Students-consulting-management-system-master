package com.example.hin.entity;

import android.graphics.Point;

import com.example.hin.ui.widget.CircleImageView;

import java.util.Objects;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Experts extends BmobObject {

    public static final String TAG = "Experts";

    private String avatar;  //头像
    private String name;      //姓名
    private String sex;      //性别
    private String degree;  //学位
    private String title;  //职称
    private String organization; //工作单位
    private String study;     //研究领域
    private String achievement; //研究成果
    private Integer consultCount;//咨询次数
    private Integer rank;       //学生评价
    private String kind;
    private String username;

    public String getUsername(){
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getStudy() {
        return study;
    }

    public void setStudy(String study) {
        this.study = study;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public Integer getConsultCount() {
        return consultCount;
    }

    public void setConsultCount(Integer consultCount) {
        this.consultCount = consultCount;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}