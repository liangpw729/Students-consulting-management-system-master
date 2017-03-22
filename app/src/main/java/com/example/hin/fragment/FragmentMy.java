package com.example.hin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hin.ui.activity.DraftsCollectActivity;
import com.example.hin.ui.activity.MyconsultActivity;
import com.example.hin.ui.activity.MymessageActivity;
import com.example.hin.system.R;
import com.example.hin.ui.activity.SetActivity;

/**
 * Created by Hin on 2016/5/25.
 */
public class FragmentMy extends Fragment implements View.OnClickListener {

    private View view;
    private ImageView iv_search;
    private LinearLayout ll_people_message,ll_myconsult,ll_drafts,ll_collect,ll_set;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        iniView();
        iniListener();
        return view;
    }

    public void iniView() {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.myfragment_my, null);
        ll_people_message=(LinearLayout)view.findViewById(R.id.ll_people_message);
        ll_myconsult=(LinearLayout)view.findViewById(R.id.ll_myconsult);
        ll_drafts=(LinearLayout)view.findViewById(R.id.ll_drafts);
        ll_collect=(LinearLayout)view.findViewById(R.id.ll_collect);
        ll_set=(LinearLayout)view.findViewById(R.id.ll_set);
    }

    public void iniListener() {

        ll_people_message.setOnClickListener(this);
        ll_myconsult.setOnClickListener(this);
        ll_drafts.setOnClickListener(this);
        ll_collect.setOnClickListener(this);
        ll_set.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_people_message:
                Intent Mymessageintent = new Intent(getActivity(), MymessageActivity.class);
                startActivityForResult(Mymessageintent, 0);
                break;
            case R.id.ll_myconsult:
                Intent Myconsultintent = new Intent(getActivity(), MyconsultActivity.class);
                startActivityForResult(Myconsultintent, 0);
                break;
            case R.id.ll_drafts:
                Intent Draftsintent = new Intent(getActivity(), DraftsCollectActivity.class);
                Draftsintent.putExtra("label","Drafts");
                startActivityForResult(Draftsintent, 0);
                break;
            case R.id.ll_collect:
                Intent Collectintent = new Intent(getActivity(), DraftsCollectActivity.class);
                Collectintent.putExtra("label","Collect");
                startActivityForResult(Collectintent, 0);
                break;
            case R.id.ll_set:
                Intent Setintent = new Intent(getActivity(), SetActivity.class);
                startActivityForResult(Setintent, 0);
                break;
            default:
                break;

        }

    }
}
