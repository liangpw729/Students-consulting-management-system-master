package com.example.hin.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.hin.toptabview.MyTopTabView;
import com.example.hin.system.R;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentExperts extends Fragment {

    private View view;
    private MyTopTabView myTopTabView;
    private TextView tv_calss;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null);
        /*
        * 初始化标题栏下的分类*/
        tv_calss = (TextView) view.findViewById(R.id.tv_class);
        tv_calss.setText("专家库");

        myTopTabView = new MyTopTabView(getContext());
        myTopTabView.init(savedInstanceState, view, 0);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
