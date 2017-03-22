package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hin.system.R;

/**
 * Created by Hin on 2016/5/24.
 */
public class DraftsCollectActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back;
    private ListView lv_content;
    private TextView tv_drafts_collect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drafts_collect);
        //用于退出程序
        CloseActivity.activityList.add(this);

        iniView();
        iniListener();
        init();

        Intent it = super.getIntent();
        String info = it.getStringExtra("label");
        if (info.equals("Drafts")) {

            tv_drafts_collect.setText("草稿箱");
        } else {
            tv_drafts_collect.setText("我的收藏");
        }


    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {


    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_content = (ListView) findViewById(R.id.lv_content);
        tv_drafts_collect = (TextView) findViewById(R.id.tv_drafts_collect);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);

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
