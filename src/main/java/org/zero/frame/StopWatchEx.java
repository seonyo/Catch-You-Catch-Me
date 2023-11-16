package org.zero.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StopWatchEx extends JFrame{

    JButton start, reset, pause;
    Thread p_display, t_display;
    JLabel w1, w2, w3;
    int mm, ss, ms, t=0;
    /* mm : minute
     * ss : second
     * ms : millisecond
     *		(0.01s)
     */

    public StopWatchEx() {
        super("스탑워치");

        buildGUI();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(600, 300);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();

    }

    private void buildGUI() {
        JPanel p = new JPanel(new BorderLayout());
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel wp = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel c1 = new JLabel(" : ");
        JLabel c2 = new JLabel(" : ");
        w1 = new JLabel("00");
        w2 = new JLabel("00");
        w3 = new JLabel("00");

        start = new JButton("START");
        pause = new JButton("PAUSE");
        reset = new JButton("RESET");
        bp.add(start);
        bp.add(pause);
        bp.add(reset);

        wp.add(w1);
        wp.add(c1);
        wp.add(w2);
        wp.add(c2);
        wp.add(w3);

        p.add(wp, BorderLayout.CENTER);
        p.add(bp, BorderLayout.SOUTH);
        add(p);

        start.setFont(new Font("serif",Font.BOLD,25));
        pause.setFont(new Font("serif",Font.BOLD,25));
        reset.setFont(new Font("serif",Font.BOLD,25));

        w1.setFont(new Font("courier",Font.BOLD,30));
        w2.setFont(new Font("courier",Font.BOLD,30));
        w3.setFont(new Font("courier",Font.BOLD,30));

        c1.setFont(new Font("courier",Font.BOLD,30));
        c2.setFont(new Font("courier",Font.BOLD,30));

        pause.setEnabled(false);
        reset.setEnabled(false);

        start.addActionListener(new ButtonListener());
        pause.addActionListener(new ButtonListener());
        reset.addActionListener(new ButtonListener());
    }

    class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();

            if (s.equals("START")) {
                start.setEnabled(false);
                pause.setEnabled(true);
                reset.setEnabled(false);

                p_display = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        mm = Integer.parseInt(w1.getText());
                        ss = Integer.parseInt(w2.getText());
                        ms = Integer.parseInt(w3.getText());


                        while (p_display == Thread.currentThread()) {

                            mm =  t % (1000*60) / 100 / 60 ;
                            ss = t % (1000*60) / 100 % 60 ;
                            ms = t %100;

                            try {
                                Thread.sleep(10);

                                w1.setText(String.format("%02d", mm));
                                w2.setText(String.format("%02d", ss));
                                w3.setText(String.format("%02d", ms));

                                t++;

                            }catch (InterruptedException e1) {}
                        }
                    }
                });


                p_display.start();

            }
            else if (s.equals("PAUSE")) {
                start.setEnabled(true);
                pause.setEnabled(false);
                reset.setEnabled(true);

                p_display = null;
            }else if(s.equals("RESET")) {
                start.setEnabled(true);
                pause.setEnabled(false);
                reset.setEnabled(false);

                w3.setText("00");
                w2.setText("00");
                w1.setText("00");

                t=0;
            }
        }
    }


    public static void main(String[] args) {
        new StopWatchEx();
    }
}