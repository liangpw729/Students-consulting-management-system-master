package com.example.hin.adapter;

/**
 * Created by Hin on 2016/5/25.
 */

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * ViewPager的适配器
 */
 public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<View> mViews;//用来存放下方滚动的layout(layout_1,layout_2,layout_3)

    public MyPagerAdapter(ArrayList<View> Views) {
        mViews=Views;
    }

    @Override
    public void destroyItem(View v, int position, Object obj) {
        // TODO Auto-generated method stub
        ((ViewPager) v).removeView(mViews.get(position));
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mViews.size();
    }

    @Override
    public Object instantiateItem(View v, int position) {
        ((ViewPager) v).addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public Parcelable saveState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
        // TODO Auto-generated method stub
    }

}
