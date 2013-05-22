package logic;

import java.util.ArrayList;

public class SpriteAssembler {

	private ArrayList<Sprite> sprites;
	private String name;
	
	public SpriteAssembler(String name) {
		this.name = name;
		sprites = new ArrayList<Sprite>();
	}
	
	public void addSprite(Sprite s1) {
		sprites.add(s1);
	}
}
