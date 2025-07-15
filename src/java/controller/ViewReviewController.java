/*
 * This file defines the ViewReviewController for the CodeUp Vietnam system, 
 * which handles viewing reviews for programming courses.
 */
package controller;

import dao.ReviewDAO;
import dto.Review;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Handles requests to view reviews of programming courses in the CodeUp Vietnam system.
 * @author PC
 */
public class ViewReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewReviewController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewReviewController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            // Not logged in => redirect to login
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        try {
            // Call DAO to get list of reviews for the user
            ReviewDAO reviewDAO = new ReviewDAO();
            List<Review> userReviews = reviewDAO.getReviewsByUserID(userID);

            // Set reviews as request attribute
            request.setAttribute("userReviews", userReviews);

        } catch (Exception ex) {
            Logger.getLogger(ViewReviewController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ERROR", "Unable to retrieve list of course reviews!");
        }

        // Forward to JSP for display
        request.getRequestDispatcher("viewReviews.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles viewing of course reviews in CodeUp Vietnam";
    }
}