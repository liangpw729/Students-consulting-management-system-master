package com.example.hin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hin.adapter.ConsultCommonQuestionAdapter;
import com.example.hin.entity.Post;
import com.example.hin.system.R;
import com.example.hin.toptabview.MyTopTabView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentReply extends Fragment implements View.OnClickListener {

    private View view;
    private Boolean is;
    private ListView lv_commomquestion;
    private SwipeRefreshLayout swipeLayout;
    List<Post> post;
    private ConsultCommonQuestionAdapter consultCommonQuestionAdapter;
    private boolean isRefresh = false;//是否刷新中

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_reply, null);
        iniView();
        getNetmessage();

        return view;
    }

    public void iniView() {
        lv_commomquestion = (ListView) view.findViewById(R.id.lv_commomquestion);
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetmessage();
            }
        });
        //加载颜色是循环播放的，只要没有完成刷新就会一直循环，color1>color2>color3>color4
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

    }

    public void getNetmessage() {

        BmobQuery<Post> query = new BmobQuery<Post>();
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        query.order("exigency");
        //执行查询方法
        query.findObjects(getContext(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> list) {
                if (list.size() > 0) {
                    //     Toast.makeText(ConsultCommonQuestionActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    post = list;
                    consultCommonQuestionAdapter = new ConsultCommonQuestionAdapter(getContext(), list);
                    lv_commomquestion.setAdapter(consultCommonQuestionAdapter);
                } else {
                    //    Toast.makeText(ConsultCommonQuestionActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }
                swipeLayout.setRefreshing(false);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        swipeLayout.setRefreshing(true);
        getNetmessage();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


}
