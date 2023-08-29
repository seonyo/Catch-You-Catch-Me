package org.zero.frame;


import javax.swing.*;
import java.awt.*;

public class Result extends JFrame{
	
	private Container c;
	private Image background = new ImageIcon(Main.class.getResource("/static/img/결과화면.png")).getImage();
	public Result() {
		// 시작기본세팅 메서드
		org.zero.common.CommonUtil.settings(this);
		
		JLabel title = new JLabel("Result");
		JLabel backgroundLabel = new JLabel(new ImageIcon(background));
		JPanel basePanel = new JPanel();
		JButton beforeBtn = new JButton(new ImageIcon("static/img/beforeBtn.png"));
		JButton beforeBtn1 = new JButton(new ImageIcon("static/img/bono.png"));
		
		title.setFont(org.zero.common.CommonUtil.midFont);
	
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
//		beforeBtn.setContentAreaFilled(false);
		beforeBtn.setBackground(Color.WHITE);
		title.setIcon(new ImageIcon("static/img/result.png"));
		
		// 좌표 설정
		beforeBtn.setBounds(13,395,50,50);
		title.setBounds(100, 100, 120, 120);
		beforeBtn1.setBounds(150, 150, 120, 120);
		basePanel.setBounds(0, 0, 750, 500);
		backgroundLabel.setBounds(0,0,750,500);
//		basePanel.setBackground(Color.BLUE);
		backgroundLabel.add(title);
		
		basePanel.add(backgroundLabel);
//		basePanel.add(title, new Integer(0));
		basePanel.add(beforeBtn);
		basePanel.add(beforeBtn1);
		basePanel.setLayout(null);
		this.getContentPane().add(basePanel);
		this.setVisible(true);
//		this.setLayout(null);
		this.setLayout(null);
//		this.repaint();
	}
/*	
	public void paint(Graphics g) {
		paintComponents(g);
		g.drawImage(background, 0, 0, null);
	}
	*/
	public static void main(String[] args) {
		Result r = new Result();
	}
}
