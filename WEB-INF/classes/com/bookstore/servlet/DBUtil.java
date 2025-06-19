package com.bookstore.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接工具类
 */
public class DBUtil {
    // 数据库连接参数
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    
    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (conn == null) {
                // This case might not be strictly necessary if DriverManager.getConnection throws SQLException on failure
                // but it's an extra check.
                throw new SQLException("Failed to establish database connection: DriverManager.getConnection returned null.");
            }
            return conn;
        } catch (ClassNotFoundException e) {
            // Log the original exception if logging framework was present
            // e.g., LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found", e);
            e.printStackTrace(); // Keep for now, or remove if too noisy and relying on RuntimeException
            throw new RuntimeException("Database driver not found: " + e.getMessage(), e);
        } catch (SQLException e) {
            // Log the original exception
            // e.g., LOGGER.log(Level.SEVERE, "SQL error when connecting to database", e);
            e.printStackTrace(); // Keep for now
            throw new RuntimeException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
    
    /**
     * 关闭数据库连接资源
     */
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}