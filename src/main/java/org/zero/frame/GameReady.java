package org.zero.frame;

import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import org.zero.common.CommonUtil;
import org.zero.db.ConnectionMgr;
import org.zero.db.DB;

import static java.util.Arrays.asList;
import static org.zero.common.CommonUtil.*;
import static org.zero.db.ConnectionMgr.closeConnection;
import static org.zero.db.ConnectionMgr.getConnection;

public class GameReady extends JFrame{
	private JPanel backgroundPanel;
	Image background = new ImageIcon(Main.class.getResource("/static/img/backGround.png")).getImage();
	String content="";
	String name="";
	public static Connection conn = null;
	public static Statement stmt = null;
	public GameReady() {
		// 시작기본세팅 메서드
		settings(this);
		String arrBtn[] = {"동물", "음식", "사물", "장소"};
		int btnX = 155;
		ArrayList<Integer> flag = new ArrayList<>(asList(0,0,0,0));
		//배경 패널 생성
		backgroundPanel = CommonUtil.makeBackground(backgroundPanel, background);
		add(backgroundPanel);

		JLabel idText = new JLabel("아이디");
		idText.setBounds(110,142,100,50);
		idText.setFont(midFont);
		backgroundPanel.add(idText);

		JTextField idTf = new JTextField();
		idTf.setBounds(188, 142, 360,50);
		idTf.setFont(midFont);
		backgroundPanel.add(idTf);

		JButton idBtn = new JButton("확인");
		idBtn.setBounds(570,142,80,50);
		idBtn.setBackground(new Color(255,228,131));
		idBtn.setFont(midFont);
		backgroundPanel.add(idBtn);

		JLabel readyText = new JLabel("플레이 할 주제를 선택하세요!");
		readyText.setBounds(235,210,400,40);
		readyText.setFont(midFont);
		readyText.setForeground(new Color(142,110,0));
		backgroundPanel.add(readyText);
		
		JButton btn [] = new JButton[4];
		for(int i=0; i<arrBtn.length; i++){
			btn[i] = new JButton(arrBtn[i]);
			btn[i].setBounds(btnX, 270, 100, 35);
			btnX += 115;
			btn[i].setBackground(new Color(255,255,255));
			btn[i].setFont(midFont);
			btn[i].setForeground(new Color (142,110,0));
			backgroundPanel.add(btn[i]);
		}
		//test
		JButton gamestartBtn = new JButton("게임시작");
		gamestartBtn.setBounds(500,383, 170, 45);
		gamestartBtn.setBackground(new Color(255,228,131));
		gamestartBtn.setBorder(new RoundBorder(30, new Color(255, 228, 131), 2.0f));
		gamestartBtn.setForeground(new Color(168, 131, 0));
		gamestartBtn.setFont(midFont);
		backgroundPanel.add(gamestartBtn);

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
				saveUserIdToDatabase(name, 1);
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

	private void saveUserIdToDatabase(String userId, int categoryIndex) {
		// 데이터베이스 연결을 설정하고 사용자 ID를 삽입합니다.
		try {
			conn = getConnection(DB.MySQL.JDBC_URL);
			String query = "INSERT INTO person (name, team_id, captain, category) VALUES ("+name+", 1, 1, "+categoryIndex+")";
			stmt = conn.createStatement();
			ResultSet rs;
			stmt.executeUpdate(query);
			rs = stmt.executeQuery("SELECT * FROM person");

			while ( rs.next()) {
				System.out.print(rs.getString("id")+" ");
				System.out.print(rs.getString("name")+" ");
				System.out.print(rs.getString("team_id")+" ");
				System.out.print(rs.getString("captain")+" ");
				System.out.print(rs.getString("category")+" ");
			}

			// 사용 후 close
			stmt.close();
			rs.close();
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveUserTopicToDatabase(String userId, String topic) {
		// 데이터베이스 연결을 설정하고 사용자가 선택한 주제를 업데이트합니다.
		try {
			conn = getConnection(DB.MySQL.JDBC_URL);
			String query = "UPDATE person SET selected_topic = ? WHERE user_id = ?";
			stmt = conn.prepareStatement(query);

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new GameReady();
	}
}
