# Blog Engine - Java Project

A simple Java console-based Blog Engine that supports basic user management and blog publishing features using a MySQL backend.

---

## ✨ Features

- User Registration and Login
- Admin Login (for managing system)
- Blog Posting
- View all posts
- Store and retrieve user/post data from MySQL

---

## 🛠️ Technologies Used

- Java (Standard Edition)
- JDBC (Java Database Connectivity)
- MySQL
- HashMap, ArrayList, Scanner

---

## 🗃️ Database Schema

Before running the app, make sure the following database and tables exist:

### Database


Blog Engine - Java Project
==========================

A simple Java console-based Blog Engine that supports basic user management and blog publishing features using a MySQL backend.

Features
--------
- User Registration and Login
- Admin Login (for managing system)
- Blog Posting
- View all posts
- Store and retrieve user/post data from MySQL

Technologies Used
-----------------
- Java (Standard Edition)
- JDBC (Java Database Connectivity)
- MySQL
- HashMap, ArrayList, Scanner

Database Schema
---------------
Before running the app, make sure the following database and tables exist:

1. Create Database:
   CREATE DATABASE blogengine;
   USE blogengine;

2. Create Tables:
   CREATE TABLE user_details (
       User_id VARCHAR(255) PRIMARY KEY,
       Password VARCHAR(255)
   );

   CREATE TABLE blog_posts (
       Title VARCHAR(255),
       Content TEXT,
       Author VARCHAR(255)
   );

How to Run
----------
1. Clone or Download
   - Clone this repository or download the 'blog_engine.java' file.

2. Setup Database
   - Ensure MySQL is running and create the 'blogengine' database with appropriate tables.

3. Configure Database Connection
   - Update the DB URL and credentials in the Java file:
     Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/blogengine", "root", "");

4. Compile and Run
   javac blog_engine.java
   java blog_engine

Admin Access
------------
Username: Kavya
Password: password

(You can change these values in the code.)

Notes
-----
- Ensure MySQL server is running before executing the program.
- This is a command-line-based interface, so interaction is through terminal/console.
- You may extend this project with a GUI, servlet/JSP frontend, or REST APIs.

Author
------
Developed by Kavya Patel
You can change these values in the code:

java
Copy
Edit
String Adminid = "Kavya";
String Admipass = "password";
📌 Notes
Ensure MySQL server is running before executing the program.

This is a command-line-based interface, so interaction is through terminal/console.

You may extend this project with a GUI, servlet/JSP frontend, or REST APIs for a full-stack blog system.

📄 License
This project is free to use and modify.
