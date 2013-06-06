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

		if (InitAccept) {

		    mntmNew.setEnabled(true);

		    project = new SpriteAssembler(projectName, panelWidth,
			    panelHeight);
		    panel.removeAll();
		    panel.revalidate();
		    addSpriteToProject(mntmNew);
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
	mnFile.add(mntmExit);

	JMenu mnEdit = new JMenu("Edit");
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
		    animation = new Sprite(files.clone(), spriteName, spriteIsLoopable, fps);
		} catch (IOException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}

		project.addSprite(animation);
		for (int i = 0; i < animation.getImages().size(); i++) {
		    animation
			    .getImages()
			    .get(i)
			    .setSize(
				    animation.getImages().get(i).getImage()
					    .getWidth(null),
				    animation.getImages().get(i).getImage()
					    .getHeight(null));

		    if (animation.getImages().get(i).getYsquares() > highestHeightInRow) {
			highestHeightInRow = animation.getImages().get(i)
				.getYsquares();
		    }		    
		    
		    animation.getImages().get(i).setLocation(x, y);
		    if (x + animation.getImages().get(i).getXsquares()
			    * spriteMinSize > getPanel().getWidth()) {
			x = 0;
			y += highestHeightInRow * spriteMinSize;
			highestHeightInRow = 0;
			animation.getImages().get(i).setLocation(x, y);
		    }
		    animation.getImages().get(i)
			    .setMatrixPos(x / spriteMinSize, y / spriteMinSize);
		    for (int wsquare = 0; wsquare < animation.getImages()
			    .get(i).getXsquares(); wsquare++) {
			for (int hsquare = 0; hsquare < animation.getImages()
				.get(i).getYsquares(); hsquare++) {
			    project.setFilled(x / spriteMinSize + wsquare, y
				    / spriteMinSize + hsquare, 1);
			}
		    }
		    x += animation.getImages().get(i).getXsquares()
			    * spriteMinSize;

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
