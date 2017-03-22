package com.example.hin.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/20 0020.
 */
public class ExpertReply extends BmobObject {

    private Post post;
    private String replyContent;
    private BmobFile replyFile;
    private Experts replyExpert;
    private String interviewTime;

    public void setPost(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyFile(BmobFile replyFile) {
        this.replyFile = replyFile;
    }

    public BmobFile getReplyFile() {
        return replyFile;
    }

    public void setReplyExpert(Experts replyExpert) {
        this.replyExpert = replyExpert;
    }

    public Experts getReplyExpert() {
        return replyExpert;
    }

    public void setInterviewTime(String interviewTime) {
        this.interviewTime = interviewTime;
    }

    public String getInterviewTime() {
        return interviewTime;
    }
}
