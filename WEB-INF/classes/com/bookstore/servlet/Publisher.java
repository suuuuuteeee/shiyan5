package com.bookstore.servlet;

/**
 * 出版社实体类
 */
public class Publisher {
    private int id;          // 自增ID
    private String code;     // 出版社编码
    private String name;     // 出版社名称
    
    public Publisher() {
    }
    
    public Publisher(int id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}