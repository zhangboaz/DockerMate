package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.boaz.dockermate.dao.ServerDao;
import com.boaz.dockermate.dao.UserDao;
import com.boaz.dockermate.entity.Server;
import com.boaz.dockermate.utils.PreferenceUtil;

import java.util.List;

public class ServerList extends AppCompatActivity {

    private ServerDao serverDao;
    private RecyclerView recyclerView;
    private List<Server> servers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        serverDao = new ServerDao();
        PreferenceUtil preferenceUtil = new PreferenceUtil(this);
        new Thread() {
            @Override
            public void run() {
                System.out.println("登录用户ID：" + preferenceUtil.getUserId());
                servers = serverDao.getServers(preferenceUtil.getUserId());
                System.out.println("服务器列表：" + servers);

                // 创建适配器
                final ServerAdapter serverAdapter = new ServerAdapter(servers);

                // 在主线程中设置适配器
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(serverAdapter);
                    }
                });
            }
        }.start();
    }
}