package com.school;

import java.sql.Connection;
import java.sql.SQLException;

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
}
