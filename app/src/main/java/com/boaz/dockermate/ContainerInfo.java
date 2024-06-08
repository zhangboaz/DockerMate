package com.boaz.dockermate;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boaz.dockermate.entity.Container;
import com.boaz.dockermate.entity.Server;
import com.boaz.dockermate.utils.JschUtil;

public class ContainerInfo extends AppCompatActivity {

    Button log;

    @Override
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_info);
        Container container = (Container) getIntent().getSerializableExtra("container");
        Server server = (Server) getIntent().getSerializableExtra("server");
        log = findViewById(R.id.logs);
        new Thread(){
            public void run()
            {
                JschUtil jschUtil = new JschUtil(server.getIp(), server.getPort(), server.getUsername(), server.getPassword());
                jschUtil.connect();
                String logs = jschUtil.exec("docker logs " + container.getName());
                System.out.println("log:" + container.getName());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        log.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView test = findViewById(R.id.test);
                                test.setText(logs);
                            }
                        });
                    }
                });
            }
        }.start();



    }
}