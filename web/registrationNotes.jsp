<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Registration" %>
<html>
<head>
    <title>Quản lý ghi chú đăng ký - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleRegistrationNotes.css">
</head>
<body>
<div class="container">
    <h2>Quản lý ghi chú đăng ký</h2>

    <!-- Hiển thị thông báo lỗi nếu có -->
    <%
        String error = (String) request.getAttribute("ERROR");
        if (error != null) {
    %>
        <p class="error"><%= error %></p>
    <%
        }
        List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
    %>

    <div class="table-container">
        <table class="registration-table">
            <thead>
                <tr>
                    <th>Mã đăng ký</th>
                    <th>Mã học viên</th>
                    <th>Mã khóa học</th>
                    <th>Ngày đăng ký</th>
                    <th>Trạng thái</th>
                    <th>Ghi chú</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (registrations != null) {
                    for (Registration reg : registrations) {
            %>
                <tr>
                    <td><%= reg.getRegistrationID() %></td>
                    <td><%= reg.getUserID() %></td>
                    <td><%= reg.getCourseID() %></td>
                    <td><%= reg.getRegistrationDate() %></td>
                    <td><%= reg.getStatus() %></td>
                    <td>
                        <%= reg.getRegistrationNotes() == null ? "" : reg.getRegistrationNotes() %>
                    </td>
                    <td>
                        <!-- Form để thêm ghi chú đăng ký -->
                        <form action="ViewRegistrationNotesController" method="post" class="inline-form">
                            <input type="hidden" name="action" value="AddRegistrationNotes">
                            <input type="hidden" name="registrationID" value="<%= reg.getRegistrationID() %>">
                            <textarea name="notes" rows="2" placeholder="Thêm ghi chú..." class="notes-textarea"></textarea>
                            <br/>
                            <input type="submit" value="Lưu ghi chú" class="btn-save">
                        </form>
                        
                        <!-- Form để đánh dấu Confirmed -->
                        <form action="ViewRegistrationNotesController" method="post" class="inline-form">
                            <input type="hidden" name="action" value="MarkConfirmed">
                            <input type="hidden" name="registrationID" value="<%= reg.getRegistrationID() %>">
                            <input type="submit" value="Xác nhận" 
                                   class="btn-confirmed"
                                   <%= "Confirmed".equals(reg.getStatus()) ? "disabled" : "" %> >
                        </form>
                    </td>
                </tr>
            <%
                    }
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