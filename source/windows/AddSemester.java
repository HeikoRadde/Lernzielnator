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
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;
import data.Semester;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class AddSemester extends JDialog {
	private static final long serialVersionUID = 186876190516048563L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/addSemester");
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
	public AddSemester(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Semester semester) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Semester");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 163, 163);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 163), prefs.getInt("h", 163));
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblJahr = new JLabel("Jahr");
			lblJahr.setFont(newStandFontPlain);
			lblJahr.setBounds(10, 14, 34, 14);
			contentPanel.add(lblJahr);
		}
		{
			txtYear = new JTextField();
			txtYear.setFont(newStandFontPlain);
			txtYear.setBounds(42, 11, 95, 20);
			contentPanel.add(txtYear);
			txtYear.setColumns(10);
		}
		
		JRadioButton rdbtnSommersemester = new JRadioButton("Sommersemester");
		rdbtnSommersemester.setFont(newStandFontPlain);
		buttonGroupSemester.add(rdbtnSommersemester);
		rdbtnSommersemester.setBounds(10, 35, 139, 23);
		contentPanel.add(rdbtnSommersemester);
		
		JRadioButton rdbtnWintersemester = new JRadioButton("Wintersemester");
		rdbtnWintersemester.setFont(newStandFontPlain);
		buttonGroupSemester.add(rdbtnWintersemester);
		rdbtnWintersemester.setBounds(10, 61, 139, 23);
		contentPanel.add(rdbtnWintersemester);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(newStandFontPlain);
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
									pane.setFont(newStandFontPlain);
									pane.setMessage("Bitte ausw\u00e4hlen, ob Sommer- oder Wintersemester!");
								    JDialog dialogError = pane.createDialog("Error");
								    dialogError.setFont(newStandFontPlain);
								    dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
								    dialogError.setLocationRelativeTo(AddSemester.this);
								    dialogError.setAlwaysOnTop(true);
								    dialogError.setVisible(true);
								    return;
								}
							}
							lernzielnator.addSemester(semester);
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							AddSemester.this.dispose();
						} catch (NumberFormatException  e){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(AddSemester.this);
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
				cancelButton.setFont(newStandFontPlain);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						AddSemester.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
