package logic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Animate extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	Sprite sp;
    SpriteElement se;
    Timer timer;
    int x, y, index;

    public Animate(Sprite sp) {
        setBackground(Color.BLACK);

        this.sp = sp;
        index =0;
        se = this.sp.getImages().get(index);

        setDoubleBuffered(true);

        x = y = 10;
        timer = new Timer((sp.getFps()*1000/4), this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(se.getImage(), x, y, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) {
    	
    	if (index == sp.getImages().size() -1) {
    		index = 0;
    	}
    	else {
    		index++; 
    	}
    	
        se = sp.getImages().get(index);
        repaint();  
    }
}