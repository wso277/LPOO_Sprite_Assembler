package gui;

import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import logic.Sprite;
import logic.SpriteAssembler;

public class Gui {

	private JFrame frame;
	final JFileChooser fc = new JFileChooser();
	SpriteAssembler project;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
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

		final JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int returnVal = fc.showOpenDialog(mntmNew);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File[] files = fc.getSelectedFiles();

					Sprite animation = null;
					try {
						animation = new Sprite(files.clone());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					project.addSprite(animation);
				}
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);

		JMenuItem mntmImport = new JMenuItem("Import");
		mnFile.add(mntmImport);

		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);

		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fc.setMultiSelectionEnabled(true);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images",
				"jpg", "gif", "png", "jpeg", "BMP");

		fc.setFileFilter(filter);

	}

}
