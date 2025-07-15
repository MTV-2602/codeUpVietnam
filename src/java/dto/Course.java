/*
 * This file defines the Course class for the CodeUp Vietnam system, 
 * which manages registration for programming courses (HTML, CSS, JS, Python, Java).
 */
package dto;

/**
 * Represents a programming course in the CodeUp Vietnam system.
 * @author PC
 */
public class Course {

    private String courseID;
    private String courseName;
    private String description;
    private double price;
    private boolean status;

    // Default constructor
    public Course() {
    }

    // Parameterized constructor
    public Course(String courseID, String courseName, String description, double price, boolean status) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    // Getter and Setter for courseID
    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    // Getter and Setter for courseName
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // toString() method to display course information
    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}