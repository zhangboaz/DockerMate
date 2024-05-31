package com.boaz.dockermate.dao;

import com.boaz.dockermate.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    /**
     * 登录
     */
    public int login(String username, String password){

        // 建立连接
        Connection connection = JDBCUtils.getConn("linux_server");
        try{
            // sql：根据username查询用户
            String sql = "select * from user where username = ?";
            if(connection != null){
                System.out.println("username:"+username);
                // 预编译
                PreparedStatement ps = connection.prepareStatement(sql);
                if(ps != null){
                    // 根据账号进行查询
                    ps.setString(1, username);
                    // 执行sql并返回结果
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();
                    if(count > 0){
                        // 获取用户信息
                        while(rs.next()){
                            String pwd = rs.getString("password");
                            if(password.equals(pwd)){
                                return rs.getInt("id");
                            }
                        }
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return -1;
        }

        return -1;
    }

    /**
     * 注册
     */
    public boolean register(String username, String password){

        // 建立连接
        Connection connection = JDBCUtils.getConn("linux_server");
        try{
            // sql：根据username查询用户
            String sql1 = "select * from user where username = ?";
            if(connection != null){
                // 预编译
                PreparedStatement ps = connection.prepareStatement(sql1);
                if(ps != null){
                    // 根据账号进行查询
                    ps.setString(1, username);
                    // 执行sql并返回结果
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();
                    if(count > 0){
                        // 用户已存在
                        return false;
                    }
                }
            }
            // sql：新增用户
            String sql2 = "insert into user(username, password) values(?, ?)";
            if(connection != null){
                // 预编译
                PreparedStatement ps = connection.prepareStatement(sql2);
                if(ps != null){
                    // 根据账号进行查询
                    ps.setString(1, username);
                    ps.setString(2, password);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 注册成功
        return true;
    }

}
