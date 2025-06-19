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
        response.setCharacterEncoding("UTF-8");
        
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
            
            // 返回错误信息
            out.print("{\"status\": \"error\", \"message\": \"必填字段不能为空\"}");
            return;
        }
        
        // 转换数据类型
        int publisherId;
        double price;
        try {
            publisherId = Integer.parseInt(publisherIdStr);
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            out.print("{\"status\": \"error\", \"message\": \"数据格式错误\"}");
            return;
        }
        
        // 添加书籍到数据库
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
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
                // 添加成功
                out.print("{\"status\": \"success\", \"message\": \"添加成功\"}");
            } else {
                // 添加失败
                out.print("{\"status\": \"error\", \"message\": \"添加失败\"}");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("{\"status\": \"error\", \"message\": \"数据库错误: " + e.getMessage() + "\"}");
        } finally {
            DBUtil.close(conn, pstmt, null);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("add-book.html");
    }
}