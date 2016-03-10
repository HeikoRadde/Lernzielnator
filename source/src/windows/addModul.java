package windows;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.lernzielnator;
import data.modul;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addModul extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtModulNr;
	private JComboBox cmBxSemester;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			addModul dialog = new addModul();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public addModul(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, modul modul) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Modul");
		setBounds(100, 100, 255, 142);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(frame);
		
		cmBxSemester = new JComboBox();
		cmBxSemester.setBounds(10, 11, 80, 20);
		contentPanel.add(cmBxSemester);
		
		for (int i = 0; i < lernzielnator.getSemesterListeSize(); i++){
			String str;
			if(lernzielnator.getSemester(i).isWS()){
				str = new String("WS" + lernzielnator.getSemester(i).getSemesterYear().toString());
			}
			else{
				str = new String("SS" + lernzielnator.getSemester(i).getSemesterYear().toString());
			}
			cmBxSemester.addItem(str);
		}
		
		JLabel lblModulnummer = new JLabel("Modulnummer:");
		lblModulnummer.setBounds(10, 42, 80, 14);
		contentPanel.add(lblModulnummer);
		
		txtModulNr = new JTextField();
		txtModulNr.setBounds(89, 39, 86, 20);
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
						int semester;
						String selStr;
						try{
							modul.setModulNumber(Integer.parseInt(txtModulNr.getText()));
							selStr = new String(String.valueOf(cmBxSemester.getSelectedItem()));
							if(selStr.contains("SS")){
								String help = new String(selStr.replaceAll("SS", ""));
								semester = Integer.parseInt(help);
								lernzielnator.addModul(semester, false, modul);
							}else
							{//WS
								String help = new String(selStr.replaceAll("WS", ""));
								semester = Integer.parseInt(help);
								lernzielnator.addModul(semester, true, modul);
							}
							addModul.this.dispose();
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
						addModul.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	/**
	 * @wbp.parser.constructor
	 */
	public addModul(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, modul modul, int semester) {
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
		txtModulNr.setBounds(89, 11, 86, 20);
		txtModulNr.setText("");
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
							lernzielnator.getSemester(semester).addSemesterModul(modul);
							mainWindow.updateTree();
							addModul.this.dispose();
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
						addModul.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
