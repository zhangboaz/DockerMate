package com.boaz.dockermate.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.InputStream;
import java.util.Properties;

public class JschUtil {

    private String ip;
    private String port;
    private String username;
    private String password;

    private JSch jsch;
    private Session session;
    private Channel channel;

    public JschUtil(String ip, String port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public boolean connect() {
        try {
            jsch = new JSch();
            // 根据主机账号、ip、端口获取一个Session对象
            session = jsch.getSession(username, ip, Integer.parseInt(port));
            // 存放主机密码
            session.setPassword(password);
            Properties config = new Properties();
            // 设置不用提示，直接信任
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            return session.isConnected();
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        if (session != null) {
            session.disconnect();
        }
    }

    public String exec(String command) {
        StringBuffer result = new StringBuffer();
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            // 执行命令，等待执行结果
            channel.connect();
// 获取命令执行结果
            InputStream in = channel.getInputStream();
            /**
             * 通过channel获取信息的方式，采用官方Demo代码
             */
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    result.append(new String(tmp, 0, i));
                }
                // 从channel获取全部信息之后，channel会自动关闭
                if (channel.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result.toString();
    }
}
