package org.zero.db;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
//import java.sql.DriverManager;
//import java.sql.DriverManager;

public class ConnectionMgr {
	private static Connection conn;
	private static Statement stmt;
	private static String url = DB.MySQL.JDBC_URL;
	private static String user = "root";
	private static String password = "gkdms~!1357";

//	static {
//		try {
//			Class.forName(DB.MySQL.DRIVER_NAME);
//		} catch (Exception e) {
//			System.out.println("DB 이름이 올바르지 않음");
//			e.printStackTrace();
//		}
//	}

	// Connection 가져오기
	public static Connection getConnection() {
		if ( conn == null ) {
			conn = makeConnection();
		}
		return conn;
	}

	// Connection 끊기
	public static void closeConnection() {
		if ( conn != null ) {
			try {
				conn.close();
			} catch (Exception e) {
				System.out.println("DB 연결 끊기 실패");
				e.printStackTrace();
			}
			conn = null;
		}
	}

	// Connection 만들기
	public static Connection makeConnection() {
		try {
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연동 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB 연동 실패");
		}
		return conn;
	}
	
	public static void main(String[] args) throws SQLException {
		// 커넥션 객체 만들기
		conn = getConnection();

		stmt = conn.createStatement();
		ResultSet rs;

		// 데이터베이스 존재 시 삭제
		stmt.executeUpdate("DROP DATABASE IF EXISTS catchmind_db");

		// 데이터베이스 생성
		stmt.executeUpdate("CREATE DATABASE catchmind_db");

		// 데이터베이스 catchmind_db 사용하겠다고 선언
		stmt.executeUpdate("USE catchmind_db");

		// 테이블 생성
		stmt.executeUpdate("CREATE TABLE person (" +
				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
				"name VARCHAR(20) NOT NULL, " +
				"team_id INT NOT NULL, " +
				"captain TINYINT(1) NOT NULL, " +
				"category VARCHAR(20)" +
				");");

		// 테이블에 데이터 추가
		stmt.executeUpdate("INSERT INTO person (name, team_id, captain, category) VALUES ('noh', 1, 1, '동물')");

		// 쿼리의 결과 값(Select된 값)을 rs에 저장
		rs = stmt.executeQuery("SELECT * FROM person");

		while ( rs.next()) {
			System.out.print(rs.getString("id")+" ");
			System.out.print(rs.getString("name")+" ");
			System.out.print(rs.getString("team_id")+" ");
			System.out.print(rs.getString("captain")+" ");
			System.out.print(rs.getString("category")+" ");
		}


		// 사용 후 close
		stmt.close();
		rs.close();
		closeConnection();
	}
}
