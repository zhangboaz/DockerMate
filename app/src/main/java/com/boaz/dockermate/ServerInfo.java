package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boaz.dockermate.entity.Server;
import com.boaz.dockermate.utils.JschUtil;

public class ServerInfo extends AppCompatActivity {

    private Server server;
    private JschUtil jschUtil;
    TextView containerCount, imageCount, volumeCount, networkCount;
    LinearLayout containers, images, volume, networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_info);

        // Docker
        containers = findViewById(R.id.containers);
        images = findViewById(R.id.images);
        volume = findViewById(R.id.volume);
        networks = findViewById(R.id.networks);

        containerCount = findViewById(R.id.containerCount);
        imageCount = findViewById(R.id.imageCount);
        volumeCount = findViewById(R.id.volumeCount);
        networkCount = findViewById(R.id.networkCount);

        // 获取Intent对象
        Intent intent = getIntent();
        if (intent != null) {
            server = (Server) intent.getSerializableExtra("server");
            if (server != null) {
                // 现在你可以使用接收到的Server对象
                // 例如，显示服务器信息
                TextView serverNameView = findViewById(R.id.serverName);
                TextView serverIpView = findViewById(R.id.serverIp);
                TextView serverUserView = findViewById(R.id.serverUser);
                TextView serverConn = findViewById(R.id.serverConn);



                serverNameView.setText(server.getServerName());
                serverIpView.setText(server.getIp());
                serverUserView.setText(server.getUsername());

                new Thread(){
                    @Override
                    public void run() {
                        // 创建JschUtil对象
                        jschUtil = new JschUtil(server.getIp(), server.getPort(), server.getUsername(), server.getPassword());
                        if (jschUtil.connect()) {
                            // 连接成功，可以执行其他操作
                            String containerNum = jschUtil.exec("docker container ls --all --quiet | wc -l");
                            String imageNum = jschUtil.exec("docker image ls --all --quiet | wc -l");
                            String volumeNum = jschUtil.exec("docker volume ls --quiet | wc -l");
                            String networkNum = jschUtil.exec("docker network ls --quiet | wc -l");
                            // 在主线程中设置适配器
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    containerCount.setText(containerNum);
                                    imageCount.setText(imageNum);
                                    volumeCount.setText(volumeNum);
                                    networkCount.setText(networkNum);
                                }
                            });
                            // 处理结果
                        } else {
                            // 在主线程中设置适配器
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // 连接失败，可以处理这种情况
                                    serverConn.setText("连接失败1");
                                }
                            });
                        }
                    }
                }.start();
            } else {
                // 如果server为null，可能表示数据没有正确传递过来，可以处理这种情况
                Toast.makeText(this, "未能获取服务器信息", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 意味着没有接收到Intent，可能是因为错误的启动方式
            Toast.makeText(this, "无法启动服务器信息页面", Toast.LENGTH_SHORT).show();
        }

        // 点击进入容器列表
        containers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServerInfo.this, "进入容器列表", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServerInfo.this, ContainerList.class);
                // 携带server对象
                intent.putExtra("server", server);
                startActivity(intent);
            }
        });
        // 点击进入镜像列表
        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServerInfo.this, "进入镜像列表", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServerInfo.this, ImageList.class);
                startActivity(intent);
            }
        });
        // 点击进入卷列表
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServerInfo.this, "进入卷列表", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServerInfo.this, VolumeList.class);
                startActivity(intent);
            }
        });
        // 点击进入网络列表
        networks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ServerInfo.this, "进入网络列表", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ServerInfo.this, NetworkList.class);
                // 进入网络列表
                startActivity(intent);
            }
        });
    }
}