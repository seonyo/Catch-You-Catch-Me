package org.zero.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class test {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Red Paint App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Create a JPanel to draw on
        JPanel drawingPanel = new DrawingPanel();
        frame.add(drawingPanel);

        // Create a JButton for red color
        JButton redButton = new JButton("Red");
//        redButton.addActionListener(e -> ((DrawingPanel) drawingPanel).setColor(Color.RED));
//        redButton.addActionListener(MouseEvent e){
//
//        }
        // Add the button to the frame
        frame.add(redButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

class DrawingPanel extends JPanel {
    private Color currentColor = Color.BLACK;
    private int prevX, prevY;

    public void setColor(Color color) {
        currentColor = color;
    }

    public DrawingPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                prevX = e.getX();
                prevY = e.getY();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                // Draw a line with the current color
                Graphics g = getGraphics();
                g.setColor(currentColor);
                g.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            }
        });
    }
}
