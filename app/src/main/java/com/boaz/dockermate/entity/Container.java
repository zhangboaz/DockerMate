package com.boaz.dockermate.entity;

import java.io.Serializable;

public class Container implements Serializable {

    private String id;
    private String image;
    private String command;
    private String created;
    private String status;
    private String ports;
    private String name;

    public Container() {
    }

    public Container(String id, String image, String command, String created, String status, String ports, String name) {
        this.id = id;
        this.image = image;
        this.command = command;
        this.created = created;
        this.status = status;
        this.ports = ports;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() {
        if (status != null && status.startsWith("Up")) {
            return "Running";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPorts() {
        if (ports != null && ports.contains("->")) {
            String[] parts = ports.split("->");
            String leftPart = parts[0].trim();
            String rightPart = parts[1].split("/")[0].trim(); // 分割"/"以获取端口号部分
            return leftPart.substring(leftPart.lastIndexOf(':') + 1) + ":" + rightPart.substring(rightPart.lastIndexOf(':') + 1);
        }
        return ""; // 如果格式不正确或ports为null，可以返回空字符串或抛出异常，根据需求调整
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
