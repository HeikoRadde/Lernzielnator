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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import data.lernzielnator;
import data.veranstaltung;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addVeranstaltung extends JDialog {
	private static final long serialVersionUID = 1960209422077358402L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> cmBxSemester;
	private JComboBox<String> cmBxModul;
	private JTextArea txtTitle;
	private JTextField txtWeek;

	/**
	 * Create the dialog.
	 */
	public addVeranstaltung(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, veranstaltung veranstaltung) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 230, 210);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(frame);
		
		cmBxModul = new JComboBox<String>();
		cmBxModul.setEnabled(false);
		cmBxModul.setBounds(114, 11, 90, 20);
		contentPanel.add(cmBxModul);
		
		cmBxSemester = new JComboBox<String>();
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
		lblNewLabel.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNewLabel);
		JScrollPane scrollTitle = new JScrollPane ();
		scrollTitle.setLocation(10, 42);
		scrollTitle.setSize(194, 62);
		scrollTitle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollTitle);
		
		txtTitle = new JTextArea();
		scrollTitle.setViewportView(txtTitle);
		txtTitle.setText(veranstaltung.getVeranstaltungTitel());
		
		JLabel lblNewLabel_1 = new JLabel("Woche");
		lblNewLabel_1.setBounds(10, 115, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtWeek = new JTextField();
		txtWeek.setBounds(66, 112, 138, 20);
		contentPanel.add(txtWeek);
		txtWeek.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
											addVeranstaltung.this.dispose();
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
							switch(error){
							case 1:
								pane.setMessage("Bitte ein Semester auswählen!");
								break;
							case 2:
								pane.setMessage("Bitte ein Modul auswählen!");
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
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(addVeranstaltung.this);
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addVeranstaltung.this.dispose();
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
	public addVeranstaltung(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, veranstaltung veranstaltung, int modul, int semester) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neue Veranstaltung");
		setBounds(100, 100, 250, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		setLocationRelativeTo(frame);
		
		
				
		JLabel lblNewLabel = new JLabel("Titel");
		lblNewLabel.setBounds(10, 14, 46, 14);
		contentPanel.add(lblNewLabel);
		
		txtTitle = new JTextArea();
		txtTitle.setText(veranstaltung.getVeranstaltungTitel());
		JScrollPane scrollTitle = new JScrollPane (txtTitle);
		scrollTitle.setLocation(38, 8);
		scrollTitle.setSize(186, 62);
		scrollTitle.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contentPanel.add(scrollTitle);
		
		JLabel lblNewLabel_1 = new JLabel("Woche");
		lblNewLabel_1.setBounds(10, 81, 46, 14);
		contentPanel.add(lblNewLabel_1);
		
		txtWeek = new JTextField();
		txtWeek.setBounds(66, 78, 158, 20);
		contentPanel.add(txtWeek);
		txtWeek.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
									addVeranstaltung.this.dispose();
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
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(addVeranstaltung.this);
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
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						addVeranstaltung.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
