package com.example.hin.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.entity.Comment;
import com.example.hin.entity.Post;
import com.example.hin.entity.Reply;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by WWF on 2016/6/6.
 */
public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private List<Comment> commentList;
    private boolean label = true;

    private Handler handler, zan_handler;


    public CommentListAdapter(Context context, List<Comment> commentList, Handler
            handler) {
        this.context = context;
        this.commentList = commentList;
        this.handler = handler;

    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder = new ViewHolder();
        Comment comment = commentList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
        }

        holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
        //     holder.tvName.setText(commentList.get(position).getUser().getSignature());
        holder.tvZan = (TextView) convertView.findViewById(R.id.tv_zan_count);
        holder.tvComment = (TextView) convertView.findViewById(R.id.tv_comment_text);
        holder.tv_zan_count = (TextView) convertView.findViewById(R.id.tv_zan_count);
        holder.lv_sv_commentComment = (ListViewForScrollView) convertView.findViewById(R.id.lv_sv_reply);
        holder.iv_comment = (ImageView) convertView.findViewById(R.id.iv_comment);
        holder.iv_zan = (ImageView) convertView.findViewById(R.id.iv_zan);
        holder.tv_reply_count = (TextView) convertView.findViewById(R.id.tv_reply_count);
        holder.sdv_head = (SimpleDraweeView) convertView.findViewById(R.id.sdv_head);

        TextviewClickListener textviewClickListener = new TextviewClickListener(position, holder);

        if (comment.getReplyList() == null) {
            List<Reply> replyList = new ArrayList<>();
            comment.setReplyList(replyList);
        }
        ReplyAdapter adapter = new ReplyAdapter(context, comment.getReplyList());
        holder.lv_sv_commentComment.setAdapter(adapter);

        holder.iv_comment.setOnClickListener(textviewClickListener);
        holder.iv_zan.setOnClickListener(textviewClickListener);

        holder.tv_zan_count.setText(String.valueOf(commentList.get(position).getMyLoveNumber()));
        holder.tvComment.setText(commentList.get(position).getCommentContent());
        holder.tv_reply_count.setText(String.valueOf(commentList.get(position).getReplyCount()));

        //设置视图
        BmobQuery<Comment> query = new BmobQuery<>();
        query.include("user");
        query.findObjects(context, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                User user = list.get(list.size() - 1).getUser();
                if (user.getAvatar() != null) {
                    holder.sdv_head.setImageURI(user.getAvatar());
                    holder.tvName.setText(user.getUsername());
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        return convertView;
    }

    private class ViewHolder {
        public TextView tvName, tvZan, tvComment, tv_zan_count, tv_reply_count;
        private ImageView iv_zan, iv_comment;
        private ListViewForScrollView lv_sv_commentComment;
        private SimpleDraweeView sdv_head;
    }

    /**
     * 获取回复评论
     */
    public void getReplyComment(Reply bean, int position) {
        List<Reply> rList = commentList.get(position).getReplyList();
        rList.add(rList.size(), bean);
    }

    /*
    * 点赞
    */
    public void dianZan(final ViewHolder holder, final int position) {


        BmobQuery<User> query = new BmobQuery<User>();
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("like", new BmobPointer(commentList.get(position)));
        query.findObjects(context, new FindListener<User>() {

            @Override
            public void onSuccess(List<User> list) {

                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getObjectId().equals(BmobUser.getCurrentUser(context).getObjectId())) {
                        Toast.makeText(context, "你已经赞过了哦！", Toast.LENGTH_SHORT).show();
                        label = false;
                    }
                }
                if (label) {
                    //User user = BmobUser.getCurrentUser(context, User.class);
                    // 查询喜欢这个帖子的所有用户，因此查询的是用户表
                    User user = new User();
                    user.setObjectId(BmobUser.getCurrentUser(context).getObjectId());
                    //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                    BmobRelation like = new BmobRelation();
                    //将当前用户添加到多对多关联中
                    like.add(user);
                    //多对多关联指向`post`的`likes`字段
                    commentList.get(position).setLike(like);
                    commentList.get(position).setMyLoveNumber(commentList.get(position).getMyLoveNumber() + 1);
                    commentList.get(position).setMyLove(true);
                    commentList.get(position).update(context, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(context, "点赞成功！", Toast.LENGTH_SHORT).show();
                                    holder.tv_zan_count.setText(String.valueOf(commentList.get(position).getMyLoveNumber()));
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(context, "点赞失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });

        holder.tv_zan_count.setText(String.valueOf(commentList.get(position).getMyLoveNumber()));
        Log.i("comment", String.valueOf(commentList.get(position).getMyLoveNumber()));
        label = true;
    }

    private final class TextviewClickListener implements View.OnClickListener {
        private int position;
        private ViewHolder viewHolder;

        public TextviewClickListener(int position, ViewHolder viewHolder) {
            this.position = position;
            this.viewHolder = viewHolder;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_comment:
                    handler.sendMessage(handler.obtainMessage(10, position));
                    break;
                case R.id.iv_zan:
                    dianZan(viewHolder, position);
                    break;
                default:
                    break;
            }
        }
    }
}
