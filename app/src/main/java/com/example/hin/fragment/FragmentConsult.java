package com.example.hin.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hin.toptabview.MyTopTabView;
import com.example.hin.system.R;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentConsult extends Fragment implements View.OnClickListener {


    private View view;
    private MyTopTabView myTopTabView;
    private Boolean is;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_topcontent, null);
        myTopTabView = new MyTopTabView(getContext());
        myTopTabView.init(savedInstanceState, view, 1);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
