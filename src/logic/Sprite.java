package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a sprite
 */
public class Sprite {

	private ArrayList<SpriteElement> images;
	private String name;
	Boolean canLoop = true;
	private int fps;

	/**
	 * Sprite constructor
	 * 
	 * @param files
	 *            Imported image files
	 * @param name
	 *            Sprite name
	 * @param canLoop
	 *            Defines if the sprite is a loop
	 * @param fps
	 *            Defines the frames per second
	 * @throws IOException
	 */
	public Sprite(File[] files, String name, boolean canLoop, int fps)
			throws IOException {

		images = new ArrayList<SpriteElement>();

		for (int i = 0; i < files.length; i++) {

			images.add(new SpriteElement(files[i]));
		}
		this.canLoop = canLoop;
		this.name = name;
		this.setFps(fps);
	}

	public ArrayList<SpriteElement> getImages() {
		return images;
	}

	public void setImages(ArrayList<SpriteElement> images) {
		this.images = images;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getCanLoop() {
		return canLoop;
	}

	public void setCanLoop(boolean canLoop) {
		this.canLoop = canLoop;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

}
