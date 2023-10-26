package org.zero.db;

import org.zero.dto.GameDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GameDAO {
    Connection conn;
    public GameDAO() {
        this.conn = ConnectionMgr.getConnection(DB.MySQL.JDBC_URL);
    }

    // 게임 정보 저장하는 작업
    public void saveGameInfo(GameDTO gameDto) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

//        String insertGameSql = "INSERT INTO catchmind_db("
    }
}
