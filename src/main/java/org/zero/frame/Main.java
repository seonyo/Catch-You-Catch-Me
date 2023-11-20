package org.zero.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.zero.common.CommonUtil;

import static org.zero.common.CommonUtil.*;

public class Main extends JFrame{
	private JPanel backgroundPanel;
	Image background = new ImageIcon(Main.class.getResource("/static/img/메인화면.png")).getImage();

    public Main() {
    	// 시작기본세팅 메서드

    	CommonUtil.settings(this);

		backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);


		//버튼 생성
		JButton rankingBtn = new JButton ("랭킹보기");
		JButton gamestartBtn = new JButton("게임시작");
		JButton explanBtn = new JButton("게임설명");

		//버튼 좌표 및 크기 설정
		rankingBtn.setBounds(106,370, 170, 45);
		gamestartBtn.setBounds(291,370, 170, 45);
		explanBtn.setBounds(476,370, 170, 45);

		//버튼 배경 색 설정
		rankingBtn.setBackground(new Color(255,255,255));
		gamestartBtn.setBackground(new Color(255,228,131));
		explanBtn.setBackground(new Color(255,255,255));

		//버튼 텍스트 색 설정
		rankingBtn.setForeground(new Color(168, 131, 0));
		gamestartBtn.setForeground(new Color(168, 131, 0));
		explanBtn.setForeground(new Color(142, 110, 0));

		//버튼 테두리 둥글게
		float borderWidth = 2.0f;

		//버튼 폰트 설정
		rankingBtn.setFont(midFont);
		gamestartBtn.setFont(midFont);
		explanBtn.setFont(midFont);

		//패널에 버튼 붙이기
		backgroundPanel.add(rankingBtn);
		backgroundPanel.add(gamestartBtn);
		backgroundPanel.add(explanBtn);

		//프레임에 패널 붙이기
		add(backgroundPanel);
		setVisible(true);

		rankingBtn.addActionListener(event ->{
			dispose();
			new Result();
		});
		gamestartBtn.addActionListener(event ->{
			dispose();
			new GameReady();
		});
		explanBtn.addActionListener(event ->{
			dispose();
			new GameExplan();
		});
	}

	public static void main(String[] args) {
		new Main();
	}

}

