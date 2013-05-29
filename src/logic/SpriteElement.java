package logic;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SpriteElement {
	
	private Coordinate pos;
	public File file;
	private ImageIcon image = null;

	public SpriteElement(File file) throws IOException {
		this.file = file;
		System.out.println(this.file.getAbsolutePath());
		image = new ImageIcon(this.file.getAbsolutePath());
		
	}

	public Coordinate getPos() {
		return pos;
	}

	public void setPos(Coordinate pos) {
		this.pos = pos;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	

}
