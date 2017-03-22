package com.example.hin.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hin.system.R;
import com.example.hin.utils.CommonUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by WWF on 2016/12/11.
 */

public class RegisterActivity extends Activity {

    @BindView(R.id.et_phone_num)
    EditText etPhone;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_get_captcha)
    TextView tvGetCaptcha;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private boolean isInit = false;

    private DisposableSubscriber countDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
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
        etPhone.addTextChangedListener(new RegisterTextWatcher());
        etCaptcha.addTextChangedListener(new RegisterTextWatcher());
        etPwd.addTextChangedListener(new RegisterTextWatcher());
    }

    private void hookClickEvent() {
        RxView.clicks(btnRegister).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String phone = etPhone.getText().toString().trim();
                if (!CommonUtils.isMobileNO(phone)) {
                    CommonUtils.errorMsg(RegisterActivity.this
                            , "您输入的手机号码格式错误，请重新输入");
                    return;
                }
                String captcha = etCaptcha.getText().toString().trim();
                if (TextUtils.isEmpty(captcha)) {
                    CommonUtils.errorMsg(RegisterActivity.this, "验证码不能为空");
                    return;
                }
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    CommonUtils.errorMsg(RegisterActivity.this, "密码不能为空");
                    return;
                }
                BmobSMS.verifySmsCode(RegisterActivity.this, phone, captcha, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            startActivity(new Intent(RegisterActivity.this, InputInfoActivity.class)
                                    .putExtra("phone", etPhone.getText().toString().trim())
                                    .putExtra("pwd", etPwd.getText().toString().trim()));
                            finish();
                        } else {
                            CommonUtils.errorMsg(RegisterActivity.this
                                    , "验证码验证失败，请检查是否输入错误");
                        }
                    }
                });
            }
        });
        RxView.clicks(tvGetCaptcha).throttleFirst(2, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String phone = etPhone.getText().toString().trim();
                if (!CommonUtils.isMobileNO(phone)) {
                    CommonUtils.errorMsg(RegisterActivity.this, "您输入的手机号码格式错误，请重新输入");
                    return;
                }
                BmobSMS.requestSMSCode(RegisterActivity.this, phone, "短信模板", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            startSmsTimeTick();
                        } else {
                            CommonUtils.errorMsg(RegisterActivity.this, "验证码发送失败");
                        }
                    }
                });
            }
        });
    }

    private class RegisterTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!TextUtils.isEmpty(etPhone.getText().toString().trim())
                    && !TextUtils.isEmpty(etCaptcha.getText().toString().trim())
                    && !TextUtils.isEmpty(etPwd.getText().toString().trim())) {
                btnRegister.setBackgroundResource(R.drawable.bg_round_orange);
                btnRegister.setEnabled(true);
            } else {
                btnRegister.setBackgroundResource(R.drawable.bg_round_gray);
                btnRegister.setEnabled(false);
            }
        }
    }

    private void startSmsTimeTick() {
        countDownTask = new DisposableSubscriber<Integer>() {

            @Override
            protected void onStart() {
                super.onStart();
                tvGetCaptcha.setEnabled(false);
            }

            @Override
            public void onNext(Integer integer) {
                tvGetCaptcha.setText(integer + "s");
            }

            @Override
            public void onError(Throwable t) {
                tvGetCaptcha.setText("重新发送");
                tvGetCaptcha.setEnabled(true);
            }

            @Override
            public void onComplete() {
                tvGetCaptcha.setText("重新发送");
                tvGetCaptcha.setEnabled(true);
            }
        };
        CommonUtils.countDownTask(60, countDownTask);
    }
}
