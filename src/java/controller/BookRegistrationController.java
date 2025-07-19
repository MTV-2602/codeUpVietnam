
package controller;

import dao.RegistrationDAO;
import dao.CourseDAO;
import dto.Registration;
import dto.Course;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

/**
 * Handles booking registrations for programming courses in the CodeUp Vietnam system.
 * @author PC
 */
public class BookRegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get list of active courses
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = null;
        try {
            courses = courseDAO.getActiveCourses();
        } catch (Exception ex) {
            Logger.getLogger(BookRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("courses", courses);

        // Forward to bookRegistration.jsp to display form
        request.getRequestDispatcher("bookRegistration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        try {
            // Check session and userID
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userID") == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            String userID = (String) session.getAttribute("userID");

            // Get form data
            String courseID = request.getParameter("courseID");
            String registrationDateStr = request.getParameter("registrationDate"); // yyyy-MM-dd'T'HH:mm

            // Parse datetime-local string to java.util.Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date parsedDate = sdf.parse(registrationDateStr);

            // Convert to Timestamp to store full date/time
            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(parsedDate.getTime());

            // Generate random registrationID (REG_ + 5 digits)
            Random random = new Random();
            String registrationID = "REG_" + String.format("%05d", random.nextInt(100000));

            // Create Registration object, default status "Pending"
            String status = "Pending";
            String instructorID = null;

            // Call Registration constructor with Timestamp
            Registration registration = new Registration(registrationID, userID, courseID,
                                                        sqlTimestamp, status, instructorID);

            // Call DAO to insert registration into DB
            RegistrationDAO registrationDAO = new RegistrationDAO();
            boolean success = registrationDAO.insertRegistration(registration);

            if (success) {
                // Success => redirect to view registrations
                response.sendRedirect("MainController?action=ViewRegistrations");
            } else {
                // Failure => display error message and return to form
                request.setAttribute("ERROR", "Failed to book registration! Please try again.");
                request.getRequestDispatcher("bookRegistration.jsp").forward(request, response);
            }

        } catch (ParseException e) {
            // Date parsing error
            request.setAttribute("ERROR", "Invalid date-time format!");
            request.getRequestDispatcher("bookRegistration.jsp").forward(request, response);
        } catch (Exception ex) {
            // Other errors (e.g., DB, NullPointer, ...)
            Logger.getLogger(BookRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("ERROR", "An error occurred. Please try again!");
            request.getRequestDispatcher("bookRegistration.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles booking registrations for programming courses in CodeUp Vietnam";
    }
}