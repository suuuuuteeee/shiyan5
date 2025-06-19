# 网上书店后台管理系统

## 项目说明

这是一个简单的网上书店后台管理系统，实现了书籍信息的展示、添加、编辑和删除功能。系统使用 Java Servlet 作为后端，使用 Vue.js 和 Ajax 技术在前端动态加载数据。

## 技术栈

- 前端：HTML、CSS、JavaScript、Vue.js、Axios
- 后端：Java Servlet、JDBC
- 数据库：MySQL

## 功能特点

1. 书籍信息的展示、添加、编辑和删除
2. 使用 Vue.js 实现前端数据的动态加载和渲染
3. 使用 Ajax 技术与后端进行数据交互
4. 使用 JDBC 技术连接数据库

## 项目结构

```
实验二/
├── index.html                # 书籍列表页面
├── add-book.html             # 添加书籍页面
├── styles.css                # 样式文件
├── script.js                 # JavaScript 文件
├── bookstore.sql             # 数据库脚本
├── WEB-INF/
│   ├── web.xml               # Web 应用配置文件
│   ├── classes/
│   │   └── com/
│   │       └── bookstore/
│   │           └── servlet/  # Servlet 类
│   └── lib/                  # 依赖库
└── README.md                 # 项目说明
```

## 数据库设计

### 出版社表 (publishers)

| 字段名 | 类型 | 说明 |
| ------ | ---- | ---- |
| id | INT | 主键，自增 |
| code | VARCHAR(50) | 出版社编码，唯一 |
| name | VARCHAR(100) | 出版社名称 |

### 书籍表 (books)

| 字段名 | 类型 | 说明 |
| ------ | ---- | ---- |
| id | INT | 主键，自增 |
| book_id | VARCHAR(50) | 书号，唯一 |
| name | VARCHAR(100) | 书名 |
| author | VARCHAR(100) | 作者 |
| publisher_id | INT | 出版社ID，外键 |
| price | DECIMAL(10,2) | 价格 |
| description | TEXT | 书籍描述 |

## 部署指南

### 1. 数据库配置

1. 创建 MySQL 数据库
2. 执行 `bookstore.sql` 脚本创建表和插入示例数据

### 2. 配置 Web 应用服务器

1. 安装 Tomcat 或其他 Java Web 应用服务器
2. 将项目部署到服务器的 webapps 目录下
3. 确保 MySQL JDBC 驱动包已添加到 WEB-INF/lib 目录中

### 3. 修改数据库连接配置

打开 `WEB-INF/classes/com/bookstore/servlet/DBUtil.java` 文件，修改数据库连接参数：

```java
private static final String URL = "jdbc:mysql://localhost:3306/bookstore?useUnicode=true&characterEncoding=utf8";
private static final String USERNAME = "root";
private static final String PASSWORD = "root";
```

### 4. 启动服务器

启动 Tomcat 或其他 Web 应用服务器，访问 `http://localhost:8080/实验二/index.html` 即可使用系统。

## 注意事项

1. 本系统仅为教学演示使用，未实现完整的安全性和错误处理机制
2. 在实际部署前，请确保修改数据库连接参数和其他配置信息
3. 如果遇到问题，请检查服务器日志和控制台输出以获取错误信息