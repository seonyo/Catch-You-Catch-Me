package org.zero.frame;

// SoloGroupCheck.java

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoloGroupCheck extends JFrame {
    JPanel panel = new JPanel();
    JLabel label = new JLabel();

    public SoloGroupCheck() {
        // SoloGroupCheck 클래스 생성자
        // SoloGroupCheck 프레임 설정

        setContentPane(panel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SoloGroupCheck");
        setResizable(false);
        setLayout(null);

        // SoloGroupCheck 프레임 내부의 패널 설정
        panel.setLayout(null); // 레이아웃 매니저를 사용하지 않음

        // "단체" 버튼 추가
        JButton groupButton = new JButton("단체 좌석");
        groupButton.setBounds(50, 50, 100, 30); // x, y, width, height

        // "개인" 버튼 추가
        JButton soloButton = new JButton("개인 좌석");
        soloButton.setBounds(200, 50, 100, 30); // x, y, width, height

        // 각 버튼에 ActionListener 등록
        groupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "단체" 버튼이 눌렸을 때 수행할 동작
                // Group.java로 이동
                dispose(); // 현재 프레임 닫기
            }
        });

        soloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // "개인" 버튼이 눌렸을 때 수행할 동작
                // Solo.java로 이동
                dispose(); // 현재 프레임 닫기
            }
        });

        // 버튼을 패널에 추가
        panel.add(groupButton);
        panel.add(soloButton);

        // 탭 패널 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(50, 100, 700, 400);

        // 첫 번째 탭에 추가할 컴포넌트 생성 및 추가
        JPanel statusPanel = new JPanel();
//        statusPanel.add(new JLabel("현재 현황을 보여주는 내용"));

        // 두 번째 탭에 추가할 컴포넌트 생성 및 추가
        JPanel anotherPanel = new JPanel();
//        anotherPanel.add(new JLabel("다른 현황을 보여주는 내용"));

        // 탭에 패널 추가
        tabbedPane.addTab("단체 좌석 현황 보기", statusPanel);
        tabbedPane.addTab("개인 좌석 현황 보기", anotherPanel);

        // SoloGroupCheck 프레임에 탭 패널 추가
        panel.add(tabbedPane);

        setVisible(true);


    }




    public static void main(String[] args) {
//        // Operator 클래스를 생성하여 SoloGroupCheck에 전달
//        Operator operator = new Operator();
//        SoloGroupCheck soloGroupCheck = new SoloGroupCheck(operator);
//        soloGroupCheck.setVisible(true);
        new SoloGroupCheck();
    }
}