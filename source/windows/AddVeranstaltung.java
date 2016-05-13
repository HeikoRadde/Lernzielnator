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

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class AddVeranstaltung extends JDialog {
	private static final long serialVersionUID = 1960209422077358402L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/addVeranstaltung");
	private JComboBox<String> cmBxSemester;
	private JComboBox<String> cmBxModul;
	private JTextArea txtTitle;
	private JTextField txtWeek;

	/**
	 * Create the dialog.
	 */
	public AddVeranstaltung(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Veranstaltung veranstaltung) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 320, 210);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 230), prefs.getInt("h", 210));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		cmBxModul = new JComboBox<String>();
		cmBxModul.setFont(newStandFontPlain);
		cmBxModul.setEnabled(false);
		cmBxModul.setBounds(114, 11, 90, 20);
		contentPanel.add(cmBxModul);
		
		cmBxSemester = new JComboBox<String>();
		cmBxSemester.setFont(newStandFontPlain);
		cmBxSemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cmBxModul.setEnabled(true);
				cmBxModul.removeAllItems();
				//Items zur ModulComboBox hinzuf\u00fcgen
				for (int i = 0; i <lernzielnator.getModulListeSize(cmBxSemester.getSelectedIndex()); i++){
					String str;
					str = new String( "M" + Integer.toString(lernzielnator.getModul(cmBxSemester.getSelectedIndex(), i).getModulNumber()) );
					cmBxModul.addItem(str);
				}
				
			}
		});
		cmBxSemester.setBounds(10, 11, 90, 20);
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
				
		JLabel lblNewLabel = new JLabel("Titel");
		lblNewLabel.setFont(newStandFontPlain);
		lblNewLabel.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNewLabel);
		JScrollPane scrollTitle = new JScrollPane ();
		scrollTitle.setLocation(10, 42);
		scrollTitle.setSize(194, 62);
		scrollTitle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollTitle);
		
		txtTitle = new JTextArea();
		txtTitle.setFont(newStandFontPlain);
		scrollTitle.setViewportView(txtTitle);
		txtTitle.setText(veranstaltung.getVeranstaltungTitel());
		
		JLabel lblNewLabel_1 = new JLabel("Woche");
		lblNewLabel_1.setFont(newStandFontPlain);
		lblNewLabel_1.setBounds(10, 115, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtWeek = new JTextField();
		txtWeek.setFont(newStandFontPlain);
		txtWeek.setBounds(66, 112, 138, 20);
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
						if( cmBxSemester.getSelectedIndex() >= 0){
							if(cmBxModul.getSelectedIndex() >= 0){
								if(!(txtTitle.getText().isEmpty()) ){
									if(!(txtWeek.getText().isEmpty()) ){
										try{
											veranstaltung.setVeranstaltungWoche(Integer.parseInt(txtWeek.getText().replaceAll("[^\\d.]", "")));
											veranstaltung.setVeranstaltungTitel(txtTitle.getText());
											lernzielnator.addVeranstaltung(lernzielnator.getModul(cmBxSemester.getSelectedIndex(), cmBxModul.getSelectedIndex()), veranstaltung);
											prefs.putInt("x", getBounds().x);
											prefs.putInt("y", getBounds().y);
											prefs.putInt("w", getBounds().width);
											prefs.putInt("h", getBounds().height);
											AddVeranstaltung.this.dispose();
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
							}
							else{
								error = 2;
							}
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
							switch(error){
							case 1:
								pane.setMessage("Bitte ein Semester ausw\u00e4len!");
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
						    dialogError.setLocationRelativeTo(AddVeranstaltung.this);
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
						AddVeranstaltung.this.dispose();
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
	public AddVeranstaltung(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Veranstaltung veranstaltung, int modul, int semester) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 255, 180);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", 100), prefs.getInt("y", 100), prefs.getInt("w", 250), prefs.getInt("h", 180));
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
		txtWeek.setBounds(66, 78, 158, 20);
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
									lernzielnator.getModul(semester, modul).addModulVerantaltung(veranstaltung);
									mainWindow.updateTree();
									prefs.putInt("x", getBounds().x);
									prefs.putInt("y", getBounds().y);
									prefs.putInt("w", getBounds().width);
									prefs.putInt("h", getBounds().height);
									AddVeranstaltung.this.dispose();
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
						    dialogError.setLocationRelativeTo(AddVeranstaltung.this);
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
						AddVeranstaltung.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
