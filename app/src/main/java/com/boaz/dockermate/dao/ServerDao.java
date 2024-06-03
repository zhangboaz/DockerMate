package com.boaz.dockermate.dao;

import com.boaz.dockermate.entity.Server;
import com.boaz.dockermate.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerDao {


    /**
     * 根据用户id获取服务器列表
     * @param userId
     * @return
     */
    public  List<Server> getServers(Long userId) {

        List<Server> servers = new ArrayList<>();

        Connection conn = JDBCUtil.getConn();
        try{
            String sql = "select * from server where user_id = ?";
            if (conn != null){
                PreparedStatement ps = conn.prepareStatement(sql);
                if (ps != null){
                    ps.setLong(1, userId);
                    // 执行sql并返回结果
                    ResultSet rs = ps.executeQuery();
                    int count = rs.getMetaData().getColumnCount();
                    while (rs.next()){
                        Server server = new Server();
                        for (int i = 1; i <= count; i++){
                            String columnName = rs.getMetaData().getColumnName(i);
                            Object columnValue = rs.getObject(i);
                            if (columnName.equals("id")){
                                server.setId((Long) columnValue);
                            } else if (columnName.equals("server_name")) {
                                server.setServerName((String) columnValue);
                            } else if (columnName.equals("ip")){
                                server.setIp((String)columnValue);
                            } else if (columnName.equals("port")) {
                                server.setPort((String) columnValue);
                            } else if (columnName.equals("username")){
                                server.setUsername((String)columnValue);
                            } else if (columnName.equals("password")){
                                server.setPassword((String)columnValue);
                            }
                        }
                        servers.add(server);
                    }
                    JDBCUtil.close(conn, ps, rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servers;
    }
}
