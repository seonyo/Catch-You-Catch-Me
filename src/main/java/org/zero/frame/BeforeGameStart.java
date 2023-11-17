package org.zero.frame;

import org.zero.common.CommonUtil;
import org.zero.db.DB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;
import java.util.Vector;

import static org.zero.common.CommonUtil.*;
import static org.zero.db.ConnectionMgr.closeConnection;
import static org.zero.db.ConnectionMgr.getConnection;


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

    private Color currentColor = new Color(0, 0, 0);
    private int currentPenSize = 5; // 펜 굵기
    private int startX, startY; // 그림 그리기 시작 위치
    private Vector<Integer> vector = new Vector<Integer>();
    private int x1Temp, y1Temp;
    Image drawIcon[] = {red, orange, yellow, green, blue, purple, pink, black, erase, trash};
    private static JTextArea chatArea = new JTextArea();
    private static JTextField messageField;
    private static PrintWriter writer;
    private static String userName;
    private static Connection conn = null;
    private static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private int prevMax = 0; // 이전 최대 값
    public static int userCnt = 0;// 현재 유저 수
    public static int userReadyNowCnt = 0;// 현재 준비완료된 유저 수
    private static DrawingPanel drawingPanel;

    public BeforeGameStart(String userName) {

        CommonUtil.settings(this);
        this.userName = userName;
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);

        JPanel pancelP = new JPanel();
        pancelP.setBounds(40, 360, 470, 107);
        pancelP.setBackground(new Color(255, 255, 255));
        backgroundPanel.add(pancelP);

        drawingPanel = new DrawingPanel();
        this.add(drawingPanel);
        drawingPanel.setBackground(new Color(255, 255, 255));
        drawingPanel.setBounds(40, 90, 470, 265);

        for (int i = 0; i < drawIcon.length; i++) {
            int index = i;
            JLabel label = new JLabel(new ImageIcon(drawIcon[i]));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    switch (index) {
                        case 0:
                            currentColor = new Color(255, 12, 12);
                            break;
                        case 1:
                            currentColor = new Color(248, 89, 0);
                            break;
                        case 2:
                            currentColor = new Color(255, 213, 64);
                            break;
                        case 3:
                            currentColor = new Color(23, 189, 9);
                            break;
                        case 4:
                            currentColor = new Color(58, 41, 255);
                            break;
                        case 5:
                            currentColor = new Color(162, 10, 255);
                            break;
                        case 6:
                            currentColor = new Color(255, 60, 212);
                            break;
                        case 7:
                            currentColor = new Color(0, 0, 0);
                            break;
                        case 8:
                            currentColor = new Color(255, 255, 255);
                            break;
                        case 9:
                            drawingPanel.clearDrawing();
                            vector.clear();
                            break;
                    }
                }
            });
            pancelP.add(label);
        }

        JButton readyBtn = new JButton("준비");
        readyBtn.addActionListener(e -> {
            this.setVisible(false);
            new GamePlay(this.userName);
        });
        readyBtn.setBounds(616, 368, 90, 30);
        readyBtn.setBackground(new Color(255, 228, 131));
        readyBtn.setForeground(new Color(142, 110, 0));
        readyBtn.setFont(semiMidFont);
        backgroundPanel.add(readyBtn);

        JButton exitBtn = new JButton("나가기");
        exitBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "정말 게임을 종료하시겠습니까?");
            if (result == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Main();
            }
        });

        exitBtn.setBounds(616, 411, 90, 30);
        exitBtn.setBackground(new Color(255, 228, 131));
        exitBtn.setForeground(new Color(142, 110, 0));
        exitBtn.setFont(semiMidFont);
        backgroundPanel.add(exitBtn);
        add(backgroundPanel);

        JPanel chattingPn = new JPanel(new BorderLayout());
        chattingPn.setBounds(525, 140, 180, 210);

        // 채팅 기록
        chatArea.setEditable(false);
        // 자동 줄바꿈
        chatArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chattingPn.add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        chattingPn.add(messageField, BorderLayout.SOUTH);

        messageField.addActionListener(e -> {
            sendMessage();
        });

        // 최근 채팅에 포커싱
        focusRecentChat(scrollPane);

        backgroundPanel.add(chattingPn);
        this.setVisible(true);

        connectToServer();
    }

    private static void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8090);
            writer = new PrintWriter(socket.getOutputStream());

            writer.println("user:" + userName);
            writer.flush();

            Thread readerThread = new Thread(new IncomingReader(socket));
            readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        writer.println(message);
        writer.flush();
        messageField.setText("");

    }

    private void focusRecentChat(JScrollPane scrollPane) {
        scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (e.getAdjustable().getMaximum() != prevMax) {
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    prevMax = e.getAdjustable().getMaximum(); // 이전 최대 값을 업데이트
                }
            }
        });
    }

    static class IncomingReader implements Runnable {
        private Socket socket;
        private Scanner scanner;

        public IncomingReader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                scanner = new Scanner(socket.getInputStream());
                while (scanner.hasNextLine()) {
                    String message = scanner.nextLine();
                    if (message.startsWith("draw:")) {
                        processDrawingMessage(message);
                    } else if (message.equals("clear")) {
                        System.out.println("야야야");
                        processClearMessage(message);
                    } else if(message.startsWith("userCnt:")){
                        System.out.println(message);
                    }
                    else {
                        chatArea.append(message + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void processDrawingMessage(String message) {
            String[] parts = message.substring(5).split(",");
            int x1 = Integer.parseInt(parts[0]);
            int y1 = Integer.parseInt(parts[1]);
            int x2 = Integer.parseInt(parts[2]);
            int y2 = Integer.parseInt(parts[3]);
            Color color = new Color(Integer.parseInt(parts[4]));
            int penSize = Integer.parseInt(parts[5]);
            drawingPanel.drawLine(x1, y1, x2, y2, color, penSize);
        }

        private void processClearMessage(String message){
            drawingPanel.clearDrawing();
        }

    }


    class DrawingPanel extends JPanel {
        public void clearDrawing() {
            Graphics g = getGraphics();
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        public void drawLine(int x1, int y1, int x2, int y2, Color color, int penSize){
            Graphics2D g = (Graphics2D) getGraphics();
            g.setColor(color);
            g.setStroke(new BasicStroke(penSize));
            g.drawLine(x1, y1, x2, y2);
        }

        public DrawingPanel() {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(x1Temp);
                    vector.add(y1Temp);

                }

            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    x1Temp = e.getX();
                    y1Temp = e.getY();


                }

            });
            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {

                    // Draw a line with the current color
                    Graphics2D g = (Graphics2D) getGraphics();
                    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g.setColor(currentColor);
                    g.setStroke(new BasicStroke(currentPenSize, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    g.drawLine(x1Temp, y1Temp, e.getX(), e.getY());
                    x1Temp = e.getX();
                    y1Temp = e.getY();
                    vector.add(e.getX());
                    vector.add(e.getY());

                    writer.println("draw:" + x1Temp + "," + y1Temp + "," + e.getX() + "," + e.getY() + "," + currentColor.getRGB() + "," + currentPenSize);
                    writer.flush();
                }
            });
        }

    }

    public static void main(String[] args) {
        new BeforeGameStart("흥");
    }
}