package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.boaz.dockermate.dao.UserDao;
import com.boaz.dockermate.entity.User;

public class Register extends AppCompatActivity {
    EditText username = null;
    EditText password1 = null;
    EditText password2 = null;
    private UserDao userDao;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        userDao = new UserDao(this);
    }


    public void register(View view){

        String username1 = username.getText().toString();
        String password11 = password1.getText().toString();
        String password22 = password2.getText().toString();


        User user = new User();

        user.setUserName(username1);
        user.setPassword(password11);

        new Thread(){
            @Override
            public void run() {

                int msg = 0;

                User uu = userDao.findUser(user.getUserName());
                if (password11.equals(password22) == false){
                    msg = 3;
                } else if(uu != null){
                    msg = 1;
                }
                else{
                    boolean flag = userDao.register(user);
                    if(flag){
                        msg = 2;
                    }
                }
                hand.sendEmptyMessage(msg);

            }
        }.start();


    }

    public void relogin(View view){
        // 跳转登录页
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }
    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler()
    {
        public void handleMessage(Message msg) {
            if(msg.what == 0) {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_LONG).show();
            } else if(msg.what == 1) {
                Toast.makeText(getApplicationContext(),"该账号已经存在，请换一个账号",Toast.LENGTH_LONG).show();
            } else if(msg.what == 2) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_LONG).show();
                finish();
            } else if(msg.what == 3) {
                Toast.makeText(getApplicationContext(), "两次密码不一致", Toast.LENGTH_LONG).show();
            }
        }
    };
}