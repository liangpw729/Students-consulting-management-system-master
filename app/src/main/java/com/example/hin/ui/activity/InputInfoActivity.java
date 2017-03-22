package com.example.hin.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hin.system.R;
import com.example.hin.ui.dialog.SelectSexDialog;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.functions.Consumer;

/**
 * Created by WWF on 2016/12/14.
 */

public class InputInfoActivity extends BaseActivity implements SelectSexDialog.OnSexSelectListener {

    @BindView(R.id.iv_head)
    SimpleDraweeView ivHead;
    @BindView(R.id.et_real_name)
    EditText etRealName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.et_no)
    TextView etNo;
    @BindView(R.id.et_mail)
    EditText etMail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private boolean isInit = false;

    private SelectSexDialog dialog;

    private String phone;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        ButterKnife.bind(this);

        phone = getIntent().getStringExtra("phone");
        pwd = getIntent().getStringExtra("pwd");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isInit) {
            isInit = true;
            initView();
            hookClickEvent();
        }
    }

    private void initView() {
        etRealName.addTextChangedListener(new textWatcher());
        tvSex.addTextChangedListener(new textWatcher());
        etNo.addTextChangedListener(new textWatcher());
        etMail.addTextChangedListener(new textWatcher());

        dialog = new SelectSexDialog(this);
        dialog.setOnSexSelectListener(this);
    }

    private void hookClickEvent() {
        RxView.clicks(btnSubmit).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                BmobUser user = new BmobUser();
                user.setEmail(etMail.getText().toString().trim());
                user.setUsername(etRealName.getText().toString().trim());
                user.setMobilePhoneNumber(phone);
                user.setPassword(pwd);
                user.signUp(InputInfoActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(InputInfoActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(InputInfoActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @OnClick({R.id.iv_head, R.id.tv_sex})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
                break;
            case R.id.tv_sex:
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
                break;
        }
    }

    @Override
    public void onSexSelected(int sex) {
        if (sex == 0) {
            tvSex.setText("男");
        } else {
            tvSex.setText("女");
        }
    }

    private class textWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (etRealName.getText().toString().trim().length() > 0
                    && tvSex.getText().toString().trim().length() > 0
                    && etNo.getText().toString().trim().length() > 0
                    && etMail.getText().toString().trim().length() > 0) {
                btnSubmit.setEnabled(true);
            } else {
                btnSubmit.setEnabled(false);
            }
        }
    }
}
