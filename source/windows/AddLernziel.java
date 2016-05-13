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

import data.Lernziel;
import data.Lernzielnator;
import data.Veranstaltung;
import util.LZ_Dimension;
import util.LZ_Kognitionsdimension;

import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class AddLernziel extends JDialog {
	private static final long serialVersionUID = 4034840577384463260L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/addLernziel");
	private JTextArea txtNotes;
	private JTextArea txtDescription;
	private JCheckBox chckbxMC;	
	private JCheckBox chckbxSMPP;
	private JCheckBox chckbxOSCE;
	private JCheckBox chckbxLerngruppe;
	private JCheckBox chckbxAusarbeitung;
	private JCheckBox chckbxKarteikarten;
	private JComboBox<Object> cmBxLzDimension;
	private JComboBox<Object> cmBxLzKognitionsdimension;
	private JCheckBox chckbxNotRelevant;

	/**
	 * Create the dialog.
	 */
	public AddLernziel(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Veranstaltung veranstaltung, Lernziel lernziel) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Lernziel");
		setBounds(100, 100, 555, 320);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 555), prefs.getInt("h", 320));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel textPanel = new JPanel();
		textPanel.setBounds(10, 11, 519, 155);
		contentPanel.add(textPanel);
		textPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtDescription = new JTextArea();
		txtDescription.setFont(newStandFontPlain);
		txtDescription.setLineWrap(true);
		txtDescription.setText(lernziel.getLzDescription());
		txtDescription.setToolTipText("Die Beschreibung des Lernzieles\r\n");
		JScrollPane scrollDesc = new JScrollPane (txtDescription);
		scrollDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textPanel.add(scrollDesc);
		
		txtNotes = new JTextArea();
		txtNotes.setFont(newStandFontPlain);
		txtNotes.setLineWrap(true);
		JScrollPane scrollNotes = new JScrollPane (txtNotes);
		txtNotes.setToolTipText("Eigene, optionale, Notizen zum Lernziel");
		scrollNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		txtNotes.setText(lernziel.getLzNotes());
		textPanel.add(scrollNotes);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setBounds(10, 173, 519, 77);
		contentPanel.add(panelBottom);
		panelBottom.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelBottomLeft = new JPanel();
		panelBottom.add(panelBottomLeft);
		panelBottomLeft.setLayout(null);
		
		chckbxMC = new JCheckBox("MC");
		chckbxMC.setFont(newStandFontPlain);
		chckbxMC.setBounds(6, 0, 97, 23);
		panelBottomLeft.add(chckbxMC);
		
		chckbxSMPP = new JCheckBox("SMPP");
		chckbxSMPP.setFont(newStandFontPlain);
		chckbxSMPP.setBounds(6, 26, 97, 23);
		panelBottomLeft.add(chckbxSMPP);
		
		chckbxOSCE = new JCheckBox("OSCE");
		chckbxOSCE.setFont(newStandFontPlain);
		chckbxOSCE.setBounds(6, 52, 97, 23);
		panelBottomLeft.add(chckbxOSCE);
		
		chckbxLerngruppe = new JCheckBox("Lerngruppe");
		chckbxLerngruppe.setFont(newStandFontPlain);
		chckbxLerngruppe.setBounds(101, 52, 152, 23);
		panelBottomLeft.add(chckbxLerngruppe);
		
		chckbxAusarbeitung = new JCheckBox("Ausarbeitung");
		chckbxAusarbeitung.setFont(newStandFontPlain);
		chckbxAusarbeitung.setBounds(101, 26, 152, 23);
		panelBottomLeft.add(chckbxAusarbeitung);
		
		chckbxKarteikarten = new JCheckBox("Karteikarten");
		chckbxKarteikarten.setFont(newStandFontPlain);
		chckbxKarteikarten.setBounds(101, 0, 152, 23);
		panelBottomLeft.add(chckbxKarteikarten);
		
		JPanel panelBottomRight = new JPanel();
		panelBottom.add(panelBottomRight);
		panelBottomRight.setLayout(null);
		
		cmBxLzDimension = new JComboBox<Object>();
		cmBxLzDimension.setFont(newStandFontPlain);
		cmBxLzDimension.setModel(new DefaultComboBoxModel<Object>(new String[] {"*none*", "Wissen/Kenntnisse(kognitiv)", "Fertigkeiten(psychomotorisch)", "Einstellungen(emotional/reflektiv)", "Mini-PA"}));
		cmBxLzDimension.setBounds(0, 0, 259, 20);
		panelBottomRight.add(cmBxLzDimension);
		
		cmBxLzKognitionsdimension = new JComboBox<Object>();
		cmBxLzKognitionsdimension.setFont(newStandFontPlain);
		cmBxLzKognitionsdimension.setModel(new DefaultComboBoxModel<Object>(new String[] {"*none*", "erinnern", "verstehen", "analysieren", "evaluieren", "erzeugen"}));
		cmBxLzKognitionsdimension.setBounds(0, 31, 259, 20);
		panelBottomRight.add(cmBxLzKognitionsdimension);
		
		chckbxNotRelevant = new JCheckBox("nicht pr\u00FCfungsrelevant");
		chckbxNotRelevant.setFont(newStandFontPlain);
		chckbxNotRelevant.setToolTipText("Wird automatisch gesetzt, wenn LZ nicht f\u00FCr MC oder SMPP relevant ist.\r\nKann manuell gesetzt werden.\r\nNicht relevante LZ's werden im Fortschritt nicht mitgez\u00E4hlt.");
		chckbxNotRelevant.setBounds(0, 54, 253, 23);
		panelBottomRight.add(chckbxNotRelevant);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(newStandFontPlain);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int error = -1;
						if( (!(txtDescription.getText().equals("Beschreibung"))) && (!(txtDescription.getText().isEmpty())) ){
							lernziel.setAusarbeitung(chckbxAusarbeitung.isSelected());
							lernziel.setKarteikarten(chckbxKarteikarten.isSelected());
							lernziel.setLerngruppe(chckbxLerngruppe.isSelected());
							lernziel.setLzDescription(txtDescription.getText());
							lernziel.setLzDimension(LZ_Dimension.toLzDimension(cmBxLzDimension.getSelectedIndex()));
							lernziel.setLzKognitionsdimension(LZ_Kognitionsdimension.toLzKognitionsdimension(cmBxLzKognitionsdimension.getSelectedIndex()));
							lernziel.setLzMC(chckbxMC.isSelected());
							lernziel.setLzOSCE(chckbxOSCE.isSelected());
							lernziel.setLzSMPP(chckbxSMPP.isSelected());
							lernziel.setRelevant(!(chckbxNotRelevant.isSelected()));
							lernziel.setNotes(txtNotes.getText());
							lernzielnator.addLernziel(veranstaltung, lernziel);
							mainWindow.changed = false;
							mainWindow.updateTree();
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							AddLernziel.this.dispose();
						}
						else
						{
							error = 1;
						}
						if(error > 0){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setFont(newStandFontPlain);
							pane.setMessage("Bitte eine Beschreibung eingeben");
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setFont(newStandFontPlain);
						    dialogError.setLocationRelativeTo(AddLernziel.this);
						    dialogError.setAlwaysOnTop(true);
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
						AddLernziel.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
