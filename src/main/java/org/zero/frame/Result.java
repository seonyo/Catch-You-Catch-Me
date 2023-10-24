package org.zero.frame;
import org.zero.common.CommonUtil;
import javax.swing.*;
import java.awt.*;
import static org.zero.common.CommonUtil.*;
public class Result extends JFrame {
	private JPanel backgroundPanel;
	private JLabel title;
	private JPanel rankBackgroundPanel = new JPanel(null);
	private RankPanel[] rankPanels;
	private JButton beforeBtn;
	private Image background = new ImageIcon(Main.class.getResource("/static/img/result.png")).getImage();
	private ImageIcon preBtnImg = new ImageIcon(Main.class.getResource("/static/img/beforeBtn.png"));
	public Result() {
		// 기본 설정
		CommonUtil.settings(this);
		// 배경 이미지
		backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);
		// 제목
		title = new JLabel("Result");
		title.setFont(CommonUtil.titleFont);
		title.setForeground(CommonUtil.titleColor);
		title.setBounds(330, 93, 120, 40);
		backgroundPanel.add(title);
		// 랭킹 패널 생성
		rankPanels = new RankPanel[10];
		for (int i = 0; i < rankPanels.length; i++) {
			rankPanels[i] = new RankPanel(i + 1);
			rankPanels[i].setBounds(0, 5 + i * 60, 550, 45);
			rankPanels[i].setBackground(new Color(0,0,0,0));
			rankBackgroundPanel.add(rankPanels[i]);
		}

		// 이전 버튼
		beforeBtn = new JButton();
		beforeBtn.setIcon(preBtnImg);
		beforeBtn.setBorderPainted(false); // 버튼 테두리 설정해제
		beforeBtn.setFocusPainted(false);
		beforeBtn.setBackground(Color.WHITE);
		beforeBtn.setBounds(40,395,50,50);
		beforeBtn.addActionListener(e -> {
			this.setVisible(false);
			new Main();
		});
		backgroundPanel.add(beforeBtn);

		// 스크롤을 포함한 랭킹 패널 생성
		JScrollPane scrollPane = new JScrollPane(rankBackgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 155, WIDTH, 300); // 조절 가능
		backgroundPanel.add(scrollPane);

		// 화면 표시
		rankBackgroundPanel.setBackground(new Color(0,0,0,0));
		rankBackgroundPanel.setBounds(100, 150, 550, 300);

		backgroundPanel.add(rankBackgroundPanel);
		this.add(backgroundPanel);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new Result();
	}
	private class RankPanel extends JPanel {
		public RankPanel(int rank) {
			this.setLayout(null);
			// 등수
			JLabel rankLabel = new JLabel(String.valueOf(rank));
			rankLabel.setFont(CommonUtil.semiMidFont);
			rankLabel.setBounds(30, 10, 20, 22);
			this.add(rankLabel);
			// 플레이타임
			JLabel timeLabel = new JLabel("30:30");
			timeLabel.setFont(CommonUtil.semiLargeFont);
			timeLabel.setForeground(CommonUtil.titleColor);
			timeLabel.setBounds(440, 10, 120, 20);
			this.add(timeLabel);
			// 팀원 명단
			JLabel[] players = new JLabel[4];
			for (int i = 0; i < players.length; i++) {
				players[i] = new JLabel("노하은");
				players[i].setFont(CommonUtil.semiMidFont);
				players[i].setBounds(80 + i * 80, 12, 120, 20);
				this.add(players[i]);
			}
			// 랭킹 배경 이미지
			JLabel rankBackImgLabel = new JLabel(new ImageIcon(Main.class.getResource("/static/img/rank_background.png")));
			rankBackImgLabel.setBounds(-107, 0, CommonUtil.WIDTH, 45);
			this.add(rankBackImgLabel);

		}
	}
}