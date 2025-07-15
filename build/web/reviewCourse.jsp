
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Registration" %>
<html>
<head>
    <title>Đánh giá khóa học đã hoàn thành - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleReviewCourses.css">
</head>
<body>
<div class="container">
    <h2>Đánh giá khóa học đã hoàn thành</h2>

    <!-- Hiển thị thông báo lỗi nếu có -->
    <%
        String error = (String) request.getAttribute("ERROR");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
        List<Registration> completedRegistrations = (List<Registration>) request.getAttribute("completedRegistrations");
    %>

    <div class="table-container">
        <table class="review-table">
            <thead>
                <tr>
                    <th>Mã khóa học</th>
                    <th>Điểm số (1-5)</th>
                    <th>Bình luận</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (completedRegistrations != null && !completedRegistrations.isEmpty()) {
                    for (Registration reg : completedRegistrations) {
            %>
                <tr>
                    <td><%= reg.getCourseID() %></td>
                    <td>
                        <form action="MainController" method="post" class="review-form">
                            <input type="hidden" name="action" value="ReviewCourse">
                            <input type="hidden" name="courseID" value="<%= reg.getCourseID() %>">
                            <input type="number" name="rating" min="1" max="5" required 
                                   placeholder="1 - 5" class="input-rating">
                    </td>
                    <td>
                            <textarea name="comments" rows="2" placeholder="Bình luận của bạn..." class="input-comments"></textarea>
                    </td>
                    <td>
                            <input type="submit" value="Gửi đánh giá" class="btn-submit">
                        </form>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="4" style="text-align:center;">Chưa có khóa học nào đã hoàn thành</td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <p><a href="dashboard.jsp" class="back-link">Trở về Dashboard</a></p>
</div>
</body>
</html>
