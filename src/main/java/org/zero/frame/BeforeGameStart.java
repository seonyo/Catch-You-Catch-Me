package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Vector;

import static org.zero.common.CommonUtil.*;


public class BeforeGameStart extends JFrame {
    Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
    private JPanel backgroundPanel;
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

    private Color currentColor = new Color(0,0,0);
    private int currentPenSize = 5; // 펜 굵기
    private int startX, startY; // 그림 그리기 시작 위치
    private Vector <Integer> vector = new Vector<Integer>();
    private int x1Temp, y1Temp;
    Image drawIcon[] = {red,orange,yellow,green,blue, purple,pink,black,erase,trash};

    public BeforeGameStart() {
        CommonUtil.settings(this);

        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        JPanel pancelP = new JPanel();
        pancelP.setBounds(40, 350, 470, 107);
        pancelP.setBackground(new Color(255, 255, 255));
        backgroundPanel.add(pancelP);

        JPanel drawingPanel = new DrawingPanel();
        this.add(drawingPanel);
        drawingPanel.setBackground(new Color(255,255,255));
        drawingPanel.setBounds(40, 90, 470, 250);

        for (int i = 0; i < drawIcon.length; i++) {
            int index = i;
            JLabel label = new JLabel(new ImageIcon(drawIcon[i]));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("이미지 " + index + "가 클릭되었습니다.");
                    switch (index){
                        case 0: currentColor = new Color(255,12,12); break;
                        case 1: currentColor = new Color(248,89,0); break;
                        case 2: currentColor = new Color(255,213,64); break;
                        case 3: currentColor = new Color(23,189,9); break;
                        case 4: currentColor = new Color(58,41,255); break;
                        case 5: currentColor = new Color(162,10,255); break;
                        case 6: currentColor = new Color(255,60,212); break;
                        case 7: currentColor = new Color(0,0,0); break;
                    }
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


    class DrawingPanel extends JPanel {

        public  DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(x1Temp);
                    vector.add(y1Temp);
                    System.out.println("눌럿다");
                }

            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    System.out.println("야");
                }

            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {

                    // Draw a line with the current color
                    Graphics2D g = (Graphics2D) getGraphics();
                    g.setColor(currentColor);
                    g.drawLine(x1Temp, y1Temp, e.getX(), e.getY());
                    g.setStroke(new BasicStroke(20.0f));
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(e.getX());
                    vector.add(e.getY());
                    System.out.println("드래그르륵");

                }
            });
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BeforeGameStart());
    }

}

