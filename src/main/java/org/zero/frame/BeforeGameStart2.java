package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import static java.lang.System.out;
import static org.zero.common.CommonUtil.*;


public class BeforeGameStart2 extends JFrame {
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
    private PrintWriter out;
    private JTextArea textArea;
    private JTextField textField;

    public BeforeGameStart2() {
        SwingUtilities.invokeLater(() -> {
        CommonUtil.settings(this);

        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        JPanel pancelP = new JPanel();
        pancelP.setBounds(40, 360, 470, 107);
        pancelP.setBackground(new Color(255, 255, 255));
        backgroundPanel.add(pancelP);

        DrawingPanel drawingPanel = new DrawingPanel();
        this.add(drawingPanel);
        drawingPanel.setBackground(new Color(255,255,255));
        drawingPanel.setBounds(40, 90, 470, 265);

        for (int i = 0; i < drawIcon.length; i++) {
            int index = i;
            JLabel label = new JLabel(new ImageIcon(drawIcon[i]));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    out.println("이미지 " + index + "가 클릭되었습니다.");
                    switch (index){
                        case 0: currentColor = new Color(255,12,12); break;
                        case 1: currentColor = new Color(248,89,0); break;
                        case 2: currentColor = new Color(255,213,64); break;
                        case 3: currentColor = new Color(23,189,9); break;
                        case 4: currentColor = new Color(58,41,255); break;
                        case 5: currentColor = new Color(162,10,255); break;
                        case 6: currentColor = new Color(255,60,212); break;
                        case 7: currentColor = new Color(0,0,0); break;
                        case 8 : currentColor = new Color(255,255,255); break;
                        case 9 : drawingPanel.clearDrawing(); vector.clear(); break;
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

        JPanel chattingPn = new JPanel(new BorderLayout());
        chattingPn.setBounds(525,140, 180,210);

        textArea = new JTextArea();
        textArea.setEditable(false);
        chattingPn.add(new JScrollPane(textArea), BorderLayout.CENTER);

        textField = new JTextField();
        chattingPn.add(textField, BorderLayout.SOUTH);

        textField.addActionListener( e -> {
            sendMessage(textField.getText());
            textField.setText("");
        });

        backgroundPanel.add(chattingPn);
        this.setVisible(true);
        });
        connectToServer();
    }

    private void sendMessage(String message) {
        out.println(message);
    }

    private void appendToTextArea(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
            textArea.setCaretPosition(textArea.getDocument().getLength());
        });
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8090); // 서버 주소와 포트를 설정하세요
                out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (true) {
                    String message = in.readLine();
                    appendToTextArea(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    class DrawingPanel extends JPanel {
        public void clearDrawing() {
            Graphics g = getGraphics();
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }


        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(x1Temp);
                    vector.add(y1Temp);
                    out.println("눌럿다");
                }

            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    out.println("야");
                }

            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {

                    // Draw a line with the current color
                    Graphics2D g = (Graphics2D) getGraphics();
                    g.setColor(currentColor);
                    g.drawLine(x1Temp, y1Temp, e.getX(), e.getY());
                    g.setStroke(new BasicStroke(20));
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(e.getX());
                    vector.add(e.getY());
                    out.println("드래그르륵");

                }
            });
        }

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BeforeGameStart2());
    }

}

