package org.zero.frame;


import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static org.zero.common.CommonUtil.*;

public class Result extends JFrame{
	// 선언 및 생성
	private JPanel backgroundPanel;// 백그라운드 배경 넣는 패널
	private JPanel titlePanel = new JPanel(new BorderLayout());// 제목 넣는 패널
	private JPanel rankBackgroundPanel = new JPanel(null);// 랭킹 전체를 감싸는 패널
	private JPanel[] rankPanel;// 하나의 랭킹을 감싸는 패널
	private JPanel playersPanel = new JPanel();
	private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
	private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
	private ImageIcon rankBackgroundImg = new ImageIcon(Main.class.getResource("/static/img/rank_background.png"));
	private JLabel rankBackImgLabel = new JLabel(rankBackgroundImg);// 랭킹 배경 이미지
	private JLabel title = new JLabel("Result");
	private JLabel rank;// 등수
	JLabel[][] players = new JLabel[10][4];// 플레이어 명단
	private JScrollPane scroll = new JScrollPane();
	private JButton beforeBtn = new JButton();

	public Result() {
		// 시작기본세팅 메서드
		settings(this);
		// 배경 그리기
		makeBackground(this, backgroundPanel, background);

		// 제목
		title.setFont(CommonUtil.titleFont);
		title.setForeground(titleColor);
		titlePanel.setSize(330, 55);
		titlePanel.setBackground(new Color(0,0,0,0));
		titlePanel.setBounds(330, 55, 120, 120);
		titlePanel.add(title);

		// 버튼 스타일
		beforeBtn.setIcon(preBtnImg);
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
		beforeBtn.setBackground(Color.WHITE);
		beforeBtn.setBounds(40,395,50,50);

		// 스크롤
		JScrollPane sp = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setBounds(100,0, WIDTH-213,300);
		sp.setOpaque(false);// 배경색 투명하게
		sp.getViewport().setOpaque(false);// 배경색 투명하게
		sp.setBorder(createEmptyBorder());// 외곽선 투명하게
		rankBackgroundPanel.add(sp);

		// 랭킹
		rankBackgroundPanel.setBounds(0,140, CommonUtil.WIDTH,300);
		rankBackgroundPanel.setBackground(new Color(0,0,0,0));
		rankPanel = new JPanel[players.length];
		for ( int i = 0; i < players.length; i++) {
			rankPanel[i] = new JPanel(null);
			sp.add(rankPanel[i]);
			// 등수
			rank = new JLabel((i+1)+"");
			rank.setBounds(30,10, 20,22);
			rank.setFont(semiMidFont);
			rankPanel[i].add(rank);

			// 랭킹 배경 이미지
			rankBackImgLabel.setBounds(-15, 10, WIDTH, 45);
			rankPanel[i].add(rankBackImgLabel);

			// 플레이타임
			JLabel time = new JLabel("30:30");
			time.setFont(semiLargeFont);
			time.setForeground(titleColor);
			time.setBounds(440, -40, 120, 120);
			rankPanel[i].add(time);

			// 팀원 명단
			for (int j =  0; j < players[i].length; j++) {
				players[i][j] = new JLabel("노하은");
				players[i][j].setFont(semiMidFont);
				players[i][j].setBounds((80 + j * 80), 12, 120, 20);
				rankPanel[i].add(players[i][j]);
			}

			rankPanel[i].setBounds(100, 10+i*60, WIDTH-230, 45);
			rankBackgroundPanel.add(rankPanel[i]);
		}

		// 이벤트 처리
		beforeBtn.addActionListener( e -> {
			this.setVisible(false);
			new Main();
		});

		// 좌표 설정
		//scroll.setBounds(0,0, 300,300);

		backgroundPanel.add(rankBackgroundPanel);
		backgroundPanel.add(titlePanel);
		backgroundPanel.add(beforeBtn);

		this.setVisible(true);
	}


	public static void main(String[] args) {
		Result r = new Result();
	}
}
