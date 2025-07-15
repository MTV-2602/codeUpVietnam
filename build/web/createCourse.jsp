```jsp<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head>
    <title>Tạo khóa học mới - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleCreateCourse.css">
</head>
<body>
<%
    // Kiểm tra session và quyền (roleID = ADM)
    if (session == null || session.getAttribute("userID") == null || !"ADM".equals(session.getAttribute("roleID"))) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<div class="container">
    <h2>Tạo khóa học mới</h2>
    <form action="MainController" method="post">
        <input type="hidden" name="action" value="CreateCourse">

        <label for="courseName">Tên khóa học:</label>
        <input type="text" name="courseName" placeholder="Nhập tên khóa học" required>

        <label for="description">Mô tả:</label>
        <textarea name="description" rows="5" placeholder="Nhập mô tả khóa học" required></textarea>

        <label for="price">Giá:</label>
        <input type="number" step="0.01" name="price" placeholder="Nhập giá" required>

        <input type="submit" value="Tạo khóa học">
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

    <p><a href="dashboard.jsp">Trở về Dashboard</a></p>
</div>
</body>
</html>
