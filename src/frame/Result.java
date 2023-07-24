package frame;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import common.CommonUtil;

public class Result extends JFrame{
	public Result() {
		// 시작기본세팅 메서드
		CommonUtil.settings(this);
		
		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		Result r = new Result();
	}
}
