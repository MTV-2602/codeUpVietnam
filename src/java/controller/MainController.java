
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Routes requests to appropriate controllers in the CodeUp Vietnam system.
 * @author PC
 */
public class MainController extends HttpServlet {

    private static final String DASHBOARD = "dashboard.jsp";

    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";

    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";

    private static final String REGISTER = "Register";
    private static final String REGISTER_CONTROLLER = "RegisterController";

    private static final String CREATE_COURSE = "CreateCourse";
    private static final String CREATE_COURSE_CONTROLLER = "CreateCourseController";

    private static final String BOOK_REGISTRATION = "BookRegistration";
    private static final String BOOK_REGISTRATION_CONTROLLER = "BookRegistrationController";

    private static final String REVIEW_COURSE = "ReviewCourse";
    private static final String REVIEW_COURSE_CONTROLLER = "ReviewCourseController";

    private static final String VIEW_COURSES = "ViewCourses";
    private static final String COURSE_CONTROLLER = "CourseController";

    private static final String VIEW_REGISTRATION = "ViewRegistrations";
    private static final String VIEW_REGISTRATION_CONTROLLER = "ViewRegistrationsController";

    private static final String VIEW_REGISTRATION_NOTES = "ViewRegistrationNotes";
    private static final String VIEW_REGISTRATION_NOTES_CONTROLLER = "ViewRegistrationNotesController";

    private static final String VIEW_REVIEW = "ViewReview";
    private static final String VIEW_REVIEW_CONTROLLER = "ViewReviewController";

    private static final String UPDATE_REVIEW = "UpdateReview";
    private static final String UPDATE_REVIEW_CONTROLLER = "UpdateReviewController";

    private static final String UPDATE_COURSE = "UpdateCourse";
    private static final String DELETE_COURSE = "DeleteCourse";

    private static final String VIEW_COURSES_INACTIVE = "ViewInActiveCourses";
    private static final String VIEW_COURSES_INACTIVE_CONTROLLER = "CourseListInactiveController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "login.jsp";  // Default page if no valid action
        String action = request.getParameter("action");
        try {
            if (action == null || action.isEmpty()) {
                url = DASHBOARD;
            } else if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (REGISTER.equals(action)) {
                url = REGISTER_CONTROLLER;
            } else if (CREATE_COURSE.equals(action)) {
                url = CREATE_COURSE_CONTROLLER;
            } else if (BOOK_REGISTRATION.equals(action)) {
                url = BOOK_REGISTRATION_CONTROLLER;
            } else if (VIEW_REGISTRATION.equals(action)) {
                url = VIEW_REGISTRATION_CONTROLLER;
            } else if (VIEW_REGISTRATION_NOTES.equals(action)) {
                url = VIEW_REGISTRATION_NOTES_CONTROLLER;
            } else if (REVIEW_COURSE.equals(action)) {
                url = REVIEW_COURSE_CONTROLLER;
            } else if (VIEW_REVIEW.equals(action)) {
                url = VIEW_REVIEW_CONTROLLER;
            } else if (VIEW_COURSES_INACTIVE.equals(action)) {
                url = VIEW_COURSES_INACTIVE_CONTROLLER;
            } else if ("DeleteRegistration".equals(action)) {
                url = "ViewRegistrationsController";
            } else if ("UpdateRegistration".equals(action)) {
                url = "ViewRegistrationsController";
            } else if (VIEW_COURSES.equals(action)
                    || UPDATE_COURSE.equals(action)
                    || DELETE_COURSE.equals(action)
                    || "SetActive".equals(action)
                    || "RestoreActive".equals(action)
                    || "ViewActiveCourses".equals(action)) {
                url = COURSE_CONTROLLER;
            } else {
                // If action doesn't match, redirect to Dashboard
                url = DASHBOARD;
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", "Error: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Routes requests for course registration management in CodeUp Vietnam";
    }
}