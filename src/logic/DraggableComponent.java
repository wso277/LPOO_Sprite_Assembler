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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

public class DraggableComponent extends JComponent {

    private static final long serialVersionUID = 1L;
    /** If sets <b>TRUE</b> this component is draggable */
    private boolean draggable = true;

    /** Position of the image */
    private Point matrixPos;
    private Point pos;
    Point validPos;
    boolean fits;

    /** Default mouse cursor for dragging action */
    protected Cursor draggingCursor = Cursor
	    .getPredefinedCursor(Cursor.HAND_CURSOR);
    /**
     * If sets <b>TRUE</b> when dragging component, it will be painted over each
     * other (z-Buffer change)
     */
    protected boolean overbearing = false;
    protected boolean started = false;

    public DraggableComponent() {
	addDragListeners();
	setOpaque(true);
	setBackground(new Color(240, 240, 240));
    }

    /**
     * We have to define this method because a JComponent is a void box. So we
     * have to define how it will be painted. We create a simple filled
     * rectangle.
     * 
     * @param g
     *            Graphics object as canvas
     */
    @Override
    protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	if (isOpaque()) {
	    g.setColor(getBackground());
	    g.fillRect(0, 0, getWidth(), getHeight());
	}
    }

    boolean tmp = false;
    protected int xsquares;
    protected int ysquares;

    /**
     * Add Mouse Motion Listener with drag function
     */
    private void addDragListeners() {
	/**
	 * This handle is a reference to THIS because in next Mouse Adapter
	 * "this" is not allowed
	 */
	final DraggableComponent handle = this;
	addMouseMotionListener(new MouseAdapter() {

	    @Override
	    public void mouseMoved(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

	    }

	    @Override
	    public void mouseDragged(MouseEvent e) {

		Point parentOnScreen = getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		pos = new Point(mouseOnScreen.x - parentOnScreen.x,
			mouseOnScreen.y - parentOnScreen.y);
		int heightCells = (int) Math.ceil((double) getHeight()
			/ (double) Gui.spriteMinSize);
		int widthCells = (int) Math.ceil((double) getWidth()
			/ (double) Gui.spriteMinSize);
		fits = true;
		matrixPos = new Point(pos.x / Gui.spriteMinSize, pos.y
			/ Gui.spriteMinSize);

		if (pos.x < 0
			|| pos.y < 0
			|| matrixPos.x
				+ (int) Math.ceil((double) getWidth()
					/ (double) Gui.spriteMinSize) > Gui
				.getProject().getFilled().length
			|| matrixPos.y
				+ (int) Math.ceil((double) getHeight()
					/ (double) Gui.spriteMinSize) > Gui
				.getProject().getFilled()[0].length) {
		    fits = false;
		}

		if (fits) {
		    for (int i = 0; i < heightCells; i++) {
			for (int j = 0; j < widthCells; j++) {
			    if (Gui.getProject().getFilled()[matrixPos.x + i][matrixPos.y
				    + j] == 1) {
				fits = false;
				break;
			    }
			}
		    }
		}
		if (fits) {
		    validPos.x = matrixPos.x;
		    validPos.y = matrixPos.y;
		    setLocation(
			    (int) Math.floor((double) pos.x
				    / (double) Gui.spriteMinSize)
				    * Gui.spriteMinSize,
			    (int) Math.floor((double) pos.y
				    / (double) Gui.spriteMinSize)
				    * Gui.spriteMinSize);
		}

		// Change Z-Buffer if it is "overbearing"
		if (overbearing) {
		    getParent().setComponentZOrder(handle, 0);
		    repaint();
		}
	    }
	});

	addMouseListener(new MouseAdapter() {
	    @Override
	    public void mousePressed(MouseEvent e) {
		validPos = new Point();
		validPos.x = matrixPos.x;
		validPos.y = matrixPos.y;

		// Cleaning previous position
		for (int i = 0; i < getXsquares(); i++) {
		    for (int j = 0; j < getYsquares(); j++) {
			Gui.getProject().getFilled()[validPos.x + i][validPos.y
				+ j] = 0;
		    }
		}

		Point parentOnScreen = getParent().getLocationOnScreen();
		Point mouseOnScreen = e.getLocationOnScreen();
		pos = new Point(mouseOnScreen.x - parentOnScreen.x,
			mouseOnScreen.y - parentOnScreen.y);
		int heightCells = (int) Math.ceil((double) getHeight()
			/ (double) Gui.spriteMinSize);
		int widthCells = (int) Math.ceil((double) getWidth()
			/ (double) Gui.spriteMinSize);
		fits = true;
		matrixPos = new Point(pos.x / Gui.spriteMinSize, pos.y
			/ Gui.spriteMinSize);

		if (pos.x < 0
			|| pos.y < 0
			|| matrixPos.x
				+ (int) Math.ceil((double) getWidth()
					/ (double) Gui.spriteMinSize) > Gui
				.getProject().getFilled().length
			|| matrixPos.y
				+ (int) Math.ceil((double) getHeight()
					/ (double) Gui.spriteMinSize) > Gui
				.getProject().getFilled()[0].length) {
		    fits = false;
		}
		if (fits) {
		    for (int i = 0; i < heightCells; i++) {
			for (int j = 0; j < widthCells; j++) {
			    if (Gui.getProject().getFilled()[matrixPos.x + i][matrixPos.y
				    + j] == 1) {
				fits = false;
				break;
			    }
			}
		    }
		}
		if (fits) {
		    setLocation(
			    (int) Math.floor((double) pos.x
				    / (double) Gui.spriteMinSize)
				    * Gui.spriteMinSize,
			    (int) Math.floor((double) pos.y
				    / (double) Gui.spriteMinSize)
				    * Gui.spriteMinSize);
		}

	    }

	    public void mouseReleased(MouseEvent e) {
		if (!fits) {
		    matrixPos.x = validPos.x;
		    matrixPos.y = validPos.y;
		    setLocation(validPos.x * Gui.spriteMinSize, validPos.y
			    * Gui.spriteMinSize);
		}
		for (int i = 0; i < getXsquares(); i++) {
		    for (int j = 0; j < getYsquares(); j++) {
			Gui.getProject().getFilled()[matrixPos.x + i][matrixPos.y
				+ j] = 1;
		    }
		}
	    }
	});
    }

    /**
     * Remove all Mouse Motion Listener. Freeze component.
     */
    private void removeDragListeners() {
	for (MouseMotionListener listener : this.getMouseMotionListeners()) {
	    removeMouseMotionListener(listener);
	}
	setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Get the value of draggable
     * 
     * @return the value of draggable
     */
    public boolean isDraggable() {
	return draggable;
    }

    /**
     * Set the value of draggable
     * 
     * @param draggable
     *            new value of draggable
     */
    public void setDraggable(boolean draggable) {
	this.draggable = draggable;
	if (draggable) {
	    addDragListeners();
	} else {
	    removeDragListeners();
	}

    }

    /**
     * Get the value of draggingCursor
     * 
     * @return the value of draggingCursor
     */
    public Cursor getDraggingCursor() {
	return draggingCursor;
    }

    /**
     * Set the value of draggingCursor
     * 
     * @param draggingCursor
     *            new value of draggingCursor
     */
    public void setDraggingCursor(Cursor draggingCursor) {
	this.draggingCursor = draggingCursor;
    }

    /**
     * Get the value of overbearing
     * 
     * @return the value of overbearing
     */
    public boolean isOverbearing() {
	return overbearing;
    }

    /**
     * Set the value of overbearing
     * 
     * @param overbearing
     *            new value of overbearing
     */
    public void setOverbearing(boolean overbearing) {
	this.overbearing = overbearing;
    }

    public Point getMatrixPos() {
	return matrixPos;
    }

    public void setMatrixPos(int x, int y) {
	this.matrixPos = new Point(x, y);
    }

    public int getXsquares() {
	return xsquares;
    }

    public void setXsquares(int xsquares) {
	this.xsquares = xsquares;
    }

    public int getYsquares() {
	return ysquares;
    }

    public void setYsquares(int ysquares) {
	this.ysquares = ysquares;
    }
}
