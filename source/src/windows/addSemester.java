/*	Lernzielnator - a Programm for the Students of the Berlin Charite
	University to manage their "Lernziele".
	Copyright (C) 2016 Heiko Radde

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package windows;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.lernzielnator;
import data.semester;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addSemester extends JDialog {
	private static final long serialVersionUID = 186876190516048563L;
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
	public addSemester(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, semester semester) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Semester");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 163, 163);
		setLocationRelativeTo(frame);
		
		//pack();
		setLocationRelativeTo(frame);		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblJahr = new JLabel("Jahr");
			lblJahr.setBounds(10, 14, 34, 14);
			contentPanel.add(lblJahr);
		}
		{
			txtYear = new JTextField();
			txtYear.setBounds(42, 11, 95, 20);
			contentPanel.add(txtYear);
			txtYear.setColumns(10);
		}
		
		JRadioButton rdbtnSommersemester = new JRadioButton("Sommersemester");
		buttonGroupSemester.add(rdbtnSommersemester);
		rdbtnSommersemester.setBounds(10, 35, 139, 23);
		contentPanel.add(rdbtnSommersemester);
		
		JRadioButton rdbtnWintersemester = new JRadioButton("Wintersemester");
		buttonGroupSemester.add(rdbtnWintersemester);
		rdbtnWintersemester.setBounds(10, 61, 139, 23);
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
								    dialogError.setLocationRelativeTo(addSemester.this);
								    dialogError.setAlwaysOnTop(true);
								    dialogError.setVisible(true);
								    return;
								}
							}
							lernzielnator.addSemester(semester);
							addSemester.this.dispose();
						} catch (NumberFormatException  e){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(addSemester.this);
						    dialogError.setAlwaysOnTop(true);
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
						addSemester.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
