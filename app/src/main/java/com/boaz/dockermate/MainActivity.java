package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.boaz.dockermate.dao.UserDao;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(){
            @Override
            public void run() {
                UserDao userDao = new UserDao();
                int result = userDao.login("boaz", "123456");
                System.out.println(result);
            }
        }.start();
    }
}