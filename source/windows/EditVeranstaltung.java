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
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;
import data.Veranstaltung;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class EditVeranstaltung extends JDialog {
	private static final long serialVersionUID = -1981884975181426254L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/editVeranstaltung");
	private JTextArea txtTitle;
	private JTextField txtWeek;

	/**
	 * Create the dialog.
	 */
	public EditVeranstaltung(final JFrame frame, final MainWindow mainWindow, Veranstaltung veranstaltung, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 255, 180);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 250), prefs.getInt("h", 180));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
						
		JLabel lblNewLabel = new JLabel("Titel");
		lblNewLabel.setFont(newStandFontPlain);
		lblNewLabel.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNewLabel);
		
		txtTitle = new JTextArea();
		txtTitle.setFont(newStandFontPlain);
		txtTitle.setText(veranstaltung.getVeranstaltungTitel());
		JScrollPane scrollTitle = new JScrollPane (txtTitle);
		scrollTitle.setLocation(38, 8);
		scrollTitle.setSize(186, 62);
		scrollTitle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollTitle);
		
		JLabel lblNewLabel_1 = new JLabel("Woche");
		lblNewLabel_1.setFont(newStandFontPlain);
		lblNewLabel_1.setBounds(10, 81, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtWeek = new JTextField();
		txtWeek.setFont(newStandFontPlain);
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
				okButton.setFont(newStandFontPlain);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int error = 0;
						if(!(txtTitle.getText().isEmpty()) ){
							if(!(txtWeek.getText().isEmpty()) ){
								try{
									veranstaltung.setVeranstaltungWoche(Integer.parseInt(txtWeek.getText().replaceAll("[^\\d.]", "")));
									veranstaltung.setVeranstaltungTitel(txtTitle.getText());
									mainWindow.updateTree();
									prefs.putInt("x", getBounds().x);
									prefs.putInt("y", getBounds().y);
									prefs.putInt("w", getBounds().width);
									prefs.putInt("h", getBounds().height);
									EditVeranstaltung.this.dispose();
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
							pane.setFont(newStandFontPlain);
							switch(error){
							case 1:
								pane.setMessage("Bitte ein Semester ausw\u00e4hlen!");
								break;
							case 2:
								pane.setMessage("Bitte ein Modul ausw\u00e4hlen!");
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
						    dialogError.setFont(newStandFontPlain);
						    dialogError.setLocationRelativeTo(EditVeranstaltung.this);
						    dialogError.setAlwaysOnTop(true);
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
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
				cancelButton.setFont(newStandFontPlain);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						EditVeranstaltung.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
