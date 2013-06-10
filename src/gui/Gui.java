package gui;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Exporter;
import logic.Main;
import logic.Sprite;
import logic.SpriteElement;

import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Main program class. Handles all other classes and most interactions. Stores
 * most variables.
 */
public class Gui {
	int highestHeightInRow = 0;
	static JFrame frame;
	final JFileChooser fc = new JFileChooser();
	static String projectName;
	private static JPanel panel;
	private static JMenu mnEdit;
	private JMenuItem mntmExport;
	static public boolean InitAccept = false;
	static public String spriteName;
	static public boolean CreateAccept = false;
	static public int panelHeight;
	static public int panelWidth;
	int k = 0;
	int z = 0;
	protected static Boolean spriteIsLoopable;
	protected static int fps;
	private static int spIndex = 0;
	private boolean fits = true;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Gui window = new Gui();
					Gui.frame.setResizable(false);
					Gui.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		setPanel(new JPanel());

		mnEdit = new JMenu("Edit Sprite");

		// Sets action for "New Sprite" menu item.
		final JMenuItem mntmNewSprite = new JMenuItem("New Sprite");
		mntmNewSprite.setEnabled(false);
		mntmNewSprite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSpriteToProject(mntmNewSprite);
			}
		});

		// Sets action for "New Project" menu item.
		JMenuItem mntmNewProject = new JMenuItem("New Project");
		mntmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Creating new Project. Are you Sure?", "New project",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					InitProject newProject = new InitProject();

					newProject.setModalityType(ModalityType.APPLICATION_MODAL);
					newProject.setVisible(true);

					// If ok button is pressed
					if (InitAccept) {
						// Now allows a new sprite to be added
						mntmNewSprite.setEnabled(true);

						new Main(projectName, panelWidth,
								panelHeight);
						panel.removeAll();
						panel.revalidate();
						// Adds first sprite
						addSpriteToProject(mntmNewSprite);
						CreateAccept = false;
						InitAccept = false;
					}
				}
			}
		});
		mnFile.add(mntmNewProject);
		mnFile.add(mntmNewSprite);

		JMenuItem mntmImport = new JMenuItem("Import Project");
		mnFile.add(mntmImport);

		// Sets action for the export menu item
		mntmExport = new JMenuItem("Export Project");
		mntmExport.setEnabled(false);
		mntmExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Exporter.export();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mntmExport);

		// Action for the close project menu item
		JMenuItem mntmCloseProject = new JMenuItem("Close Project");
		mntmCloseProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Closing Project. Are you Sure?", "Close project",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					panel.removeAll();
					panel.revalidate();
					panel.repaint();
					mntmNewSprite.setEnabled(false);
					mnEdit.setEnabled(false);
					mntmExport.setEnabled(false);
				}
			}
		});
		mnFile.add(mntmCloseProject);

		// Sets action for the exit menu item
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Exiting Program. Are you Sure?", "Exiting",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		mnFile.add(mntmExit);

		mnEdit.setEnabled(false);
		menuBar.add(mnEdit);

		// Sets action for the sprite properties menu item.
		JMenuItem mntmProperties = new JMenuItem("Sprite Properties");
		mntmProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EditSprite edit = new EditSprite();
				edit.setModalityType(ModalityType.APPLICATION_MODAL);
				edit.setVisible(true);
			}
		});
		mnEdit.add(mntmProperties);

		// Sets action for the delete sprite menu item.
		JMenuItem mntmDelete = new JMenuItem("Delete Sprite");
		mntmDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (JOptionPane.showConfirmDialog(null,
						"Delete Sprite. Are you Sure?", "Delete Sprite",
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					deleteSprite();

					panel.revalidate();
					panel.repaint();
				}
			}

		});
		mnEdit.add(mntmDelete);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog about = new AboutDialog();
				about.setModalityType(ModalityType.APPLICATION_MODAL);
				about.setVisible(true);
			}
		});
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.getContentPane().add(getPanel());
		getPanel().setLayout(null);

		// File chooser settings
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
				"jpg", "gif", "png", "jpeg", "BMP");

		fc.setFileFilter(filter);

	}

	/**
	 * Adds a sprite to the project adding each image to the panel and filling
	 * the matrix that indicates which positions are free
	 * 
	 * @param mntmNew
	 *            New Sprite menu item button
	 */
	private void addSpriteToProject(final JMenuItem mntmNew) {
		CreateSprite newSprite = new CreateSprite();
		newSprite.setModalityType(ModalityType.APPLICATION_MODAL);
		newSprite.setVisible(true);

		if (CreateAccept) {
			int returnVal = fc.showOpenDialog(mntmNew);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File[] files = fc.getSelectedFiles();

				Sprite animation = null;
				try {
					animation = new Sprite(files.clone(), spriteName,
							spriteIsLoopable, fps);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				// Adds sprite to project
				Main.getProject().addSprite(animation);

				// Adds sprite to panel
				for (int i = 0; i < animation.getImages().size(); i++) {
					animation
							.getImages()
							.get(i)
							.setSize(
									animation.getImages().get(i).getImage()
											.getWidth(null),
									animation.getImages().get(i).getImage()
											.getHeight(null));

					fits = checkIfFits(animation.getImages().get(i));

					if (fits) {
						animation
								.getImages()
								.get(i)
								.setLocation(k * Main.spriteMinSize,
										z * Main.spriteMinSize);

						animation.getImages().get(i).setMatrixPos(k, z);
						fillMatrix(animation, i);

						getPanel().add(animation.getImages().get(i));
					} else {
						spIndex = Main.getProject().getSprites().size() - 1;
						JOptionPane.showMessageDialog(null,
								"Too many images for the Workspace", "Alert",
								JOptionPane.ERROR_MESSAGE);
						deleteSprite();
						break;

					}
					getPanel().revalidate();
				}
				getPanel().repaint();
				mntmExport.setEnabled(true);
			}
		}
	}

	/**
	 * Fills the matrix in the specified image's position
	 * 
	 * @param animation
	 *            Selected Sprite
	 * @param i
	 *            Image index in the SpriteElements ArrayList
	 */
	private void fillMatrix(Sprite animation, int i) {
		for (int wsquare = 0; wsquare < animation.getImages().get(i)
				.getXsquares(); wsquare++) {
			for (int hsquare = 0; hsquare < animation.getImages().get(i)
					.getYsquares(); hsquare++) {
				Main.getProject().setFilled(k + wsquare, z + hsquare, 1);
			}
		}
	}

	/**
	 * Checks if an image fits in the project by checking all possible
	 * positions. If it fits it stores the position in the matrix in the k and z
	 * global variables and sets fits as true.
	 * 
	 * @param image
	 *            The image to be tested
	 * @return True if it fits. False if it doesn't.
	 */
	private boolean checkIfFits(SpriteElement image) {
		boolean res = true;
		for (k = 0; k < Main.getProject().getFilled().length; k++) {
			for (z = 0; z < Main.getProject().getFilled()[k].length; z++) {
				for (int l = 0; l < image.getXsquares(); l++) {
					for (int m = 0; m < image.getYsquares(); m++) {

						if (Main.getProject().getFilled().length <= (k + l)
								|| (k + l) < 0
								|| Main.getProject().getFilled()[k].length <= (z + m)
								|| (z + m) < 0
								|| Main.getProject().getFilled()[k + l][z + m] == 1) {
							res = false;
							break;
						}
					}
				}
				if (res) {
					break;
				} else if (z != Main.getProject().getFilled()[k].length - 1) {
					res = true;
				}
			}
			if (res) {
				break;
			} else if (k != Main.getProject().getFilled().length - 1) {
				res = true;
			}
		}
		return res;
	}

	/**
	 * Removes a sprite identified by spIndex from the project and clears its
	 * position in the filled matrix.
	 */
	private void deleteSprite() {
		
		try {
		for (int i = 0; i < Main.getProject().getSprites().get(spIndex).getImages()
				.size(); i++) {

			for (int j = 0; j < Main.getProject().getSprites().get(spIndex).getImages()
					.get(i).getXsquares(); j++) {
				for (int k = 0; k < Main.getProject().getSprites().get(spIndex)
						.getImages().get(i).getYsquares(); k++) {
					Main.getProject().getFilled()[Main.getProject().getSprites().get(spIndex)
							.getImages().get(i).getValidPos().x
							+ j][Main.getProject().getSprites().get(spIndex).getImages()
							.get(i).getValidPos().y
							+ k] = 0;
				}
			}

			panel.remove(Main.getProject().getSprites().get(spIndex).getImages().get(i));
			mnEdit.setEnabled(false);
		}

		Main.getProject().getSprites().remove(spIndex);
		if (Main.getProject().getSprites().size() == 0) {
			mntmExport.setEnabled(false);
		}
		}
		catch (NullPointerException e){
			Main.getProject().getSprites().remove(spIndex);
			if (Main.getProject().getSprites().size() == 0) {
				mntmExport.setEnabled(false);
			}
		}
	}

	public static JPanel getPanel() {
		return panel;
	}

	public static void setPanel(JPanel panel) {
		Gui.panel = panel;
	}

	public static JMenu getMnEdit() {
		return mnEdit;
	}

	public static void setMnEdit(JMenu mnEdit) {
		Gui.mnEdit = mnEdit;
	}

	public static int getSpIndex() {
		return spIndex;
	}

	public static void setSpIndex(int spIndex) {
		Gui.spIndex = spIndex;
	}
}
