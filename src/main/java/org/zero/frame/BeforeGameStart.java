package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;

import static org.zero.common.CommonUtil.semiMidFont;
import static org.zero.common.CommonUtil.smallFont;


public class BeforeGameStart extends JFrame {
    Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();

    public BeforeGameStart(){
        CommonUtil.settings(this);
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); // JPanel의 paintComponent() 메소드 호출
                g.drawImage(background, 0, 0, null);
            }
        };
        p.setBounds(0,0,750,500);
        p.setLayout(null);

        JPanel pancelP = new JPanel();
        pancelP.setBounds(45,350,452,107);
        pancelP.setBackground(new Color (255,255,255));
        p.add(pancelP);

        JButton readyBtn = new JButton("준비");
        readyBtn.setBounds(616,368,90,30);
        readyBtn.setBackground(new Color(255,228,131));
        readyBtn.setForeground(new Color(142,110,0));
        readyBtn.setFont(semiMidFont);
        p.add(readyBtn);

        JButton exitBtn = new JButton("나가기");
        exitBtn.setBounds(616,411,90,30);
        exitBtn.setBackground(new Color(255,228,131));
        exitBtn.setForeground(new Color(142,110,0));
        exitBtn.setFont(semiMidFont);
        p.add(exitBtn);

        add(p);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BeforeGameStart();
    }

}

