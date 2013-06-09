package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import logic.Sprite;
import logic.SpriteElement;

/**
 * This class handles the panel in which the animations are drawn.
 */
public class Animate extends JPanel implements ActionListener {

	private static final int FPS_CONST = 1000;
	private static final long serialVersionUID = 1L;
	Sprite sp;
	SpriteElement se;
	Timer timer;
	int index; // Selected image index on the SpriteElement ArrayList

	/**
	 * Class contructor
	 * @param sp The sprite to be drawn
	 */
	public Animate(Sprite sp) {
		this.sp = sp;
		index = 0;
		se = this.sp.getImages().get(index);
		setDoubleBuffered(true);
		timer = new Timer((FPS_CONST/sp.getFps()), this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(se.getImage(), 0, 0, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	/**
	 * Handler called when the fps timer sends a signal
	 */
	public void actionPerformed(ActionEvent e) {

		//If the sprite is loopable, it resets the element index when it reaches the final image
		if (index == (sp.getImages().size() - 1) && sp.getCanLoop()) {
			index = 0;
		} else {
			index++;
		}

		//If it is a valid index, paints new image
		if (index <= sp.getImages().size() - 1) {
			se = sp.getImages().get(index);
		}
		repaint();
	}
}