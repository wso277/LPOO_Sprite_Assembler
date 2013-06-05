package logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sprite {

	private ArrayList<SpriteElementBackup> images;
	private ArrayList<Integer> breaks;
	private String name;


	public Sprite(File[] files, String name) throws IOException {
		
		images = new ArrayList<SpriteElementBackup>();
		breaks = new ArrayList<Integer>();
		
		System.out.println(files.length);
		for (int i=0; i<files.length; i++) {
			
			images.add(new SpriteElementBackup(files[i]));
		}
		
		this.name = name;
	}


	public ArrayList<SpriteElementBackup> getImages() {
		return images;
	}


	public void setImages(ArrayList<SpriteElementBackup> images) {
		this.images = images;
	}


	public ArrayList<Integer> getBreaks() {
		return breaks;
	}


	public void setBreaks(ArrayList<Integer> breaks) {
		this.breaks = breaks;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
