package com.boaz.dockermate.utils;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtils {

    private static String driver = "com.mysql.jdbc.Driver";// MySql驱动
    private static String user = "root";// 用户名
    private static String password = "123456";// 密码

    public static Connection getConn(String dbname) {
        Connection connection = null;
        try {
            Class.forName(driver);// 动态加载类
            String ip = "123.57.23.42"; // 写成本机地址，不能写成localhost
            // 尝试建立到给定数据库URL的连接
            connection = DriverManager.getConnection("jdbc:mysql://" + ip
                    + ":13306/" + dbname, user, password);
        } catch (Exception e) {
            Log.i("DBUtils", "Exception");
            e.printStackTrace();
        }
        return connection;
    }

}
