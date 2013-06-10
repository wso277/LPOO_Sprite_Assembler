package logic;

import gui.Gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Represents a sprite element. Inherits the DraggableComponent class'
 * abilities. This is an extension of Draggable Component with a custom Image
 * for Background setting it by <b>setImage</b> method. It implements
 * ImageObserver for Image loading problems. it repaints itself after image is
 * full loaded. *
 */
public class SpriteElement extends DraggableComponent implements ImageObserver {
	private static final long serialVersionUID = 1L;
	protected Image image;
	private File file;

	public SpriteElement(File file) {
		super();
		this.file = file;
		try {
			this.image = ImageIO.read(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		xsquares = (int) Math.ceil((double) this.image.getWidth(this)
				/ (double) Main.spriteMinSize);
		ysquares = (int) Math.ceil((double) this.image.getHeight(this)
				/ (double) Main.spriteMinSize);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		// If it fits the image is drawn
		if (fits) {
			g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		}
		// If it doesn't fit the image is filled with RED colour
		else {
			g2d.setColor(Color.RED);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/**
	 * Get the value of image
	 * 
	 * @return the value of image
	 */
	public Image getImage() {
		return image;
	}
}
