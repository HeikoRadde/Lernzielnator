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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class sort extends JDialog {

	private static final long serialVersionUID = 2024446469014189303L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public sort(final JFrame frame, final mainWindow mainWindow) {
		setBounds(100, 100, 450, 202);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		JCheckBox chckbxSemester;
		JCheckBox chckbxModule;
		JCheckBox chckbxVeranstaltungen;
		setLocationRelativeTo(frame);
		
		setLocationRelativeTo(frame);
		
		JTextArea txtrHelp = new JTextArea();
		txtrHelp.setEditable(false);
		txtrHelp.setLineWrap(true);
		txtrHelp.setWrapStyleWord(true);
		txtrHelp.setText("Hier kann ausgew\u00E4hlt werden, was alphabetisch sortiert werden soll. \r\nWenn z.B. \"Module\" ausgew\u00E4hlt wird, werden die Module aller Semester sortiert. \r\nWenn z.B. nur die Module eines einzelnen Semesters alphabetisch sortiert werden sollen, kannst du die Funktion im Baum per Rechtsklick aufrufen. \r\n");
		txtrHelp.setBounds(121, 11, 303, 207);
		contentPanel.add(txtrHelp);
		{
			chckbxSemester = new JCheckBox("Semester");
			chckbxSemester.setBounds(10, 32, 105, 23);
			contentPanel.add(chckbxSemester);
		}
		{
			chckbxModule = new JCheckBox("Module");
			chckbxModule.setBounds(10, 58, 105, 23);
			contentPanel.add(chckbxModule);
		}
		{
			chckbxVeranstaltungen = new JCheckBox("Veranstaltungen");
			chckbxVeranstaltungen.setBounds(10, 84, 105, 23);
			contentPanel.add(chckbxVeranstaltungen);
		}
		{
			JLabel lblAuswahl = new JLabel(" Auswahl:");
			lblAuswahl.setBounds(10, 11, 101, 14);
			contentPanel.add(lblAuswahl);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						mainWindow.sortAllSelect(chckbxSemester.isSelected(), chckbxModule.isSelected(), chckbxVeranstaltungen.isSelected());
						sort.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sort.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
