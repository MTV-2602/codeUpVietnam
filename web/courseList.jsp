<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Course" %>
<html>
<head>
    <title>Danh sách khóa học - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleCourseList.css">
</head>
<body>
<div class="container">
    <h2>Danh sách khóa học</h2>
    <!-- Hiển thị thông báo lỗi nếu có -->
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
                <th>Hoạt động</th>
            </tr>
            <%
                if (courses != null && !courses.isEmpty()) {
                    for (Course course : courses) {
                        if (course.getCourseID().equals(editCourseID)) {
            %>
            <tr>
                <form action="MainController" method="post">
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
                        <a href="MainController?action=ViewActiveCourses" class="btn-cancel">Hủy</a>
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
                    <form action="MainController" method="get" style="display:inline;">
                        <input type="hidden" name="action" value="ViewActiveCourses">
                        <input type="hidden" name="editCourseID" value="<%= course.getCourseID() %>">
                        <input type="submit" value="Sửa" class="btn-edit">
                    </form>
                    <form action="MainController" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="DeleteCourse">
                        <input type="hidden" name="courseID" value="<%= course.getCourseID() %>">
                        <input type="submit" value="Xóa" class="btn-delete" 
                               onclick="return confirm('Bạn có chắc muốn xóa khóa học này?');">
                    </form>
                    <form action="MainController" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="SetActive">
                        <input type="hidden" name="courseID" value="<%= course.getCourseID() %>">
                        <input type="submit" value="Ẩn" class="btn-hide"
                               onclick="return confirm('Bạn có chắc muốn ẩn khóa học này?');">
                    </form>
                </td>
            </tr>
            <%
                        }
                    }
                } else {
            %>
            <tr>
                <td colspan="5" style="text-align:center;">Không có khóa học nào được tìm thấy.</td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div class="action-links">
        <a href="dashboard.jsp" class="btn-back">Trở về Dashboard</a>
        <a href="createCourse.jsp" class="btn-create">Tạo khóa học</a>
        <a href="CourseListInactiveController?action=ViewInactiveCourses">Xem khóa học không hoạt động</a>
    </div>
</div>
</body>
</html>
