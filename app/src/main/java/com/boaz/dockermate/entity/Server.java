package com.boaz.dockermate.entity;

import java.io.Serializable;

public class Server implements Serializable {
    private Long id;
    private String serverName;
    private String ip;
    private String port;
    private String username;
    private String password;

    public Server() {
    }

    public Server(Long id, String serverName, String ip, String port, String username, String password) {
        this.id = id;
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
