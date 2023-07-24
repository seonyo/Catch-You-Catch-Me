package common;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CommonUtil {
	
	public static void settings(JFrame f) {
		f.setSize(765,538);		
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
