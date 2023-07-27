package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.DriverManager;
//import java.sql.DriverManager;

public class ConnectionMgr {
	private static Connection con;

	static {
		try {
			Class.forName(DB.MySQL.DRIVER_NAME);
		} catch (Exception e) {
			System.out.println("DB 이름이 올바르지 않음");
			e.printStackTrace();
		}
	}

	// Connection 가져오기
	public static Connection getConnection() {
		if ( con == null ) {
			con = makeConnection();
		}
		return con;
	}

	// Connection 끊기
	public static void closeConnection() {
		if ( con != null ) {
			try {
				con.close();
			} catch (Exception e) {
				System.out.println("DB 연결 끊기 실패");
				e.printStackTrace();
			}
			con = null;
		}
	}

	// Connection 만들기
	public static Connection makeConnection() {
		try {
			con = DriverManager.getConnection(DB.MySQL.JDBC_URL);
			System.out.println("DB 연동 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연동 실패");
		}
		return con;
	}
	
	public static void main(String[] args) {
		ConnectionMgr cm = new ConnectionMgr();
		System.out.println("왜 안나와");
	}
}
