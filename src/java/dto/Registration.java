/*
 * This file defines the Registration class for the CodeUp Vietnam system, 
 * which manages course registrations for programming courses.
 */
package dto;

import java.sql.Timestamp;

/**
 * Represents a course registration in the CodeUp Vietnam system.
 * @author PC
 */
public class Registration {

    private String registrationID;
    private String userID;
    private String courseID;
    private Timestamp registrationDate; // Stores full date-time
    private String status;             // 'Pending', 'Confirmed', 'Cancelled'
    private String instructorID;       // Can be null if not assigned
    private String notes;

    // Default constructor
    public Registration() {
    }

    // Parameterized constructor
    public Registration(String registrationID, String userID, String courseID, 
                       Timestamp registrationDate, String status, String instructorID) {
        this.registrationID = registrationID;
        this.userID = userID;
        this.courseID = courseID;
        this.registrationDate = registrationDate;
        this.status = status;
        this.instructorID = instructorID;
    }

    // Getter/Setter for registrationID
    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    // Getter/Setter for userID
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    // Getter/Setter for courseID
    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    // Getter/Setter for registrationDate (Timestamp)
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Getter/Setter for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter/Setter for instructorID
    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

    // Getter/Setter for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "registrationID='" + registrationID + '\'' +
                ", userID='" + userID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", registrationDate=" + registrationDate +
                ", status='" + status + '\'' +
                ", instructorID='" + instructorID + '\'' +
                '}';
    }
}