package org.zero.frame;

import java.awt.*;

import javax.swing.*;

import org.zero.common.CommonUtil;

import static org.zero.common.CommonUtil.*;

public class GameReady extends JFrame{
	Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
	public GameReady() {
		// 시작기본세팅 메서드
		CommonUtil.settings(this);

		//배경 패널 생성
		JPanel p = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g); // JPanel의 paintComponent() 메소드 호출
				g.drawImage(background, 0, 0, null);
			}
		};
		p.setBounds(0,0,750,500);
		p.setLayout(null);
		add(p);

		JTextField idTf = new JTextField();
		idTf.setBounds(240, 142, 360,50);
		idTf.setFont(midFont);
		p.add(idTf);

		JLabel idText = new JLabel("아이디");
		idText.setBounds(160,142,100,50);
		idText.setFont(midFont);
		p.add(idText);

		JLabel readyText = new JLabel("플레이 할 주제를 선택하세요!");
		readyText.setBounds(235,210,400,40);
		readyText.setFont(midFont);
		readyText.setForeground(new Color(142,110,0));
		p.add(readyText);
		setVisible(true);


	}
	
	public static void main(String[] args) {
		new GameReady();
	}
}
