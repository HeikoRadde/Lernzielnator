package windows;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.modul;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class editModul extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtModulNr;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			editModul dialog = new addModul();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public editModul(final JFrame frame, final mainWindow mainWindow, modul modul) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Modul");
		setBounds(100, 100, 255, 109);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(frame);
		
		JLabel lblModulnummer = new JLabel("Modulnummer:");
		lblModulnummer.setBounds(10, 14, 80, 14);
		contentPanel.add(lblModulnummer);
		
		txtModulNr = new JTextField();
		txtModulNr.setText(Integer.toString(modul.getModulNumber()));
		txtModulNr.setBounds(89, 11, 86, 20);
		contentPanel.add(txtModulNr);
		txtModulNr.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							modul.setModulNumber(Integer.parseInt(txtModulNr.getText()));
							mainWindow.updateTree();
							editModul.this.dispose();
						}
						catch (NumberFormatException  e1){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setVisible(true);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						editModul.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
