// 书籍数据存储
let books = JSON.parse(localStorage.getItem('books')) || [];

// 页面加载时执行
document.addEventListener('DOMContentLoaded', function() {
    // 检查是否在添加书籍页面
    if (document.getElementById('addBookForm')) {
        // 添加书籍表单提交事件
        document.getElementById('addBookForm').addEventListener('submit', function(e) {
            e.preventDefault();
            addBook();
        });
    }
});

// 添加书籍函数
function addBook() {
    // 获取表单数据
    const bookId = document.getElementById('bookId').value;
    const bookName = document.getElementById('bookName').value;
    const author = document.getElementById('author').value;
    const publisher = document.getElementById('publisher').value;
    const price = document.getElementById('price').value;
    const category = document.querySelector('input[name="category"]:checked').value;
    const description = document.getElementById('description').value;
    
    // 创建新书籍对象
    const newBook = {
        id: bookId,
        name: bookName,
        author: author,
        publisher: publisher,
        category: category,
        price: price,
        description: description
    };
    
    // 获取现有书籍数据
    let books = JSON.parse(localStorage.getItem('books')) || [];
    
    // 添加新书籍
    books.push(newBook);
    
    // 保存到本地存储
    localStorage.setItem('books', JSON.stringify(books));
    
    // 提示添加成功
    alert('添加成功！');
    
    // 重置表单
    document.getElementById('addBookForm').reset();
    
    // 可选：跳转回列表页
    // window.location.href = 'index.html';
}