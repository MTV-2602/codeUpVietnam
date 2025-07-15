/*
 * This file defines the ReviewDAO class for the CodeUp Vietnam system, 
 * which manages database operations for course reviews.
 */
package dao;

import dto.Review;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // Insert a new course review
    public boolean insertReview(Review review) throws Exception {
        boolean success = false;
        String sql = "INSERT INTO tblReviews (reviewID, userID, courseID, rating, comments) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, review.getReviewID());
            ps.setString(2, review.getUserID());
            ps.setString(3, review.getCourseID());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComments());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Update a course review
    public boolean updateReview(Review review) throws Exception {
        boolean success = false;
        String sql = "UPDATE tblReviews SET userID = ?, courseID = ?, rating = ?, comments = ? WHERE reviewID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, review.getUserID());
            ps.setString(2, review.getCourseID());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComments());
            ps.setString(5, review.getReviewID());
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Delete a review by reviewID
    public boolean deleteReview(String reviewID) throws Exception {
        boolean success = false;
        String sql = "DELETE FROM tblReviews WHERE reviewID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, reviewID);
            success = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Get a review by reviewID
    public Review getReviewByID(String reviewID) throws Exception {
        Review review = null;
        String sql = "SELECT reviewID, userID, courseID, rating, comments FROM tblReviews WHERE reviewID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, reviewID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    review = new Review();
                    review.setReviewID(rs.getString("reviewID"));
                    review.setUserID(rs.getString("userID"));
                    review.setCourseID(rs.getString("courseID"));
                    review.setRating(rs.getInt("rating"));
                    review.setComments(rs.getString("comments"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return review;
    }

    // Get reviews by userID
    public List<Review> getReviewsByUserID(String userID) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT reviewID, userID, courseID, rating, comments FROM tblReviews WHERE userID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setReviewID(rs.getString("reviewID"));
                    r.setUserID(rs.getString("userID"));
                    r.setCourseID(rs.getString("courseID"));
                    r.setRating(rs.getInt("rating"));
                    r.setComments(rs.getString("comments"));
                    reviews.add(r);
                }
            }
        }
        return reviews;
    }

    // Get reviews by courseID
    public List<Review> getReviewsByCourseID(String courseID) throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT reviewID, userID, courseID, rating, comments FROM tblReviews WHERE courseID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, courseID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setReviewID(rs.getString("reviewID"));
                    r.setUserID(rs.getString("userID"));
                    r.setCourseID(rs.getString("courseID"));
                    r.setRating(rs.getInt("rating"));
                    r.setComments(rs.getString("comments"));
                    reviews.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Delete a review by reviewID and userID
    public boolean deleteReviewByUser(String reviewID, String userID) throws Exception {
        String sql = "DELETE FROM tblReviews WHERE reviewID = ? AND userID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setString(1, reviewID);
            ps.setString(2, userID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Get all reviews
    public List<Review> getAllReviews() throws Exception {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT reviewID, userID, courseID, rating, comments FROM tblReviews";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Review r = new Review();
                    r.setReviewID(rs.getString("reviewID"));
                    r.setUserID(rs.getString("userID"));
                    r.setCourseID(rs.getString("courseID"));
                    r.setRating(rs.getInt("rating"));
                    r.setComments(rs.getString("comments"));
                    reviews.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    // Update review for a specific user
    public boolean updateReviewForUser(String reviewID, String userID, int rating, String comments) throws Exception {
        String sql = "UPDATE tblReviews SET rating = ?, comments = ? WHERE reviewID = ? AND userID = ?";
        try (PreparedStatement ps = DatabaseConnection.initializeDatabase().prepareStatement(sql)) {
            ps.setInt(1, rating);
            ps.setString(2, comments);
            ps.setString(3, reviewID);
            ps.setString(4, userID);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        ReviewDAO dao = new ReviewDAO();
        Review r = new Review("R001", "user1", "course1", 5, "Great course!");
        try {
            boolean inserted = dao.insertReview(r);
            if (inserted) {
                System.out.println("Review inserted successfully.");
            } else {
                System.out.println("Failed to insert review.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}