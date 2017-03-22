package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.adapter.CommentListAdapter;
import com.example.hin.entity.Comment;
import com.example.hin.entity.ExpertReply;
import com.example.hin.entity.Post;
import com.example.hin.entity.Reply;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.example.hin.ui.widget.ListViewForScrollView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by WWF on 2016/6/5.
 */
public class QuestionDetailActivity extends Activity implements View.OnClickListener {

    private ScrollView scrollView;
    private ListViewForScrollView lvComment;
    private TextView tv_title, tv_kind, tv_topic, tv_content, tv_reply, tv_comment_count, tv_myreply, tv_zan, tv_zan_postcount;
    private CommentListAdapter adapter;
    private List<Comment> commentlist = new ArrayList<>();
    private LinearLayout commentLinear, ll_boomlinear, ll_expertreply, ll_myreply;
    private EditText commentEdit;
    private Button commentButton;
    private FrameLayout fl_comment_count;
    private LinearLayout ll_time;
    private EditText et_time, et_reply;
    private TextView tv_time;

    private int position;                //记录回复评论的索引
    private boolean isReply;
    private String reply;
    private ImageView iv_reply;
    private ImageView iv_download, iv_upload, iv_replydownload, iv_replydownload_up;
    private final int FILE_SELECT_CODE = 1;
    private String path = null;
    private HorizontalScrollView hsv_uploadfilename, hsv_downloadfilename, hsv_download_replyfilename, hsv_download_replyfilename_up;
    private RelativeLayout rl_zan, rl_fav;
    private int zan_label = -1;
    private boolean label = true;
    private Post post;
    private ExpertReply expertReply;
    private String RESULT_CODE="ISREFRESH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        parseIntent();
        findView();
        initView();
        getQuestionDetail();
        uploadComment();


    }

    public void parseIntent() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        post = (Post) bundle.getSerializable("postObject");
        post.getObjectId();
    }

    /*
      *获取控件ID
      */
    private void findView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_reply = (TextView) findViewById(R.id.tv_reply);
        scrollView = (ScrollView) findViewById(R.id.scroll_detail);
        lvComment = (ListViewForScrollView) findViewById(R.id.lv_comment);
        ll_expertreply = (LinearLayout) findViewById(R.id.ll_expertreply);
        ll_myreply = (LinearLayout) findViewById(R.id.ll_myreply);
        commentLinear = (LinearLayout) findViewById(R.id.commentLinear);
        commentEdit = (EditText) findViewById(R.id.commentEdit);
        commentButton = (Button) findViewById(R.id.commentButton);
        ll_boomlinear = (LinearLayout) findViewById(R.id.ll_boomlinear);
        fl_comment_count = (FrameLayout) findViewById(R.id.fl_comment_count);
        tv_comment_count = (TextView) findViewById(R.id.tv_comment_count);
        ll_time = (LinearLayout) findViewById(R.id.ll_time);
        et_time = (EditText) findViewById(R.id.et_time);
        et_reply = (EditText) findViewById(R.id.et_reply);
        iv_reply = (ImageView) findViewById(R.id.iv_reply);
        tv_myreply = (TextView) findViewById(R.id.tv_myrely);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_download = (ImageView) findViewById(R.id.iv_download);
        iv_upload = (ImageView) findViewById(R.id.iv_upload);
        hsv_downloadfilename = (HorizontalScrollView) findViewById(R.id.hsv_downloadfilename);
        hsv_uploadfilename = (HorizontalScrollView) findViewById(R.id.hsv_uploadfilename);
        hsv_download_replyfilename = (HorizontalScrollView) findViewById(R.id.hsv_download_replyfilename);
        hsv_download_replyfilename_up = (HorizontalScrollView) findViewById(R.id.hsv_download_replyfilename_up);
        iv_replydownload = (ImageView) findViewById(R.id.iv_replydownload);
        iv_replydownload_up = (ImageView) findViewById(R.id.iv_replydownload_up);
        rl_zan = (RelativeLayout) findViewById(R.id.rl_zan);
        rl_fav = (RelativeLayout) findViewById(R.id.rl_fav);
        tv_zan = (TextView) findViewById(R.id.tv_zan);
        tv_zan_postcount = (TextView) findViewById(R.id.tv_zan_postcount);

    }

    /*
   * 设置监听事件
   */
    private void initView() {
        scrollView.smoothScrollTo(0, 0);

        findViewById(R.id.iv_back).setOnClickListener(this);
        commentButton.setOnClickListener(this);
        findViewById(R.id.tv_comment).setOnClickListener(this);
        fl_comment_count.setOnClickListener(this);
        ll_time.setOnClickListener(this);
        iv_reply.setOnClickListener(this);
        iv_upload.setOnClickListener(this);
        iv_download.setOnClickListener(this);
        iv_replydownload.setOnClickListener(this);
        iv_replydownload_up.setOnClickListener(this);
        rl_zan.setOnClickListener(this);
        rl_fav.setOnClickListener(this);

    }

    /*
    从服务器获得问题详情
    */
    public void getQuestionDetail() {

        if (post.getExpertReply() != null) {
            BmobQuery<ExpertReply> query = new BmobQuery<ExpertReply>();
            query.include("Post");
            query.findObjects(QuestionDetailActivity.this, new FindListener<ExpertReply>() {
                @Override
                public void onSuccess(List<ExpertReply> list) {
                    ExpertReply expertReply = list.get(list.size() - 1);
                    if (expertReply.getInterviewTime() == null) {
                        tv_time.setText("");
                    } else {
                        tv_time.setText(expertReply.getInterviewTime());
                    }
                    //专家回复中添加附件
                    if (expertReply.getReplyFile() != null) {

                    }
                    if (expertReply != null && expertReply.getReplyContent() != null) {
                        tv_myreply.setText(expertReply.getReplyContent());
                        tv_reply.setText(expertReply.getReplyContent());
                    }
                    if (expertReply != null && expertReply.getInterviewTime() != null) {
                        tv_time.setText(expertReply.getInterviewTime());
                    }

                }

                @Override
                public void onError(int i, String s) {

                }
            });
        }
        tv_title.setText(post.getTitle());
        tv_kind.setText(post.getKind());
        tv_topic.setText(post.getTopic());
        tv_content.setText(post.getContent());
        tv_zan_postcount.setText("" + post.getLove());

        //点赞
        Drawable drawable;
        if (post.getMyLove()) {
            drawable = getResources().getDrawable(R.mipmap.zan);
        } else {
            drawable = getResources().getDrawable(R.mipmap.dis_zan);
        }
        // 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_zan.setCompoundDrawables(drawable, null, null, null);

        //判断接受方账号，专家可以回复，更新post内容
        if (BmobUser.getCurrentUser(QuestionDetailActivity.this).getUsername().equals(post.getSendPeople())) {

            ll_expertreply.setVisibility(View.GONE);
            ll_myreply.setVisibility(View.VISIBLE);
            ll_time.setVisibility(View.VISIBLE);

            if (null != expertReply)
                if (expertReply.getReplyContent() != null)
                    tv_myreply.setText(expertReply.getReplyContent());
        } else {
            ll_expertreply.setVisibility(View.VISIBLE);
            ll_myreply.setVisibility(View.GONE);
            ll_time.setVisibility(View.GONE);

        }

    }

    /*
    * 回复post
    * */
    public void replyPost() {
        if (post.getExpertReply() == null) {
            expertReply = new ExpertReply();
            expertReply.setReplyContent(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
            expertReply.setInterviewTime(et_time.getText().toString());

            expertReply.save(QuestionDetailActivity.this, new SaveListener() {
                @Override
                public void onSuccess() {

                    post.setExpertReply(expertReply);
                    post.getExpertReply().setReplyContent(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
                    post.getExpertReply().setInterviewTime(et_time.getText().toString());

                    if (!post.getOpen()) {
                        BmobACL acl = new BmobACL();  //创建ACL对象
                        acl.setReadAccess(post.getAuthor(), true); // 设置当前用户可写的权限
                        acl.setWriteAccess(post.getAuthor(), true);
                        acl.setReadAccess(BmobUser.getCurrentUser(QuestionDetailActivity.this), true);
                        acl.setWriteAccess(BmobUser.getCurrentUser(QuestionDetailActivity.this), true);
                        post.setACL(acl);
                    }
                    if (path != null) {
                        BmobFile bmobFile = new BmobFile(new File(path));
                        uploadFile(post, bmobFile);
                    } else {
                        post.update(QuestionDetailActivity.this, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(QuestionDetailActivity.this, "回复成功！", Toast.LENGTH_SHORT).show();
                                tv_myreply.setText(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
                                tv_time.setText(et_time.getText().toString());
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(QuestionDetailActivity.this, "回复失败！" + s, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        } else {

            if (!post.getOpen()) {
                BmobACL acl = new BmobACL();  //创建ACL对象
                acl.setReadAccess(post.getAuthor(), true); // 设置当前用户可写的权限
                acl.setWriteAccess(post.getAuthor(), true);
                acl.setReadAccess(BmobUser.getCurrentUser(QuestionDetailActivity.this), true);
                acl.setWriteAccess(BmobUser.getCurrentUser(QuestionDetailActivity.this), true);
                post.setACL(acl);
            }
            expertReply = post.getExpertReply();
            expertReply.setReplyContent(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
            expertReply.setInterviewTime(et_time.getText().toString());

            expertReply.update(QuestionDetailActivity.this, new UpdateListener() {
                @Override
                public void onSuccess() {

                    post.setExpertReply(expertReply);
                    post.getExpertReply().setReplyContent(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
                    post.getExpertReply().setInterviewTime(et_time.getText().toString());


                    if (path != null) {
                        BmobFile bmobFile = new BmobFile(new File(path));
                        uploadFile(post, bmobFile);
                    } else {
                        post.update(QuestionDetailActivity.this, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(QuestionDetailActivity.this, "回复成功！", Toast.LENGTH_SHORT).show();
                                tv_myreply.setText(tv_myreply.getText().toString() + "\n" + et_reply.getText().toString());
                                tv_time.setText(et_time.getText().toString());
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(QuestionDetailActivity.this, "回复失败！" + s, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

                @Override
                public void onFailure(int i, String s) {

                }
            });
        }


    }

    /*
    从服务器加载问题相关的评论
    */
    public void uploadComment() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        query.addWhereEqualTo("post", new BmobPointer(post));
        //希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.findObjects(QuestionDetailActivity.this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {
                commentlist = list;
                /*
                获得评论人数
                */
                tv_comment_count.setText(String.valueOf(commentlist.size()));

                adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                lvComment.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /*
    * 接受回复评论点击事件回传的handler
    */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                isReply = true;
                position = (Integer) msg.obj;
                commentLinear.setVisibility(View.VISIBLE);
                ll_boomlinear.setVisibility(View.GONE);
                onFocusChange(true);
            }

        }
    };

    /**
     * 显示或隐藏输入法
     */
    private void onFocusChange(boolean hasFocus) {
        final boolean isFocus = hasFocus;
        (new Handler()).postDelayed(new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        commentEdit.getContext().getSystemService(INPUT_METHOD_SERVICE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                if (isFocus) {
                    //显示输入法
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //隐藏输入法
                    imm.hideSoftInputFromWindow(commentEdit.getWindowToken(), 0);
                }
            }
        }, 100);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                showCommentDialog();
                break;
            case R.id.commentButton:
                if (isEditEmply()) {
                    uploadCommengReply();
                }
                commentLinear.setVisibility(View.GONE);
                ll_boomlinear.setVisibility(View.VISIBLE);
                onFocusChange(false);
                break;
            case R.id.fl_comment_count:
                scrollView.smoothScrollTo((int) lvComment.getX(), (int) lvComment.getY());
                break;
            case R.id.ll_time:
                et_time.setFocusableInTouchMode(true);
                et_time.setFocusable(true);
                break;
            case R.id.iv_reply:
                replyPost();
                break;
            case R.id.iv_download:
                download();
                break;
            case R.id.iv_upload:
                new android.app.AlertDialog.Builder(this).setItems(new String[]{"上传图片", "上传文档"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                showFileChooser(0);
                                break;
                            case 1:
                                showFileChooser(1);
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.iv_replydownload:
                downloadReply(hsv_download_replyfilename);
                downloadReply(hsv_download_replyfilename_up);
                break;
            case R.id.rl_zan:
                setZan();
                break;
            case R.id.rl_fav:
                break;
            default:
                break;
        }
    }

    /**
     * 判断对话框中是否输入内容
     */
    private boolean isEditEmply() {
        reply = commentEdit.getText().toString().trim();
        if (reply.equals("")) {
            Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        commentEdit.setText("");
        return true;
    }

    /*
    显示评论弹窗
    */
    public void showCommentDialog() {
        onFocusChange(true);
        final EditText editText = new EditText(this);
        editText.setHint("忍不住说点什么");
        editText.setHintTextColor(getResources().getColor(android.R.color.darker_gray));
        editText.setHeight(150);
        new AlertDialog.Builder(this).setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadComment(editText);
                onFocusChange(false);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onFocusChange(false);
            }
        }).show();
    }

    /*
    * 将评论上传到服务器
    */
    public void uploadComment(EditText editText) {

        post.getObjectId();
        final Comment comment = new Comment();
        if (!editText.getText().toString().equals("")) {
            comment.setUser(BmobUser.getCurrentUser(QuestionDetailActivity.this, User.class));
            comment.setCommentContent(editText.getText().toString());
            comment.setPost(post);
            // comment.setUser(user);
            comment.save(QuestionDetailActivity.this, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(QuestionDetailActivity.this, "评论以发表！", Toast.LENGTH_SHORT).show();
                            commentlist.add(comment);
                            /*
                            更新评论人数
                            */
                            tv_comment_count.setText(String.valueOf(commentlist.size()));
                            post.setCommentCount(commentlist.size());
                            post.update(QuestionDetailActivity.this, post.getObjectId(), new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(QuestionDetailActivity.this, "评论人数！", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(QuestionDetailActivity.this, "评论人数更新失败！", Toast.LENGTH_SHORT).show();
                                }
                            });

                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            } else {
                                adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                                lvComment.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(QuestionDetailActivity.this, "评论发表失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        } else {
            Toast.makeText(QuestionDetailActivity.this, "评论内容不能为空啊！", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    将评论的回复上传到服务器
    */
    public void uploadCommengReply() {


        BmobQuery<Comment> query = new BmobQuery<>();
        query.include("user");
        query.findObjects(QuestionDetailActivity.this, new FindListener<Comment>() {
            @Override
            public void onSuccess(List<Comment> list) {

                User user = list.get(list.size() - 1).getUser();
                final Reply bean = new Reply();
                bean.setCommentNickname(user.getUsername());
                bean.setReplyNickname(BmobUser.getCurrentUser(QuestionDetailActivity.this, User.class).getUsername());
                bean.setReplyContent(reply);
                //获得回复对应的评论
                Comment comment = commentlist.get(position);
                if (comment == null || comment.getReplyList() == null) {
                    List<Reply> replyList = new ArrayList<>();
                    comment.setReplyList(replyList);
                }
                List<Reply> commentList = comment.getReplyList();
                commentList.add(bean);
                comment.setReplyList(commentList);
                comment.setReplyCount(commentList.size());
                String id = (String) comment.getObjectId();
                comment.update(QuestionDetailActivity.this, id, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(QuestionDetailActivity.this, "回复发表成功！", Toast.LENGTH_SHORT).show();
                        if (adapter == null) {
                            adapter = new CommentListAdapter(QuestionDetailActivity.this, commentlist, handler);
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(QuestionDetailActivity.this, "回复发表失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

     /*文件上传*/

    /*打开文件选择器*/
    private void showFileChooser(int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (code == 0)
            intent.setType("*/*");
        else intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    /*获得文件路径*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    //只能上传一个图片
                    hsv_uploadfilename.removeAllViews();
                    Uri uri = data.getData();
                    path = com.example.hin.utils.FileUtils.getPath(this, uri);
                    TextView picPath = new TextView(QuestionDetailActivity.this);
                    picPath.setText(path);
                    hsv_uploadfilename.addView(picPath);
                    Toast.makeText(QuestionDetailActivity.this, path, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*文件上传*/
    public void uploadFile(final Post post, final BmobFile bmobFile) {

        bmobFile.uploadblock(QuestionDetailActivity.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                post.getExpertReply().setReplyFile(bmobFile);
                Toast.makeText(QuestionDetailActivity.this, "上传文件成功:" + bmobFile.getFileUrl(QuestionDetailActivity.this), Toast.LENGTH_SHORT).show();
                post.update(QuestionDetailActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(QuestionDetailActivity.this, "success", Toast.LENGTH_SHORT).show();
                        tv_myreply.setText(tv_myreply.getText().toString() + "\\r\\n" + et_reply.getText().toString());
                        tv_time.setText(et_time.getText().toString());

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(QuestionDetailActivity.this, "onFailure:" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(QuestionDetailActivity.this, "上传文件失败:" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });
    }

    /*
    *
    * 文件下载*/


    public void download() {
         /*获得文件下载的实例*/
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.findObjects(QuestionDetailActivity.this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list.size() > 0) {
                    for (Post post : list) {
                        BmobFile bmobfile = post.getContentfigureurl();
                        if (bmobfile != null) {
                            //调用bmobfile.download方法

                            File saveFile = new File(Environment.getExternalStorageDirectory(), bmobfile.getFilename());
                            bmobfile.download(QuestionDetailActivity.this, saveFile, new DownloadFileListener() {
                                @Override
                                public void onSuccess(String s) {
                                    hsv_downloadfilename.removeAllViews();
                                    Toast.makeText(QuestionDetailActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                                    final TextView picPath = new TextView(QuestionDetailActivity.this);
                                    picPath.setText(s.toString());
                                    hsv_downloadfilename.addView(picPath);
                                    picPath.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            File file = new File(picPath.getText().toString());
                                            if (file != null && file.isFile() == true) {
                                                Intent intent = new Intent();
                                                intent.setAction(android.content.Intent.ACTION_VIEW);
                                                intent.setDataAndType(Uri.fromFile(file), "image/*");
                                                QuestionDetailActivity.this.startActivity(intent);
                                            }

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(QuestionDetailActivity.this, "下载失败", Toast.LENGTH_SHORT).show();

                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onError(int i, String s) {

                Toast.makeText(QuestionDetailActivity.this, "没有该文件" + s, Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void downloadReply(final HorizontalScrollView h) {
         /*获得文件下载的实例*/
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.findObjects(QuestionDetailActivity.this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list.size() > 0) {
                    for (Post post : list) {
                        if (null != post.getExpertReply()) {

                            //先获取pointer指向内容
                            BmobQuery<ExpertReply> query = new BmobQuery<ExpertReply>();
                            query.include("Post");
                            query.findObjects(QuestionDetailActivity.this, new FindListener<ExpertReply>() {
                                @Override
                                public void onSuccess(List<ExpertReply> list) {

                                    BmobFile bmobfile = list.get(list.size() - 1).getReplyFile();
                                    if (bmobfile != null) {

                                        //调用bmobfile.download方法

                                        File saveFile = new File(Environment.getExternalStorageDirectory(), bmobfile.getFilename());
                                        bmobfile.download(QuestionDetailActivity.this, saveFile, new DownloadFileListener() {
                                            @Override
                                            public void onSuccess(String s) {
                                                h.removeAllViews();
                                                Toast.makeText(QuestionDetailActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                                                final TextView picPath = new TextView(QuestionDetailActivity.this);
                                                picPath.setText(s.toString());
                                                h.addView(picPath);
                                                picPath.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        File file = new File(picPath.getText().toString());
                                                        if (file != null && file.isFile() == true) {
                                                            Intent intent = new Intent();
                                                            intent.setAction(android.content.Intent.ACTION_VIEW);
                                                            intent.setDataAndType(Uri.fromFile(file), "image/*");
                                                            QuestionDetailActivity.this.startActivity(intent);
                                                        }
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {
                                                Toast.makeText(QuestionDetailActivity.this, "下载失败", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onError(int i, String s) {

                                }
                            });


                        }

                    }
                }
            }

            @Override
            public void onError(int i, String s) {

                Toast.makeText(QuestionDetailActivity.this, "没有改文件" + s, Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*
    * 点赞功能*/
    public void setZan() {
        BmobQuery<User> query = new BmobQuery<User>();
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("like", new BmobPointer(post));
        query.findObjects(QuestionDetailActivity.this, new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        int i;
                        for (i = 0; i < list.size(); i++) {
                            if (list.get(i).getObjectId().equals(BmobUser.getCurrentUser(QuestionDetailActivity.this).getObjectId())) {
                                label = false;
                                BmobUser user = BmobUser.getCurrentUser(QuestionDetailActivity.this);
                                BmobRelation relation = new BmobRelation();
                                relation.remove(user);
                                post.setLike(relation);
                                post.setMyLove(false);
                                post.setLove((Integer.parseInt(tv_zan_postcount.getText().toString())) - 1);
                                post.update(QuestionDetailActivity.this, new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(QuestionDetailActivity.this, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                        Drawable drawable;
                                        drawable = getResources().getDrawable(R.mipmap.dis_zan);
                                        if ((Integer.parseInt(tv_zan_postcount.getText().toString())) > 0)
                                            /// 这一步必须要做,否则不会显示.
                                            tv_zan_postcount.setText("" + ((Integer.parseInt(tv_zan_postcount.getText().toString())) - 1));
                                        /// 这一步必须要做,否则不会显示.
                                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                        tv_zan.setCompoundDrawables(drawable, null, null, null);
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {
                                        Toast.makeText(QuestionDetailActivity.this, "取消点赞失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            }
                        }

                        if (label || i == list.size()) {
                            //User user = BmobUser.getCurrentUser(context, User.class);
                            // 查询喜欢这个帖子的所有用户，因此查询的是用户表
                            BmobUser user = new BmobUser();
                            user.setObjectId(BmobUser.getCurrentUser(QuestionDetailActivity.this).getObjectId());
                            //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                            BmobRelation like = new BmobRelation();
                            //将当前用户添加到多对多关联中
                            like.add(user);
                            post.setLike(like);
                            post.setMyLove(true);
                            post.setLove((Integer.parseInt(tv_zan_postcount.getText().toString())) + 1);
                            post.update(QuestionDetailActivity.this, new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(QuestionDetailActivity.this, "点赞成功！", Toast.LENGTH_SHORT).show();
                                    Drawable drawable;
                                    drawable = getResources().getDrawable(R.mipmap.zan);
                                    tv_zan_postcount.setText("" + ((Integer.parseInt(tv_zan_postcount.getText().toString())) + 1));
                                    /// 这一步必须要做,否则不会显示.
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    tv_zan.setCompoundDrawables(drawable, null, null, null);
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(QuestionDetailActivity.this, "点赞失败！" + s, Toast.LENGTH_SHORT).show();

                                }
                            });


                        }

                    }

                    @Override
                    public void onError(int i, String s) {

                        Toast.makeText(QuestionDetailActivity.this, "点赞失败！" + s, Toast.LENGTH_SHORT).show();

                    }
                }

        );

    }


}