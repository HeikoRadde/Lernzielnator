package windows;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import data.veranstaltung;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class editVeranstaltung extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextArea txtTitle;
	private JTextField txtWeek;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			editVeranstaltung dialog = new addVeranstaltung();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public editVeranstaltung(final JFrame frame, final mainWindow mainWindow, veranstaltung veranstaltung) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 250, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(frame);
						
		JLabel lblNewLabel = new JLabel("Titel");
		lblNewLabel.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNewLabel);
		
		txtTitle = new JTextArea();
		txtTitle.setText(veranstaltung.getVeranstaltungTitel());
		JScrollPane scrollTitle = new JScrollPane (txtTitle);
		scrollTitle.setLocation(38, 8);
		scrollTitle.setSize(186, 62);
		scrollTitle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollTitle);
		
		JLabel lblNewLabel_1 = new JLabel("Woche");
		lblNewLabel_1.setBounds(10, 81, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtWeek = new JTextField();
		txtWeek.setText(Integer.toString(veranstaltung.getVeranstaltungWoche()));
		txtWeek.setBounds(52, 78, 172, 20);
		contentPanel.add(txtWeek);
		txtWeek.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int error = 0;
						if(!(txtTitle.getText().isEmpty()) ){
							if(!(txtWeek.getText().isEmpty()) ){
								try{
									veranstaltung.setVeranstaltungWoche(Integer.parseInt(txtWeek.getText().replaceAll("[^\\d.]", "")));
									veranstaltung.setVeranstaltungTitel(txtTitle.getText());
									mainWindow.updateTree();
									editVeranstaltung.this.dispose();
								}
								catch(NumberFormatException e1){
									error = 5;
								}
							}
							else{
								error = 4;
							}
						}
						else{
							error = 3;
						}
						if(error > 0){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							switch(error){
							case 1:
								pane.setMessage("Bitte ein Semester auswählen!");
								break;
							case 2:
								pane.setMessage("Bitte ein Modul auswählen!");
								break;
							case 3:
								pane.setMessage("Bitte einen Titel eingeben!");
								break;
							case 4:
								pane.setMessage("Bitte eine Woche eingeben!");
								break;
							case 5:
								pane.setMessage("Bitte eine Nummer als Woche eingeben!");
								break;
							}
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setVisible(true);
						    error = -1;
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
						editVeranstaltung.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
