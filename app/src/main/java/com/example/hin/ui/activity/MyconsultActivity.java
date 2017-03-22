package com.example.hin.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Hin on 2016/5/24.
 */
public class MyconsultActivity extends Activity implements View.OnClickListener {

    private ImageView iv_back;
    private RadioGroup rg_class;
    private RadioButton rbtn_consult, rbtn_reply;
    private ListView lv_content;

    private ArrayList<HashMap<String, Object>> consultingList, replyList;
    private HashMap<String, Object> consultingQuestion, replyQuestion;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter, replyCommonQuestionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myconsult);
        //用于退出程序
        CloseActivity.activityList.add(this);
        iniView();
        iniListener();
        init();


    }

    //初始化数据，主要是adapter数据的初始化
    public void init() {

        consultingList = new ArrayList<HashMap<String, Object>>();
        consultingQuestion = new HashMap<String, Object>();
        replyList = new ArrayList<HashMap<String, Object>>();
        replyQuestion = new HashMap<String, Object>();

        consultingQuestion.put("tv_title", "数学分析Cauchy准则怎么证明？");
        consultingQuestion.put("tv_date", "2015年12月10日");
        consultingList.add(consultingQuestion);

        replyQuestion.put("tv_title", "你猜我想问什么");
        replyQuestion.put("tv_date", "2015年12月10日");
        replyList.add(replyQuestion);

      /*  consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(MyconsultActivity.this, consultingList);
        replyCommonQuestionAdapter = new ConsultCommonQuestionAdapter(MyconsultActivity.this, replyList);
*/
        lv_content.setAdapter(consultCommonQuestionAdapter);
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        rbtn_consult = (RadioButton) findViewById(R.id.rbtn_consult);
        rbtn_reply = (RadioButton) findViewById(R.id.rbtn_reply);
        lv_content = (ListView) findViewById(R.id.lv_content);

    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_consult:
                        lv_content.setAdapter(consultCommonQuestionAdapter);
                        break;
                    case R.id.rbtn_reply:
                        lv_content.setAdapter(replyCommonQuestionAdapter);
                        break;
                    default:
                        break;
                }
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
