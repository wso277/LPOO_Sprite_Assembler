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
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class InitProject extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textProjectName;
	static String projectName;
	static public boolean accept = false;

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
		lblWidth.setEnabled(false);
		lblWidth.setBounds(60, 120, 57, 17);
		contentPanel.add(lblWidth);

		final JLabel lblHeight = new JLabel("Height");
		lblHeight.setEnabled(false);
		lblHeight.setBounds(60, 149, 57, 17);
		contentPanel.add(lblHeight);

		final JCheckBox maxSizeCheckBox = new JCheckBox("Set max project size");
		maxSizeCheckBox.setBounds(40, 87, 311, 25);
		contentPanel.add(maxSizeCheckBox);

		final JSpinner widthSpinner = new JSpinner();
		widthSpinner.setEnabled(false);
		widthSpinner.setModel(new SpinnerNumberModel(new Integer(100),
				new Integer(100), null, new Integer(50)));
		widthSpinner.setBounds(135, 119, 75, 20);
		contentPanel.add(widthSpinner);

		final JSpinner heightSpinner = new JSpinner();
		heightSpinner.setEnabled(false);
		heightSpinner.setModel(new SpinnerNumberModel(new Integer(100),
				new Integer(100), null, new Integer(50)));
		heightSpinner.setBounds(135, 148, 75, 20);
		contentPanel.add(heightSpinner);
		maxSizeCheckBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (maxSizeCheckBox.isSelected()) {
					lblWidth.setEnabled(true);
					lblHeight.setEnabled(true);
					widthSpinner.setEnabled(true);
					heightSpinner.setEnabled(true);
					Gui.fixedSize = true;
				} else {
					lblWidth.setEnabled(false);
					lblHeight.setEnabled(false);
					widthSpinner.setEnabled(false);
					heightSpinner.setEnabled(false);
					Gui.fixedSize = false;
				}
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				projectName = textProjectName.getText();
				accept = true;
				if (maxSizeCheckBox.isSelected()) {
					Gui.panel.setSize((Integer) widthSpinner.getValue(),
							(Integer) heightSpinner.getValue());
					Gui.frame.setSize((Integer) widthSpinner.getValue(),
							(Integer) heightSpinner.getValue() + 45);
					System.out.println("TESTE");
				}
				if(Gui.fixedSize)
				{
					Gui.frame.getContentPane().setLayout(null);
					Gui.frame.setResizable(false);
				}
				else
				{
					Gui.frame.getContentPane().setLayout(new BorderLayout(0, 0));
					Gui.frame.setResizable(false);
				}
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
