package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.zero.common.CommonUtil.*;

public class GameEnd extends JFrame {
    private JPanel backgroundPanel;
    Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
    Image clock = new ImageIcon(Main.class.getResource("/static/img/clockIcon.png")).getImage();

    public GameEnd(){
        CommonUtil.settings(this);
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        add(backgroundPanel);

        //clock 이미지 추가
        JPanel clockPanel = new JPanel() {
            public void paint(Graphics g) {//그리는 함수
                g.drawImage(clock, 0, 0, null);//background를 그려줌
            }
        };
        clockPanel.setBounds(220, 150, 800, 800);
        backgroundPanel.add(clockPanel);

        //시간 JLabel 추가
        JLabel time = new JLabel("05:14");
        time.setBounds(320, 159, 640, 50);
        time.setFont(timeFont);
        time.setForeground(new Color(142,110,0));
        backgroundPanel.add(time);

        //이름 Label 추가
        ArrayList<String> name = new ArrayList<>(Arrays.asList("노하은", "정선영", "이지수", "박화경"));
        int x = 188, y = 260;

        for(int i=0; i<name.size(); i++){
            JLabel nameLabel = new JLabel(name.get(i));
            nameLabel.setBounds(x,y,80,35);
            nameLabel.setForeground(new Color(79, 62, 2));
            nameLabel.setFont(semiLargeFont);
            backgroundPanel.add(nameLabel);
            x += 100;
        }
        
        //랭킹보기 버튼
        JButton rankingBtn = new JButton("랭킹보기");
        rankingBtn.setBounds(180,342, 170, 45);
        rankingBtn.setBackground(new Color(255,228,131));
        rankingBtn.setBorder(new RoundBorder(30, new Color(255, 228, 131), 2.0f));
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
        exitBtn.setBorder(new RoundBorder(30, new Color(255, 228, 131), 2.0f));
        exitBtn.setForeground(new Color(142, 110, 0));
        exitBtn.setFont(midFont);
        backgroundPanel.add(exitBtn);

        exitBtn.addActionListener(event->{
            dispose();
            new Main();
        });

        setVisible(true);

    }

    public static void main(String args[]) {new GameEnd();}
}
