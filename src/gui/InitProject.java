package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InitProject extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textProjectName;
	private JTextField textField;
	private JTextField textField_1;

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
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setBounds(40, 93, 140, 15);
		contentPanel.add(lblHeight);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setBounds(40, 122, 140, 15);
		contentPanel.add(lblWidth);
		
		textField = new JTextField();
		textField.setBounds(237, 91, 114, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(237, 120, 114, 19);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setEnabled(false);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
