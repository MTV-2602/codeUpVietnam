
-- Create Database
CREATE DATABASE	CodeUpVietnam;
GO

USE CodeUpVietnam;
GO

-- Create table tblUsers
CREATE TABLE tblUsers (
    userID VARCHAR(50) PRIMARY KEY NOT NULL,
    fullName NVARCHAR(500) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15) NOT NULL,
    roleID NVARCHAR(5) NOT NULL,  -- 'ADM' (Admin), 'STU' (Student), 'INS' (Instructor)
    password VARCHAR(50) NOT NULL
);
GO

-- Insert sample data for tblUsers
INSERT INTO tblUsers (userID, fullName, email, phoneNumber, roleID, password) VALUES
('admin1', 'Admin User', 'admin@codeup.vn', '1234567890', 'ADM', 'adminpass'),
('student1', 'Nguyen Van A', 'student1@codeup.vn', '0987654321', 'STU', 'studentpass'),
('instructor1', 'Tran Thi B', 'instructor1@codeup.vn', '1122334455', 'INS', 'instructorpass');
GO

-- Create table tblCourses
CREATE TABLE tblCourses (
    courseID VARCHAR(50) PRIMARY KEY NOT NULL,
    courseName NVARCHAR(200) NOT NULL,
    description NVARCHAR(1000) NOT NULL,
    price DECIMAL(18, 2) NOT NULL,
    status BIT NOT NULL DEFAULT 1
);
GO

-- Insert sample data for tblCourses
INSERT INTO tblCourses (courseID, courseName, description, price, status) VALUES
('COURSE_001', 'HTML & CSS Basics', 'Learn the fundamentals of web development with HTML and CSS.', 150.00, 1),
('COURSE_002', 'JavaScript Essentials', 'Master JavaScript for interactive web applications.', 200.00, 1),
('COURSE_003', 'Python Programming', 'Comprehensive course on Python for beginners and intermediates.', 180.00, 1),
('COURSE_004', 'Java Fundamentals', 'Introduction to Java programming for software development.', 220.00, 1),
('COURSE_005', 'Advanced Web Development', 'Build dynamic web applications with advanced HTML, CSS, and JS.', 250.00, 1),
('COURSE_006', 'Python for Data Science', 'Learn Python for data analysis and machine learning.', 230.00, 1),
('COURSE_007', 'Java Web Development', 'Develop web applications using Java and Spring framework.', 240.00, 1),
('COURSE_008', 'Frontend Development Bootcamp', 'Intensive course covering modern frontend technologies.', 300.00, 1),
('COURSE_009', 'Full-Stack Development', 'End-to-end web development with frontend and backend skills.', 350.00, 1),
('COURSE_010', 'Python Automation', 'Automate tasks and workflows using Python scripting.', 190.00, 0); -- Inactive course
GO

-- Create table tblRegistrations
CREATE TABLE tblRegistrations (
    registrationID VARCHAR(50) PRIMARY KEY NOT NULL,
    userID VARCHAR(50) NOT NULL,
    courseID VARCHAR(50) NOT NULL,
    registrationDate DATETIME NOT NULL,
    status NVARCHAR(20) NOT NULL,  -- 'Pending', 'Confirmed', 'Cancelled'
    instructorID VARCHAR(50) NULL,  -- Can be null if no instructor assigned yet
    registrationNotes NVARCHAR(1000) NULL,
    CONSTRAINT FK_UserRegistration FOREIGN KEY (userID) REFERENCES tblUsers(userID),
    CONSTRAINT FK_CourseRegistration FOREIGN KEY (courseID) REFERENCES tblCourses(courseID),
    CONSTRAINT FK_InstructorRegistration FOREIGN KEY (instructorID) REFERENCES tblUsers(userID)
);
GO

-- Insert sample data for tblRegistrations
INSERT INTO tblRegistrations (registrationID, userID, courseID, registrationDate, status, instructorID, registrationNotes) VALUES
('REG_00001', 'student1', 'COURSE_001', '2025-07-16 09:00:00', 'Pending', NULL, NULL),
('REG_00002', 'student1', 'COURSE_002', '2025-07-17 14:00:00', 'Confirmed', 'instructor1', 'Student needs extra resources for JS.'),
('REG_00003', 'student1', 'COURSE_003', '2025-07-18 10:00:00', 'Cancelled', NULL, NULL);
GO

-- Create table tblReviews
CREATE TABLE tblReviews (
    reviewID VARCHAR(50) PRIMARY KEY NOT NULL,
    userID VARCHAR(50) NOT NULL,
    courseID VARCHAR(50) NOT NULL,
    rating INT CHECK (rating BETWEEN 1 AND 5) NOT NULL,
    comments NVARCHAR(1000),
    CONSTRAINT FK_UserReview FOREIGN KEY (userID) REFERENCES tblUsers(userID),
    CONSTRAINT FK_CourseReview FOREIGN KEY (courseID) REFERENCES tblCourses(courseID)
);
GO

-- Insert sample data for tblReviews
INSERT INTO tblReviews (reviewID, userID, courseID, rating, comments) VALUES
('REV_00001', 'student1', 'COURSE_002', 4, 'Great JavaScript course, very practical!'),
('REV_00002', 'student1', 'COURSE_001', 5, 'Excellent introduction to HTML and CSS.');
GO

select * from tblCourses