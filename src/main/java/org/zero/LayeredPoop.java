
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class LayeredPoop extends JFrame {
     
    private JLayeredPane content;
    private JLabel[] label;
     
    public static void main( String[] arg ) {
        new LayeredPoop();
    }
     
    public LayeredPoop() {
        super( "LayeredPoop" );
        content = new JLayeredPane();
        content.setOpaque( true );
        label = new  JLabel[ 10 ];
        JLabel backgroundLabel = new JLabel(new ImageIcon("static/img/결과화면.png"));
        backgroundLabel.setBounds(0,0,750,500);
        content.add(backgroundLabel);
        setContentPane( content );
        for ( int i = 0; i < label.length; i++ ) {
            label[ i ] = new JLabel( new ImageIcon("static/img/beforeBtn.png") );
            label[ i ].setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED ) );
            int w = label[ i ].getPreferredSize().width;
            int h = label[ i ].getPreferredSize().height;
            int x = 10 * i;
            int y = x;
            label[ i ].setBounds( x, y, w, h );
            label[ i ].addMouseMotionListener( new LabelDragger() );
            label[ i ].setOpaque( true );
            content.setLayer( label[ i ], i );
            getContentPane().add( label[ i ] );
        }
        setSize( 400, 400 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        show();
    }
     
    private class LabelDragger implements MouseMotionListener {
        public void mouseDragged( MouseEvent e ) {
            JLabel l = (JLabel)e.getSource();
            Point p = e.getPoint();
            SwingUtilities.convertPointToScreen( p, l );
            l.setLocation( p );
            SwingUtilities.invokeLater( new RefreshThread() );           
        }
         
        public void mouseMoved( MouseEvent e ) {}
    }
     
    private class RefreshThread extends Thread {
        public void run() {
            content.revalidate();
            content.repaint(); 
        }
    }
}