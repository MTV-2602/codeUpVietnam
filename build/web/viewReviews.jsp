
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Review" %>
<html>
<head>
    <title>Đánh giá của tôi - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleViewReviews.css">
</head>
<body>
<div class="container">
    <h2>Đánh giá của tôi</h2>

    <%
        String error = (String) request.getAttribute("ERROR");
        String success = (String) request.getAttribute("SUCCESS");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        } else if (success != null) {
    %>
        <p class="success"><%= success %></p>
    <%
        }
        
        List<Review> userReviews = (List<Review>) request.getAttribute("userReviews");
    %>

    <div class="table-container">
        <table class="review-table">
            <thead>
                <tr>
                    <th>Mã đánh giá</th>
                    <th>Mã khóa học</th>
                    <th>Điểm số</th>
                    <th>Bình luận</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (userReviews != null && !userReviews.isEmpty()) {
                    for (Review r : userReviews) {
            %>
            <tr>
                <form action="UpdateReviewController" method="post">
                    <td><%= r.getReviewID() %></td>
                    <td><%= r.getCourseID() %></td>
                    <td>
                        <select name="rating" class="select-rating">
                            <%
                                for (int i = 1; i <= 5; i++) {
                                    String selected = (i == r.getRating()) ? "selected" : "";
                            %>
                            <option value="<%= i %>" <%= selected %>><%= i %></option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>
                        <textarea name="comments" rows="2" class="comments-input"><%= r.getComments() %></textarea>
                    </td>
                    <td>
                        <input type="hidden" name="reviewID" value="<%= r.getReviewID() %>">
                        <button type="submit" name="action" value="update" class="btn-update">Cập nhật</button>
                        <button type="submit" name="action" value="delete" class="btn-delete"
                                onclick="return confirm('Bạn có chắc muốn xóa đánh giá này?');">Xóa</button>
                    </td>
                </form>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="5" style="text-align:center;">Bạn chưa có đánh giá nào.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <p>
        <a href="dashboard.jsp" class="back-link">Trở về Dashboard</a>
    </p>
</div>
</body>
</html>
