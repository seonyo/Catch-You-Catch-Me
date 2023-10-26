package org.zero.db;

import org.zero.dto.RankDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RankDAO {
    Connection conn;
    public RankDAO() {
        this.conn = ConnectionMgr.getConnection(DB.MySQL.JDBC_URL);
    }

    public List<RankDTO> getPlayerListByTeam(int team_id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // GameDTO를 담을 공간 준비
        List<RankDTO> rankList = new ArrayList<>();

        String sql = "SELECT * FROM catchmind_db WHERE team_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, team_id+"");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int teamId = rs.getInt("team_id");
                String playerName = rs.getString("name");

                RankDTO rank = new RankDTO(teamId, playerName);
                rankList.add(rank);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if ( pstmt != null ) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                pstmt = null;
            }
            if ( rs != null ) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                rs = null;
            }
        }

        return rankList;
    }
}
