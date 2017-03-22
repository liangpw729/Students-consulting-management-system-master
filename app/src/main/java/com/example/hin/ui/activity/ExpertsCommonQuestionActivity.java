package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.adapter.ExpertCommonQuestionAdapter;
import com.example.hin.entity.Experts;
import com.example.hin.entity.Post;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Hin on 2016/5/16.
 */
public class ExpertsCommonQuestionActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{


    private ListView lv_commomquestion;
    List<Experts> expertses;
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    HashMap<String, Object> expert = new HashMap<String, Object>();
    private SwipeRefreshLayout swipeLayout;
    private ExpertCommonQuestionAdapter expertCommonQuestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_question);
        //用于退出程序
        CloseActivity.activityList.add(this);
        iniView();
        iniListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        selectPart(id);

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
       // swipeLayout.setRefreshing(true);
    }

    public void iniView() {
        lv_commomquestion = (ListView) findViewById(R.id.lv_commomquestion);

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        swipeLayout.setRefreshing(true);
    }

    public void iniListener() {

    }

    public void selectPart(int position) {
        switch (position) {
            case 0:
                getNetmessage(0);
                break;
            case 1:
                getNetmessage(1);
                break;
            case 2:
                getNetmessage(2);
                break;
            case 3:
                getNetmessage(3);
                break;
            case 4:
                getNetmessage(4);
                break;
            case 5:
                getNetmessage(5);
                break;
        }
    }

    public void getNetmessage(int label) {

        BmobQuery<Experts> query = new BmobQuery<Experts>();
        String kind;
        switch (label) {
            case 0:
                kind = "课程答疑";
                break;
            case 1:
                kind = "人际交往";
                break;
            case 2:
                kind = "身心健康";
                break;
            case 3:
                kind = "就业招聘";
                break;
            case 4:
                kind = "发展规划";
                break;
            case 5:
                kind = "其他";
                break;
            default:
                kind = "课程答疑";
                break;
        }
        query.addWhereEqualTo("kind", kind);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(this, new FindListener<Experts>() {

            @Override
            public void onSuccess(List<Experts> list) {
                if (list.size() > 0) {
                    Toast.makeText(ExpertsCommonQuestionActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    expertses = list;
                    expertCommonQuestionAdapter = new ExpertCommonQuestionAdapter(ExpertsCommonQuestionActivity.this, list);
                    lv_commomquestion.setAdapter(expertCommonQuestionAdapter);
                } else {
                    Toast.makeText(ExpertsCommonQuestionActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }
                if (swipeLayout.isRefreshing()) {
                    swipeLayout.setRefreshing(false);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(ExpertsCommonQuestionActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("id");
        selectPart(id);
    }
}
