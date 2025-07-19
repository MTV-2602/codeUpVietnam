
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head>
    <title>Dashboard - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleDashBoard.css">
</head>
<body>
<%
    // Kiểm tra session và chuyển hướng nếu chưa đăng nhập
    if (session == null || session.getAttribute("userID") == null) {
        response.sendRedirect("login.jsp");
    }
    String fullName = (String) session.getAttribute("fullName");
    String roleID = (String) session.getAttribute("roleID");
%>
<div class="container dashboard-container">
    <h2>Chào mừng, <%= fullName %>!</h2>
    
    <!-- Form chứa các nút điều hướng -->
    <form class="dashboard-form" action="MainController" method="get">
        <div class="button-group">
            <% if ("ADM".equals(roleID)) { %>
                <button type="submit" name="action" value="ViewActiveCourses">Quản lý khóa học</button>
            <% } else if ("INS".equals(roleID)) { %>
                <button type="submit" name="action" value="ViewRegistrationNotes">Xem ghi chú đăng ký</button>
            <% } else if ("STU".equals(roleID)) { %>
                <button type="submit" name="action" value="BookRegistration">Đăng ký khóa học</button>
                <button type="submit" name="action" value="ViewRegistrations">Xem đăng ký</button>
                <button type="submit" name="action" value="ReviewCourse">Đánh giá khóa học</button>
                <button type="submit" name="action" value="ViewReview">Xem đánh giá</button>
            <% } %>
            <button type="submit" name="action" value="Logout">Đăng xuất</button>
        </div>
    </form>
</div>
</body>
</html>
