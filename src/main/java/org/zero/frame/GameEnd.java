package org.zero.frame;

import org.zero.common.CommonUtil;
import org.zero.db.DB;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.zero.common.CommonUtil.*;
import static org.zero.db.ConnectionMgr.getConnection;

public class GameEnd extends JFrame {
    private JPanel backgroundPanel;
    Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
    Image clock = new ImageIcon(Main.class.getResource("/static/img/clockIcon.png")).getImage();
    public static Connection conn = getConnection("dasdaf");
    public static Statement stmt = null;
    public GameEnd(String currentTime, ArrayList<String> userTempName){
        CommonUtil.settings(this);
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        add(backgroundPanel);

        System.out.println(currentTime);
        System.out.println(userTempName);


        //clock 이미지 추가
        JPanel clockPanel = new JPanel() {
            public void paint(Graphics g) {//그리는 함수
                g.drawImage(clock, 0, 0, null);//background를 그려줌
            }
        };
        clockPanel.setBounds(220, 150, 800, 800);
        backgroundPanel.add(clockPanel);

        //시간 JLabel 추가
//        JLabel time = new JLabel("05:14");
//        time.setBounds(320, 159, 640, 50);
//        time.setFont(timeFont);
//        time.setForeground(new Color(142,110,0));
//        backgroundPanel.add(time);

        JLabel c = new JLabel(" : ");
        JLabel minute = new JLabel(currentTime.substring(8,10));
        JLabel second = new JLabel(currentTime.substring(12,14));

//        JLabel minute = new JLabel("00");
//        JLabel second = new JLabel("11");

        c.setFont(timeFont);
        c.setForeground(new Color(142,110,0));
        minute.setFont(timeFont);
        minute.setForeground(new Color(142,110,0));
        second.setFont(timeFont);
        second.setForeground(new Color(142,110,0));

        minute.setBounds(320, 162, 800, 50);
        c.setBounds(390, 159, 640, 50);
        second.setBounds(445, 162, 800, 50);

        backgroundPanel.add(c);
        backgroundPanel.add(minute);
        backgroundPanel.add(second);
        //이름 Label 추가
        int x = 188, y = 260;

        for(int i=0; i<userTempName.size(); i++){
            JLabel nameLabel = new JLabel(userTempName.get(i));
            nameLabel.setBounds(x,y,180,35);
            nameLabel.setForeground(new Color(79, 62, 2));
            nameLabel.setFont(semiLargeFont);
            backgroundPanel.add(nameLabel);
            x += 100;
        }
        
        //랭킹보기 버튼
        JButton rankingBtn = new JButton("랭킹보기");
        rankingBtn.setBounds(180,342, 170, 45);
        rankingBtn.setBackground(new Color(255,228,131));
        rankingBtn.setForeground(new Color(168, 131, 0));
        rankingBtn.setFont(midFont);
        backgroundPanel.add(rankingBtn);

        rankingBtn.addActionListener(event->{
           dispose();
           new Result();
        });

        //나가기 버튼
        JButton exitBtn = new JButton("나가기");
        exitBtn.setBounds(400,342, 170, 45);
        exitBtn.setBackground(new Color(255,255,255));
        exitBtn.setForeground(new Color(142, 110, 0));
        exitBtn.setFont(midFont);
        backgroundPanel.add(exitBtn);

        exitBtn.addActionListener(event->{
            dispose();
            new Main();
        });

        try{
            conn = getConnection(DB.MySQL.JDBC_URL);
            String name= userTempName.toString().replace("[","").replace("]","");
            String Time = currentTime.substring(7).replace(" ","");
            String query = "INSERT INTO ranking (name, time) VALUES ('"+name+"', '"+Time+"')";
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        }catch (SQLException e) {
            e.printStackTrace();
        }

        setVisible(true);

    }

    public static void main(String args[]) {
        ArrayList <String> temp = new ArrayList<>();
        new GameEnd("Time : 00 : 11", temp);}
}
