package com.example.hin.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject implements Serializable {

    /**

     */
    private static final long serialVersionUID = -6280656428527540320L;

    private User author;
    private String content;
    private BmobFile Contentfigureurl;
    private String title;
    private String topic;
    private String expert;
    private int exigency;//紧急程度
    private boolean interview;//面谈
    private boolean open;//问题公开
    private boolean anonymity;//匿名
    private String kind;
    private int love;
    private int comment;
    private boolean isPass;
    private boolean myFav;//收藏
    private boolean myLove;//赞
    private int commentCount = 0;//评论数
    private BmobRelation like;
    private String sendPeople;
    private ExpertReply expertReply;

    public void setExpertReply(ExpertReply expertReply) {
        this.expertReply = expertReply;
    }

    public ExpertReply getExpertReply() {
        return expertReply;
    }

    public void setSendPeople(String sendPeople) {
        this.sendPeople = sendPeople;
    }

    public String getSendPeople() {
        return sendPeople;
    }


    public int getExigency() {
        return exigency;
    }

    public void setExigency(int exigency) {
        this.exigency = exigency;
    }

    public boolean getInterview() {
        return interview;
    }

    public void setInterview(boolean interview) {
        this.interview = interview;
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public BmobRelation getLike() {
        return like;
    }

    public void setLike(BmobRelation like) {
        this.like = like;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getContentfigureurl() {
        return Contentfigureurl;
    }

    public void setContentfigureurl(BmobFile contentfigureurl) {
        Contentfigureurl = contentfigureurl;
    }

    public int getLove() {
        return love;
    }

    public void setLove(int love) {
        this.love = love;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public boolean isPass() {
        return isPass;
    }

    public void setPass(boolean isPass) {
        this.isPass = isPass;
    }

    public boolean getMyFav() {
        return myFav;
    }

    public void setMyFav(boolean myFav) {
        this.myFav = myFav;
    }

    public boolean getMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

//    @Override
//    public String toString() {
//        return "Post [author=" + author + ", content=" + content
//                + ", Contentfigureurl=" + Contentfigureurl + ", love=" + love
//                + ", hate=" + hate + ", share=" + share + ", comment="
//                + comment + ", isPass=" + isPass + ", myFav=" + myFav
//                + ", myLove=" + myLove + ",like=" + like + "]";
//    }

}
