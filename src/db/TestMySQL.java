package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQL {

	public static void main(String[] args) {
		Connection con;
		
		try {
			con = DriverManager.getConnection(DB.MySQL.JDBC_URL);
			System.out.println("DB 연결 성공");
			
			con.close();
		} catch (Exception e) {
			System.out.println("DB 실패");
			e.printStackTrace();
		}
	}

}
