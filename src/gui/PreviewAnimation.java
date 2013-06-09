package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import logic.Sprite;

/**
 * Represents the window that shows the sprite animation preview
 */
public class PreviewAnimation extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int maxHeight, maxWidth;

	/**
	 * Create the frame.
	 */
	public PreviewAnimation(Sprite sp) {
		
		defineWindowSize(sp);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(Gui.getPanel().getX(), Gui.getPanel().getY(), maxWidth, maxHeight + 25);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(new Animate(sp));
	}

	/**
	 * Finds the necessary window size to accommodate the largest image in the sprite
	 * @param sp
	 */
	private void defineWindowSize(Sprite sp) {
		maxHeight = 0;
		maxWidth = 0;
		for (int i = 0; i < sp.getImages().size(); i++) {
			if (sp.getImages().get(i).getHeight() > maxHeight)
				maxHeight = sp.getImages().get(i).getHeight();
			if (sp.getImages().get(i).getWidth() > maxWidth)
				maxWidth = sp.getImages().get(i).getWidth();
		}
	}

}
