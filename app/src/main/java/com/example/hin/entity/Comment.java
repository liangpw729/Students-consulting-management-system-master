package com.example.hin.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Comment extends BmobObject {

    public static final String TAG = "Comment";

    private User user;
    private String commentContent;
    private Post post;
    private int myLoveNumber = 0;//赞的次数
    private boolean myLove = false;//赞
    private BmobRelation like;
    private List<Reply> replyList;
    private int replyCount=0; //评论次数


    public BmobRelation getLike() {
        return like;
    }

    public void setLike(BmobRelation like) {
        this.like = like;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getMyLoveNumber() {
        return myLoveNumber;
    }

    public void setMyLoveNumber(int myLoveNumber) {
        this.myLoveNumber = myLoveNumber;
    }

    public boolean getMyLove() {
        return myLove;
    }

    public void setMyLove(boolean myLove) {
        this.myLove = myLove;
    }

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public int getReplyCount() {
        return replyCount;
    }
}
