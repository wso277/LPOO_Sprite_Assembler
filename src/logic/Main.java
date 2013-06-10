package logic;

/**
 * Class containing the current project
 */
public class Main {

	protected static SpriteAssembler project;
	static public int spriteMinSize;
	
	public static SpriteAssembler getProject() {
		return project;
	}

	public Main(String projectName, int panelWidth, int panelHeight) {
		
		project = new SpriteAssembler(projectName, panelWidth, panelHeight);

	}

}