/*
 * This file defines the CreateCourseController for the CodeUp Vietnam system,
 * which handles the creation of new programming courses.
 */
package controller;

import dao.CourseDAO;
import dto.Course;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Handles the creation of new programming courses in the CodeUp Vietnam system.
 * @author PC
 */
public class CreateCourseController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        // Set encoding to support Vietnamese characters
        request.setCharacterEncoding("UTF-8");

        // Get form data
        String courseName = request.getParameter("courseName");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid price!");
            request.getRequestDispatcher("createCourse.jsp").forward(request, response);
            return;
        }

        // Generate random courseID (COURSE_ + 3 digits)
        Random random = new Random();
        String courseID = "COURSE_" + String.format("%03d", random.nextInt(1000));

        // Default status = true (course is active)
        boolean status = true;

        // Create Course object with status
        Course course = new Course(courseID, courseName, description, price, status);

        // Call DAO to add course
        CourseDAO courseDAO = new CourseDAO();
        boolean success = courseDAO.createCourse(course);

        if (success) {
            // Success => redirect to ViewActiveCourses
            response.sendRedirect("MainController?action=ViewActiveCourses");
        } else {
            // Failure => set error message and return to createCourse.jsp
            request.setAttribute("ERROR", "Failed to create course!");
            request.getRequestDispatcher("createCourse.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CreateCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CreateCourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "CreateCourseController - Creates a new programming course (status = true) in CodeUp Vietnam";
    }
}