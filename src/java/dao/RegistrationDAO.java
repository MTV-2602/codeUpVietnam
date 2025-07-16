/*
 * This file defines the RegistrationDAO class for the CodeUp Vietnam system, 
 * which manages database operations for course registrations.
 */
package dao;

import dto.Registration;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DatabaseConnection;

public class RegistrationDAO {

    // Insert a new registration
    public boolean insertRegistration(Registration registration) throws Exception {
        boolean success = false;
        String sql = "INSERT INTO tblRegistrations (registrationID, userID, courseID, registrationDate, status, instructorID) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, registration.getRegistrationID());
            ps.setString(2, registration.getUserID());
            ps.setString(3, registration.getCourseID());
            ps.setTimestamp(4, registration.getRegistrationDate());
            ps.setString(5, registration.getStatus());
            ps.setString(6, registration.getInstructorID());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Update a registration
    public boolean updateRegistration(Registration registration) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblRegistrations "
                   + "SET userID = ?, courseID = ?, registrationDate = ?, status = ?, instructorID = ? "
                   + "WHERE registrationID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, registration.getUserID());
            ps.setString(2, registration.getCourseID());
            ps.setTimestamp(3, registration.getRegistrationDate());
            ps.setString(4, registration.getStatus());
            ps.setString(5, registration.getInstructorID());
            ps.setString(6, registration.getRegistrationID());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Delete a registration by registrationID
    public boolean deleteRegistration(String registrationID) throws Exception {
        boolean success = false;
        String sql = "DELETE FROM tblRegistrations WHERE registrationID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, registrationID);
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Get a registration by registrationID
    public Registration getRegistrationByID(String registrationID) throws Exception {
            Registration registration = null;
    String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID, registrationNotes "
               + "FROM tblRegistrations WHERE registrationID = ?";
    try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
        ps.setString(1, registrationID);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                registration = new Registration();
                registration.setRegistrationID(rs.getString("registrationID"));
                registration.setUserID(rs.getString("userID"));
                registration.setCourseID(rs.getString("courseID"));
                registration.setRegistrationDate(rs.getTimestamp("registrationDate"));
                registration.setStatus(rs.getString("status"));
                registration.setInstructorID(rs.getString("instructorID"));
                registration.setRegistrationNotes(rs.getString("registrationNotes")); // ✅ THÊM DÒNG NÀY
            }
        }
    }
    return registration;
    }

    // Get all registrations
    public List<Registration> getAllRegistrations() throws Exception {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID FROM tblRegistrations";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegistrationID(rs.getString("registrationID"));
                    registration.setUserID(rs.getString("userID"));
                    registration.setCourseID(rs.getString("courseID"));
                    registration.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    registration.setStatus(rs.getString("status"));
                    registration.setInstructorID(rs.getString("instructorID"));
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    // Get registrations by userID
    public List<Registration> getRegistrationsByUserID(String userID) throws Exception {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID "
                   + "FROM tblRegistrations WHERE userID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegistrationID(rs.getString("registrationID"));
                    registration.setUserID(rs.getString("userID"));
                    registration.setCourseID(rs.getString("courseID"));
                    registration.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    registration.setStatus(rs.getString("status"));
                    registration.setInstructorID(rs.getString("instructorID"));
                    registrations.add(registration);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

    // Get registrations by instructorID
    public List<Registration> getRegistrationsByInstructorID(String instructorID) throws Exception {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID, registrationNotes "
                   + "FROM tblRegistrations WHERE instructorID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, instructorID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration reg = new Registration();
                    reg.setRegistrationID(rs.getString("registrationID"));
                    reg.setUserID(rs.getString("userID"));
                    reg.setCourseID(rs.getString("courseID"));
                    reg.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    reg.setStatus(rs.getString("status"));
                    reg.setInstructorID(rs.getString("instructorID"));
                    reg.setRegistrationNotes(rs.getString("registrationNotes"));
                    list.add(reg);
                }
            }
        }
        return list;
    }

    // Get all registrations for instructors
    public List<Registration> getAllRegistrationsForInstructors() throws Exception {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID, registrationNotes "
                   + "FROM tblRegistrations";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegistrationID(rs.getString("registrationID"));
                    registration.setUserID(rs.getString("userID"));
                    registration.setCourseID(rs.getString("courseID"));
                    registration.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    registration.setStatus(rs.getString("status"));
                    registration.setInstructorID(rs.getString("instructorID"));
                    registration.setRegistrationNotes(rs.getString("registrationNotes"));
                    registrations.add(registration);
                }
            }
        }
        return registrations;
    }

    // Update registration registrationNotes
    public boolean updateNotes(String registrationID, String registrationNotes) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblRegistrations SET registrationNotes = ? WHERE registrationID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, registrationNotes);
            ps.setString(2, registrationID);
            success = ps.executeUpdate() > 0;
        }
        return success;
    }

    // Mark a registration as confirmed
    public boolean markRegistrationConfirmed(String registrationID) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblRegistrations SET status = 'Confirmed' WHERE registrationID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, registrationID);
            success = ps.executeUpdate() > 0;
        }
        return success;
    }

    // Get registrations by userID and status
    public List<Registration> getRegistrationsByUserIDAndStatus(String userID, String status) throws Exception {
        List<Registration> registrations = new ArrayList<>();
        String sql = "SELECT registrationID, userID, courseID, registrationDate, status, instructorID "
                   + "FROM tblRegistrations WHERE userID = ? AND status = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, userID);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration registration = new Registration();
                    registration.setRegistrationID(rs.getString("registrationID"));
                    registration.setUserID(rs.getString("userID"));
                    registration.setCourseID(rs.getString("courseID"));
                    registration.setRegistrationDate(rs.getTimestamp("registrationDate"));
                    registration.setStatus(rs.getString("status"));
                    registration.setInstructorID(rs.getString("instructorID"));
                    registrations.add(registration);
                }
            }
        }
        return registrations;
    }

    // Main method for testing
    public static void main(String[] args) {
        RegistrationDAO dao = new RegistrationDAO();
        String userID = "user1";

        try {
            List<Registration> registrations = dao.getRegistrationsByUserID(userID);
            if (!registrations.isEmpty()) {
                for (Registration registration : registrations) {
                    System.out.println("Registration ID: " + registration.getRegistrationID());
                    System.out.println("User ID: " + registration.getUserID());
                    System.out.println("Course ID: " + registration.getCourseID());
                    System.out.println("Registration Date: " + registration.getRegistrationDate());
                    System.out.println("Status: " + registration.getStatus());
                    System.out.println("Instructor ID: " + registration.getInstructorID());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println("No registrations found for userID: " + userID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}