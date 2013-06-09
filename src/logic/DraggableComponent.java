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
import gui.PreviewAnimation;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;

/**
 * Class that handles sprite dragging and its implications. Based off the code
 * by De Gregorio Daniele (license above)
 */
public class DraggableComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	/** If sets <b>TRUE</b> this component is draggable */
	private boolean draggable = true;

	/** Position of the image */
	private Point matrixPos;
	private Point mouse1;
	private Point mouse2;
	private Point delta;
	Point validPos;

	protected int xsquares;
	protected int ysquares;
	boolean fits = true;
	private int deltax;
	private int deltay;

	/** Default mouse cursor for dragging action */
	protected Cursor draggingCursor = Cursor
			.getPredefinedCursor(Cursor.HAND_CURSOR);
	/**
	 * If sets <b>TRUE</b> when dragging component, it will be painted over each
	 * other (z-Buffer change)
	 */
	protected boolean overbearing = true;
	protected boolean started = false;

	public DraggableComponent() {
		addDragListeners();
		setOpaque(true);
		setBackground(new Color(240, 240, 240));
		mouse1 = new Point(0, 0);
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

			// If the mouse is over a sprite it changes the mouse icon to the
			// HAND_CURSOR do indicate its "grabbable"
			@Override
			public void mouseMoved(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseDragged(MouseEvent e) {

				// Gets the window position on screen
				Point parentOnScreen = getParent().getLocationOnScreen();
				// Gets the mouse position on screen
				Point mouseOnScreen = e.getLocationOnScreen();
				// Gets the relative mouse position
				mouse2 = new Point(mouseOnScreen.x - parentOnScreen.x,
						mouseOnScreen.y - parentOnScreen.y);

				// Gets the image position in the fillable matrix scale
				int heightCells = (int) Math.ceil((double) getHeight()
						/ (double) Gui.spriteMinSize);
				int widthCells = (int) Math.ceil((double) getWidth()
						/ (double) Gui.spriteMinSize);

				// Gets the mouse change
				deltax = (mouse2.x - mouse1.x) / Gui.spriteMinSize;
				deltay = (mouse2.y - mouse1.y) / Gui.spriteMinSize;

				fits = checkIfFits(heightCells, widthCells);
				if (fits) {
					validPos.x = deltax + delta.x;
					validPos.y = deltay + delta.y;
				}
				setLocation((deltax + delta.x) * Gui.spriteMinSize,
						(deltay + delta.y) * Gui.spriteMinSize);

				// Change Z-Buffer if it is "overbearing"
				if (overbearing) {
					getParent().setComponentZOrder(handle, 0);
					repaint();
				}

			}

		});

		addMouseListener(new MouseAdapter() {
			@Override
			// Sets what happens when a sprite element is pressed on
			public void mousePressed(MouseEvent e) {

				// Cleaning previous position
				for (int i = 0; i < getXsquares(); i++) {
					for (int j = 0; j < getYsquares(); j++) {
						Gui.getProject().getFilled()[validPos.x + i][validPos.y
								+ j] = 0;
					}
				}

				// Gets mouse relative position and sets anchor point
				Point parentOnScreen = getParent().getLocationOnScreen();
				Point mouseOnScreen = e.getLocationOnScreen();
				mouse1 = new Point(mouseOnScreen.x - parentOnScreen.x,
						mouseOnScreen.y - parentOnScreen.y);

				delta = new Point();
				delta.x = validPos.x;
				delta.y = validPos.y;

			}

			// Sets what happens when a sprite element is released
			public void mouseReleased(MouseEvent e) {

				// If it doesn't fit it is sent to the last valid position
				if (!fits) {
					fits = true;
					setLocation(validPos.x * Gui.spriteMinSize, validPos.y
							* Gui.spriteMinSize);
				}

				for (int i = 0; i < getXsquares(); i++) {
					for (int j = 0; j < getYsquares(); j++) {
						Gui.getProject().getFilled()[validPos.x + i][validPos.y
								+ j] = 1;
					}
				}

				delta.x = deltax;
				delta.y = deltay;
			}

			// Sets what happens when a sprite element is clicked on
			public void mouseClicked(MouseEvent e) {
				// If it is a right click
				if (e.getButton() == MouseEvent.BUTTON3) {

					// Finds the sprite element index
					int i = findSprite();

					// Sets of a preview
					PreviewAnimation preview = new PreviewAnimation(Gui
							.getProject().getSprites().get(i));
					preview.setVisible(true);
				}

				// If it is a left click
				if (e.getButton() == MouseEvent.BUTTON1) {
					// Makes editing possible
					Gui.getMnEdit().setEnabled(true);
					// Finds out the sprite index
					Gui.setSpIndex(findSprite());
				}
			}
		});
	}

	/**
	 * Finds the sprite being selected
	 * 
	 * @return Its index
	 */
	public int findSprite() {

		int i;
		boolean found = false;

		for (i = 0; i < Gui.getProject().getSprites().size(); i++) {
			for (int j = 0; j < Gui.getProject().getSprites().get(i)
					.getImages().size(); j++) {
				if (Gui.getProject().getSprites().get(i).getImages().get(j) == this) {
					found = true;
					break;
				}
			}
			if (found) {
				break;
			}
		}

		return i;
	}

	/**
	 * Checks if an image fits the position it's currently hovering
	 * 
	 * @param heightCells
	 *            The image height in matrix cells
	 * @param widthCells
	 *            The image width in matrix cells
	 * @return True if it fits. False if it doesn't.
	 */
	private boolean checkIfFits(int heightCells, int widthCells) {
		boolean res = true;
		for (int i = 0; i < widthCells; i++) {
			for (int j = 0; j < heightCells; j++) {
				if (Gui.getProject().getFilled().length <= (deltax + delta.x + i)
						|| (deltax + delta.x + i) < 0
						|| Gui.getProject().getFilled()[validPos.x].length <= (deltay
								+ delta.y + j)
						|| (deltay + delta.y + j) < 0
						||

						Gui.getProject().getFilled()[deltax + delta.x + i][deltay
								+ delta.y + j] == 1) {
					res = false;
					break;
				}
			}
		}
		return res;
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

		validPos = new Point();
		validPos.x = matrixPos.x;
		validPos.y = matrixPos.y;
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

	public Point getValidPos() {
		return validPos;
	}

	public void setValidPos(Point validPos) {
		this.validPos = validPos;
	}
}
