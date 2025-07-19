
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng nhập - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleLogin.css">
</head>
<body>
<div class="container">
    <h2>Đăng nhập</h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="Login">
        
        <label for="email">Email:</label>
        <input type="text" name="email" placeholder="Nhập email" required>
        
        <label for="password">Mật khẩu:</label>
        <input type="password" name="password" placeholder="Nhập mật khẩu" required>
        
        <input type="submit" value="Đăng nhập">
    </form>
    
    <!-- Hiển thị thông báo lỗi nếu có -->
    <%
        String error = (String) request.getAttribute("ERROR");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
    %>
    
    <p>Chưa có tài khoản? <a href="register.jsp">Đăng ký ngay</a></p>
</div>
</body>
</html>
