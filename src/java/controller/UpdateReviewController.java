
package controller;

import dao.ReviewDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * Handles updating and deleting course reviews in the CodeUp Vietnam system.
 * @author PC
 */
public class UpdateReviewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateReviewController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateReviewController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userID = (String) session.getAttribute("userID");
        String reviewID = request.getParameter("reviewID");
        String action = request.getParameter("action");

        // Create DAO
        ReviewDAO reviewDAO = new ReviewDAO();

        try {
            if ("update".equals(action)) {
                // Handle update
                String ratingStr = request.getParameter("rating");
                String comments = request.getParameter("comments");

                int rating = 0;
                try {
                    rating = Integer.parseInt(ratingStr);
                    if (rating < 1 || rating > 5) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    request.setAttribute("ERROR", "Invalid rating value.");
                    request.getRequestDispatcher("ViewReviewController").forward(request, response);
                    return;
                }

                boolean updated = reviewDAO.updateReviewForUser(reviewID, userID, rating, comments);
                if (updated) {
                    request.setAttribute("SUCCESS", "Course review updated successfully.");
                } else {
                    request.setAttribute("ERROR", "Failed to update course review.");
                }

            } else if ("delete".equals(action)) {
                // Handle delete
                boolean deleted = reviewDAO.deleteReviewByUser(reviewID, userID);
                if (deleted) {
                    request.setAttribute("SUCCESS", "Course review deleted successfully.");
                } else {
                    request.setAttribute("ERROR", "Failed to delete course review.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("ERROR", "An error occurred!");
        }

        // Forward to ViewReviewController to reload the list
        request.getRequestDispatcher("ViewReviewController").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Handles updating and deleting course reviews in CodeUp Vietnam";
    }
}