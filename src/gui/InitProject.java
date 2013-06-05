package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class InitProject extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textProjectName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			InitProject dialog = new InitProject();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public InitProject() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel ProjectName = new JLabel("Insert Project Name");
			ProjectName.setBounds(40, 64, 140, 15);
			contentPanel.add(ProjectName);
		}

		textProjectName = new JTextField();
		textProjectName.setText("New Project");
		textProjectName.setBounds(237, 62, 114, 19);
		contentPanel.add(textProjectName);
		textProjectName.setColumns(10);

		final JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(40, 91, 57, 17);
		contentPanel.add(lblWidth);

		final JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(40, 120, 57, 17);
		contentPanel.add(lblHeight);

		final JSpinner widthSpinner = new JSpinner();
		widthSpinner.setModel(new SpinnerNumberModel(new Integer(512), new Integer(16), null, new Integer(16)));
		widthSpinner.setBounds(237, 93, 114, 20);
		contentPanel.add(widthSpinner);

		final JSpinner heightSpinner = new JSpinner();
		heightSpinner.setModel(new SpinnerNumberModel(new Integer(512), new Integer(16), null, new Integer(16)));
		heightSpinner.setBounds(237, 119, 114, 20);
		contentPanel.add(heightSpinner);

		JLabel lblProjectProperties = new JLabel("Project Properties");
		lblProjectProperties.setBounds(40, 12, 202, 15);
		contentPanel.add(lblProjectProperties);
		
		JLabel lblSpriteLenght = new JLabel("Minimum Sprite Lenght");
		lblSpriteLenght.setBounds(40, 149, 202, 15);
		contentPanel.add(lblSpriteLenght);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(16), new Integer(16), null, new Integer(16)));
		spinner.setBounds(237, 147, 114, 20);
		contentPanel.add(spinner);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Gui.projectName = textProjectName.getText();
				Gui.InitAccept = true;

				Gui.panel.setSize((Integer) widthSpinner.getValue(),
						(Integer) heightSpinner.getValue());
				Gui.frame.setSize((Integer) widthSpinner.getValue(),
						(Integer) heightSpinner.getValue() + 45);

				System.out.println("TESTE");
				System.out.println((Integer) widthSpinner.getValue());
				System.out.println((Integer) heightSpinner.getValue());
				System.out.println(Gui.panel.getHeight());
				System.out.println(Gui.panel.getWidth());
				//Gui.frame.setResizable(false);

				dispose();

			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}
}
