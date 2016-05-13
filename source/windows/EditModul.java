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
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;
import data.Modul;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class EditModul extends JDialog {

	private static final long serialVersionUID = -8844371396098776125L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/editModul");
	private JTextField txtModulNr;

	/**
	 * Create the dialog.
	 */
	public EditModul(final JFrame frame, final MainWindow mainWindow, Modul modul, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);		
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Modul");
		setBounds(100, 100, 255, 110);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 255), prefs.getInt("h", 110));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(frame);
		
		JLabel lblModulnummer = new JLabel("Modulnummer:");
		lblModulnummer.setFont(newStandFontPlain);
		lblModulnummer.setBounds(10, 14, 80, 14);
		contentPanel.add(lblModulnummer);
		
		txtModulNr = new JTextField();
		txtModulNr.setFont(newStandFontPlain);
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
				okButton.setFont(newStandFontPlain);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							modul.setModulNumber(Integer.parseInt(txtModulNr.getText()));
							mainWindow.updateTree();
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							EditModul.this.dispose();
						}
						catch (NumberFormatException  e1){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setFont(newStandFontPlain);
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setLocationRelativeTo(EditModul.this);
						    dialogError.setFont(newStandFontPlain);
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
						EditModul.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
