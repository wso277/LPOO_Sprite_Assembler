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

/**
 * Handles the window in which a sprite can be edited.
 */
public class EditSprite extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField spriteNameTxt;
	private int index = Gui.getSpIndex();


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
		
		spriteNameTxt = new JTextField();
		spriteNameTxt.setBounds(249, 65, 114, 19);
		contentPanel.add(spriteNameTxt);
		spriteNameTxt.setColumns(10);
		
		final JSpinner fpsSpinner = new JSpinner();
		fpsSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		fpsSpinner.setBounds(286, 92, 77, 20);
		
		spriteNameTxt.setText(Gui.getProject().getSprites().get(index).getName());
		fpsSpinner.setValue(Gui.getProject().getSprites().get(index).getFps());
		chckbxCanLoop.setSelected(Gui.getProject().getSprites().get(index).getCanLoop());
		
		contentPanel.add(fpsSpinner);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						
						Gui.getProject().getSprites().get(index).setName(spriteNameTxt.getText());
						Gui.getProject().getSprites().get(index).setFps((int)fpsSpinner.getValue());
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
