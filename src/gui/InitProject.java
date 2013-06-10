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

import logic.Main;

/**
 * Class that handles the window in which the project is created.
 */
public class InitProject extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textProjectName;
	final JSpinner minSpriteSizeSpinner = new JSpinner();
	final JSpinner heightSpinner = new JSpinner();
	final JSpinner widthSpinner = new JSpinner();

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

		// Adds the width spinner and initializes the valid value handler
		final JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(40, 91, 57, 17);
		contentPanel.add(lblWidth);
		widthSpinner.setModel(new SpinnerNumberModel(new Integer(512),
				new Integer(16), null, new Integer(16)));
		widthSpinner.setBounds(237, 93, 114, 20);
		contentPanel.add(widthSpinner);
		widthSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				checkSpinnerValues(2);
			}
		});

		// Adds the height spinner and initializes the valid value handler
		final JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(40, 120, 57, 17);
		contentPanel.add(lblHeight);
		heightSpinner.setModel(new SpinnerNumberModel(new Integer(512),
				new Integer(16), null, new Integer(16)));
		heightSpinner.setBounds(237, 119, 114, 20);
		contentPanel.add(heightSpinner);
		heightSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				checkSpinnerValues(1);
			}
		});

		// Adds the minimum sprite size spinner and initializes the valid value
		// handler
		JLabel lblSpriteLength = new JLabel("Minimum Sprite Length");
		lblSpriteLength.setBounds(40, 149, 202, 15);
		contentPanel.add(lblSpriteLength);
		minSpriteSizeSpinner.setModel(new SpinnerNumberModel(new Integer(16),
				new Integer(16), null, new Integer(16)));
		minSpriteSizeSpinner.setBounds(237, 147, 114, 20);
		contentPanel.add(minSpriteSizeSpinner);
		minSpriteSizeSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				checkSpinnerValues(0);
			}
		});

		JLabel lblProjectProperties = new JLabel("Project Properties");
		lblProjectProperties.setBounds(40, 12, 202, 15);
		contentPanel.add(lblProjectProperties);

		// Sets the OK and Cancel button actions
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				Gui.projectName = textProjectName.getText();
				Gui.InitAccept = true;

				Gui.getPanel().setSize((Integer) widthSpinner.getValue(),
						(Integer) heightSpinner.getValue());
				Gui.frame.setSize((Integer) widthSpinner.getValue(),
						(Integer) heightSpinner.getValue() + 45);
				Main.spriteMinSize = (Integer) minSpriteSizeSpinner.getValue();
				Gui.panelHeight = (Integer) heightSpinner.getValue();
				Gui.panelWidth = (Integer) widthSpinner.getValue();

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

	public void checkSpinnerValues(int mode) {

		int value;

		switch (mode) {
		// when minSpriteSizeSpinner is modified
		case 0:
			value = (Integer) widthSpinner.getValue()
					/ (Integer) minSpriteSizeSpinner.getValue()
					* (Integer) minSpriteSizeSpinner.getValue();

			widthSpinner.setValue(value);

			value = (Integer) heightSpinner.getValue()
					/ (Integer) minSpriteSizeSpinner.getValue()
					* (Integer) minSpriteSizeSpinner.getValue();

			heightSpinner.setValue(value);

			break;
		// when heightSpinner is modified
		case 1:
			value = (Integer) heightSpinner.getValue()
					/ (Integer) minSpriteSizeSpinner.getValue()
					* (Integer) minSpriteSizeSpinner.getValue();

			heightSpinner.setValue(value);
			break;
		// when widthSpinner is modified
		case 2:
			value = (Integer) widthSpinner.getValue()
					/ (Integer) minSpriteSizeSpinner.getValue()
					* (Integer) minSpriteSizeSpinner.getValue();

			widthSpinner.setValue(value);
			break;
		}
	}
}
