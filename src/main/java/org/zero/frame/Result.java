package org.zero.frame;

import org.zero.common.CommonUtil;
import org.zero.db.DB;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import static org.zero.db.ConnectionMgr.getConnection;

public class Result extends JFrame {
    private JPanel backgroundPanel;
    private JLabel title;
    private JPanel rankBackgroundPanel = new JPanel(null);
    private RankPanel[] rankPanels;
    private JButton beforeBtn;
    private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
    private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static int rankIndex = 0;
    private String[][] playerNames = null;
    private int teamNum = 0;

    public Result() {
        // 기본 설정
        CommonUtil.settings(this);
        // 배경 이미지
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);
        // 제목
        title = new JLabel("Result");
        title.setFont(CommonUtil.titleFont);
        title.setForeground(CommonUtil.mainColor);
        title.setBounds(330, 93, 120, 40);
        backgroundPanel.add(title);

        // 팀 수 세기
        teamNum = getTeamNum();
        playerNames = new String[teamNum][4];
        getPlayerName();// 각 팀별로 플레이어 이름을 2차원배열에 저장
//        for ( int i = 0; i < playerNames.length; i++ ) {
//            for ( int j = 0; j < playerNames[i].length; j++ ) {
//                System.out.print(playerNames[i][j]+", ");
//            }
//            System.out.println();
//        }
        // 랭킹 패널 생성
        rankPanels = new RankPanel[teamNum];
        for (int i = 0; i < rankPanels.length; i++) {
            rankPanels[i] = new RankPanel(i + 1);
            rankPanels[i].setBounds(0, 5 + i * 60, 550, 45);
//            setPlayerNamesToRanking(rankPanels[i]);
            rankBackgroundPanel.add(rankPanels[i]);
        }

        // 이전 버튼
        beforeBtn = new JButton();
        beforeBtn.setIcon(preBtnImg);
        beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
        beforeBtn.setFocusPainted(false);
        beforeBtn.setBackground(Color.WHITE);
        beforeBtn.setBounds(40, 395, 50, 50);
        beforeBtn.addActionListener(e -> {
            this.setVisible(false);
            new Main();
        });
        backgroundPanel.add(beforeBtn);

        // 스크롤을 포함한 랭킹 패널 생성
        JScrollPane scrollPane = new JScrollPane(rankBackgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(10, 155, WIDTH, 300); // 조절 가능
        backgroundPanel.add(scrollPane);

        // 화면 표시
        rankBackgroundPanel.setBackground(new Color(0, 0, 0, 0));
        rankBackgroundPanel.setBounds(100, 150, 550, 300);

        backgroundPanel.add(rankBackgroundPanel);
        this.add(backgroundPanel);
        this.setVisible(true);
    }

    // 상위 5위 팀 불러오기
    private int getTeamNum() {
        int rowCount = 0;
        try {
            conn = getConnection(DB.MySQL.JDBC_URL);
            stmt = conn.createStatement();
            String query = "SELECT COUNT(*) FROM ranking ORDER BY time LIMIT 5";
            rs = stmt.executeQuery(query);
            // 현재 ranking table의 사이즈 구하기
            rowCount = 0;
            while (rs.next()) {
                rowCount = Integer.parseInt(rs.getString("count(*)"));
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("팀 수 불러오기 실패");
        }
        return rowCount;
    }

    private void getPlayerName() {
        JLabel[] players = null;
        try {
            conn = getConnection(DB.MySQL.JDBC_URL);
            stmt = conn.createStatement();
            players = new JLabel[this.teamNum];// 랭킹 수
            String query = "SELECT name FROM ranking ORDER BY time LIMIT 5";
            rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                players[i] = new JLabel(rs.getString("name"));
                playerNames[i] = players[i].getText().split(",");
//                players[i].setFont(CommonUtil.semiMidFont);
//                players[i].setBounds(80 + i * 150, 12 + i * 60, 400, 20);
                i++;
            }

            //findUserName(this.userId);
            // 사용 후 close
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("사용자 이름 저장 실패");
        }
    }

    private void setPlayerNamesToRanking(JPanel panel) {
        JLabel[] players = new JLabel[playerNames[rankIndex].length];

        String teamPlayers = "";
        for (int i = 0; i < playerNames[rankIndex].length; i++) {
            System.out.print(playerNames[rankIndex][i] + " ");
            teamPlayers += playerNames[rankIndex][i] + "  ";
        }

        // 수정된 부분
        for (int i = 0; i < playerNames[rankIndex].length; i++) {
            players[i] = new JLabel(playerNames[rankIndex][i]);
            players[i].setFont(CommonUtil.semiMidFont);
            players[i].setBounds(80 + i * 90, 12 + rankIndex * 60, 400, 20);
            panel.add(players[i]);
        }

        System.out.println();
        rankIndex++;
        System.out.println(rankIndex);
    }


    private class RankPanel extends JPanel {
        public RankPanel(int rank) {
            this.setLayout(null);
            // 등수
            JLabel rankLabel = new JLabel(String.valueOf(rank));
            rankLabel.setFont(CommonUtil.semiMidFont);
            rankLabel.setBounds(30, 10, 20, 22);
            this.add(rankLabel);
            // 플레이타임
            JLabel timeLabel = new JLabel("30:30");
            timeLabel.setFont(CommonUtil.semiLargeFont);
            timeLabel.setForeground(CommonUtil.mainColor);
            timeLabel.setBounds(440, 10, 120, 20);
            this.add(timeLabel);

            // 팀원 명단
            setPlayerNamesToRanking(this);

            // 랭킹 배경 이미지
            JLabel rankBackImgLabel = new JLabel(new ImageIcon(Main.class.getResource("/static/img/rank_background.png")));
            rankBackImgLabel.setBounds(-107, 0, CommonUtil.WIDTH, 45);
            this.add(rankBackImgLabel);

        }
    }


    public static void main(String[] args) {
        new Result();
    }
}