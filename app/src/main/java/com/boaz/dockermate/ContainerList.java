package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.boaz.dockermate.entity.Container;
import com.boaz.dockermate.entity.Server;
import com.boaz.dockermate.utils.JschUtil;

import java.util.ArrayList;
import java.util.List;

public class ContainerList extends AppCompatActivity {

    TextView test;
    private JschUtil jschUtil;
    private RecyclerView recyclerView;
    List<Container> containers;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        test = findViewById(R.id.test);

        // 获取Intent对象
        Intent intent = getIntent();
        Server server = (Server) intent.getSerializableExtra("server");
        new Thread(){
            @Override
            public void run() {
                jschUtil = new JschUtil(server.getIp(), server.getPort(), server.getUsername(), server.getPassword());
                if(jschUtil.connect()){
                    String result = jschUtil.exec("docker ps -a");
                    containers = parseDockerContainer(result);
                    // 格式输出容器信息
                    for (Container container : containers) {
                        System.out.println("Container ID: " + container.getId());
                        System.out.println("Container Name: " + container.getName());
                        System.out.println("Image Name: " + container.getImage());
                        System.out.println("Status: " + container.getStatus());
                        System.out.println("----------------------------------------");
                    }
                    // 创建适配器
                    final ContainerAdapter containerAdapter = new ContainerAdapter(containers, server);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            test.setText(result);
                            recyclerView.setAdapter(containerAdapter);
                        }
                    });
                }

            }
        }.start();

    }

    private List<Container> parseDockerContainer(String dockerOutput) {
        List<Container> containers = new ArrayList<>();
        String[] lines = dockerOutput.split("\n");
        for (String line : lines) {
            if (!line.isEmpty()) {
                String[] columns = line.split("\\s{2,}");
                if (columns.length >= 7) {
                    String containerId = columns[0];
                    String imageName = columns[1];
                    String command = columns[2];
                    String created = columns[3];
                    String status = columns[4];
                    String ports = columns[5];
                    String name = columns[6];
                    Container container = new Container(containerId, imageName, command, created, status, ports, name);
                    containers.add(container);
                }
            }
        }
        return containers;
    }
}