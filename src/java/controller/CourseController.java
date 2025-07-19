
package controller;

import dao.CourseDAO;
import dto.Course;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class CourseController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        CourseDAO courseDAO = new CourseDAO();

        if ("UpdateCourse".equals(action)) {
            String courseID = request.getParameter("courseID");
            String courseName = request.getParameter("courseName");
            String description = request.getParameter("description");
            String priceStr = request.getParameter("price");

            // Parse price
            double price = 0;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                request.setAttribute("ERROR", "Invalid price!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
                return;
            }

            // Get existing course
            Course oldCourse = courseDAO.getCourseByID(courseID);
            if (oldCourse == null) {
                request.setAttribute("ERROR", "Course not found!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
                return;
            }
            // Update
            oldCourse.setCourseName(courseName);
            oldCourse.setDescription(description);
            oldCourse.setPrice(price);

            boolean updated = courseDAO.updateCourse(oldCourse);
            if (updated) {
                // Redirect to Active
                response.sendRedirect("MainController?action=ViewActiveCourses");
            } else {
                request.setAttribute("ERROR", "Failed to update course!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
            }

        } else if ("DeleteCourse".equals(action)) {
            String courseID = request.getParameter("courseID");
            boolean deleted = false;
            try {
                deleted = courseDAO.deleteCourse(courseID);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("ERROR", "Lỗi khi xóa khóa học: " + e.getMessage());
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
                return;
            }
            if (deleted) {
                // Xóa thành công, load lại danh sách
                response.sendRedirect("MainController?action=ViewActiveCourses");
            } else {
                request.setAttribute("ERROR", "Không thể xóa khóa học này!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
            }
            return;

        } else if ("ViewCourses".equals(action)) {
            // Display course list
            List<Course> courses = courseDAO.getAllCourses();
            request.setAttribute("courses", courses);
            request.getRequestDispatcher("courseList.jsp").forward(request, response);

        } else if ("SetActive".equals(action)) {
            // Set course to inactive (status = false)
            String courseID = request.getParameter("courseID");
            boolean success = courseDAO.setInactive(courseID);
            if (success) {
                response.sendRedirect("MainController?action=ViewActiveCourses");
            } else {
                request.setAttribute("ERROR", "Unable to set course inactive!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
            }

        } else if ("RestoreActive".equals(action)) {
            // Restore course to active (status = true)
            String courseID = request.getParameter("courseID");
            boolean success = courseDAO.restoreActive(courseID);
            if (success) {
                response.sendRedirect("MainController?action=ViewActiveCourses");
            } else {
                request.setAttribute("ERROR", "Unable to restore course!");
                request.getRequestDispatcher("courseList.jsp").forward(request, response);
            }

        } else if ("ViewActiveCourses".equals(action)) {
            // Display active courses (status = true)
            List<Course> activeCourses = courseDAO.getActiveCourses();
            request.setAttribute("courses", activeCourses);
            request.getRequestDispatcher("courseList.jsp").forward(request, response);

        } else {
            // Unsupported action
            request.setAttribute("ERROR", "Action not supported!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "CourseController: Update, Delete, View, SetActive, RestoreActive, ViewActiveCourses in CodeUp Vietnam";
    }
}