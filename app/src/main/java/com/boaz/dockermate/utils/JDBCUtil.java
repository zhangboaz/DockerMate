package com.boaz.dockermate.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动
    private static String user = "root";
    private static String password = "123456";
    private static String dbname = "linux_server";

    public static Connection getConn() {
        Connection connection = null;
        try {
            Class.forName(driver);// 动态加载类
            String ip = "123.57.23.42";
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip
                    + ":13306/" + dbname, user, password);
        } catch (Exception e) {
            Log.i("DBUtils", "Exception");
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
