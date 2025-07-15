/*
 * This file defines the Review class for the CodeUp Vietnam system, 
 * which manages reviews for programming courses.
 */
package dto;

/**
 * Represents a review for a programming course in the CodeUp Vietnam system.
 */
public class Review {
    private String reviewID;
    private String userID;
    private String courseID;
    private int rating;       // Rating value (1 to 5)
    private String comments;  // Comments

    // Default constructor
    public Review() {
    }

    // Full constructor
    public Review(String reviewID, String userID, String courseID, int rating, String comments) {
        this.reviewID = reviewID;
        this.userID = userID;
        this.courseID = courseID;
        this.rating = rating;
        this.comments = comments;
    }

    // Getter and Setter
    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    // toString() for debugging/logging
    @Override
    public String toString() {
        return "Review{" +
                "reviewID='" + reviewID + '\'' +
                ", userID='" + userID + '\'' +
                ", courseID='" + courseID + '\'' +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                '}';
    }
}