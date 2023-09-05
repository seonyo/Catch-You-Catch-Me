package org.zero.frame;


import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static org.zero.common.CommonUtil.*;

public class Result extends JFrame{
	// 선언 및 생성
	private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
	private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
	private JLabel title = new JLabel("Result");
	private JLabel backgroundLabel = new JLabel(new ImageIcon(background));
	private JPanel basePanel = new JPanel(null);
	private JPanel rankPanel = new JPanel(new BorderLayout());
	private JPanel btnPanel = new JPanel(new GridLayout(10,1));

	private JTabbedPane tabbedPane = new JTabbedPane();
	private JScrollPane rankScroll = new JScrollPane();
	private JButton beforeBtn = new JButton();
	private JButton[] rankBtn = new JButton[20];
	public Result() {
		// 시작기본세팅 메서드
		settings(this);

		// 제목 스타일
		title.setFont(CommonUtil.titleFont);
		title.setForeground(titleColor);

		// 버튼 스타일
		beforeBtn.setIcon(preBtnImg);
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
		beforeBtn.setBackground(Color.WHITE);

		// 스크롤
//		rankScroll.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);// 항상 스크롤바 표시
//		rankScroll.setBackground(titleColor);
//		rankPanel.setBackground(titleColor);

		// 랭킹
//		for ( JButton btn : rankBtn ) {
//			btn = new JButton("버튼");
//			btn.revalidate();
//			btn.repaint();
//			btnPanel.add(btn);
//
//		}
		btnPanel.add(new JButton("버튼"));
		btnPanel.add(new JButton("버튼"));

		// 이벤트 처리
		beforeBtn.addActionListener( e -> {
			this.setVisible(false);
			new Main();
		});

		// 좌표 설정
		beforeBtn.setBounds(50,395,50,50);
		title.setBounds(330, 55, 120, 120);
		basePanel.setBounds(0, 0, 750, 500);
		basePanel.setBackground(Color.BLACK);
		rankPanel.setBounds(150,150, 300,300);
		rankScroll.setBounds(0,0, 300,300);
		backgroundLabel.setBounds(0,0,750,500);
		backgroundLabel.add(title);

		rankPanel.add(btnPanel);
		basePanel.add(backgroundLabel);
		basePanel.add(beforeBtn);
		basePanel.add(rankPanel);
		this.add(basePanel);
		this.setVisible(true);
//		basePanel.revalidate();
//		basePanel.repaint();
//		rankPanel.revalidate();
//		rankPanel.repaint();
//		beforeBtn.revalidate();
//		beforeBtn.repaint();
	}

	public static void main(String[] args) {
		Result r = new Result();
	}
}
