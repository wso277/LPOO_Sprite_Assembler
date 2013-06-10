package logic;

import gui.Gui;

import java.util.ArrayList;

public class SpriteAssembler {

	private ArrayList<Sprite> sprites;
	private String name;
	private Integer[][] filled;
	
	public Integer[][] getFilled() {
		return filled;
	}

	public void setFilled(int w, int h, int mode) {
		
		filled[w][h] = mode;
	}
	
	public SpriteAssembler(String name, int width, int height) {
		this.setName(name);
		sprites = new ArrayList<Sprite>();
		
		
		height = height / Main.spriteMinSize;
		width = width / Main.spriteMinSize;
		
		filled = new Integer[width][height];
		
		for (int i=0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				filled[i][j] = 0;
			}
		}
	}
	
	public void addSprite(Sprite s1) {
		sprites.add(s1);
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public ArrayList<Sprite> getSprites() {
	    return sprites;
	}

	public void setSprites(ArrayList<Sprite> sprites) {
	    this.sprites = sprites;
	}
}
