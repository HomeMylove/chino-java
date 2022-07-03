package com.homemylove.core.basedao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnUtil {
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    private static final Properties properties;

    static {
        InputStream resource = ConnUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        properties = new Properties();

        try {
            properties.load(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static Connection createConn() {
        try {
            DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Connection getConn() {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = createConn();
            assert conn != null;
            threadLocal.set(conn);
        }
        return threadLocal.get();
    }

    // 移除
    public static void closeConn() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null) {
            return;
        }
        if (!conn.isClosed()) {
            conn.close();
            threadLocal.set(null);
        }
    }
}


