package org.zero.db;

import java.sql.*;

public class ConnectionMgr {
	private static Connection conn;
	private static Statement stmt;
	private static String url = DB.MySQL.JDBC_URL;
	private static String user = "root";
	//private static String password = "gkdms~!1357";
	private static String password = "990327";

	// Connection 가져오기
	public static Connection getConnection(String jdbcUrl) {
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
		conn = getConnection(DB.MySQL.JDBC_URL);

		stmt = conn.createStatement();
		ResultSet rs;

		// 데이터베이스 존재 시 삭제
		stmt.executeUpdate("DROP DATABASE IF EXISTS catchmind_db");

		// 데이터베이스 생성
		stmt.executeUpdate("CREATE DATABASE catchmind_db");

		// 데이터베이스 catchmind_db 사용하겠다고 선언
		stmt.executeUpdate("USE catchmind_db");

		stmt.executeUpdate("DROP TABLE IF EXISTS user");
		// 테이블 생성
		stmt.executeUpdate("CREATE TABLE user (" +
				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
				"name VARCHAR(20), " +
				"team_id INT" +
				");");

		stmt.executeUpdate("DROP TABLE IF EXISTS topic");
		// 테이블 생성
		stmt.executeUpdate("CREATE TABLE topic (" +
				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
				"name VARCHAR(20)" +
				");");

		stmt.executeUpdate("INSERT INTO topic (name)" +
				"VALUES" +
				"('퇴학')," +
				"('우거지')," +
				"('피고인')," +
				"('핵가족')," +
				"('연장전')," +
				"('포크레인')," +
				"('삼국시대')," +
				"('풍년')," +
				"('새우젓')," +
				"('열매')," +
				"('소방관')," +
				"('전사자')," +
				"('태양')," +
				"('카레이서')," +
				"('개인기')," +
				"('가로수')," +
				"('사시나무')," +
				"('쥐불놀이')," +
				"('가격표')," +
				"('공중전화')," +
				"('불똥')," +
				"('양반')," +
				"('양팔')," +
				"('잠수')," +
				"('철종경기')," +
				"('코너킥')," +
				"('티눈')," +
				"('귓속말')," +
				"('백수')," +
				"('원빈')," +
				"('줄다리기')," +
				"('토양')," +
				"('초음파검사')," +
				"('창조물')," +
				"('창업자')," +
				"('중고생')," +
				"('손맛')," +
				"('무야호')," +
				"('너T야?')," +
				"('오타쿠')," +
				"('급똥')," +
				"('틱톡')," +
				"('중2병')," +
				"('수면')," +
				"('악취')," +
				"('동공지진')," +
				"('사악');");

		stmt.executeUpdate("DROP TABLE IF EXISTS current_topic");
		// 테이블 생성
		stmt.executeUpdate("CREATE TABLE current_topic (" +
				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
				"name VARCHAR(20)" +
				");");

		stmt.executeUpdate("DROP TABLE IF EXISTS ranking");
		// 테이블 생성
		stmt.executeUpdate("CREATE TABLE ranking (" +
				"id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
				"name VARCHAR(255)," +
				"time TIME," +
				"ranking INT);");

		stmt.executeUpdate("INSERT INTO ranking(name, time) VALUES" +
				"('하은,지민,선영', now())");
		stmt.executeUpdate("INSERT INTO ranking(name, time) VALUES" +
				"('하,지,선', now()+1)");
		stmt.executeUpdate("INSERT INTO ranking(name, time) VALUES" +
				"('은,민,영', now()+2)");
		stmt.executeUpdate("INSERT INTO ranking(name, time) VALUES" +
				"('하은1,지민1,선영1', now()+3)");
		stmt.executeUpdate("INSERT INTO ranking(name, time) VALUES" +
				"('하은2,지민2,선영2', now()-5)");

		//stmt.executeUpdate("SELECT name FROM ranking ORDER BY time");
		// 사용 후 close
		stmt.close();
//		rs.close();
		closeConnection();
	}
}
