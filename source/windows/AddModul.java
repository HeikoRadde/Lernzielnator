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
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;

public class AddModul extends JDialog {
	private static final long serialVersionUID = 8678448174443709186L;
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/addModul");
	private final JPanel contentPanel = new JPanel();
	private JTextField txtModulNr;
	private JComboBox<String> cmBxSemester;

	/**
	 * Create the dialog.
	 */
	public AddModul(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Modul modul) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Modul");
		setBounds(100, 100, 255, 142);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 255), prefs.getInt("h", 142));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		cmBxSemester = new JComboBox<String>();
		cmBxSemester.setFont(newStandFontPlain);
		cmBxSemester.setBounds(10, 11, 87, 20);
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
		
		JLabel lblModulnummer = new JLabel("Modulnummer:");
		lblModulnummer.setFont(newStandFontPlain);
		lblModulnummer.setBounds(10, 42, 87, 14);
		contentPanel.add(lblModulnummer);
		
		txtModulNr = new JTextField();
		txtModulNr.setFont(newStandFontPlain);
		txtModulNr.setBounds(107, 39, 122, 20);
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
						int semester;
						String selStr;
						try{
							modul.setModulNumber(Integer.parseInt(txtModulNr.getText()));
							selStr = new String(String.valueOf(cmBxSemester.getSelectedItem()));
							if(selStr.contains("SS")){
								String help = new String(selStr.replaceAll("SS", ""));
								semester = Integer.parseInt(help);
								lernzielnator.addModul(semester, false, modul);
							}else
							{//WS
								String help = new String(selStr.replaceAll("WS", ""));
								semester = Integer.parseInt(help);
								lernzielnator.addModul(semester, true, modul);
							}
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							AddModul.this.dispose();
						}
						catch (NumberFormatException  e1){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setFont(newStandFontPlain);
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setFont(newStandFontPlain);
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(contentPanel);
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
						AddModul.this.dispose();
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
	public AddModul(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, Modul modul, int semester) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Modul");
		setBounds(100, 100, 255, 110);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", 100), prefs.getInt("y", 100), prefs.getInt("w", 255), prefs.getInt("h", 110));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblModulnummer = new JLabel("Modulnummer:");
		lblModulnummer.setFont(newStandFontPlain);
		lblModulnummer.setBounds(10, 14, 87, 14);
		contentPanel.add(lblModulnummer);
		
		txtModulNr = new JTextField();
		txtModulNr.setFont(newStandFontPlain);
		txtModulNr.setBounds(107, 11, 122, 20);
		txtModulNr.setText("");
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
							lernzielnator.getSemester(semester).addSemesterModul(modul);
							mainWindow.updateTree();
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							AddModul.this.dispose();
						}
						catch (NumberFormatException  e1){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setFont(newStandFontPlain);
							pane.setMessage("Bitte Ziffern in das Textfeld eingeben!");
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setFont(newStandFontPlain);
						    dialogError.setLocationRelativeTo(contentPanel);
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
						AddModul.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
