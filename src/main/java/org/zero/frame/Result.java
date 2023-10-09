package org.zero.frame;


import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;

import static org.zero.common.CommonUtil.*;

public class Result extends JFrame{
	// 선언 및 생성
	private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
	private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
	private JLabel title = new JLabel("Result");
	private JLabel backgroundLabel = new JLabel(new ImageIcon(background));
	private JPanel rankPanel = new JPanel(new BorderLayout());
	private JScrollPane scroll = new JScrollPane();
	private JButton beforeBtn = new JButton();

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

		JLabel ranking1 = new JLabel("1");
		ranking1.setBounds(125,170, 20,20);
		ranking1.setFont(semiMidFont);
		this.add(ranking1);

		// 플레이어 명단
		JLabel []players = new JLabel[4];
		for (int i =  0; i < players.length; i++) {
			players[i] = new JLabel("노하은");
			players[i].setFont(semiMidFont);
			players[i].setBounds(100+((i+1)*80), 170, 120,20);
			this.add(players[i]);
		}
		JLabel time = new JLabel("30:30");
		time.setFont(semiLargeFont);
		time.setForeground(titleColor);
		time.setBounds(530, 120, 120, 120);
		this.add(time);

		// 랭킹 바탕 그림
		JLabel rankBackground = new JLabel();
		ImageIcon rankBackgroundImg = new ImageIcon(Main.class.getResource("/static/img/rank_background.png"));
		rankBackground.setIcon(rankBackgroundImg);
		rankBackground.setBounds(100,130,600,100);
		this.getContentPane().add(rankBackground);

		// 이벤트 처리
		beforeBtn.addActionListener( e -> {
			this.setVisible(false);
			new Main();
		});

		// 좌표 설정
		beforeBtn.setBounds(50,395,50,50);
		title.setBounds(330, 55, 120, 120);
		rankPanel.setBounds(150,150, 300,300);
		scroll.setBounds(0,0, 300,300);
		backgroundLabel.setBounds(0,0,750,500);
		backgroundLabel.add(title);

		this.add(backgroundLabel);
		this.add(beforeBtn);
		this.add(rankPanel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Result r = new Result();
	}
}
