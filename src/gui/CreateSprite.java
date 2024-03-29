package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


/**
 * Class that handles the window in which the sprite is inserted into the program
 */
public class CreateSprite extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField spriteNameTxt;


	/**
	 * Create the dialog.
	 */
	public CreateSprite() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblName = new JLabel("Name:");
			lblName.setBounds(77, 62, 45, 15);
			contentPanel.add(lblName);
		}

		JLabel lblFramesPerSecon = new JLabel("Frames Per Second:");
		lblFramesPerSecon.setBounds(77, 89, 148, 15);
		contentPanel.add(lblFramesPerSecon);

		spriteNameTxt = new JTextField();
		spriteNameTxt.setText("Sprite");
		spriteNameTxt.setBounds(281, 60, 114, 19);
		contentPanel.add(spriteNameTxt);
		spriteNameTxt.setColumns(10);

		final JSpinner fpsSpinner = new JSpinner();
		fpsSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(
				1), null, new Integer(1)));
		fpsSpinner.setBounds(281, 87, 114, 20);
		contentPanel.add(fpsSpinner);

		final JCheckBox canLoopCheckBox = new JCheckBox("Can Loop");
		canLoopCheckBox.setBounds(77, 112, 129, 23);
		canLoopCheckBox.setSelected(true);
		contentPanel.add(canLoopCheckBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Gui.spriteName = spriteNameTxt.getText();

						Gui.spriteIsLoopable = canLoopCheckBox.isSelected();

						Gui.CreateAccept = true;
						Gui.fps = (int) fpsSpinner.getValue();
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
						Gui.CreateAccept = false;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
