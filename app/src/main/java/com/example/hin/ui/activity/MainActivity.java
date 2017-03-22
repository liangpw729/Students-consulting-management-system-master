package com.example.hin.ui.activity;

import com.example.hin.fragment.FragmentConsult;
import com.example.hin.fragment.FragmentExperts;
import com.example.hin.fragment.FragmentMy;
import com.example.hin.fragment.FragmentReply;
import com.example.hin.system.R;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Hin on 2016/5/16.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private RadioGroup myTabRg;
    private FragmentReply fragmentReply;
    private FragmentConsult fragmentConsult;
    private FragmentExperts fragmentExperts;
    private FragmentMy fragmentMy;
    private RadioButton expertDatabase, consult, my;
    private ImageView search, jiahao;
    private Boolean is;//判断学生登录或者教师登录
    private LocalActivityManager mLocalActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //用于退出程序
        CloseActivity.activityList.add(this);

        mLocalActivityManager=new LocalActivityManager(this,true);//此处为true

        parseIntent();
        iniView();
        iniListener();


        if (is) {
            fragmentReply = new FragmentReply();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentReply).commit();

        } else {
            fragmentConsult = new FragmentConsult();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();

        }

        myTabRg = (RadioGroup) findViewById(R.id.tab_menu);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.expertDatabase:
                        fragmentExperts = new FragmentExperts();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentExperts)
                                .commit();
                        break;
                    case R.id.consult:

                        if (is) {
                            fragmentReply = new FragmentReply();
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentReply).commit();

                        } else {
                            fragmentConsult = new FragmentConsult();
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();

                        }
                        break;
                    case R.id.my:
                        fragmentMy = new FragmentMy();
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentMy).commit();
                        break;
                    default:
                        break;
                }
            }
        });

        expertDatabase = (RadioButton) findViewById(R.id.expertDatabase);
        consult = (RadioButton) findViewById(R.id.consult);
        my = (RadioButton) findViewById(R.id.my);
        setDrawableSize(expertDatabase);
        setDrawableSize(consult);
        setDrawableSize(my);


    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (consult.isChecked()) {
//            if (is) {
//                fragmentReply = new FragmentReply();
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentReply).commit();
//
//            } else {
//                fragmentConsult = new FragmentConsult();
//                getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragmentConsult).commit();
//
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void parseIntent() {
        Intent intent = getIntent();
        is = intent.getBooleanExtra("is", false);

    }

    public void iniView() {
        search = (ImageView) findViewById(R.id.iv_search);
        jiahao = (ImageView) findViewById(R.id.jiahao);
        consult = (RadioButton) findViewById(R.id.consult);
        if (is) {
            consult.setText("答问题");
        }

    }

    public void iniListener() {
        search.setOnClickListener(this);
        jiahao.setOnClickListener(this);
    }

    //该方法设置底部Tab图片的大小
    public void setDrawableSize(RadioButton v) {
        Drawable[] drawable = v.getCompoundDrawables();
        int height = (int) (drawable[1].getIntrinsicHeight() / 1.5);
        int width = (int) (drawable[1].getIntrinsicWidth() / 1.5);
        drawable[1].setBounds(0, 0, height, width);
        v.setCompoundDrawables(null, drawable[1], null, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.jiahao:
                startActivityForResult(new Intent(MainActivity.this, ConsultActivity.class), 0);
                break;
            default:
                break;
        }
    }
}
