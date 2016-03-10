package windows;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.semester;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class editSemester extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtYear;
	private final ButtonGroup buttonGroupSemester = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			addSemester dialog = new addSemester(null,null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public editSemester(final JFrame frame, final mainWindow mainWindow, semester semester) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Semester");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 171, 163);
		
		//pack();
		setLocationRelativeTo(frame);		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblJahr = new JLabel("Jahr");
			lblJahr.setBounds(10, 14, 46, 14);
			contentPanel.add(lblJahr);
		}
		{
			txtYear = new JTextField(Integer.toString(semester.getSemesterYear()));
			txtYear.setBounds(44, 11, 86, 20);
			contentPanel.add(txtYear);
			txtYear.setColumns(10);
		}
		
		JRadioButton rdbtnSommersemester = new JRadioButton("Sommersemester");
		if(semester.isSS()){
			rdbtnSommersemester.setSelected(true);
		}
		buttonGroupSemester.add(rdbtnSommersemester);
		rdbtnSommersemester.setBounds(10, 35, 109, 23);
		contentPanel.add(rdbtnSommersemester);
		
		JRadioButton rdbtnWintersemester = new JRadioButton("Wintersemester");
		if(semester.isWS()){
			rdbtnWintersemester.setSelected(true);
		}
		buttonGroupSemester.add(rdbtnWintersemester);
		rdbtnWintersemester.setBounds(10, 61, 109, 23);
		contentPanel.add(rdbtnWintersemester);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int userYear;
						try{
							userYear = Integer.parseInt(txtYear.getText());
							semester.setSemesterYear(userYear);
							if(rdbtnWintersemester.isSelected()){
								semester.setWS();
							}
							else{
								if(rdbtnSommersemester.isSelected()){
									semester.setSS();
								}
								else{
									//Display Error Message
									JOptionPane pane = new JOptionPane(null);
								    //configure
									pane.setMessageType(JOptionPane.ERROR_MESSAGE );
									pane.setMessage("Bitte auswählen, ob Sommer- oder Wintersemester!");
								    JDialog dialogError = pane.createDialog("Error");
								    dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
								    dialogError.setVisible(true);
								    return;
								}
							}
							mainWindow.updateTree();
							editSemester.this.dispose();
						} catch (NumberFormatException  e){
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
						editSemester.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
