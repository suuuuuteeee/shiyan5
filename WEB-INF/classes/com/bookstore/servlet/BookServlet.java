package com.bookstore.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 书籍数据 Servlet
 */
@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型为 JSON
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter out = response.getWriter();
        
        // 获取所有书籍数据
        List<Book> books = getAllBooks();
        
        // 构建 JSON 响应
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");
        jsonBuilder.append("\"status\": \"success\",");
        jsonBuilder.append("\"data\": [");
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"id\": " + book.getId() + ",");
            jsonBuilder.append("\"bookId\": \"" + book.getBookId() + "\",");
            jsonBuilder.append("\"name\": \"" + book.getName() + "\",");
            jsonBuilder.append("\"author\": \"" + book.getAuthor() + "\",");
            jsonBuilder.append("\"publisherId\": " + book.getPublisherId() + ",");
            jsonBuilder.append("\"publisherName\": \"" + book.getPublisherName() + "\",");
            jsonBuilder.append("\"price\": " + book.getPrice() + ",");
            jsonBuilder.append("\"description\": \"" + book.getDescription() + "\"");
            jsonBuilder.append("}");
            
            if (i < books.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        
        jsonBuilder.append("]");
        jsonBuilder.append("}");
        
        out.print(jsonBuilder.toString());
        out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    
    /**
     * 获取所有书籍数据
     */
    private List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.id, b.book_id, b.name, b.author, b.publisher_id, p.name as publisher_name, "
                    + "b.price, b.description FROM books b "
                    + "LEFT JOIN publishers p ON b.publisher_id = p.id";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setBookId(rs.getString("book_id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisherId(rs.getInt("publisher_id"));
                book.setPublisherName(rs.getString("publisher_name"));
                book.setPrice(rs.getDouble("price"));
                book.setDescription(rs.getString("description"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        
        return books;
    }
}