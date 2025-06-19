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
        
        try {
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
                jsonBuilder.append("\"id\": ").append(book.getId()).append(",");
                jsonBuilder.append("\"bookId\": \"").append(book.getBookId()).append("\",");
                jsonBuilder.append("\"name\": \"").append(book.getName()).append("\",");
                jsonBuilder.append("\"author\": \"").append(book.getAuthor()).append("\",");
                jsonBuilder.append("\"publisherId\": ").append(book.getPublisherId()).append(",");
                jsonBuilder.append("\"publisherName\": \"").append(book.getPublisherName()).append("\",");
                jsonBuilder.append("\"price\": ").append(book.getPrice()).append(",");
                jsonBuilder.append("\"description\": \"").append(book.getDescription()).append("\"");
                jsonBuilder.append("}");

                if (i < books.size() - 1) {
                    jsonBuilder.append(",");
                }
            }

            jsonBuilder.append("]");
            jsonBuilder.append("}");
            
            out.print(jsonBuilder.toString());

        } catch (RuntimeException e) {
            // Log for server-side debugging, e.g., using a logging framework
            e.printStackTrace();
            // Inform the client about the error
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // Set HTTP status to 500
            // Sanitize error message for client
            String clientErrorMessage = "Failed to retrieve books due to a server error. Please try again later.";
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("database driver not found")) {
                clientErrorMessage = "Database driver not found. Please contact administrator.";
            } else if (e.getMessage() != null && e.getMessage().toLowerCase().contains("failed to connect to database")) {
                 clientErrorMessage = "Could not connect to the database. Please check server configuration.";
            }
            // Escape double quotes in the clientErrorMessage for valid JSON
            out.print("{\"status\": \"error\", \"message\": \"" + clientErrorMessage.replace("\"", "\\\"") + "\"}");
        } finally {
            if (out != null) {
                out.flush();
                // out.close(); // Closing the writer is generally good practice, managed by servlet container
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // For this servlet, POST might not be conventionally used for fetching all books,
        // but if it is, it should have similar error handling.
        // Or, it could return a "Method Not Allowed" error.
        // Original just called doGet. Let's keep that but be mindful.
        doGet(request, response);
    }
    
    /**
     * 获取所有书籍数据
     * This method will now propagate RuntimeException from DBUtil.getConnection()
     */
    private List<Book> getAllBooks() { // Consider adding "throws SQLException, RuntimeException" if not caught inside
        List<Book> books = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection(); // This can now throw RuntimeException
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
            // This catch block is for SQLExceptions from prepareStatement, executeQuery, etc.
            // RuntimeExceptions from DBUtil.getConnection() will propagate out of this method
            // unless also caught here.
            // For robustness, let's also catch RuntimeException here and rethrow or handle.
            // However, the plan was to handle it in doGet.
            e.printStackTrace(); // Log this specific SQL error
            // Optionally, rethrow as a custom application exception or a new RuntimeException
            // throw new RuntimeException("SQL error during book retrieval: " + e.getMessage(), e);
        }
        // RuntimeException from DBUtil.getConnection() will bypass the above SQLException catch.
        // To make sure it's handled before finally, it should be caught here or in doGet.
        // The current structure has it handled in doGet.
        finally {
            DBUtil.close(conn, pstmt, rs);
        }
        
        return books;
    }
}