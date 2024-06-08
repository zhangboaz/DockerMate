package com.boaz.dockermate;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.boaz.dockermate.dao.ServerDao;
import com.boaz.dockermate.dao.UserDao;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private ServerAdapter serverAdapter;
    private ServerDao serverDao;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = new UserDao(this);
    }

    /**
     * 登录
     */
    public void login(View view){

        EditText EditTextAccount = findViewById(R.id.username);
        EditText EditTextPassword = findViewById(R.id.password);

        new Thread(){
            @Override
            public void run() {
                int msg = userDao.login(EditTextAccount.getText().toString(),EditTextPassword.getText().toString());
                hand1.sendEmptyMessage(msg);
            }
        }.start();

    }

    public void register(View view){
        // 跳转注册页面
        Intent intent = new Intent(MainActivity.this,Register.class);
        startActivity(intent);
    }

    @SuppressLint("HandlerLeak")
    final Handler hand1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0){
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,ServerList.class);
                startActivity(intent);
            } else if (msg.what == 2){
                Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 3){
                Toast.makeText(getApplicationContext(), "账号不存在", Toast.LENGTH_LONG).show();
            }
        }
    };
}