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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import static org.zero.common.CommonUtil.*;
import static org.zero.db.ConnectionMgr.getConnection;


public class GamePlay extends JFrame {
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
    private static JPanel backgroundPanel;
    private static DrawingPanel drawingPanel;
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
    public static Connection conn = null;
    public static Statement stmt = null;
    private static PreparedStatement ps = null;
    private static ResultSet rs = null;
    private static JLabel categoryContentJL = null;// 현재 제시어
    private String currentTopic;// 현재 주제
    private int prevMax = 0; // 이전 최대 값
    public static int userCnt = 0;// 현재 유저 수
    private static int readyUserCnt = 0;
    //유저 이름 (임의의 값으로 초기화)
    ArrayList<String> nameArr = new ArrayList<>();
    private static Thread p_display;
    private Thread t_display;
    private static JLabel minute;
    private static JLabel second;
    private JLabel w3;
    private static int mm;
    private static int ss;
    private static int ms;
    private static int t = 0;
    private static String currentTime;// 현재 시간
    private static String userTempName[];
    private static JLabel categoryJL;

    public GamePlay(String userName) {

        CommonUtil.settings(this);
        this.userName = userName;
        dropCurrentTopic();// 현재 주제 초기화
        backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);
        setTimer(this.backgroundPanel);// timer

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

        //제시어 Label 추가 코드
        categoryJL = new JLabel("제시어");
        categoryJL.setForeground(new Color(89, 89, 89));
        categoryJL.setBounds(540, 358, 200, 50);
        categoryJL.setFont(semiMidFont);

        categoryContentJL = new JLabel(this.currentTopic);
        categoryContentJL.setHorizontalAlignment(JLabel.CENTER);
        categoryContentJL.setForeground(new Color(0, 0, 0));
        categoryContentJL.setBounds(513, 392, 100, 50);
        categoryContentJL.setFont(semiMidFont);
        backgroundPanel.add(categoryContentJL);

        // 문제 바꾸는 코드
        JButton changeBtn = new JButton("문제변경");
        changeBtn.addActionListener(e -> {
            writer.println("topic");
            writer.flush();
            //this.currentTopic = setCurrentTopic(this.currentTopic);
            //categoryContentJL.setText(this.currentTopic);
        });
        changeBtn.setBounds(616, 368, 90, 30);
        changeBtn.setBackground(new Color(255, 228, 131));
        changeBtn.setForeground(new Color(142, 110, 0));
        changeBtn.setFont(smallFont);
        backgroundPanel.add(changeBtn);

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

        add(backgroundPanel);
        this.setVisible(true);

        connectToServer();
    }

    private static void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 8090);
            writer = new PrintWriter(socket.getOutputStream());

            writer.println("user:" + userName);
            writer.flush();

            Thread readerThread = new Thread(new GamePlay.IncomingReader(socket));
            readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = messageField.getText();
        writer.println(message);
        if (message.replaceAll(" ", "").contains(currentTopic)) {
            //changeCurrentTopic();// 현재 주제 변경
        }
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

    // db table current_topic에 있는 모든 주제 초기화 (GamePlay가 시작될 때만 사용)
    private void dropCurrentTopic() {
        try {
            conn = getConnection(DB.MySQL.JDBC_URL);
            stmt = conn.createStatement();

            String sql = "DELETE FROM current_topic";
            ps = conn.prepareStatement(sql);

            int deleteCount = ps.executeUpdate();
            System.out.println(deleteCount + " 삭제됨");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("현재 주제 초기화 실패");
        }
    }

    // 현재 주제 정하기
    private static String setCurrentTopic(String currentTopic) {
        backgroundPanel.add(categoryJL);
        categoryContentJL.setText(currentTopic.substring(8));
        backgroundPanel.add(categoryContentJL);
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
        return currentTopic.substring(8);
    }



    // 맞추거나 패스했을 경우, 새 주제 정하기
    private void changeCurrentTopic() {
        writer.println("[ 정답: " + currentTopic + " ]");
        this.currentTopic = setCurrentTopic(this.currentTopic);
        categoryContentJL.setText(this.currentTopic);
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
                        userCnt = Integer.parseInt(message.substring(8));
                    } else if(message.startsWith("Time")){
                        processTimeMessage(message);
                    }
                    else if(message.startsWith("userName")) {
                        userTempName = processAddName(message.substring(11));
                        changeName(userTempName);
                    } else if(message.startsWith("Topic")){
                        System.out.println(message);
                        setCurrentTopic(message);
                    }

                    else {
                        chatArea.append(message + "\n");
                    }
                        {
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

        private void processTimeMessage(String message){
            setTextTimer(backgroundPanel,  message);
        }

        private String[] processAddName(String message){
            message = message.substring(1, message.length() - 1);
            String nameTempArr[] = message.split(",");
            return nameTempArr;
        }

        private void changeName(String[] userTempName){
            int nameX = 522;
            int nameY = 68;
            // 기존에 추가된 이름 JLabel 제거
            Component[] components = backgroundPanel.getComponents();
            for (Component component : components) {
                if (component instanceof JLabel) {
                    backgroundPanel.remove(component);
                }
            }

            // 서버에서 전송된 유저 이름 추가
            for (int i = 0; i < userTempName.length; i++) {
                JLabel nameJL = new JLabel(userTempName[i]);
                nameJL.setBounds(nameX, nameY, 60, 60);
                backgroundPanel.add(nameJL);
                nameX += 45;

                if (i == 1) {
                    nameX = 522;
                    nameY = 88;
                }
            }

            // 패널을 다시 그리도록 갱신
            backgroundPanel.revalidate();
            backgroundPanel.repaint();
        }
    }

    // 타이머
    private static void setTimer(JPanel backgroundPanel) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel c = new JLabel(" : ");
        minute = new JLabel("00");
        second = new JLabel("00");

        p.add(minute);
        p.add(c);
        p.add(second);

        p.setBounds(615, 90, 90, 40);
        p.setBackground(new Color(255, 255,253));
        backgroundPanel.add(p);

        minute.setFont(new Font("courier", Font.BOLD, 20));
        second.setFont(new Font("courier", Font.BOLD, 20));
        c.setFont(new Font("courier", Font.BOLD, 20));

        minute.setForeground(mainColor);
        second.setForeground(mainColor);
        c.setForeground(mainColor);

    }

    private static void setTextTimer(JPanel backgroundPanel, String message){
        minute.setText(message.substring(8,10));
        second.setText(message.substring(12,14));
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
       new GamePlay(userName);
    }
}
