/*
 * This file defines the CourseListInactiveController for the CodeUp Vietnam system, 
 * which handles viewing and managing inactive programming courses.
 */
package controller;

import dao.CourseDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class CourseListInactiveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        CourseDAO dao = new CourseDAO();

        if ("ViewInActiveCourses".equals(action)) {
            try {
                request.setAttribute("courses", dao.getInactiveCourses());
                request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("ERROR", "Không thể lấy danh sách khóa học không hoạt động!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } else if ("DeleteCourse".equals(action)) {
            deleteCourse(request, response, dao);
        } else if ("RestoreCourse".equals(action)) {
            restoreCourse(request, response, dao);
        } else {
            request.setAttribute("ERROR", "Action không hỗ trợ!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response, CourseDAO dao)
            throws ServletException, IOException {
        String courseID = request.getParameter("courseID");
        if (courseID == null || courseID.trim().isEmpty()) {
            request.setAttribute("ERROR", "Mã khóa học không hợp lệ!");
            request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            return;
        }
        try {
            boolean deleted = dao.deleteCourse(courseID);
            if (deleted) {
                response.sendRedirect("CourseListInactiveController?action=ViewInActiveCourses");
            } else {
                request.setAttribute("ERROR", "Không thể xóa khóa học này!");
                request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Lỗi hệ thống khi xóa khóa học!");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void restoreCourse(HttpServletRequest request, HttpServletResponse response, CourseDAO dao)
            throws ServletException, IOException {
        String courseID = request.getParameter("courseID");
        if (courseID == null || courseID.trim().isEmpty()) {
            request.setAttribute("ERROR", "Mã khóa học không hợp lệ!");
            request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            return;
        }
        boolean restored = false;
        try {
            restored = dao.restoreActive(courseID);
            if (restored) {
                response.sendRedirect("CourseListInactiveController?action=ViewInActiveCourses");
            } else {
                request.setAttribute("ERROR", "Không thể khôi phục khóa học này!");
                request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Đã xảy ra lỗi khi khôi phục khóa học: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}