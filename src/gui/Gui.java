package gui;

import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Sprite;
import logic.SpriteAssembler;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

public class Gui {
	static Boolean fixedSize = false;
	int x = 0, y = 0, highestHeightInRow = 0;
	static JFrame frame;
	final JFileChooser fc = new JFileChooser();
	public SpriteAssembler project;
	static JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Gui window = new Gui();
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

		panel = new JPanel();

		final JMenuItem mntmNew = new JMenuItem("New Sprite");
		mntmNew.setEnabled(false);
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addSpriteToProject(mntmNew);
			}
		});

		JMenuItem mntmNewProject = new JMenuItem("New Project");
		mntmNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InitProject newProject = new InitProject();

				newProject.setModalityType(ModalityType.APPLICATION_MODAL);
				newProject.setVisible(true);

				if (InitProject.accept) {

					mntmNew.setEnabled(true);

					project = new SpriteAssembler(InitProject.projectName);
					addSpriteToProject(mntmNew);
					CreateSprite.accept = false;
					InitProject.accept = false;
				}
			}
		});
		mnFile.add(mntmNewProject);
		mnFile.add(mntmNew);

		JMenuItem mntmExport = new JMenuItem("Import Project");
		mnFile.add(mntmExport);

		JMenuItem mntmImport = new JMenuItem("Export Project");
		mnFile.add(mntmImport);

		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setEnabled(false);
		menuBar.add(mnEdit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0 };
		gbl_panel.rowHeights = new int[] { 0 };
		gbl_panel.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { Double.MIN_VALUE };

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

		newSprite.setModalityType(ModalityType.APPLICATION_MODAL);
		newSprite.setVisible(true);

		if (CreateSprite.accept) {
			int returnVal = fc.showOpenDialog(mntmNew);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File[] files = fc.getSelectedFiles();

				Sprite animation = null;
				try {
					animation = new Sprite(files.clone(),
							CreateSprite.spriteName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				project.addSprite(animation);
				for (int i = 0; i < animation.getImages().size(); i++) {
					JLabel image = new JLabel(animation.getImages().get(i)
							.getImage());
					image.setSize(animation.getImages().get(i).getImage()
							.getIconHeight(), animation.getImages().get(i)
							.getImage().getIconWidth());
					if (animation.getImages().get(i).getImage().getIconHeight() > highestHeightInRow) {
						highestHeightInRow = animation.getImages().get(i)
								.getImage().getIconHeight();
					}
					image.setLocation(x, y);
					if (x
							+ animation.getImages().get(i).getImage()
									.getIconWidth() > panel.getWidth()) {
						x = 0;
						y += highestHeightInRow;
						highestHeightInRow = 0;
						image.setLocation(x, y);
					}
					x += animation.getImages().get(i).getImage().getIconWidth();
					panel.add(image);
					panel.revalidate();
				}
				panel.repaint();
			}
		}
	}

}
