ISO 15939 Measurement Process Simulator

Student Name: Rasül Hakan FİDAN
Student ID: 202319013

About the Project:
This project is a Java Swing desktop application for Software Project II course.
The program simulates the basic steps of the ISO/IEC 15939 measurement process.

The application has 5 steps:
1. Profile
2. Define
3. Plan
4. Collect
5. Analyse

In the Profile step, the user enters username, school, and session name.
In the Define step, the user selects quality type, mode, and scenario.
In the Plan step, the program shows the dimensions and metrics of the selected scenario.
In the Collect step, the program shows raw metric values and calculates scores between 1 and 5.
In the Analyse step, the program calculates dimension scores and shows the lowest dimension as gap analysis.

Used Technologies:
- Java SE
- Java Swing
- ArrayList
- HashMap
- Object-Oriented Programming

How to Compile:
Open the terminal in the project folder and run this command:

javac -d out src/Main.java src/gui/*.java src/model/*.java src/data/*.java src/util/*.java

How to Run:
After compiling, run this command:

java -cp out Main

Screenshot:
A screenshot of the running application is included in the GitHub repository.