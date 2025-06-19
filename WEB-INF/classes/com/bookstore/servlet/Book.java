package com.bookstore.servlet;

/**
 * 书籍实体类
 */
public class Book {
    private int id;              // 自增ID
    private String bookId;       // 书号
    private String name;         // 书名
    private String author;       // 作者
    private int publisherId;     // 出版社ID（关联出版社表的主键）
    private String publisherName; // 出版社名称（非数据库字段，用于显示）
    private double price;        // 价格
    private String description;   // 书籍描述
    
    public Book() {
    }
    
    public Book(int id, String bookId, String name, String author, int publisherId, 
               String publisherName, double price, String description) {
        this.id = id;
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.price = price;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public int getPublisherId() {
        return publisherId;
    }
    
    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }
    
    public String getPublisherName() {
        return publisherName;
    }
    
    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}