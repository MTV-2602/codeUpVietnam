<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="dto.Course" %>
<html>
<head>
    <title>Đăng ký khóa học - CodeUp Vietnam</title>
    <link rel="stylesheet" type="text/css" href="css/StyleBookRegistration.css">
</head>
<body>
<div class="container">
    <h2>Đăng ký khóa học</h2>
    
    <div class="two-col-container">
        <!-- Cột trái: Form đăng ký khóa học -->
        <div class="left-col">
            <form action="MainController" method="post" class="registration-form">
                <input type="hidden" name="action" value="BookRegistration">
                
                <label for="courseID">Chọn khóa học:</label>
                <select name="courseID" required>
                    <%
                        List<Course> courses = (List<Course>) request.getAttribute("courses");
                        if (courses != null && !courses.isEmpty()) {
                            for (Course course : courses) {
                    %>
                    <option value="<%= course.getCourseID() %>">
                        <%= course.getCourseID() %> - <%= course.getCourseName() %> - $<%= course.getPrice() %>
                    </option>
                    <%
                            }
                        } else {
                    %>
                    <option value="">Không có khóa học nào</option>
                    <%
                        }
                    %>
                </select>
                
                <label for="registrationDate">Ngày giờ:</label>
                <input type="datetime-local" name="registrationDate" id="registrationDate" required>
                
                <input type="submit" value="Đăng ký khóa học">
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
        </div>
        
        <!-- Cột phải: Giờ đăng ký khóa học -->
        <div class="right-col">
            <form class="course-hours-form">
                <h3>Giờ đăng ký khóa học</h3>
                <p>-- Cả tuần --</p>
                <p>-- Từ 8h sáng đến 5h chiều --</p>
                <p>-- Đặt trước tối đa 4 ngày --</p>
            </form>
        </div>
    </div>
    
    <p>
        <a href="dashboard.jsp" class="back-link">Trở về Dashboard</a>
    </p>
</div>

<!-- Script cài đặt min, max cho input datetime-local và cảnh báo nếu chọn ngoài khoảng -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    const dateTimeInput = document.getElementById('registrationDate');
    if (!dateTimeInput) return;

    // Hàm định dạng ngày giờ thành yyyy-MM-ddTHH:mm
    function formatLocalDateTime(date) {
        let y = date.getFullYear();
        let m = String(date.getMonth() + 1).padStart(2, '0');
        let d = String(date.getDate()).padStart(2, '0');
        let hh = String(date.getHours()).padStart(2, '0');
        let mm = String(date.getMinutes()).padStart(2, '0');
        return `${y}-${m}-${d}T${hh}:${mm}`;
    }

    // Lấy thời gian hiện tại
    let now = new Date();

    // Tính min: Nếu giờ < 8 thì 08:00 hôm nay, nếu >= 17 thì 08:00 ngày mai
    let minDateTime = new Date(now);
    if (minDateTime.getHours() < 8) {
        minDateTime.setHours(8, 0, 0, 0);
    } else if (minDateTime.getHours() >= 17) {
        minDateTime.setDate(minDateTime.getDate() + 1);
        minDateTime.setHours(8, 0, 0, 0);
    }

    // Tính max: min + 4 ngày, 17:00
    let maxDateTime = new Date(minDateTime);
    maxDateTime.setDate(maxDateTime.getDate() + 4);
    maxDateTime.setHours(17, 0, 0, 0);

    // Gán min, max để trình duyệt khóa các giá trị ngoài khoảng
    dateTimeInput.min = formatLocalDateTime(minDateTime);
    dateTimeInput.max = formatLocalDateTime(maxDateTime);

    // Đặt giá trị mặc định là min
    dateTimeInput.value = formatLocalDateTime(minDateTime);

    // Xử lý khi người dùng thay đổi giá trị
    dateTimeInput.addEventListener('change', function(e) {
        let chosen = new Date(e.target.value);

        if (chosen < minDateTime) {
            alert("Bạn không thể đăng ký ngày trong quá khứ");
            e.target.value = formatLocalDateTime(minDateTime);
        } 
        else if (chosen > maxDateTime) {
            alert("Bạn chỉ được đăng ký trước tối đa 4 ngày");
            e.target.value = formatLocalDateTime(maxDateTime);
        } 
        else {
            // Kiểm tra giờ < 8 hoặc >= 17
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
