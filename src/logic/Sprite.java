package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sprite {

	private ArrayList<SpriteElement> images;
	private ArrayList<Integer> breaks;


	public Sprite(File[] files) throws IOException {
		
		images = new ArrayList<SpriteElement>();
		breaks = new ArrayList<Integer>();
		
		System.out.println(files.length);
		for (int i=0; i<files.length; i++) {
			
			System.out.println(files[i].getAbsolutePath());
			images.add(new SpriteElement(files[i]));
		}
	}
}
