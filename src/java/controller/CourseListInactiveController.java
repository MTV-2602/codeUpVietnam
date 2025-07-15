/*
 * This file defines the CourseListInactiveController for the CodeUp Vietnam system, 
 * which handles viewing and managing inactive programming courses.
 */
package controller;

import dao.CourseDAO;
import dto.Course;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class CourseListInactiveController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        CourseDAO courseDAO = new CourseDAO();

        if ("ViewInActiveCourses".equals(action)) {
            // Get courses with status=false
            List<Course> inactiveList = courseDAO.getInactiveCourses();
            request.setAttribute("courses", inactiveList);
            request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);

        } else if ("DeleteCourse".equals(action)) {
            // Delete course
            String courseID = request.getParameter("courseID");
            boolean deleted = courseDAO.deleteCourse(courseID);
            if (deleted) {
                response.sendRedirect("CourseListInactiveController?action=ViewInActiveCourses");
            } else {
                request.setAttribute("ERROR", "Failed to delete course!");
                request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            }

        } else if ("RestoreCourse".equals(action)) {
            // Restore course to active (status = true)
            String courseID = request.getParameter("courseID");
            boolean success = courseDAO.restoreActive(courseID);
            if (success) {
                response.sendRedirect("CourseListInactiveController?action=ViewInActiveCourses");
            } else {
                request.setAttribute("ERROR", "Unable to restore course!");
                request.getRequestDispatcher("courseListInactive.jsp").forward(request, response);
            }

        } else {
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
            Logger.getLogger(CourseListInactiveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CourseListInactiveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controller for managing inactive courses in CodeUp Vietnam";
    }
}