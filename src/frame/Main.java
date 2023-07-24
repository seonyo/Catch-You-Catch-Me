package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.AbstractBorder;

import common.CommonUtil;

public class Main extends JFrame{
    Font buttonFont = new Font("Noto Sans KR", Font.BOLD, 20);
    public Main() {
    	// 시작기본세팅 메서드
    	CommonUtil.settings(this);
    	//배경 패널 생성
		 Panel p = new Panel() {
			Image background=new ImageIcon(Main.class.getResource("../img/메인화면.png")).getImage();
			public void paint(Graphics g) {
				g.drawImage(background,0,0,null);
				
			}
		};

	    
		p.setBounds(0,0,750,500);
		p.setLayout(null);
		
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
		rankingBtn.setBorder(new RoundBorder(30));
		gamestartBtn.setBorder(new RoundBorder(30));
		explanBtn.setBorder(new RoundBorder(30));
		
		//버튼 폰트 설정
		rankingBtn.setFont(buttonFont);
		gamestartBtn.setFont(buttonFont);
		explanBtn.setFont(buttonFont);
		
	
		//패널에 버튼 붙이기
		p.add(rankingBtn);
		p.add(gamestartBtn);
		p.add(explanBtn);
		
		//프레임에 패널 붙이기
		this.add(p);
		setVisible(true);

	}

	public static void main(String[] args) {
		new Main();
	}

}

//버튼 테두리 둥글게 만드는 클래스
class RoundBorder extends AbstractBorder{
	int radius;
	
	public RoundBorder (int radius) {
		this.radius = radius;
	}
	
	@Override
	public void paintBorder(java.awt.Component c, Graphics g, int x, int y, int width, int height) {
		g.setColor(c.getForeground());
		g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	}
	@Override
	public Insets getBorderInsets(java.awt.Component c) {
		return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
	 }
	   @Override
	    public Insets getBorderInsets(java.awt.Component c, Insets insets) {
	        insets.left = insets.right = this.radius + 1;
	        insets.top = insets.bottom = this.radius + 2;
	        return insets;
	    }

	    @Override
	    public boolean isBorderOpaque() {
	        return true;
	    }

	    public Shape getBorderShape(int x, int y, int width, int height) {
	        return new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius);
	    }
	}

