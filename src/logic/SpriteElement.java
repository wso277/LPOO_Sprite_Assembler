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
	private BufferedImage image = null;

	public SpriteElement(File file) throws IOException {
		this.file = file;
		image = ImageIO.read(this.file);
		
	}

}
