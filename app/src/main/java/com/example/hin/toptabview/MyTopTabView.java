package com.example.hin.toptabview;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hin.adapter.MyPagerAdapter;
import com.example.hin.ui.activity.ExpertsCommonQuestionActivity;
import com.example.hin.ui.activity.ConsultCommonQuestionActivity;
import com.example.hin.system.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hin on 2016/5/26.
 */
public class MyTopTabView extends View {

    private View view;
    private ImageView mImageView;
    private float mCurrentCheckedRadioLeft;//当前被选中的RadioButton距离左侧的距离
    private HorizontalScrollView mHorizontalScrollView;//上面的水平滚动控件
    private ViewPager mViewPager;    //下方的可横向拖动的控件
    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)
    LocalActivityManager manager = null;
    private RadioGroup myRadioGroup;
    private int _id = 1000;
    private LinearLayout layout, titleLayout;
    private TextView textView;
    private int label = 0;
    private RadioButton rb;

    public MyTopTabView(Context context) {
        super(context);
    }

    public void init(Bundle savedInstanceState, View v, int l) {
        view = v;
        label = l;
        manager = new LocalActivityManager((Activity) getContext(), true);
        manager.dispatchCreate(savedInstanceState);
        getTitleInfo();
        initGroup();
        iniListener();
        iniVariable();
        mViewPager.setCurrentItem(0);
    }

    private List<Map<String, Object>> titleList = new ArrayList<Map<String, Object>>();

    private void getTitleInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 0);
        map.put("title", "课程答疑");
        titleList.add(map);
        map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("title", "人际交往");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 2);
        map.put("title", "身心健康");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 3);
        map.put("title", "就业招聘");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 4);
        map.put("title", "发展规划");
        titleList.add(map);

        map = new HashMap<String, Object>();
        map.put("id", 5);
        map.put("title", "其他");
        titleList.add(map);

    }

    private void initGroup() {

        titleLayout = (LinearLayout) view.findViewById(R.id.title_lay);
        layout = (LinearLayout) view.findViewById(R.id.lay);

        //mImageView = new ImageView(this);

        mImageView = (ImageView) view.findViewById(R.id.img1);
        mHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);

        mViewPager = (ViewPager) view.findViewById(R.id.pager);

        myRadioGroup = new RadioGroup(getContext());
        myRadioGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        myRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        layout.addView(myRadioGroup);
        for (int i = 0; i < titleList.size(); i++) {
            Map<String, Object> map = titleList.get(i);
            RadioButton radio = new RadioButton(getContext());
            radio.setBackgroundResource(R.drawable.radiobtn_selector);
            radio.setButtonDrawable(android.R.color.transparent);
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
            radio.setLayoutParams(l);
            radio.setGravity(Gravity.CENTER);
            radio.setPadding(20, 15, 20, 15);
            //radio_selector_teacher.setPadding(left, top, right, bottom)
            radio.setId(_id + i);
            radio.setText(map.get("title") + "");
            radio.setTextColor(Color.WHITE);
            radio.setTag(map);
            if (i == 0) {
                radio.setChecked(true);
                int itemWidth = (int) radio.getPaint().measureText(map.get("title") + "");
                mImageView.setLayoutParams(new LinearLayout.LayoutParams(itemWidth + radio.getPaddingLeft() + radio.getPaddingRight(), 4));
            }
            myRadioGroup.addView(radio);
        }
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //Map<String, Object> map = (Map<String, Object>) group.getChildAt(checkedId).getTag();
                int radioButtonId = group.getCheckedRadioButtonId();
                //根据ID获取RadioButton的实例
                RadioButton rb = (RadioButton) view.findViewById(radioButtonId);
                //  Map<String, Object> selectMap = (Map<String, Object>) rb.getTag();

                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(mCurrentCheckedRadioLeft, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(300);

                mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
                mViewPager.setCurrentItem(radioButtonId - _id);//让下方ViewPager跟随上面的HorizontalScrollView切换
                mCurrentCheckedRadioLeft = rb.getLeft();//更新当前蓝色横条距离左边的距离
                Log.d("mCurrent", String.valueOf(mCurrentCheckedRadioLeft));

                mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft - (int) getResources().getDimension(R.dimen.rdo2), 0);
                mImageView.setLayoutParams(new LinearLayout.LayoutParams(rb.getRight() - rb.getLeft(), 4));


            }
        });

    }

    public void initImage(View v) {
        int radioButtonId = myRadioGroup.getCheckedRadioButtonId();
        Log.d("getCheckedRadioButtonId", String.valueOf(radioButtonId));
        rb = (RadioButton) view.findViewById(radioButtonId);
        rb.post(new Runnable() {
            public void run() {
                Log.d("TAG", rb.getLeft() + "," + rb.getRight());
                mImageView.setLayoutParams(new LinearLayout.LayoutParams(rb.getRight() - rb.getLeft(), 4));
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation translateAnimation;
                translateAnimation = new TranslateAnimation(0, rb.getLeft(), 0f, 0f);
                animationSet.addAnimation(translateAnimation);
                animationSet.setFillBefore(true);
                animationSet.setFillAfter(true);
                animationSet.setDuration(300);
                mImageView.startAnimation(animationSet);//开始上面蓝色横条图片的动画切换
                mHorizontalScrollView.scrollTo((int) rb.getLeft() - (int) getResources().getDimension(R.dimen.rdo2), 0);


            }
        });

    }

    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    private void iniVariable() {
        mViews = new ArrayList<View>();
        for (int i = 0; i < titleList.size(); i++) {
            Intent intent1;
            if (label == 0) {
                intent1 = new Intent(getContext(), ExpertsCommonQuestionActivity.class);
            } else {
                intent1 = new Intent(getContext(), ConsultCommonQuestionActivity.class);
            }
            intent1.putExtra("id", i);
            mViews.add(getView("View" + i, intent1));
        }
        mViewPager.setAdapter(new MyPagerAdapter(mViews));//设置ViewPager的适配器
    }

    private void iniListener() {
        // TODO Auto-generated method stub
        mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener());
    }

    /**
     * ViewPager的PageChangeListener(页面改变的监听器)
     */
    private class MyPagerOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        /**
         * 滑动ViewPager的时候,让上方的HorizontalScrollView自动切换
         */
        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            RadioButton radioButton = (RadioButton) view.findViewById(_id + position);
            radioButton.performClick();

        }
    }


}
