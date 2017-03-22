package com.example.hin.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hin.system.R;

/**
 * Created by Hin on 2016/6/1.
 */
public class SetActivity extends Activity implements View.OnClickListener {
    private ImageView iv_back, iv_close;
    private SlidingDrawer sd_content;
    private TextView tv_new, tv_model, tv_textsize, tv_help, tv_exit, tv_introduce, tv_exit_confirm, tv_exit_cancel, tv_starttime, tv_endtime;
    private LinearLayout ll_item, ll_new_warn, ll_model, ll_help, ll_feedback, ll_exit,ll_settitle;
    private RadioGroup rg_class;
    private RelativeLayout rl_start_time, rl_end_time;
    private View ll_selecttime;
    private DatePicker dp_date;
    private TimePicker tp_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        //用于退出程序
        CloseActivity.activityList.add(this);
        iniView();
        iniListener();
    }

    //获取控件ID
    public void iniView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        sd_content = (SlidingDrawer) findViewById(R.id.sd_content);
        tv_new = (TextView) findViewById(R.id.tv_new);
        tv_model = (TextView) findViewById(R.id.tv_model);
        tv_textsize = (TextView) findViewById(R.id.tv_textsize);
        tv_help = (TextView) findViewById(R.id.tv_help);
        tv_exit = (TextView) findViewById(R.id.tv_exit);
        ll_settitle=(LinearLayout)findViewById(R.id.ll_settitle);
        ll_item = (LinearLayout) findViewById(R.id.ll_item);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        ll_new_warn = (LinearLayout) findViewById(R.id.ll_new_warn);
        ll_model = (LinearLayout) findViewById(R.id.ll_model);
        ll_help = (LinearLayout) findViewById(R.id.ll_help);
        rg_class = (RadioGroup) findViewById(R.id.rg_class);
        tv_introduce = (TextView) findViewById(R.id.tv_introduce);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        ll_exit = (LinearLayout) findViewById(R.id.ll_exit);
        tv_exit_cancel = (TextView) findViewById(R.id.tv_exit_cancel);
        tv_exit_confirm = (TextView) findViewById(R.id.tv_exit_confirm);
        rl_start_time = (RelativeLayout) findViewById(R.id.rl_start_time);
        rl_end_time = (RelativeLayout) findViewById(R.id.rl_end_time);
        tv_starttime = (TextView) findViewById(R.id.tv_starttime);
        tv_endtime = (TextView) findViewById(R.id.tv_endtime);


    }

    //监听事件
    public void iniListener() {
        iv_back.setOnClickListener(this);
        tv_new.setOnClickListener(this);
        tv_model.setOnClickListener(this);
        tv_textsize.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_exit.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_exit.setOnClickListener(this);
        tv_exit_cancel.setOnClickListener(this);
        tv_exit_confirm.setOnClickListener(this);
        rl_start_time.setOnClickListener(this);
        rl_end_time.setOnClickListener(this);
        //抽屉监听事件
        sd_content.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                ll_settitle.setVisibility(View.VISIBLE);
                ll_item.setVisibility(View.VISIBLE);
                sd_content.setVisibility(View.GONE);
                ll_new_warn.setVisibility(View.GONE);
                ll_model.setVisibility(View.GONE);
                ll_help.setVisibility(View.GONE);
                ll_exit.setVisibility(View.GONE);
            }
        });
        sd_content.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {

            }
        });
        //帮助设置监听事件
        rg_class.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbtn_explain:
                        ll_feedback.setVisibility(View.GONE);
                        tv_introduce.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbtn_feedback:
                        ll_feedback.setVisibility(View.VISIBLE);
                        tv_introduce.setVisibility(View.GONE);
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
            case R.id.tv_new:
                ll_settitle.setVisibility(View.GONE);
                ll_item.setVisibility(View.GONE);
                sd_content.setVisibility(View.VISIBLE);
                sd_content.open();
                ll_new_warn.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_model:
                ll_settitle.setVisibility(View.GONE);
                ll_item.setVisibility(View.GONE);
                sd_content.setVisibility(View.VISIBLE);
                sd_content.open();
                ll_model.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_help:
                ll_settitle.setVisibility(View.GONE);
                ll_item.setVisibility(View.GONE);
                sd_content.setVisibility(View.VISIBLE);
                sd_content.open();
                ll_help.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_exit:
                ll_settitle.setVisibility(View.GONE);
                ll_item.setVisibility(View.GONE);
                sd_content.setVisibility(View.VISIBLE);
                sd_content.open();
                ll_exit.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_close:
                sd_content.close();
            case R.id.tv_exit_cancel:
                sd_content.close();
                break;
            case R.id.tv_exit_confirm:
                CloseActivity.exitClient(SetActivity.this);
                break;
            case R.id.rl_start_time:
                ll_selecttime = getLayoutInflater().inflate(R.layout.set_model_time, null);
                dp_date = (DatePicker) ll_selecttime.findViewById(R.id.dp_date);
                tp_time = (TimePicker) ll_selecttime.findViewById(R.id.tp_time);

                Dialog dialogstarttime=  new AlertDialog.Builder(SetActivity.this).setTitle("选择开始时间").setView(ll_selecttime)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                tv_starttime.setText(dp_date.getYear()+"-"+" "+(dp_date.getMonth()+1)+"-"+dp_date.getDayOfMonth()+" "+
                                tp_time.getCurrentHour()+ ":"+tp_time.getCurrentMinute());

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.rl_end_time:
                ll_selecttime = getLayoutInflater().inflate(R.layout.set_model_time, null);
                dp_date = (DatePicker) ll_selecttime.findViewById(R.id.dp_date);
                tp_time = (TimePicker) ll_selecttime.findViewById(R.id.tp_time);
                Dialog dialogendtime = new AlertDialog.Builder(SetActivity.this).setTitle("选择结束时间").setView(ll_selecttime)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                tv_endtime.setText(dp_date.getYear() + "-" + " " + (dp_date.getMonth() + 1) + "-" + dp_date.getDayOfMonth() + " " +
                                        tp_time.getCurrentHour() + ":" + tp_time.getCurrentMinute());
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(sd_content.isOpened())
        {
            ll_settitle.setVisibility(View.VISIBLE);
            sd_content.close();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //slidingDraw监听事件

}
