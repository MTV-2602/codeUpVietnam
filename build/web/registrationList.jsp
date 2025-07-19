
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Registration" %>
<%@ page import="java.text.SimpleDateFormat" %>
<html>
<head>
    <title>Danh sách đăng ký - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleListRegistration.css">
</head>
<body>
<div class="two-col-container">
    <h2>Danh sách đăng ký của tôi</h2>
    <% 
        String error = (String) request.getAttribute("ERROR");
        if (error != null) { 
    %>
    <p class="error"><%= error %></p>
    <% 
        }
        List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
        String editRegistrationID = request.getParameter("editRegistrationID");
    %>
    <div class="table-container">
        <table class="registration-table">
            <thead>
                <tr>
                    <th>Mã đăng ký</th>
                    <th>Mã khóa học</th>
                    <th>Ngày đăng ký (yyyy-MM-dd HH:mm)</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <% if (registrations != null && !registrations.isEmpty()) { 
                    for (Registration reg : registrations) { 
                        if (reg.getRegistrationID().equals(editRegistrationID)) { %>
                <!-- Chế độ chỉnh sửa -->
                <tr>
                    <form action="MainController" method="post">
                        <td>
                            <%= reg.getRegistrationID() %>
                            <input type="hidden" name="registrationID" value="<%= reg.getRegistrationID() %>">
                        </td>
                        <td>
                            <input type="text" name="courseID" value="<%= reg.getCourseID() %>" disabled>
                        </td>
                        <td>
                            <input type="datetime-local" id="registrationDate" name="registrationDate"
                                   value="<%= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(reg.getRegistrationDate()) %>"
                                   required>
                        </td>
                        <td>
                            <input type="text" name="status" value="<%= reg.getStatus() %>" disabled>
                        </td>
                        <td>
                            <input type="hidden" name="action" value="UpdateRegistration">
                            <input type="submit" value="Cập nhật" class="btn-update">
                            <a href="MainController?action=ViewRegistrations" class="btn-cancel">Hủy</a>
                        </td>
                    </form>
                </tr>
                <% } else { %>
                <!-- Chế độ xem -->
                <tr>
                    <td><%= reg.getRegistrationID() %></td>
                    <td><%= reg.getCourseID() %></td>
                    <td><%= new SimpleDateFormat("yyyy-MM-dd HH:mm").format(reg.getRegistrationDate()) %></td>
                    <td><%= reg.getStatus() %></td>
                    <td>
                        <form action="MainController" method="get" style="display:inline;">
                            <input type="hidden" name="action" value="ViewRegistrations">
                            <input type="hidden" name="editRegistrationID" value="<%= reg.getRegistrationID() %>">
                            <input type="submit" value="Sửa" class="btn-edit">
                        </form>
                        <form action="MainController" method="post" style="display:inline;">
                            <input type="hidden" name="action" value="DeleteRegistration">
                            <input type="hidden" name="registrationID" value="<%= reg.getRegistrationID() %>">
                            <input type="submit" value="Xóa" class="btn-delete"
                                   onclick="return confirm('Bạn có chắc muốn xóa đăng ký này?');">
                        </form>
                    </td>
                </tr>
                <% } 
                    } 
                } else { %>
                <tr>
                    <td colspan="5" style="text-align:center;">Không có đăng ký nào.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>

    <div class="right-col">
        <form class="course-hours-form">
            <h3>Giờ đăng ký khóa học</h3>
            <p>-- Cả tuần --</p>
            <p>-- Từ 08:00 đến 17:00 --</p>
            <p>-- Đặt trước tối đa 4 ngày --</p>
        </form>
    </div>
</div>
<p>
    <a href="dashboard.jsp" class="back-link">Trở về Dashboard</a>
</p>

<!-- JavaScript giới hạn thời gian đăng ký -->
<script>
document.addEventListener('DOMContentLoaded', function () {
    const dateTimeInput = document.getElementById('registrationDate');
    if (!dateTimeInput) return;

    function formatLocalDateTime(date) {
        let y = date.getFullYear();
        let m = String(date.getMonth() + 1).padStart(2, '0');
        let d = String(date.getDate()).padStart(2, '0');
        let hh = String(date.getHours()).padStart(2, '0');
        let mm = String(date.getMinutes()).padStart(2, '0');
        return `${y}-${m}-${d}T${hh}:${mm}`;
    }

    let now = new Date();

    let minDateTime = new Date(now);
    if (minDateTime.getHours() < 8) {
        minDateTime.setHours(8, 0, 0, 0);
    } else if (minDateTime.getHours() >= 17) {
        minDateTime.setDate(minDateTime.getDate() + 1);
        minDateTime.setHours(8, 0, 0, 0);
    }

    let maxDateTime = new Date(minDateTime);
    maxDateTime.setDate(maxDateTime.getDate() + 4);
    maxDateTime.setHours(17, 0, 0, 0);

    dateTimeInput.min = formatLocalDateTime(minDateTime);
    dateTimeInput.max = formatLocalDateTime(maxDateTime);
    dateTimeInput.value = formatLocalDateTime(minDateTime);

    dateTimeInput.addEventListener('change', function (e) {
        let chosen = new Date(e.target.value);

        if (chosen < minDateTime) {
            alert("Bạn không thể đăng ký ngày trong quá khứ.");
            e.target.value = formatLocalDateTime(minDateTime);
        } else if (chosen > maxDateTime) {
            alert("Bạn chỉ được đăng ký trước tối đa 4 ngày.");
            e.target.value = formatLocalDateTime(maxDateTime);
        } else {
            let h = chosen.getHours();
            if (h < 8) {
                alert("Giờ phải từ 08:00!");
                chosen.setHours(8, 0, 0, 0);
                e.target.value = formatLocalDateTime(chosen);
            } else if (h >= 17) {
                alert("Giờ phải trước 17:00!");
                chosen.setHours(17, 0, 0, 0);
                e.target.value = formatLocalDateTime(chosen);
            }
        }
    });
});
</script>
</body>
</html>
