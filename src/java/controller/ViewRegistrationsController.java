
package controller;

import dao.RegistrationDAO;
import dto.Registration;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.util.Date;

public class ViewRegistrationsController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set content type
        response.setContentType("text/html;charset=UTF-8");

        // Check session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userID") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String userID = (String) session.getAttribute("userID");

        // Get action
        String action = request.getParameter("action");
        if (action == null) {
            action = "ViewRegistrations";
        }

        // Create DAO
        RegistrationDAO dao = new RegistrationDAO();

        try {
            if ("ViewRegistrations".equals(action)) {
                // Get list of registrations for user
                List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                request.setAttribute("registrations", registrations);

                // If user clicks Edit => editRegistrationID
                String editRegistrationID = request.getParameter("editRegistrationID");
                if (editRegistrationID != null) {
                    request.setAttribute("editRegistrationID", editRegistrationID);
                }

                // Forward to registrationList.jsp
                request.getRequestDispatcher("registrationList.jsp").forward(request, response);

            } else if ("UpdateRegistration".equals(action)) {
                // Handle update
                String registrationID = request.getParameter("registrationID");
                String courseID = request.getParameter("courseID");
                String dateStr = request.getParameter("registrationDate"); // "yyyy-MM-dd'T'HH:mm"
                String status = request.getParameter("status");

                // Parse date with format "yyyy-MM-dd'T'HH:mm"
                Date parsedDate = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    parsedDate = sdf.parse(dateStr);
                } catch (ParseException e) {
                    // Invalid format => report error
                    request.setAttribute("ERROR", "Invalid date-time format (yyyy-MM-dd'T'HH:mm)!");
                    List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                    request.setAttribute("registrations", registrations);
                    request.setAttribute("editRegistrationID", registrationID);
                    request.getRequestDispatcher("registrationList.jsp").forward(request, response);
                    return;
                }

                // Find existing registration
                Registration oldReg = dao.getRegistrationByID(registrationID);
                if (oldReg == null) {
                    request.setAttribute("ERROR", "Registration not found!");
                    List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                    request.setAttribute("registrations", registrations);
                    request.getRequestDispatcher("registrationList.jsp").forward(request, response);
                    return;
                }

                // Update: Only update Date
                oldReg.setRegistrationDate(new java.sql.Timestamp(parsedDate.getTime()));

                boolean updated = dao.updateRegistration(oldReg);
                if (updated) {
                    response.sendRedirect("ViewRegistrationsController?action=ViewRegistrations");
                } else {
                    request.setAttribute("ERROR", "Failed to update registration!");
                    List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                    request.setAttribute("registrations", registrations);
                    request.setAttribute("editRegistrationID", registrationID);
                    request.getRequestDispatcher("registrationList.jsp").forward(request, response);
                }

            } else if ("DeleteRegistration".equals(action)) {
                // Delete registration
                String registrationID = request.getParameter("registrationID");
                boolean deleted = dao.deleteRegistration(registrationID);
                if (deleted) {
                    response.sendRedirect("ViewRegistrationsController?action=ViewRegistrations");
                } else {
                    request.setAttribute("ERROR", "Failed to delete registration!");
                    List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                    request.setAttribute("registrations", registrations);
                    request.getRequestDispatcher("registrationList.jsp").forward(request, response);
                }

            } else {
                // Unsupported action
                request.setAttribute("ERROR", "Action not supported!");
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }

        } catch (Exception ex) {
            Logger.getLogger(ViewRegistrationsController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ERROR", "An error occurred: " + ex.getMessage());
            try {
                List<Registration> registrations = dao.getRegistrationsByUserID(userID);
                request.setAttribute("registrations", registrations);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            request.getRequestDispatcher("registrationList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewRegistrationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewRegistrationsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Controller for viewing, updating, and deleting registrations in CodeUp Vietnam";
    }
}