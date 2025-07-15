```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Đăng ký - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleRegister.css">
</head>
<body>
<div class="container">
    <h2>Đăng ký tài khoản</h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="Register">

        <label for="fullName">Họ và tên:</label>
        <input type="text" name="fullName" placeholder="Nhập họ và tên" required>

        <label for="email">Email:</label>
        <input type="email" name="email" placeholder="Nhập email" required>

        <label for="phoneNumber">Số điện thoại:</label>
        <input type="text" name="phoneNumber" placeholder="Nhập số điện thoại" required>

        <label for="password">Mật khẩu:</label>
        <input type="password" name="password" placeholder="Nhập mật khẩu" required>

        <input type="submit" value="Đăng ký">
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

    <p>Đã có tài khoản? <a href="login.jsp">Đăng nhập ngay</a></p>
</div>
</body>
</html>
```