package logic;

/*
 *  Copyright 2010 De Gregorio Daniele.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

import gui.Gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is an extension of Draggable Component with a custom Image for
 * Background setting it by <b>setImage</b> method. You can use it as simple
 * Image Panel with <b>setDraggable(false)</b>. It implements ImageObserver for
 * Image loading problems. it repaints itself after image is full loaded.
 * 
 */
public class SpriteElement extends DraggableComponent implements ImageObserver {
	private static final long serialVersionUID = 1L;
	protected Image image;
	private File file;

	public SpriteElement(File file) {
		super();
		/*
		 * setLayout(null); setBackground(Color.black);
		 */
		this.file = file;
		// setImage(this.file.getAbsolutePath());
		try {
			this.image = ImageIO.read(this.file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		xsquares = (int) Math.ceil((double) this.image.getWidth(this)
				/ (double) Gui.spriteMinSize);
		ysquares = (int) Math.ceil((double) this.image.getHeight(this)
				/ (double) Gui.spriteMinSize);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * This overrided method paints image on Component if any. Else it paints a
	 * Background color. If <b>autoSize</b> is TRUE , it paints image with
	 * original ration, on a Background Color box if opaque.
	 * 
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.clearRect(0, 0, getWidth(), getHeight());
		if (fits) {
			g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		} else {
			g2d.setColor(Color.RED);
			g2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	/**
	 * Checks if image is full loaded.
	 * 
	 * @param img
	 *            target image
	 * @param infoflags
	 *            is equal to <b>>ALLBITS</b> when loading is finished
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @return TRUE if image can generates events, FALSE otherwise
	 */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
			int h) {
		if (infoflags == ALLBITS) {
			repaint();
			return false;
		}
		return true;
	}

	/**
	 * Get the value of image
	 * 
	 * @return the value of image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Set the value of image
	 * 
	 * @param image
	 *            new value of image
	 */
	public void setImage(Image image) {
		this.image = image;
		repaint();
	}
}
