package org.zero.frame;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import org.zero.common.CommonUtil;

import static java.util.Arrays.asList;
import static org.zero.common.CommonUtil.*;

public class GameReady extends JFrame{
	Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
	String content="";
	String name="";
	public GameReady() {
		// 시작기본세팅 메서드
		settings(this);
		String arrBtn[] = {"동물", "음식", "사물", "장소"};
		int btnX = 155;
		ArrayList<Integer> flag = new ArrayList<>(asList(0,0,0,0));
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

		JLabel idText = new JLabel("아이디");
		idText.setBounds(110,142,100,50);
		idText.setFont(midFont);
		p.add(idText);

		JTextField idTf = new JTextField();
		idTf.setBounds(188, 142, 360,50);
		idTf.setFont(midFont);
		p.add(idTf);

		JButton idBtn = new JButton("확인");
		idBtn.setBounds(570,142,80,50);
		idBtn.setBackground(new Color(255,228,131));
		idBtn.setFont(midFont);
		p.add(idBtn);

		JLabel readyText = new JLabel("플레이 할 주제를 선택하세요!");
		readyText.setBounds(235,210,400,40);
		readyText.setFont(midFont);
		readyText.setForeground(new Color(142,110,0));
		p.add(readyText);
		
		JButton btn [] = new JButton[4];
		for(int i=0; i<arrBtn.length; i++){
			btn[i] = new JButton(arrBtn[i]);
			btn[i].setBounds(btnX, 270, 100, 35);
			btnX += 115;
			btn[i].setBackground(new Color(255,255,255));
			btn[i].setFont(midFont);
			btn[i].setForeground(new Color (142,110,0));
			p.add(btn[i]);
		}
		//test
		JButton gamestartBtn = new JButton("게임시작");
		gamestartBtn.setBounds(500,383, 170, 45);
		gamestartBtn.setBackground(new Color(255,228,131));
		gamestartBtn.setBorder(new RoundBorder(30, new Color(255, 228, 131), 2.0f));
		gamestartBtn.setForeground(new Color(168, 131, 0));
		gamestartBtn.setFont(midFont);
		p.add(gamestartBtn);

		btn[0].addActionListener(event -> {
			changeBtn(btn[0], 0, flag, btn);
		});

		btn[1].addActionListener(event -> {
			changeBtn(btn[1], 1, flag, btn);
		});

		btn[2].addActionListener(event -> {
			changeBtn(btn[2], 2, flag, btn);
		});

		btn[3].addActionListener(event -> {
			changeBtn(btn[3], 3, flag, btn);

		});

		idBtn.addActionListener(event->{
			name = idTf.getText();
			if(name.equals("")){
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
			}
			else {
				JOptionPane.showMessageDialog(null, "아이디가 정상적으로 등록 되었습니다.");
				idBtn.setBackground(new Color(198,198,198));
			}
		});

		gamestartBtn.addActionListener(event ->{
			if(content.equals("")){
				JOptionPane.showMessageDialog(null, "주제를 선택하세요.");
			}
			else if(name.equals("")){
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
			}
			else{
				dispose();
				new BeforeGameStart();
			}
		});
		setVisible(true);


	}

	public void changeBtn(JButton btn, int index, ArrayList<Integer> flag, JButton button[]){
		content = btn.getText();
		if (flag.get(index) == 0) {
			btn.setBackground(new Color(255, 228, 131));
			if (flag.contains(1)) {
				int changeColor = flag.indexOf(1);
				button[changeColor].setBackground(new Color(255, 255, 255));
				flag.set(changeColor, 0);
			}
			flag.set(index, 1);
		} else {
			btn.setBackground(new Color(255, 255, 255));
			flag.set(index, 0);
		}
	}
	
	public static void main(String[] args) {
		new GameReady();
	}
}
