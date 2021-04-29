# attendencesystem

Automated Attendance System Using Facial Recognition
Authors: Arya Khokhar, Xinyu Zhao
Revision: 4/25/2021

Introduction: 

Our program is an automated attendance system that uses machine learning based facial recognition to recognize students in the classroom and mark them as present or absent. This program can be used for a visual check in system that could be used during Covid over Zoom, or on a normal in-person basis. It will be based on a java Package library for OpenCV (Open Source Computer Vision Library). This program will save attendance for later access. This program is for teachers or instructors to automate their attendance so they do not have to.
 What our program requires is a set of student ID photos and names and the classes they go to, and needs pictures of the people who went to class. 
The program will train itself to recognize and correlate the photos to the names, and once a photo of a student is given, the program will automatically check the student as present. The program will have two main parts for facial recognition: the first part will detect the faces, and the second part will match the faces detected to the ID photos and the name. 
This program will also have a classroom feature, where students will be added to specific class groups, and for every student in the class, the program will run once to mark all that has an in class photo present and those without the in class photo absent. If there is a student who does not have an ID photo yet but is present in the class, a new student will be added to the class and marked as unknown. Attendance for the students will be saved for later access and teachers can see the past history of a student's attendance.
A GUI will also be available to show the attendance saved by the program, and can be later checked by the teachers. It will be two columns: one column of the names of students in the class, and one column of their attendance in this class. No photos will be displayed on the GUI. 

Instructions:

The teacher will start the application(by clicking run on eclipse) with the camera facing the students, or have a zoom meeting or anything that shows student faces is fine. As students walk past the camera, or a picture of the student is shown, the application will record their names into the attendance system. Later, the teacher can use the application to: 1) to see data of a student by day, 2) See overall attendance of class by school day. How this menu will be created is by clicking on the menu button on the screen, and a list of dates and classes will be displayed. Click on the date and classes, and a menu in the application to see the current students and absent students will be made. The system can also flag the student that has been absent for more than a specified number of days: their names will be highlighted. To stop the application, close the window using the “x” mark on the top right corner of the windows. 

Features List (THE ONLY SECTION THAT CANNOT CHANGE LATER):
Must-have Features:

Face detection in a picture: Using the laptop's camera, the system should be able to detect any faces in the picture. These face pictures are then going to be a part of the training data set. The system should allow for face pictures to be taken and saved if it detects a face in it.
Add students into the system: The program should be able to see a list of unknown faces in the database and tag these pictures with a student id and name. This step needs to be done every time a new student joins a class.
Face recognition: Once the program has enough tagged pictures, the system should be able to train/re-train itself on the current set of images. Once trained, the application should be able to recognize and match a given face to a pre-identified name. 
Create a classroom: The system should make it easy to add students in the class with names and grade level. A classroom can be identified by Teacher and course name. A typical GUI based list of students should allow students to be added to a class. If a student picture is not known, we should mark it as such but should allow the student to be added to the class. Later we should be able to add a picture id tag of the student, when we get the picture. 
 GUI that shows all students and their attendance in the class. A simple navigation will be implemented using menus and buttons. For example, a menu for the teacher to see which students are absent and present on a given day.

Want-to-have Features:

Support for multiple classes because the system may be used by one teacher teaching multiple classes and multiple teachers who are using the system for their own class.
Networking support if camera and application are running on separate computer systems. This way the teacher can have a camera running on one device by the door and access the data at their desk.
See history of student attendance for a class by semester or year. This way the teacher can view the past attendance of any student.
Feeding a multiface picture of students instead of one student per picture. This reduces the work of the teacher in taking multiple pictures for the program training. 
Create a set of search queries - for example - highlight students if a student is absent for more than a specified number of days. The teacher does not have to analyze the students history themselves and can get information about the student.

Stretch Features:

Create a website extension: Teachers can use the program with their designated school system so they do not have to transfer the information from the application to their school database.
Support for multiple cameras: Different types of cameras will be compatible with the software so there is no limitation for the teachers.
Continuous monitoring of the class and flag unknown faces: This way the teacher will be able to know if there is someone who is not supposed to be present or if someone leaves during class and does not come back. 

Class List:

Main (creates an AttendanceSystem object)
AttendanceSystem (use use FacialRecognition to mark the attendance of students)
FacialRecognition (will use OpenCV to recognize faces)
Classroom (Students will be added to the classroom, and a new class will be created.)
Student (Students will require a name and a photo, but you can also not have a photo temporarily)
Utils (will store allow teachers to access past data)
Menu (will allow teachers to view the attendance of a specific student or see which students were absent on a given date)

Credits:
[Gives credit for project components. This includes both internal credit (your group members) and external credit (other people, websites, libraries). To do this:
List the group members and describe how each member contributed to the completion of the final program. This could be classes written, art assets created, leadership/organizational skills exercises, or other tasks. Initially, this is how you plan on splitting the work.
Give credit to all outside resources used. This includes downloaded images or sounds, external java libraries, parent/tutor/student coding help, etc.]

Arya
In charge of facial recognition
In charge of classroom and adding new students to system
In charge of storing past data
Xinyu
In charge of the attendance system
In charge of Student class
In charge of menu 
In charge of any art for GUI components

OpenCV.org: https://opencv.org/releases/
OpenCV Java Documentation:  https://docs.opencv.org/master/javadoc/index.html
Processing.org: https://processing.org/ 

