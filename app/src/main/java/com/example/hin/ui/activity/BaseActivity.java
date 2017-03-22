package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;

public class BaseActivity extends Activity {

    private SwipeRefreshLayout swipeLayout;
    private ListView listView;
    private boolean isRefresh = false;//是否刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //用于退出程序
        CloseActivity.activityList.add(this);


        //第一：默认初始化
        Bmob.initialize(this, "24d8176deed0a4472a3c5d2fc123f6ec");
        Fresco.initialize(this);
    }


}