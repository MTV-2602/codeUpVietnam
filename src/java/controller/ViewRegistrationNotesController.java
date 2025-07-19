
package controller;

import dao.RegistrationDAO;
import dto.Registration;
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
 * Handles viewing and updating registration notes in the CodeUp Vietnam system.
 * @author PC
 */
public class ViewRegistrationNotesController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewRegistrationNotesController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewRegistrationNotesController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session and role Instructor
        HttpSession session = request.getSession(false);
        if (session == null || !"INS".equals(session.getAttribute("roleID"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get all registrations
            RegistrationDAO dao = new RegistrationDAO();
            List<Registration> list = dao.getAllRegistrationsForInstructors();

            // Send list to JSP
            request.setAttribute("registrations", list);

            // Forward to registrationNotes.jsp
            request.getRequestDispatcher("registrationNotes.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ViewRegistrationNotesController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ERROR", "Unable to retrieve list of registrations!");
            request.getRequestDispatcher("registrationNotes.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check session & role
        HttpSession session = request.getSession(false);
        if (session == null || !"INS".equals(session.getAttribute("roleID"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        // Get action
        String action = request.getParameter("action");
        String registrationID = request.getParameter("registrationID");
        RegistrationDAO dao = new RegistrationDAO();

        try {
            if ("AddRegistrationNotes".equals(action)) {
                // Receive notes
                String notes = request.getParameter("notes");
                dao.updateNotes(registrationID, notes);

            } else if ("MarkCompleted".equals(action)) {
                // Mark as Completed
                dao.markRegistrationConfirmed(registrationID);
            }

            // After updating, call doGet to refresh list
            doGet(request, response);

        } catch (Exception ex) {
            Logger.getLogger(ViewRegistrationNotesController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ERROR", "Unable to update registration!");
            request.getRequestDispatcher("registrationNotes.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles viewing and updating registration notes in CodeUp Vietnam";
    }
}