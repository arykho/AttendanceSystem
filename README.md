# Attendance system

Automated Attendance System Using Facial Recognition
Authors: Arya Khokhar

Introduction: 

Our program is an automated attendance system that uses machine learning based facial recognition to recognize students in the classroom and mark them as present or absent. This program can be used for a visual check in system that could be used during Covid over Zoom, or on a normal in-person basis. It will be based on a java Package library for OpenCV (Open Source Computer Vision Library). This program will save attendance for later access. This program is for teachers or instructors to automate their attendance so they do not have to.
 What our program requires is a set of student ID photos and names and the classes they go to, and needs pictures of the people who went to class. 
The program will train itself to recognize and correlate the photos to the names, and once a photo of a student is given, the program will automatically check the student as present. The program will have two main parts for facial recognition: the first part will detect the faces, and the second part will match the faces detected to the ID photos and the name. 
This program will also have a classroom feature, where students will be added to specific class groups, and for every student in the class, the program will run once to mark all that has an in class photo present and those without the in class photo absent. If there is a student who does not have an ID photo yet but is present in the class, a new student will be added to the class and marked as unknown. Attendance for the students will be saved for later access and teachers can see the past history of a student's attendance.
A GUI will also be available to show the attendance saved by the program, and can be later checked by the teachers. It will be two columns: one column of the names of students in the class, and one column of their attendance in this class. No photos will be displayed on the GUI. 

Instructions:

The teacher will start the application with the camera facing the students, or have a zoom meeting or anything that shows student faces is fine. As students walk past the camera, or a picture of the student is shown, the application will record their names into the attendance system. Later, the teacher can use the application to: 1) to see data of a student by day, 2) See overall attendance of class by school day. How this menu will be created is by clicking on the menu button on the screen, and a list of dates and classes will be displayed. Click on the date and classes, and a menu in the application to see the current students and absent students will be made. The system can also flag the student that has been absent for more than a specified number of days: their names will be highlighted. To stop the application, close the window using the “x” mark on the top right corner of the windows. 

Class List:

Main (creates an AttendanceSystem object)
AttendanceSystem (use use FacialRecognition to mark the attendance of students)
FacialRecognition (will use OpenCV to recognize faces)
Classroom (Students will be added to the classroom, and a new class will be created.)
Student (Students will require a name and a photo, but you can also not have a photo temporarily)
Utils (will store allow teachers to access past data)
Menu (will allow teachers to view the attendance of a specific student or see which students were absent on a given date)

OpenCV.org: https://opencv.org/releases/
OpenCV Java Documentation:  https://docs.opencv.org/master/javadoc/index.html
Processing.org: https://processing.org/ 

