package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;

import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Search extends JDialog {

	private static final long serialVersionUID = -7487579675500724068L;
	private final JPanel contentPanel = new JPanel();
	private java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/search");
	private JCheckBox ckbxVeranstaltungstitel;
	private JCheckBox ckbxLernzielbeschreibung;
	private JCheckBox ckbxLernzielnotiz;
	private JCheckBox ckbxAlleTexte;
	private JTextArea txtSearch;
	private JCheckBox chckbxGrokleinschreibungBeachten;
	
	/**
	 * Create the dialog.
	 */
	public Search(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 440, 215);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 440), prefs.getInt("h", 215));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JPanel contentPanelLeft = new JPanel();
			contentPanel.add(contentPanelLeft);
			contentPanelLeft.setLayout(new GridLayout(0, 1, 0, 0));
			ckbxVeranstaltungstitel = new JCheckBox("Veranstaltungstitel");
			ckbxVeranstaltungstitel.setFont(newStandFontPlain);
			contentPanelLeft.add(ckbxVeranstaltungstitel);
			ckbxLernzielbeschreibung = new JCheckBox("Lernzielbeschreibung");
			ckbxLernzielbeschreibung.setFont(newStandFontPlain);
			contentPanelLeft.add(ckbxLernzielbeschreibung);
			ckbxLernzielnotiz = new JCheckBox("Lernzielnotiz");
			ckbxLernzielnotiz.setFont(newStandFontPlain);
			contentPanelLeft.add(ckbxLernzielnotiz);
			ckbxAlleTexte = new JCheckBox("alle Texte");
			ckbxAlleTexte.setFont(newStandFontPlain);
			contentPanelLeft.add(ckbxAlleTexte);
			chckbxGrokleinschreibungBeachten = new JCheckBox("Gro\u00DF-/Kleinschreibung beachten");
			chckbxGrokleinschreibungBeachten.setSelected(true);
			chckbxGrokleinschreibungBeachten.setFont(newStandFontPlain);
			contentPanelLeft.add(chckbxGrokleinschreibungBeachten);
		}
		{
			JPanel contentPanelRight = new JPanel();
			contentPanel.add(contentPanelRight);
			contentPanelRight.setLayout(new GridLayout(0, 1, 0, 0));
			txtSearch = new JTextArea();
			txtSearch.setFont(newStandFontPlain);
			contentPanelRight.add(txtSearch);
		}
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
						if(!txtSearch.getText().isEmpty()){
							if(ckbxVeranstaltungstitel.isSelected() || ckbxLernzielbeschreibung.isSelected() || ckbxLernzielnotiz.isSelected() || ckbxAlleTexte.isSelected()){
								Search.this.dispose();
								if(ckbxAlleTexte.isSelected()){
									lernzielnator.search(txtSearch.getText(), true, true, true, chckbxGrokleinschreibungBeachten.isSelected());
								}
								else{
									lernzielnator.search(txtSearch.getText(), ckbxVeranstaltungstitel.isSelected(), ckbxLernzielbeschreibung.isSelected(), ckbxLernzielnotiz.isSelected(), chckbxGrokleinschreibungBeachten.isSelected());
								}
								prefs.putInt("x", getBounds().x);
								prefs.putInt("y", getBounds().y);
								prefs.putInt("w", getBounds().width);
								prefs.putInt("h", getBounds().height);
							}
							else{
								error = 2;
							}
						}
						else{
							error = 1;
						}
						if(error != -1){
							//Display Error Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.ERROR_MESSAGE );
							pane.setFont(newStandFontPlain);
							switch(error){
							case 1:
								pane.setMessage("Bitte einen Text eingeben!");
								break;
							case 2:
								pane.setMessage("Bitte mind. ein Suchkriterium auswählen!");
								break;
							}
						    JDialog dialogError = pane.createDialog("Error");
						    dialogError.setFont(newStandFontPlain);
						    dialogError.setLocationRelativeTo(Search.this);
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
						Search.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
