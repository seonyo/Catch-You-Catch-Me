package org.zero.common;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CommonUtil {
	public static int WIDTH = 765;
	public static int HEIGHT = 538;
	public static Font smallFont = new Font("Noto Sans KR", Font.BOLD, 10);
	public static Font semiMidFont = new Font("Noto Sans KR", Font.BOLD, 15);
	public static Font midFont = new Font("Noto Sans KR", Font.BOLD, 20);
	public static Font titleFont = new Font("Noto Sans KR", Font.BOLD, 25);
	public static Font semiLargeFont = new Font("Noto Sans KR", Font.BOLD, 25);
	public static Font largeFont = new Font("Noto Sans KR", Font.BOLD, 30);
	public static Color titleColor = new Color(142,110,0);

	public static void settings(JFrame f) {
		f.setSize(WIDTH,HEIGHT);
//		f.setSize(750,500);								

		f.setResizable(false);
		f.setTitle("CatchYouCatchMe");			
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	// 확인 창
	public static void infoMsg(Container container, String msg) {
		JOptionPane.showMessageDialog(container, msg, "안내", JOptionPane.INFORMATION_MESSAGE);
	}

	// Yes or No
	public static int infoMsg(Container container, String msg, String msg2) {
		int result = JOptionPane.showConfirmDialog(container, msg, msg2, JOptionPane.OK_CANCEL_OPTION);
		return result;
	}

	// 에러 창
	public static void errMsg(Container container, String msg) {
		JOptionPane.showMessageDialog(container, msg, "주의", JOptionPane.ERROR_MESSAGE);
	}
}
