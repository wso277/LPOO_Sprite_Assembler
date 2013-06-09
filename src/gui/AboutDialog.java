package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("SpriteAssembler - Developed for LPOO- FEUP - MIEIC 2013");
		lblNewLabel.setBounds(12, 0, 422, 46);
		contentPanel.add(lblNewLabel);
		
		JLabel lblDevelopedBy = new JLabel("Developed by João Santos and Wilson Oliveira");
		lblDevelopedBy.setBounds(12, 42, 422, 20);
		contentPanel.add(lblDevelopedBy);
		
		JLabel lblOverseenByNuno = new JLabel("Overseen by Professor Nuno Flores");
		lblOverseenByNuno.setBounds(12, 68, 422, 20);
		contentPanel.add(lblOverseenByNuno);
		
		JLabel lblInstructionOfUse = new JLabel("Instructions:");
		lblInstructionOfUse.setBounds(12, 100, 422, 32);
		contentPanel.add(lblInstructionOfUse);
		
		JLabel lblNewLabel_1 = new JLabel("Left mouse click - Selects Sprite");
		lblNewLabel_1.setBounds(12, 133, 422, 20);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblRightMouseClick = new JLabel("Right mouse click - Shows animation preview");
		lblRightMouseClick.setBounds(12, 151, 422, 20);
		contentPanel.add(lblRightMouseClick);
		
		JLabel lblNewLabel_2 = new JLabel("Mouse drag - Moves image across panel");
		lblNewLabel_2.setBounds(12, 175, 422, 20);
		contentPanel.add(lblNewLabel_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}
