/*
 * This file defines the RegisterController for the CodeUp Vietnam system, 
 * which handles user registration for the course management platform.
 */
package controller;

import dao.UserDAO;
import dto.User;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Handles user registration in the CodeUp Vietnam system.
 * @author PC
 */
public class RegisterController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        // Get form data
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");

        // Generate new userID, e.g., "USER_XXXXXX"
        Random random = new Random();
        String userID = "USER_" + String.format("%05d", random.nextInt(10000));
        // Default role for registered users is 'STU' (Student)
        String roleID = "STU";

        // Create User object with form data
        User newUser = new User(userID, fullName, email, phoneNumber, roleID, password);

        // Use UserDAO to register user
        UserDAO userDAO = new UserDAO();
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            // If registration successful, redirect to login page
            response.sendRedirect("login.jsp");
        } else {
            // If registration failed, set error message and forward to register page
            request.setAttribute("ERROR", "Registration failed! Please check your information.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles user registration in CodeUp Vietnam";
    }
}