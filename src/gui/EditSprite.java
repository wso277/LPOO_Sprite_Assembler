package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class EditSprite extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int index = Gui.getSpIndex();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			EditSprite dialog = new EditSprite();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public EditSprite() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewName = new JLabel("New Name:");
		lblNewName.setBounds(75, 67, 101, 15);
		contentPanel.add(lblNewName);
		
		JLabel lblNewFps = new JLabel("New Fps:");
		lblNewFps.setBounds(75, 94, 101, 15);
		contentPanel.add(lblNewFps);
		
		final JCheckBox chckbxCanLoop = new JCheckBox("Can Loop");
		chckbxCanLoop.setBounds(75, 119, 129, 23);
		contentPanel.add(chckbxCanLoop);
		
		textField = new JTextField();
		textField.setBounds(249, 65, 114, 19);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		final JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spinner.setBounds(286, 92, 77, 20);
		
		textField.setText(Gui.getProject().getSprites().get(index).getName());
		spinner.setValue(Gui.getProject().getSprites().get(index).getFps());
		chckbxCanLoop.setSelected(Gui.getProject().getSprites().get(index).getCanLoop());
		
		contentPanel.add(spinner);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						Gui.getProject().getSprites().get(index).setName(textField.getText());
						Gui.getProject().getSprites().get(index).setFps((int)spinner.getValue());
						Gui.getProject().getSprites().get(index).setCanLoop(chckbxCanLoop.isSelected());
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
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
}
