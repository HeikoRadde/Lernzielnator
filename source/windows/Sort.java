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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;
import java.awt.GridLayout;

public class Sort extends JDialog {

	private static final long serialVersionUID = 2024446469014189303L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/sort");

	/**
	 * Create the dialog.
	 */
	public Sort(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setBounds(100, 100, 450, 200);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 450), prefs.getInt("h", 200));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JCheckBox chckbxSemester;
		JCheckBox chckbxModule;
		JCheckBox chckbxVeranstaltungen;
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JLabel lblAuswahl = new JLabel(" Auswahl:");
		panel.add(lblAuswahl);
		lblAuswahl.setFont(newStandFontPlain);
		
		chckbxSemester = new JCheckBox("Semester");
		panel.add(chckbxSemester);
		chckbxSemester.setFont(newStandFontPlain);
		
		chckbxModule = new JCheckBox("Module");
		panel.add(chckbxModule);
		chckbxModule.setFont(newStandFontPlain);
		
		chckbxVeranstaltungen = new JCheckBox("Veranstaltungen");
		panel.add(chckbxVeranstaltungen);
		chckbxVeranstaltungen.setFont(newStandFontPlain);
		
		JPanel panel_1 = new JPanel();
		contentPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea txtrHelp = new JTextArea();
		panel_1.add(txtrHelp);
		txtrHelp.setFont(newStandFontPlain);
		txtrHelp.setEditable(false);
		txtrHelp.setLineWrap(true);
		txtrHelp.setWrapStyleWord(true);
		txtrHelp.setText("Hier kann ausgew\u00E4hlt werden, was alphabetisch sortiert werden soll. \r\nWenn z.B. \"Module\" ausgew\u00E4hlt wird, werden die Module aller Semester sortiert. \r\nWenn z.B. nur die Module eines einzelnen Semesters alphabetisch sortiert werden sollen, kannst du die Funktion im Baum per Rechtsklick aufrufen. \r\n");
		
		JPanel buttonPane = new JPanel();
		FlowLayout fl_buttonPane = new FlowLayout(FlowLayout.RIGHT);
		buttonPane.setLayout(fl_buttonPane);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.setFont(newStandFontPlain);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					mainWindow.sortAllSelect(chckbxSemester.isSelected(), chckbxModule.isSelected(), chckbxVeranstaltungen.isSelected());
					prefs.putInt("x", getBounds().x);
					prefs.putInt("y", getBounds().y);
					prefs.putInt("w", getBounds().width);
					prefs.putInt("h", getBounds().height);
					Sort.this.dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			getRootPane().setDefaultButton(okButton);
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setFont(newStandFontPlain);
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Sort.this.dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
		}
	}
}
