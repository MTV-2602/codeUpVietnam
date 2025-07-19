<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Course" %>
<html>
<head>
    <title>Khóa học không hoạt động - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleCourseList.css">
</head>
<body>
<div class="container">
    <h2>Khóa học không hoạt động</h2>
    <%
        String error = (String) request.getAttribute("ERROR");
        if (error != null) {
    %>
    <p class="error"><%= error %></p>
    <%
        }
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        String editCourseID = request.getParameter("editCourseID");
    %>
    <div class="table-container">
        <table>
            <tr>
                <th>Mã khóa học</th>
                <th>Tên khóa học</th>
                <th>Mô tả</th>
                <th>Giá</th>
                <th>Hành động</th>
            </tr>
            <%
                if (courses != null && !courses.isEmpty()) {
                    for (Course course : courses) {
                        if (course.getCourseID().equals(editCourseID)) {
            %>
            <tr>
                <form action="CourseListInactiveController" method="post">
                    <td>
                        <%= course.getCourseID() %>
                        <input type="hidden" name="courseID" value="<%= course.getCourseID() %>">
                    </td>
                    <td>
                        <input type="text" name="courseName" value="<%= course.getCourseName() %>" required>
                    </td>
                    <td>
                        <input type="text" name="description" value="<%= course.getDescription() %>" required>
                    </td>
                    <td>
                        <input type="text" name="price" value="<%= course.getPrice() %>" required>
                    </td>
                    <td>
                        <input type="hidden" name="action" value="UpdateCourse">
                        <input type="submit" value="Cập nhật" class="btn-update">
                        <a href="CourseListInactiveController?action=ViewInactiveCourses" class="btn-cancel">Hủy</a>
                    </td>
                </form>
            </tr>
            <%
                        } else {
            %>
            <tr>
                <td><%= course.getCourseID() %></td>
                <td><%= course.getCourseName() %></td>
                <td><%= course.getDescription() %></td>
                <td>$<%= course.getPrice() %></td>
                <td>
                    <form action="CourseListInactiveController" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="DeleteCourse">
                        <input type="hidden" name="courseID" value="<%= course.getCourseID() %>">
                        <input type="submit" value="Xóa" class="btn-delete"
                               onclick="return confirm('Bạn có chắc muốn xóa hẳn khóa học này?');">
                    </form>
                    <form action="CourseListInactiveController" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="RestoreCourse">
                        <input type="hidden" name="courseID" value="<%= course.getCourseID() %>">
                        <input type="submit" value="Khôi phục" class="btn-restore"
                               onclick="return confirm('Bạn có chắc muốn khôi phục khóa học này?');">
                    </form>
                </td>
            </tr>
            <%
                        }
                    }
                } else {
            %>
            <tr>
                <td colspan="5" style="text-align:center;">Không có khóa học không hoạt động.</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div class="action-links">
        <a href="dashboard.jsp" class="btn-back">Trở về Dashboard</a>
        <a href="MainController?action=ViewActiveCourses" class="btn-active">Xem khóa học hoạt động</a>
    </div>
</div>
</body>
</html>