CREATE DATABASE School;
USE School;

-- Öğrenciler tablosu
CREATE TABLE Students (
    StudentID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    BirthDate DATE
);


-- Dersler tablosu 
CREATE TABLE Courses(
CourseID INT AUTO_INCREMENT PRIMARY KEY,
    CourseName VARCHAR(100),
    Credits INT
);

-- Veri ekleme 
INSERT INTO Students (FirstName, LastName, BirthDate) VALUES ('Alice', 'Johnson', '2005-04-15');
INSERT INTO Students (FirstName, LastName, BirthDate) VALUES ('Bob', 'Smith', '2004-09-22');
INSERT INTO Students (FirstName, LastName, BirthDate) VALUES ('Charlie', 'Brown', '2005-12-03');

INSERT INTO Courses (CourseName, Credits) VALUES ('Mathematics', 4);
INSERT INTO Courses (CourseName, Credits) VALUES ('Physics', 3);
INSERT INTO Courses (CourseName, Credits) VALUES ('Chemistry', 4);


SELECT * FROM Students;
SELECT * FROM Courses;

-- Koşullu sorgular
SELECT * FROM Students WHERE LastName = 'Smith';
SELECT * FROM Courses WHERE Credits >= 4;

-- Sıralama ve limit
SELECT * FROM Students ORDER BY BirthDate DESC LIMIT 2;

-- güncelleme 
UPDATE Students
SET FirstName = 'Robert'
WHERE StudentID = 2;

-- silme 
DELETE FROM Courses WHERE CourseName = 'Chemistry';

-- öğrencilerin aldıkları dersler , farklı bir tablo 
CREATE TABLE Enrollments (
    EnrollmentID INT AUTO_INCREMENT PRIMARY KEY,
    StudentID INT,
    CourseID INT,
    Grade CHAR(2),
    FOREIGN KEY (StudentID) REFERENCES Students(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- veri ekleme ve JOIN ile sorgulama 
INSERT INTO Enrollments (StudentID, CourseID, Grade)
VALUES 
(1, 1, 'A'),
(2, 1, 'B'),
(3, 2, 'A');

SELECT s.FirstName, s.LastName, c.CourseName, e.Grade
FROM Enrollments e
JOIN Students s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID;

-- fonksiyonlar 
SELECT c.CourseName, AVG(CASE e.Grade  -- ortalama not 
                           WHEN 'A' THEN 4
                           WHEN 'B' THEN 3
                           WHEN 'C' THEN 2
                           ELSE 0 END) AS AvgGPA
FROM Enrollments e
JOIN Courses c ON e.CourseID = c.CourseID
GROUP BY c.CourseName;

SELECT COUNT(*) AS TotalStudents FROM Students; -- kaç öğrenci kayıtlı 

-- subquery
SELECT FirstName, LastName
FROM Students
WHERE StudentID IN (
    SELECT StudentID
    FROM Enrollments
    WHERE CourseID = 1
);

-- update join 
UPDATE Enrollments e
JOIN Students s ON e.StudentID = s.StudentID
SET e.Grade = 'A'
WHERE s.FirstName = 'Bob';

-- case when 
SELECT FirstName, LastName,
CASE Grade
    WHEN 'A' THEN 'Excellent'
    WHEN 'B' THEN 'Good'
    ELSE 'Needs Improvement'
END AS Performance
FROM Enrollments;


-- raporlama 
SELECT c.CourseName, s.FirstName, s.LastName
FROM Enrollments e
JOIN Students s ON e.StudentID = s.StudentID
JOIN Courses c ON e.CourseID = c.CourseID
ORDER BY c.CourseName, s.LastName;






