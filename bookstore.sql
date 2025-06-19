-- 创建数据库
CREATE DATABASE IF NOT EXISTS bookstore DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE bookstore;

-- 创建出版社表
CREATE TABLE IF NOT EXISTS publishers (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50) NOT NULL COMMENT '出版社编码',
  name VARCHAR(100) NOT NULL COMMENT '出版社名称',
  UNIQUE KEY (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出版社表';

-- 创建书籍表
CREATE TABLE IF NOT EXISTS books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  book_id VARCHAR(50) NOT NULL COMMENT '书号',
  name VARCHAR(100) NOT NULL COMMENT '书名',
  author VARCHAR(100) NOT NULL COMMENT '作者',
  publisher_id INT NOT NULL COMMENT '出版社ID',
  price DECIMAL(10,2) NOT NULL COMMENT '价格',
  description TEXT COMMENT '书籍描述',
  UNIQUE KEY (book_id),
  FOREIGN KEY (publisher_id) REFERENCES publishers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='书籍表';

-- 插入示例数据：出版社
INSERT INTO publishers (code, name) VALUES
('TH001', '清华大学出版社'),
('REN001', '人民教育出版社'),
('JX001', '机械工业出版社'),
('STEAM001', 'steam出版社'),
('WM001', '完美出版社'),
('RS001', 'R*出版社'),
('NG001', '南宫出版社'),
('YX001', '游戏科学出版社');

-- 插入示例数据：书籍
INSERT INTO books (book_id, name, author, publisher_id, price, description) VALUES
('B001', 'Web应用系统开发', '范月华', 1, 28.00, 'Web应用系统开发教程'),
('B002', 'CS2系统开发', 'V社', 5, 10000.00, 'CS2游戏开发指南'),
('B003', '赛博朋克2077', '波兰驴', 4, 168.00, '赛博朋克2077游戏指南'),
('B004', '荒野大镖客', 'R*', 6, 92.00, '荒野大镖客游戏指南'),
('B005', '艾尔登法环', '宫崎英高', 7, 178.00, '艾尔登法环游戏指南'),
('B006', '黑神话悟空', '冯冀', 8, 268.00, '黑神话悟空游戏指南');