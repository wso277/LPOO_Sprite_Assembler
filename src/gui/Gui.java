package gui;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Exporter;
import logic.Sprite;
import logic.SpriteAssembler;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class Gui {
	int x = 0, y = 0, highestHeightInRow = 0;
	static JFrame frame;
	final JFileChooser fc = new JFileChooser();
	static String projectName;
	static private SpriteAssembler project;

	private static JPanel panel;
	static public boolean InitAccept = false;
	static public String spriteName;
	static public boolean CreateAccept = false;
	static public int spriteMinSize;
	static public int panelHeight;
	static public int panelWidth;
	protected static Boolean spriteIsLoopable;
	protected static int fps;

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
	 * Initialise the contents of the frame.
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
		
		final JMenu mnEdit = new JMenu("Edit");

		final JMenuItem mntmNew = new JMenuItem("New Sprite");
		mntmNew.setEnabled(false);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSpriteToProject(mntmNew);
				if (CreateAccept) {
					mnEdit.setEnabled(true);
				}
			}
		});
		
		
		JMenuItem mntmNewProject = new JMenuItem("New Project");
		mntmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitProject newProject = new InitProject();

				newProject.setModalityType(ModalityType.APPLICATION_MODAL);
				newProject.setVisible(true);

				if (InitAccept) {

					mntmNew.setEnabled(true);

					project = new SpriteAssembler(projectName, panelWidth,
							panelHeight);
					panel.removeAll();
					panel.revalidate();
					addSpriteToProject(mntmNew);
					if (CreateAccept) {
						mnEdit.setEnabled(true);
					}
					CreateAccept = false;
					InitAccept = false;
				}
			}
		});
		mnFile.add(mntmNewProject);
		mnFile.add(mntmNew);

		JMenuItem mntmExport = new JMenuItem("Import Project");
		mnFile.add(mntmExport);

		JMenuItem mntmImport = new JMenuItem("Export Project");
		mntmImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exporter.export();
			}
		});
		mnFile.add(mntmImport);

		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		mnFile.add(mntmExit);

		mnEdit.setEnabled(false);
		menuBar.add(mnEdit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.getContentPane().add(getPanel());
		getPanel().setLayout(null);

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
				"jpg", "gif", "png", "jpeg", "BMP");

		fc.setFileFilter(filter);

	}

	/**
	 * @param mntmNew
	 */
	private void addSpriteToProject(final JMenuItem mntmNew) {
		CreateSprite newSprite = new CreateSprite();
		System.out.println("TESTE1");
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
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				project.addSprite(animation);
				boolean insert = true;
				int k = 0;
				int z = 0;
				for (int i = 0; i < animation.getImages().size(); i++) {
					animation
							.getImages()
							.get(i)
							.setSize(
									animation.getImages().get(i).getImage()
											.getWidth(null),
									animation.getImages().get(i).getImage()
											.getHeight(null));


					for (k = 0; k < project.getFilled().length; k++) {
						for (z = 0; z < project.getFilled()[k].length ; z++) {
							for (int l = 0; l < animation.getImages().get(i)
									.getXsquares(); l++) {
								for (int m = 0; m < animation.getImages()
										.get(i).getYsquares(); m++) {

									if (Gui.getProject().getFilled().length <= (k + l)
											|| (k + l) < 0
											|| Gui.getProject().getFilled()[k].length <= (z + m)
											|| (z + m) < 0
											|| Gui.getProject().getFilled()[k
													+ l][z + m] == 1) {
										insert = false;
										break;
									}
								}
							}
							if (insert) {
								break;
							}
							else if (z != project.getFilled()[k].length-1){
								insert = true;
							}
						}
						if (insert) {
							break;
						}
						else if (z != project.getFilled().length-1){
							insert = true;
						}
					}

					if (insert) {

						animation
								.getImages()
								.get(i)
								.setLocation(k * spriteMinSize,
										z * spriteMinSize);
					}

					animation.getImages().get(i).setMatrixPos(k, z);
					System.out.println(k);
					System.out.println(z);
					for (int wsquare = 0; wsquare < animation.getImages()
							.get(i).getXsquares(); wsquare++) {
						for (int hsquare = 0; hsquare < animation.getImages()
								.get(i).getYsquares(); hsquare++) {
							project.setFilled(k + wsquare , z + hsquare , 1);
						}
					}

					getPanel().add(animation.getImages().get(i));
					getPanel().revalidate();
				}
				getPanel().repaint();
			}
		}
	}

	public static JPanel getPanel() {
		return panel;
	}

	public static void setPanel(JPanel panel) {
		Gui.panel = panel;
	}

	static public SpriteAssembler getProject() {
		return project;
	}

}
