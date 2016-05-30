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

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class About extends JDialog {

	private static final long serialVersionUID = 2763047195870497421L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/about");

	/**
	 * Create the dialog.
	 */
	
	private Boolean showsWarrantyOverview = new Boolean(true);
	
	public About(final JFrame frame, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 450, 300);//has to be called in order to properly place the window in the center of the mainWindow
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 450), prefs.getInt("h", 300));
		setTitle("about");
		setFont(newDialFont);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JTextArea infoTextArea;
		JButton okButton = new JButton("OK");
		okButton.setFont(newStandFontPlain);
		JButton btnbersicht = new JButton("\u00DCbersicht");
		btnbersicht.setFont(newStandFontPlain);
		btnbersicht.setVisible(false);
		JButton btnConditions = new JButton("Conditions");
		btnConditions.setFont(newStandFontPlain);
		btnConditions.setVisible(false);
		JButton btnWarranty = new JButton("Warranty");
		btnWarranty.setFont(newStandFontPlain);
		btnWarranty.setVisible(false);	
		
		//TODO aktualisiere Versionsnummer bei jedem Release
		String mainInfoText = new String("Lernzielnator\n"
				+ "Version 1.11\n"
				+ "\n"
				+ "Autor: Heiko Radde\n"
				+ "Texte: Sonja Radde\n"
				+ "\n"
				+ "Kontakt (Heiko Radde):\n"
				+ "s61717@beuth-hochschule.de");
		String LicenseInfo = new String("Lernzielnator  Copyright (C) 2016  Heiko Radde\n"
				+ "This program comes with ABSOLUTELY NO WARRANTY; for details click the button \"Warranty\".\n"
				+ "This is free software, and you are welcome to redistribute it under certain conditions; click the button \"Conditions\" for details.\n"
				+ "For the full text of the licence, click the \"Licence\" button.");
		
		StringBuilder builder = new StringBuilder();
		String line = new String();
		BufferedReader br = null;
		InputStream is = getClass().getResourceAsStream("/util/License.txt");
		InputStreamReader isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		try {
			line = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		try {
			while ((line = br.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String licenseText = new String(builder.toString());
		
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		infoTextArea = new JTextArea();
		infoTextArea.setFont(newStandFontPlain);
		JScrollPane scroll = new JScrollPane ( infoTextArea );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		infoTextArea.setEditable(false);
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setLineWrap(true);
		contentPanel.add(scroll);
		infoTextArea.setText(mainInfoText);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						prefs.putInt("x", getBounds().x);
						prefs.putInt("y", getBounds().y);
						prefs.putInt("w", getBounds().width);
						prefs.putInt("h", getBounds().height);
						About.this.dispose();
					}
				});
				{
					JButton btnLizenz = new JButton("Lizenz");
					btnLizenz.setFont(newStandFontPlain);
					btnLizenz.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							btnbersicht.setVisible(true);
							btnWarranty.setVisible(true);
							btnConditions.setVisible(true);
							if(showsWarrantyOverview){
								infoTextArea.setText(LicenseInfo);
							}
							else{
								infoTextArea.setText(licenseText);
							}
							showsWarrantyOverview = (!showsWarrantyOverview);
						}
					});
					{
						btnbersicht.setVisible(false);
						btnbersicht.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								infoTextArea.setText(mainInfoText);
								btnbersicht.setVisible(false);
								btnWarranty.setVisible(false);
								btnConditions.setVisible(false);
							}
						});
						buttonPane.add(btnbersicht);
					}
					{
						btnWarranty.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								infoTextArea.setText(licenseText);
								infoTextArea.setCaretPosition(infoTextArea.getDocument().getDefaultRootElement().getElement(205).getStartOffset());
							}
						});
						buttonPane.add(btnWarranty);
					}
					{
						btnConditions.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								infoTextArea.setText(licenseText);
								infoTextArea.setCaretPosition(infoTextArea.getDocument().getDefaultRootElement().getElement(25).getStartOffset());
							}
						});
						buttonPane.add(btnConditions);
					}
					buttonPane.add(btnLizenz);
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
