/*
 * This file defines the CourseDAO class for the CodeUp Vietnam system, 
 * which manages database operations for programming courses.
 */
package dao;

import dto.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

/**
 * Manages course-related database operations for the CodeUp Vietnam system.
 * @author PC
 */
public class CourseDAO {

    // Get all courses
    public List<Course> getAllCourses() throws Exception {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT courseID, courseName, description, price, status FROM tblCourses";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourseID(rs.getString("courseID"));
                course.setCourseName(rs.getString("courseName"));
                course.setDescription(rs.getString("description"));
                course.setPrice(rs.getDouble("price"));
                course.setStatus(rs.getBoolean("status"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // Create a new course
    public boolean createCourse(Course course) throws Exception {
        boolean success = false;
        String sql = "INSERT INTO tblCourses (courseID, courseName, description, price, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, course.getCourseID());
            ps.setString(2, course.getCourseName());
            ps.setString(3, course.getDescription());
            ps.setDouble(4, course.getPrice());
            ps.setBoolean(5, course.isStatus());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Update a course
    public boolean updateCourse(Course course) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblCourses SET courseName = ?, description = ?, price = ? WHERE courseID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());
            ps.setDouble(3, course.getPrice());
            ps.setString(4, course.getCourseID());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Delete a course by courseID (delete related reviews and registrations first)
    public boolean deleteCourse(String courseID) throws Exception {
        boolean success = false;
        try (Connection conn = DatabaseConnection.initializeDatabase()) {
            // Xóa các review liên quan
            try (PreparedStatement ps1 = conn.prepareStatement("DELETE FROM tblReviews WHERE courseID = ?")) {
                ps1.setString(1, courseID);
                ps1.executeUpdate();
            }
            // Xóa các registration liên quan
            try (PreparedStatement ps2 = conn.prepareStatement("DELETE FROM tblRegistrations WHERE courseID = ?")) {
                ps2.setString(1, courseID);
                ps2.executeUpdate();
            }
            // Xóa khóa học
            try (PreparedStatement ps3 = conn.prepareStatement("DELETE FROM tblCourses WHERE courseID = ?")) {
                ps3.setString(1, courseID);
                success = ps3.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("[CourseDAO] Lỗi khi xóa khóa học: " + e.getMessage());
            throw e;
        }
        return success;
    }

    // Get a course by courseID
    public Course getCourseByID(String courseID) throws Exception {
        Course course = null;
        String sql = "SELECT courseID, courseName, description, price, status FROM tblCourses WHERE courseID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, courseID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                course = new Course();
                course.setCourseID(rs.getString("courseID"));
                course.setCourseName(rs.getString("courseName"));
                course.setDescription(rs.getString("description"));
                course.setPrice(rs.getDouble("price"));
                course.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    // Set a course to inactive
    public boolean setInactive(String courseID) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblCourses SET status = 0 WHERE courseID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, courseID);
            success = ps.executeUpdate() > 0;
        }
        return success;
    }

    // Restore a course to active
    public boolean restoreActive(String courseID) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblCourses SET status = 1 WHERE courseID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, courseID);
            success = ps.executeUpdate() > 0;
        }
        return success;
    }

    // Get all active courses
    public List<Course> getActiveCourses() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT courseID, courseName, description, price, status FROM tblCourses WHERE status = 1";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourseID(rs.getString("courseID"));
                c.setCourseName(rs.getString("courseName"));
                c.setDescription(rs.getString("description"));
                c.setPrice(rs.getDouble("price"));
                c.setStatus(rs.getBoolean("status"));
                list.add(c);
            }
        }
        return list;
    }

    // Get all inactive courses
    public List<Course> getInactiveCourses() throws Exception {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT courseID, courseName, description, price, status FROM tblCourses WHERE status = 0";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                c.setCourseID(rs.getString("courseID"));
                c.setCourseName(rs.getString("courseName"));
                c.setDescription(rs.getString("description"));
                c.setPrice(rs.getDouble("price"));
                c.setStatus(rs.getBoolean("status"));
                list.add(c);
            }
        }
        return list;
    }

    // Main method for testing
    public static void main(String[] args) {
        CourseDAO courseDAO = new CourseDAO();
        try {
            // Delete a course by ID
            String courseIDToDelete = "course8"; // Example course ID
            boolean isDeleted = courseDAO.deleteCourse(courseIDToDelete);
            if (isDeleted) {
                System.out.println("Course with ID " + courseIDToDelete + " was deleted successfully.");
            } else {
                System.out.println("Failed to delete course with ID " + courseIDToDelete);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}