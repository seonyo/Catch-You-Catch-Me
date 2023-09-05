package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;


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
        add(p);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BeforeGameStart();
    }

}

