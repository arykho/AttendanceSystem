package com.school;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class SQLiteManager {
	private SQLiteDataSource ds;
	private Connection conn;
	public static String basePath = System.getProperty("user.dir");

	SQLiteManager() {
		try {
			ds = new SQLiteDataSource();
			ds.setUrl("jdbc:sqlite:" + basePath + "/resources/sqlite.db");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println("Opened database");
	}
	
	public Connection getConnection() {
		return conn;
	}

	public void initTables() {
		Connection conn = getConnection();
		
		String classRoomQuery = "CREATE TABLE IF NOT EXISTS classroom ( " +
                "ID INTEGER PRIMARY KEY, " +
                "TEACHER TEXT NOT NULL, SUBJECT TEXT NOT NULL)";

		String studentQuery = "CREATE TABLE IF NOT EXISTS student ( " +
                "ID INTEGER PRIMARY KEY, " +
                "NAME TEXT NOT NULL, GRADE INTEGER NOT NULL, NICKNAME TEXT NOT NULL)";

		String studentClassesQuery = "CREATE TABLE IF NOT EXISTS student_classes ( " +
                "STUDENT_ID INTEGER PRIMARY KEY, " +
                "CLASS_ID INTEGER)";
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(classRoomQuery);
			stmt.executeUpdate(studentQuery);
			stmt.executeUpdate(studentClassesQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
