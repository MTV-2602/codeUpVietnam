
package controller;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Handles user login for the CodeUp Vietnam system.
 * @author PC
 */
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        // Get login information from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Use UserDAO to verify login credentials
        UserDAO userDAO = new UserDAO();
        User user = userDAO.checkLogin(email, password);

        if (user != null) {
            // Login successful: create session and store user info
            HttpSession session = request.getSession();
            session.setAttribute("userID", user.getUserID());
            session.setAttribute("fullName", user.getFullName());
            session.setAttribute("roleID", user.getRoleID());

            // Redirect to dashboard
            response.sendRedirect("dashboard.jsp");
        } else {
            // Login failed: set error message and return to login page
            request.setAttribute("ERROR", "Incorrect email or password!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user login in CodeUp Vietnam";
    }
}