package com.example.hin.ui.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.adapter.ExpertCommonQuestionAdapter;
import com.example.hin.entity.Experts;
import com.example.hin.entity.Post;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Hin on 2016/5/24.
 */
public class SearchActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back, iv_search;
    private RadioGroup rg_class;
    private RadioButton rbtn_topic, rbtn_expert;
    private ListView lv_content;
    private EditText et_search;

    private List<Post> topicList;
    private List<Experts> expertList;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;
    private ExpertCommonQuestionAdapter expertCommonQuestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //用于退出程序
        CloseActivity.activityList.add(this);
        iniView();
        iniListener();
        init();


    }

    //从服务器获取数据

    public void getPostMessage() {
        BmobQuery<Post> query = new BmobQuery<Post>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(SearchActivity.this, new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {

                topicList = new ArrayList<Post>();
                for (Post post : list) {
                    topicList.add(post);
                }
                consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(SearchActivity.this, topicList);
                lv_content.setAdapter(consultCommonQuestionAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public void getExpertsMessage() {
        BmobQuery<Experts> query = new BmobQuery<Experts>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(SearchActivity.this, new FindListener<Experts>() {
            @Override
            public void onSuccess(List<Experts> list) {

                expertList = new ArrayList<Experts>();
                for (Experts experts : list) {
                    expertList.add(experts);
                }
                expertCommonQuestionAdapter = new ExpertCommonQuestionAdapter(SearchActivity.this, expertList);
                lv_content.setAdapter(consultCommonQuestionAdapter);

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {
        getPostMessage();
        getExpertsMessage();
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rbtn_topic = (RadioButton) findViewById(R.id.rtbn_topic);
        rbtn_expert = (RadioButton) findViewById(R.id.rtbn_expert);
        lv_content = (ListView) findViewById(R.id.lv_content);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        et_search = (EditText) findViewById(R.id.et_search);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rtbn_topic:
                        lv_content.setAdapter(consultCommonQuestionAdapter);
                        break;
                    case R.id.rtbn_expert:
                        lv_content.setAdapter(expertCommonQuestionAdapter);
                        break;
                    default:
                        break;
                }
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            /**
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int checkId = rg_class.getCheckedRadioButtonId();
                switch (checkId) {
                    case R.id.rtbn_topic:
                        String content = s.toString();
                        Pattern p = Pattern.compile(content);
                        List<Post> List = new ArrayList<Post>();
                        for (int i = 0; i < topicList.size(); i++) {
                            String title = topicList.get(i).getTitle();
                            Matcher matcher = p.matcher(title);
                            if (matcher.find()) {
                                List.add(topicList.get(i));
                            }
                        }
                        ConsultCommonQuestionAdapter adapter = new ConsultCommonQuestionAdapter(SearchActivity.this, List);
                        lv_content.setAdapter(adapter);
                        break;
                    case R.id.rtbn_expert:
                        String expert_content = s.toString();
                        Pattern p_expert = Pattern.compile(expert_content);
                        List<Experts> List_expert = new ArrayList<Experts>();
                        for (int i = 0; i < topicList.size(); i++) {
                            String name = expertList.get(i).getName();
                            String file = expertList.get(i).getStudy();
                            Matcher matcher = p_expert.matcher(name + file);
                            if (matcher.find()) {
                                List_expert.add(expertList.get(i));
                            }
                        }
                        ExpertCommonQuestionAdapter adapter_expert = new ExpertCommonQuestionAdapter(SearchActivity.this, List_expert);
                        lv_content.setAdapter(adapter_expert);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

}
