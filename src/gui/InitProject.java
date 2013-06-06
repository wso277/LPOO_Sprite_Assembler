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
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class InitProject extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textProjectName;
	final JSpinner spinner = new JSpinner();
	final JSpinner heightSpinner = new JSpinner();
	final JSpinner widthSpinner = new JSpinner();

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
		widthSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeValue(2);
			}
		});

		widthSpinner.setModel(new SpinnerNumberModel(new Integer(512),
				new Integer(16), null, new Integer(16)));
		widthSpinner.setBounds(237, 93, 114, 20);
		contentPanel.add(widthSpinner);
		heightSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeValue(1);
			}
		});

		heightSpinner.setModel(new SpinnerNumberModel(new Integer(512),
				new Integer(16), null, new Integer(16)));
		heightSpinner.setBounds(237, 119, 114, 20);
		contentPanel.add(heightSpinner);

		JLabel lblProjectProperties = new JLabel("Project Properties");
		lblProjectProperties.setBounds(40, 12, 202, 15);
		contentPanel.add(lblProjectProperties);

		JLabel lblSpriteLenght = new JLabel("Minimum Sprite Lenght");
		lblSpriteLenght.setBounds(40, 149, 202, 15);
		contentPanel.add(lblSpriteLenght);

		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				changeValue(0);
			}
		});

		spinner.setModel(new SpinnerNumberModel(new Integer(16),
				new Integer(16), null, new Integer(16)));
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
				Gui.spriteMinSize = (Integer)spinner.getValue();
				Gui.panelHeight = (Integer)heightSpinner.getValue();
				Gui.panelWidth = (Integer)widthSpinner.getValue();

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

	public void changeValue(int mode) {

		int value;

		switch (mode) {
		case 0:
			value = (Integer) widthSpinner.getValue()
					/ (Integer) spinner.getValue()
					* (Integer) spinner.getValue();

			widthSpinner.setValue(value);

			value = (Integer) heightSpinner.getValue()
					/ (Integer) spinner.getValue()
					* (Integer) spinner.getValue();

			heightSpinner.setValue(value);
			break;
		case 1:
			value = (Integer) heightSpinner.getValue()
					/ (Integer) spinner.getValue()
					* (Integer) spinner.getValue();

			heightSpinner.setValue(value);
			break;
		case 2:
			value = (Integer) widthSpinner.getValue()
					/ (Integer) spinner.getValue()
					* (Integer) spinner.getValue();

			widthSpinner.setValue(value);
			break;
		}
	}
}
