
package dao;

import dto.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DatabaseConnection;


public class UserDAO {

    // Check login credentials
    public User checkLogin(String email, String password) throws Exception {
        User user = null;
        String sql = "SELECT userID, fullName, email, phoneNumber, roleID FROM tblUsers WHERE email = ? AND password = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setUserID(rs.getString("userID"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setRoleID(rs.getString("roleID"));
                // Note: Password is not returned for security
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Register a new user
    public boolean registerUser(User user) throws Exception {
        boolean success = false;
        String sql = "INSERT INTO tblUsers (userID, fullName, email, phoneNumber, roleID, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getRoleID());
            ps.setString(6, user.getPassword());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Main method for testing
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        try {
            User user = userDAO.checkLogin("admin@example.com", "adminpass");
            if (user != null) {
                System.out.println("Login successful!");
                System.out.println("User ID: " + user.getUserID());
                System.out.println("Full Name: " + user.getFullName());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Phone Number: " + user.getPhoneNumber());
                System.out.println("Role ID: " + user.getRoleID());
            } else {
                System.out.println("Login failed. Invalid email or password.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}