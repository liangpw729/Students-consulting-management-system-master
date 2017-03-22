package com.example.hin.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.hin.system.R;

/**
 * Created by jclang on 2016/11/25.
 */

public class SelectSexDialog extends Dialog implements View.OnClickListener  {

    private TextView btn1, btn2;
    private OnSexSelectListener listener;

    public SelectSexDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        setContentView(R.layout.view_select_sex);
        btn1 = (TextView) findViewById(R.id.btn_one);
        btn1.setOnClickListener(this);
        btn2 = (TextView) findViewById(R.id.btn_two);
        btn2.setOnClickListener(this);
        TextView btnCancel = (TextView) findViewById(R.id.btn_cancel);
        findViewById(R.id.mask).setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        dismiss();
        int id = view.getId();
        if (id == R.id.btn_one) {
            if (listener != null) {
                listener.onSexSelected(0);
            }
        } else if (id == R.id.btn_two) {
            if (listener != null) {
                listener.onSexSelected(1);
            }
        }
    }

    public void setOnSexSelectListener(OnSexSelectListener listener) {
        this.listener = listener;
    }

    public interface OnSexSelectListener {
        void onSexSelected(int sex);
    }
}
