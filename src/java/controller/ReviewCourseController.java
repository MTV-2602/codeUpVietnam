/*
 * This file defines the ReviewCourseController for the CodeUp Vietnam system, 
 * which handles submitting reviews for programming courses.
 */
package controller;

import dao.RegistrationDAO;
import dao.ReviewDAO;
import dto.Registration;
import dto.Review;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Handles course review submission in the CodeUp Vietnam system.
 * @author PC
 */
public class ReviewCourseController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReviewCourseController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewCourseController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session, ensure user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userID = (String) session.getAttribute("userID");

        try {
            // Call RegistrationDAO to get list of completed registrations for user
            RegistrationDAO registrationDAO = new RegistrationDAO();
            List<Registration> completedRegistrations = registrationDAO.getRegistrationsByUserIDAndStatus(userID, "Confirmed");

            // Send list to JSP for user to select course to review
            request.setAttribute("completedRegistrations", completedRegistrations);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "Unable to retrieve list of completed courses!");
        }

        // Forward to reviewCourse.jsp
        request.getRequestDispatcher("reviewCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session, require user to be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userID = (String) session.getAttribute("userID");

        // Get form data
        String courseID = request.getParameter("courseID");
        String ratingStr = request.getParameter("rating");
        String comments = request.getParameter("comments");

        // Convert rating to int
        int rating = 0;
        try {
            rating = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            request.setAttribute("ERROR", "Invalid rating value!");
            request.getRequestDispatcher("reviewCourse.jsp").forward(request, response);
            return;
        }

        // Generate random reviewID, e.g., "REV_XXXXXX"
        Random random = new Random();
        String reviewID = "REV_" + String.format("%05d", random.nextInt(10000));

        // Create Review object
        Review review = new Review(reviewID, userID, courseID, rating, comments);

        // Call DAO to save Review to DB
        ReviewDAO reviewDAO = new ReviewDAO();
        boolean success = false;
        try {
            success = reviewDAO.insertReview(review);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (success) {
            // Review successful => redirect to review list
            response.sendRedirect("MainController?action=ViewReview");
        } else {
            // If failed, report error and return to form
            request.setAttribute("ERROR", "Failed to review course! Please try again.");
            request.getRequestDispatcher("reviewCourse.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles course review submission in CodeUp Vietnam";
    }
}