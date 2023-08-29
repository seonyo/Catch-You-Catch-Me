package org.zero.frame;


import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.zero.common.CommonUtil.settings;

public class Result extends JFrame{
	
	private Container c;
	private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
	private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
	public Result() {
		// 시작기본세팅 메서드
		settings(this);
		
		JLabel title = new JLabel("Result");
		JLabel backgroundLabel = new JLabel(new ImageIcon(background));
		JPanel basePanel = new JPanel(null);
		JButton beforeBtn = new JButton();

		title.setFont(CommonUtil.midFont);

		beforeBtn.setIcon(preBtnImg);
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
		beforeBtn.setBackground(Color.WHITE);

		// 이벤트 처리
		beforeBtn.addActionListener( e -> {
			this.setVisible(false);
			new Main();
		});


		// 좌표 설정
		beforeBtn.setBounds(50,395,50,50);
		title.setBounds(335, 55, 120, 120);
		basePanel.setBounds(0, 0, 750, 500);
		backgroundLabel.setBounds(0,0,750,500);
		backgroundLabel.add(title);

		basePanel.add(backgroundLabel);
		basePanel.add(beforeBtn);
		this.getContentPane().add(basePanel);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		Result r = new Result();
	}
}
