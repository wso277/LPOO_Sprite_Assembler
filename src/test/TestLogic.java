package test;

import static org.junit.Assert.*;

import java.awt.Point;

import junit.framework.Assert;
import logic.DraggableComponent;
import logic.Main;
import logic.SpriteAssembler;
import logic.SpriteElement;

import org.junit.Test;

/**
 * JUnit test class containing some basic test of image postion
 */
public class TestLogic { //test the loading and reposition of the images

	@Test
	public void imageOutOfWindow() { 

		DraggableComponent dc = new DraggableComponent();
		Main.spriteMinSize = 16;
		Main project = new Main("teste", 100, 100);
		
		dc.setDelta(new Point(0,0));
		dc.setDeltax(-1);
		dc.setDeltay(-1);
		dc.setValidPos(new Point(0,0));
		
		boolean fits = dc.checkIfFits(3, 4);
		
		assertEquals(false, fits);
		
	}

	@Test
	public void imageInsideWindow() { 

		DraggableComponent dc = new DraggableComponent();
		Main.spriteMinSize = 16;
		Main project = new Main("teste", 100, 100);
		
		dc.setDelta(new Point(0,0));
		dc.setDeltax(2);
		dc.setDeltay(2);
		dc.setValidPos(new Point(0,0));
		
		boolean fits = dc.checkIfFits(3, 4);
		
		assertEquals(true, fits);
		
	}

}
