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
    private String[][] playerNames = null;
    private String[] teamTimes = null;
    private int teamNum = 0;
    private static int nameRankIndex;
    private static int timeRankIndex;

    public Result() {
        // 기본 설정
        CommonUtil.settings(this);
        nameRankIndex = 0;
        timeRankIndex = 0;
        // 배경 이미지
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);
        // 제목
        title = new JLabel("Ranking");
        title.setFont(CommonUtil.titleFont);
        title.setForeground(CommonUtil.mainColor);
        title.setBounds(322, 93, 120, 40);
        backgroundPanel.add(title);

        // 팀 수 세기
        teamNum = getTeamNum();
        playerNames = new String[teamNum][4];// 각 팀별로 플레이어 이름을 2차원배열에 저장
        getPlayerName();
        teamTimes = new String[teamNum];// 각 팀별로 걸린 시간을 1차원배열에 저장

        // 랭킹 패널 생성
        rankPanels = new RankPanel[teamNum];
        for (int i = 0; i < rankPanels.length; i++) {
            rankPanels[i] = new RankPanel(i + 1);
            rankPanels[i].setBounds(0, 5 + i * 60, 550, 45);
            rankPanels[i].setBackground(new Color(0, 0, 0, 0));
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
                i++;
            }
            // 사용 후 close
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("사용자 이름 2차원 배열로 저장 실패");
        }
    }

    private void getTeamTime(JPanel panel) {
        JLabel[] times = new JLabel[teamNum];
        try {
            conn = getConnection(DB.MySQL.JDBC_URL);
            stmt = conn.createStatement();
            String query = "SELECT time FROM ranking ORDER BY time LIMIT 5";
            rs = stmt.executeQuery(query);

            int i = 0;
            while (rs.next()) {
                // db에서 time컬럼의 값을 가져온다
                times[i] = new JLabel(rs.getString("time"));
                // 플레이타임 1차원배열에 저장
                String[] currentTime = times[i].getText().split(":");
                teamTimes[i++] = String.format("%02d : %02d", Integer.parseInt(currentTime[0]), Integer.parseInt(currentTime[1]));// ms는 반영하지 않는다
            }
            
            // 사용 후 close
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("걸린 시간 불러오기 실패");
        }
    }

    // 팀별 이름 랭킹에 보여주기
    private void setPlayerNamesToRanking(JPanel panel) {
        JLabel[] players = new JLabel[playerNames[nameRankIndex].length];

        // 팀별 이름들을 묶어서 문자열로 저장
        String teamPlayers = "";
        for (int i = 0; i < playerNames[nameRankIndex].length; i++) {
            teamPlayers += playerNames[nameRankIndex][i] + "  ";
        }

        // 이름들 배치
        for (int i = 0; i < playerNames[nameRankIndex].length; i++) {
            players[i] = new JLabel(playerNames[nameRankIndex][i]);
            players[i].setFont(CommonUtil.semiMidFont);
            players[i].setBounds(80 + i * 90, 12, 400, 20);
            panel.add(players[i]);
        }
        nameRankIndex++;
    }

    // 팀별 걸린 시간 랭킹에 보여주기
    private void setTeamTimesToRanking(JPanel panel) {
        JLabel timeLabel = new JLabel(teamTimes[timeRankIndex]);
        System.out.println(teamTimes[timeRankIndex]);
        timeLabel.setFont(CommonUtil.semiLargeFont);
        timeLabel.setForeground(CommonUtil.mainColor);
        timeLabel.setBounds(440, 10, 120, 20);
        panel.add(timeLabel);
        timeRankIndex++;
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
            getTeamTime(this);

            // 팀원 명단
            setPlayerNamesToRanking(this);

            // 걸린 시간
            setTeamTimesToRanking(this);

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