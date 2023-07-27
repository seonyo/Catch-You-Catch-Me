package frame;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.CommonUtil;

public class Result extends JFrame{
	
	private Container c;
	private Image background = new ImageIcon(Main.class.getResource("../img/결과화면-제목없는ver.png")).getImage();
	
	public Result() {
		// 시작기본세팅 메서드
		CommonUtil.settings(this);
		
		JLabel title = new JLabel("Result");
		JPanel basePanel = new JPanel(null);
		JButton beforeBtn = new JButton(new ImageIcon("../img/before.png"));
		
		title.setFont(CommonUtil.midFont);
	
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
		beforeBtn.setContentAreaFilled(false);
		beforeBtn.setIcon(new ImageIcon("./img/before.png"));
		
		
		// 좌표 설정
		beforeBtn.setBounds(13,395,50,50);
		title.setBounds(0, 0, 120, 120);
		basePanel.setBounds(0, 0, 750, 500);
		
		basePanel.add(beforeBtn);
		basePanel.add(title);
		this.add(basePanel);
		this.setVisible(true);
//		this.repaint();
	}
	
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
	}
	
	public static void main(String[] args) {
		Result r = new Result();
	}
}
