package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.hin.system.R;

/**
 * Created by Hin on 2016/6/1.
 */
public class MymessageActivity extends Activity implements View.OnClickListener {
    private ImageView iv_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        //用于退出程序
        CloseActivity.activityList.add(this);

        iniView();
        iniListener();
    }
    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);

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
