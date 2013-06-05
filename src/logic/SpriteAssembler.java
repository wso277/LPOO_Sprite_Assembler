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

	public void setFilled(int h, int w, int mode) {
		
		filled[h][w] = mode;
	}

	private int panelHeight;
	private int panelWidth;
	
	public SpriteAssembler(String name, int height, int width) {
		this.name = name;
		sprites = new ArrayList<Sprite>();
		
		
		panelHeight = height;
		panelWidth = width;
		
		filled = new Integer[panelHeight][panelWidth];
		
		for (int i=0; i < panelHeight; i++) {
			for (int j = 0; j < panelWidth; j++) {
				
				filled[i][j] = 0;
			}
		}
	}
	
	public void addSprite(Sprite s1) {
		sprites.add(s1);
	}
}
