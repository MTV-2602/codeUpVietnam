/*
 * This file defines the User class for the CodeUp Vietnam system, 
 * which manages users (students, instructors, admins) for programming courses.
 */
package dto;

/**
 * Represents a user in the CodeUp Vietnam system.
 * @author PC
 */
public class User {
    private String userID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String roleID; // 'ADM' (Admin), 'STU' (Student), 'INS' (Instructor)
    private String password;

    // Default constructor
    public User() {
    }

    // Parameterized constructor
    public User(String userID, String fullName, String email, String phoneNumber, String roleID, String password) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleID = roleID;
        this.password = password;
    }

    // Getter and Setter
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString() (excluding password for security)
    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleID='" + roleID + '\'' +
                '}';
    }
}