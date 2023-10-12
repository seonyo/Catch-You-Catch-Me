package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static org.zero.common.CommonUtil.*;


public class BeforeGameStart extends JFrame {
    private JPanel backgroundPanel;
    Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
    Image red = new ImageIcon(Main.class.getResource("/static/img/red.png")).getImage();
    Image orange = new ImageIcon(Main.class.getResource("/static/img/orange.png")).getImage();
    Image yellow = new ImageIcon(Main.class.getResource("/static/img/yellow.png")).getImage();
    Image green = new ImageIcon(Main.class.getResource("/static/img/green.png")).getImage();
    Image blue = new ImageIcon(Main.class.getResource("/static/img/blue.png")).getImage();
    Image purple = new ImageIcon(Main.class.getResource("/static/img/purple.png")).getImage();
    Image pink = new ImageIcon(Main.class.getResource("/static/img/pink.png")).getImage();
    Image black = new ImageIcon(Main.class.getResource("/static/img/black.png")).getImage();
    Image erase = new ImageIcon(Main.class.getResource("/static/img/erase.png")).getImage();
    Image trash = new ImageIcon(Main.class.getResource("/static/img/trash.png")).getImage();

    Image drawIcon[] = {red,orange,yellow,green,blue, purple,pink,black,erase,trash};

    public BeforeGameStart() {
        CommonUtil.settings(this);

        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        JPanel pancelP = new JPanel();
        pancelP.setBounds(40, 350, 470, 107);
        pancelP.setBackground(new Color(255, 255, 255));
        backgroundPanel.add(pancelP);

        for (int i = 0; i < drawIcon.length; i++) {
            final int index = i;
            JLabel label = new JLabel(new ImageIcon(drawIcon[i]));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("이미지 " + index + "가 클릭되었습니다.");
                }
            });
            pancelP.add(label);
        }

            JButton readyBtn = new JButton("준비");
            readyBtn.setBounds(616, 368, 90, 30);
            readyBtn.setBackground(new Color(255, 228, 131));
            readyBtn.setForeground(new Color(142, 110, 0));
            readyBtn.setFont(semiMidFont);
            backgroundPanel.add(readyBtn);

            JButton exitBtn = new JButton("나가기");
            exitBtn.setBounds(616, 411, 90, 30);
            exitBtn.setBackground(new Color(255, 228, 131));
            exitBtn.setForeground(new Color(142, 110, 0));
            exitBtn.setFont(semiMidFont);
            backgroundPanel.add(exitBtn);
            add(backgroundPanel);
            setVisible(true);
    }

    public static void main(String[] args) {
        new BeforeGameStart();
    }

}

