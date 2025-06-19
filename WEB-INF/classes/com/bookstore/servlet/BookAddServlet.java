package com.bookstore.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 添加书籍 Servlet
 */
@WebServlet("/addBook")
public class BookAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置请求和响应编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        // response.setCharacterEncoding("UTF-8"); // Already set by setContentType with charset

        PrintWriter out = response.getWriter();
        
        // 获取表单数据
        String bookId = request.getParameter("bookId");
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String publisherIdStr = request.getParameter("publisher");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        
        // 数据验证
        if (bookId == null || bookId.trim().isEmpty() ||
            name == null || name.trim().isEmpty() ||
            author == null || author.trim().isEmpty() ||
            publisherIdStr == null || publisherIdStr.trim().isEmpty() ||
            priceStr == null || priceStr.trim().isEmpty()) {
            
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\": \"error\", \"message\": \"必填字段不能为空\"}");
            out.flush();
            return;
        }
        
        int publisherId;
        double price;
        try {
            publisherId = Integer.parseInt(publisherIdStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"status\": \"error\", \"message\": \"数据格式错误: 'publisher' and 'price' must be numbers.\"}");
            out.flush();
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection(); // This can throw RuntimeException
            String sql = "INSERT INTO books (book_id, name, author, publisher_id, price, description) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookId);
            pstmt.setString(2, name);
            pstmt.setString(3, author);
            pstmt.setInt(4, publisherId);
            pstmt.setDouble(5, price);
            pstmt.setString(6, description);
            
            int result = pstmt.executeUpdate();
            
            if (result > 0) {
                out.print("{\"status\": \"success\", \"message\": \"添加成功\"}");
            } else {
                // This case might indicate an issue not throwing an exception but still failing.
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"status\": \"error\", \"message\": \"添加失败，数据库未返回影响行数\"}");
            }
        } catch (RuntimeException e) { // Catch RuntimeExceptions from DBUtil
            e.printStackTrace(); // Log for server-side debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String clientErrorMessage = "Failed to add book due to a server error. Please try again later.";
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("database driver not found")) {
                clientErrorMessage = "Database driver not found. Cannot add book.";
            } else if (e.getMessage() != null && e.getMessage().toLowerCase().contains("failed to connect to database")) {
                 clientErrorMessage = "Could not connect to the database. Cannot add book.";
            }
            out.print("{\"status\": \"error\", \"message\": \"" + clientErrorMessage.replace("\"", "\\\"") + "\"}");
        } catch (SQLException e) { // Catch SQLExceptions from PreparedStatement, executeUpdate, etc.
            e.printStackTrace(); // Log for server-side debugging
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // Check for duplicate entry specific error (e.g., MySQL error code 1062 for duplicate unique key)
            if (e.getErrorCode() == 1062) { // MySQL specific error code for duplicate entry
                 out.print("{\"status\": \"error\", \"message\": \"添加失败: 书号 '" + bookId + "' 已存在.\"}");
            } else {
                 out.print("{\"status\": \"error\", \"message\": \"数据库错误: " + e.getMessage().replace("\"", "\\\"") + "\"}");
            }
        } finally {
            DBUtil.close(conn, pstmt, null); // Close resources
            if (out != null) {
                out.flush();
                // out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests for /addBook to the add-book.html page
        response.sendRedirect("add-book.html");
    }
}