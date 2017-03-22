package com.example.hin.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hin.entity.Experts;
import com.example.hin.entity.User;
import com.example.hin.system.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button bt_regist;
    private EditText et_account, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //用于退出程序
        CloseActivity.activityList.add(this);

        findView();
        initOnclick();

    }

    //获得控件ID
    public void findView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        bt_regist = (Button) findViewById(R.id.bt_regist);
    }

    public void initOnclick() {
        bt_regist.setOnClickListener(this);
        findViewById(R.id.tv_register).setOnClickListener(this);
    }

    /*
    * 假登陆，测试时直接使用后台提供的登陆账号*/
    public void login() {
        User bu2 = new User();
        bu2.setUsername(et_account.getText().toString());
        bu2.setPassword(et_password.getText().toString());
        bu2.login(LoginActivity.this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                User user = BmobUser.getCurrentUser(LoginActivity.this, User.class);
                Boolean is = user.getIsExpert();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("is", is);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(LoginActivity.this, "登陆失败" + s, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regist:
                login();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }


}
