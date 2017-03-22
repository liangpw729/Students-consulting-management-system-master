package com.example.hin.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.Consts.LocalStringConst;
import com.example.hin.adapter.ExpertCommonQuestionAdapter;
import com.example.hin.entity.ExpertReply;
import com.example.hin.entity.Experts;
import com.example.hin.entity.Post;
import com.example.hin.entity.User;
import com.example.hin.system.R;
import com.example.hin.ui.widget.SpinnerWindow.AbstractSpinerAdapter;
import com.example.hin.ui.widget.SpinnerWindow.SpinerPopWindow;
import com.example.hin.ui.widget.SpinnerWindow.SpinnerAdapter;
import com.facebook.common.file.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobACL;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by WWF on 2016/6/4.
 */
public class ConsultActivity extends Activity implements View.OnClickListener {

    private TextView tvExpert;
    private AbstractSpinerAdapter adapter, kindAdapter, topicAdapter;
    private Experts experts;
    private List<Experts> expertsList = new ArrayList<Experts>();
    private List<String> name = new ArrayList<String>();
    private List<String> kind = new ArrayList<String>();
    private List<String> topic = new ArrayList<String>();
    private SpinerPopWindow mSpinerPopWindow, kindSpinerPopWindow, topicSpinerPopWindow;
    private int star[] = {R.id.iv_star_one, R.id.iv_star_two, R.id.iv_star_three};
    int flag;
    private EditText et_title, et_content;
    private TextView tv_kind, tv_topic, tv_expert;
    private CheckBox cb_chat, cb_open, cb_anonymity;
    private final int PHOTO_SELECT_CODE = 0;
    private final int FILE_SELECT_CODE = 1;
    private String path = null;
    private HorizontalScrollView hsv_filename;
    private String expertAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        parseIntent();
        initView();
        getEepert(kind.get(0));
    }


    public void parseIntent() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null)
            experts = (Experts) bundle.getSerializable("experts");
    }

    public void getEepert(String kind) {
        if (experts != null) {
            tv_kind.setText(experts.getKind());
            tv_expert.setText(experts.getName());
        } else {

            //默认选择expert
            mSpinerPopWindow = new SpinerPopWindow(ConsultActivity.this);
            adapter = new SpinnerAdapter(ConsultActivity.this);
            BmobQuery<Experts> query = new BmobQuery<Experts>();
            query.addWhereEqualTo("kind", kind);
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
            //执行查询方法
            query.findObjects(this, new FindListener<Experts>() {

                @Override
                public void onSuccess(List<Experts> list) {
                    if (list.size() > 0) {
                        name.clear();
                        Toast.makeText(ConsultActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                        expertsList = list;
                        int length = list.size();
                        int i = 0;
                        while (i < length) {
                            name.add(expertsList.get(i).getName());
                            i++;
                        }
                        experts = expertsList.get(0);
                        adapter.refreshData(name, 0);
                        mSpinerPopWindow.setAdatper(adapter);
                        if (name.size() > 0)
                            tv_expert.setText(name.get(0));

                    } else {
                        Toast.makeText(ConsultActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(int i, String s) {
                    Toast.makeText(ConsultActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void initView() {
        findViewById(R.id.iv_upload).setOnClickListener(this);
        cb_chat = (CheckBox) findViewById(R.id.cb_chat);
        cb_open = (CheckBox) findViewById(R.id.cb_open);
        cb_anonymity = (CheckBox) findViewById(R.id.cb_anonymity);
        tvExpert = (TextView) findViewById(R.id.tv_expert);
        tvExpert.setOnClickListener(this);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_kind.setOnClickListener(this);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_topic.setOnClickListener(this);
        hsv_filename = (HorizontalScrollView) findViewById(R.id.hsv_filename);
        findViewById(R.id.iv_back).setOnClickListener(this);
        for (int i : star) {
            findViewById(i).setOnClickListener(this);
        }

        //类别栏目
        kind.add("课程答疑");
        kind.add("人际交往");
        kind.add("身心健康");
        kind.add("就业招聘");
        kind.add("发展规划");
        kind.add("其他");
        kindSpinerPopWindow = new SpinerPopWindow(this);
        kindAdapter = new SpinnerAdapter(this);
        kindAdapter.refreshData(kind, 0);
        kindSpinerPopWindow.setAdatper(kindAdapter);


        //话题栏目
        topic.add("高数");
        topic.add("数据结构");
        topic.add("算法设计");
        topic.add("数理统计");
        topic.add("微分方程");
        topic.add("课程设计");
        topicSpinerPopWindow = new SpinerPopWindow(this);
        topicAdapter = new SpinnerAdapter(this);
        topicAdapter.refreshData(topic, 0);
        topicSpinerPopWindow.setAdatper(topicAdapter);


        findViewById(R.id.iv_send).setOnClickListener(this);

        et_title = (EditText) findViewById(R.id.et_title);
        tv_kind = (TextView) findViewById(R.id.tv_kind);
        tv_topic = (TextView) findViewById(R.id.tv_topic);
        tv_expert = (TextView) findViewById(R.id.tv_expert);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_upload:
                new AlertDialog.Builder(this).setItems(new String[]{"上传图片", "上传文档"}, new DialogInterface.OnClickListener() {
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
            case R.id.tv_kind:
                kindSpinerPopWindow.setWidth(tvExpert.getWidth());
                kindSpinerPopWindow.showAsDropDown(tv_kind);
                kindSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                    @Override
                    public void onItemClick(int pos) {
                        tv_kind.setText(kind.get(pos).toString());
                        switch (pos) {
                            case 0:
                                getEepert("课程答疑");
                                topic.clear();
                                experts = null;
                                break;
                            case 1:
                                getEepert("人际交往");
                                topic.clear();
                                topic.add("社团工作");
                                topic.add("如何交友");
                                topic.add("宿舍友谊");
                                tv_topic.setText(topic.get(0).toString());
                                experts = null;
                                break;
                            case 2:
                                getEepert("身心健康");
                                topic.clear();
                                topic.add("如何增重");
                                topic.add("沉迷游戏");
                                tv_topic.setText(topic.get(0).toString());
                                experts = null;
                                break;
                            case 3:
                                getEepert("就业招聘");
                                topic.clear();
                                topic.add("实习");
                                topic.add("正职");
                                tv_topic.setText(topic.get(0).toString());
                                experts = null;
                                break;
                            case 4:
                                getEepert("发展规划");
                                topic.clear();
                                topic.add("考研");
                                topic.add("就业指导");
                                topic.add("以后");
                                tv_topic.setText(topic.get(0).toString());
                                experts = null;
                                break;
                            case 5:
                                getEepert("其他");
                                topic.clear();
                                topic.add("水群");
                                topic.add("wow");
                                topic.add("dnf");
                                tv_topic.setText(topic.get(0).toString());
                                experts = null;
                                break;
                        }
                    }
                });
                break;
            case R.id.tv_topic:
                topicSpinerPopWindow.setWidth(tvExpert.getWidth());
                topicSpinerPopWindow.showAsDropDown(tv_topic);
                topicSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                    @Override
                    public void onItemClick(int pos) {
                        tv_topic.setText(topic.get(pos).toString());
                    }
                });
                break;
            case R.id.tv_expert:
                mSpinerPopWindow.setWidth(tvExpert.getWidth());
                mSpinerPopWindow.showAsDropDown(tvExpert);
                mSpinerPopWindow.setItemListener(new AbstractSpinerAdapter.IOnItemSelectListener() {
                    @Override
                    public void onItemClick(int pos) {
//                        startActivityForResult(new Intent(ConsultActivity.this, ExpertsActivity.class)
//                                , LocalStringConst.REQUEST_CODE_EXPERT_VIEW);
                        tv_expert.setText(name.get(pos).toString());
                        experts = expertsList.get(pos);

                    }
                });
                break;
            case R.id.iv_star_one:
                flag = 1;
                setEmergent();
                break;
            case R.id.iv_star_two:
                flag = 2;
                setEmergent();
                break;
            case R.id.iv_star_three:
                flag = 3;
                setEmergent();
                break;
            case R.id.iv_send:
                sendPost();
                break;
            default:
                break;
        }
    }

    /*
    紧急程度星级设置
    */
    private void setEmergent() {
        int i;
        for (i = 0; i < flag; i++) {
            ((ImageView) findViewById(star[i])).setImageResource(R.mipmap.star_on);
        }
        for (; i < star.length; i++) {
            ((ImageView) findViewById(star[i])).setImageResource(R.mipmap.star);
        }
    }

    /*
    发送帖子,通过设置ACL对象来实现发送到特定对象的目的
    */
    public void sendPost() {

        if (!(et_title.getText().toString().equals(""))) {
            /*在专家库中选择咨询的专家，通过该专家的账号查询对应的专家USEA,设置该user对象唯一可读*/
            BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
            query.addWhereEqualTo("username", experts.getUsername());
            query.findObjects(ConsultActivity.this, new FindListener<BmobUser>() {

                @Override
                public void onSuccess(List<BmobUser> list) {
                    Post post = new Post();
                    BmobACL acl = new BmobACL();  //创建ACL对象
                    if (cb_open.isChecked()) {
                        acl.setPublicReadAccess(true);    // 设置所有人可读的权限
                    } else {
                        acl.setReadAccess(list.get(list.size() - 1), true); // 设置当前用户可写的权限
                        acl.setWriteAccess(list.get(list.size() - 1), true);
                        acl.setReadAccess(BmobUser.getCurrentUser(ConsultActivity.this), true);
                        acl.setWriteAccess(BmobUser.getCurrentUser(ConsultActivity.this), true);

                    }
                    post.setAuthor(BmobUser.getCurrentUser(ConsultActivity.this, User.class));
                    post.setSendPeople(list.get(list.size() - 1).getUsername());
                    post.setACL(acl);    //设置这条数据的ACL信息
                    post.setTitle(et_title.getText().toString());
                    post.setKind(tv_kind.getText().toString());
                    post.setTopic(tv_topic.getText().toString());
                    post.setExigency(flag);
                    post.setInterview(cb_chat.isChecked());
                    post.setOpen(cb_open.isChecked());
                    post.setAnonymity(cb_anonymity.isChecked());

                    if (!(et_content.getText().toString().equals(""))) {
                        post.setContent(et_content.getText().toString());
                        if (path != null) {
                            BmobFile bmobFile = new BmobFile(new File(path));
                            uploadFile(post, bmobFile);
                        } else {
                            post.save(ConsultActivity.this, new SaveListener() {
                                @Override
                                public void onSuccess() {

                                    Toast.makeText(ConsultActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(ConsultActivity.this, "onFailure:" + s, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        Toast.makeText(ConsultActivity.this, "内容不能为空哦!", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onError(int i, String s) {

                }
            });
        } else {
            Toast.makeText(ConsultActivity.this, "标题不能为空哦！", Toast.LENGTH_SHORT).show();
        }
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
                    //只能上传一张图片
                    hsv_filename.removeAllViews();
                    Uri uri = data.getData();
                    path = com.example.hin.utils.FileUtils.getPath(this, uri);
                    TextView picPath = new TextView(ConsultActivity.this);
                    picPath.setText(path);
                    hsv_filename.addView(picPath);
                    Toast.makeText(ConsultActivity.this, path, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*文件上传*/
    public void uploadFile(final Post post, final BmobFile bmobFile) {

        bmobFile.uploadblock(ConsultActivity.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                post.setContentfigureurl(bmobFile);
                //      Toast.makeText(ConsultActivity.this, "上传文件成功:" + bmobFile.getFileUrl(ConsultActivity.this), Toast.LENGTH_SHORT).show();
                post.save(ConsultActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(ConsultActivity.this, "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(ConsultActivity.this, "onFailure:" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(ConsultActivity.this, "上传文件失败:" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
            }
        });
    }
}
