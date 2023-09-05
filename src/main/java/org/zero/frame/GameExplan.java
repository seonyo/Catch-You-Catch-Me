package org.zero.frame;

import org.zero.common.CommonUtil;

import javax.swing.*;
import java.awt.*;

import static org.zero.common.CommonUtil.*;

public class GameExplan extends JFrame{
	Image background = new ImageIcon(Main.class.getResource("/static/img/게임설명.png")).getImage();
	String arr[]= {
			"1. 사용할 아이디를 입력하세요", "2. 그릴 그림의 주제를 선택하세요",
			"3. 팀원 모두 들어올때까지 기다리세요 (2~4명까지 플레이 가능해요)", "4. 모든 팀원이 들어왔다면 준비 버튼을 누르세요",
			"(팀원 모두 눌러야 게임을 시작할 수 있어요)" ,"5. 팀원이 그리는 그림이 무엇인지 맞추세요",
			"6. 자신의 턴이 돌아오면 키워드에 맞게 그림을 그리세요", "7. 키워드를 그리기 어렵다면 딱 한 번 주제 변경이 가능해요",
			"8. 8문제를 다 맞추면 끝!", "9. 게임이 끝난 뒤 우리팀의 랭킹을 확인해보세요"
	};
	int y = 140;
	public GameExplan() {
		// 시작기본세팅 메서드
		CommonUtil.settings(this);
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

		for(int i=0; i<arr.length; i++){
			JLabel jl = new JLabel(arr[i]);
			jl.setBounds(80, y, 640, 30); // 위치와 크기를 설정
			jl.setFont(semiMidFont);
			p.add(jl);
			y += 30; // 적절한 간격으로 조정
		}

		JLabel title = new JLabel("게임 방법");
		title.setBounds(320,90,200,40);
		title.setFont(semiLargeFont);
		title.setForeground(new Color(153,120,0));
		p.add(title);


		JButton gamestartBtn = new JButton("게임시작");
		gamestartBtn.setBounds(510,400, 170, 45);
		gamestartBtn.setBackground(new Color(255,228,131));
		gamestartBtn.setBorder(new RoundBorder(30, new Color(255, 228, 131), 2.0f));
		gamestartBtn.setForeground(new Color(168, 131, 0));
		gamestartBtn.setFont(midFont);
		p.add(gamestartBtn);


		setVisible(true);

		gamestartBtn.addActionListener(event ->{
			dispose();
			new GameReady();
		});
	}
	
	public static void main(String[] args) {
		new GameExplan();
	}
}
