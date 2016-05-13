package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.JRadioButton;

public class LoadingSemesterSelection extends JDialog {

	private static final long serialVersionUID = -1535839380017428508L;
	private final JPanel contentPanel = new JPanel();
	
	private JTextArea textArea;
	
	private java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/loadingSemesterSelection");

	/**
	 * Create the dialog.
	 */
	public LoadingSemesterSelection(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, File file) {
		setTitle("Semester Selektion");
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 350, 300);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 350), prefs.getInt("h", 300));
		
		ButtonGroup buttonGroupSemester = new ButtonGroup();
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		JPanel contentPanelLeft = new JPanel();
		contentPanel.add(contentPanelLeft);
		contentPanelLeft.setLayout(new GridLayout(0, 1, 0, 0));
		JRadioButton rdbtnsem_1 = new JRadioButton("1.Sem");
		rdbtnsem_1.setFont(newStandFontPlain);
		rdbtnsem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		buttonGroupSemester.add(rdbtnsem_1);
		contentPanelLeft.add(rdbtnsem_1);
		JRadioButton rdbtnsem_2 = new JRadioButton("2.Sem");
		rdbtnsem_2.setFont(newStandFontPlain);
		rdbtnsem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		buttonGroupSemester.add(rdbtnsem_2);
		contentPanelLeft.add(rdbtnsem_2);
		JRadioButton rdbtnsem_3 = new JRadioButton("3.Sem");
		rdbtnsem_3.setFont(newStandFontPlain);
		rdbtnsem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		buttonGroupSemester.add(rdbtnsem_3);
		contentPanelLeft.add(rdbtnsem_3);
		JRadioButton rdbtnsem_4 = new JRadioButton("4.Sem");
		rdbtnsem_4.setFont(newStandFontPlain);
		rdbtnsem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n");
			}
		});
		buttonGroupSemester.add(rdbtnsem_4);
		contentPanelLeft.add(rdbtnsem_4);
		JRadioButton rdbtnsem_5 = new JRadioButton("5.Sem");
		rdbtnsem_5.setFont(newStandFontPlain);
		rdbtnsem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n"
						+ "Fertigkeiten  --> OSCE\n"
						+ "Mini-PA --> OSCE");
			}
		});
		buttonGroupSemester.add(rdbtnsem_5);
		contentPanelLeft.add(rdbtnsem_5);
		JRadioButton rdbtnsem_6 = new JRadioButton("6.Sem");
		rdbtnsem_6.setFont(newStandFontPlain);
		rdbtnsem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n");
			}
		});
		buttonGroupSemester.add(rdbtnsem_6);
		contentPanelLeft.add(rdbtnsem_6);
		JRadioButton rdbtnsem_7 = new JRadioButton("7.Sem");
		rdbtnsem_7.setFont(newStandFontPlain);
		rdbtnsem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n"
						+ "Fertigkeiten --> OSCE");
			}
		});
		buttonGroupSemester.add(rdbtnsem_7);
		contentPanelLeft.add(rdbtnsem_7);
		JRadioButton rdbtnsem_8 = new JRadioButton("8.Sem");
		rdbtnsem_8.setFont(newStandFontPlain);
		rdbtnsem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n"
						+ "Fertigkeiten --> OSCE");
			}
		});
		buttonGroupSemester.add(rdbtnsem_8);
		contentPanelLeft.add(rdbtnsem_8);
		JRadioButton rdbtnsem_9 = new JRadioButton("9.Sem");
		rdbtnsem_9.setFont(newStandFontPlain);
		rdbtnsem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(" Wissen/Kenntnisse --> MC\n"
						+ "Fertigkeiten --> OSCE");
			}
		});
		buttonGroupSemester.add(rdbtnsem_9);
		contentPanelLeft.add(rdbtnsem_9);
		
		JRadioButton rdbtnsem_0 = new JRadioButton("eigene Einstellungen");
		rdbtnsem_0.setFont(newStandFontPlain);
		rdbtnsem_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		rdbtnsem_0.setToolTipText("Selber die Zuordnung von Lernzieldimension zu Pr\u00FCfungsformaten setzen");
		buttonGroupSemester.add(rdbtnsem_0);
		contentPanelLeft.add(rdbtnsem_0);
		{
			JPanel contentPanelRight = new JPanel();
			contentPanel.add(contentPanelRight);
			contentPanelRight.setLayout(new GridLayout(0, 1, 0, 0));
			{
				textArea = new JTextArea();
				textArea.setFont(newStandFontPlain);
				textArea.setFont(newStandFontPlain);
				contentPanelRight.add(textArea);
			}
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
						ArrayList<Boolean> mc = new ArrayList<>();
						ArrayList<Boolean> smpp = new ArrayList<>();
						ArrayList<Boolean> osce = new ArrayList<>();
						if(rdbtnsem_0.isSelected() || rdbtnsem_1.isSelected() || rdbtnsem_2.isSelected() || rdbtnsem_3.isSelected()){
							SetPruefungLernzielkat window = new SetPruefungLernzielkat(frame, mainWindow, lernzielnator, file);
							window.setVisible(true);
							LoadingSemesterSelection.this.dispose();
						}
						else{
							if(rdbtnsem_4.isSelected()){
								mc.add(new Boolean(true));		//Wissen/Kenntnisse
								mc.add(new Boolean(false));		//Fertigkeiten
								mc.add(new Boolean(false));		//Einstellungen
								mc.add(new Boolean(false));		//Mini-PAs
								smpp.add(new Boolean(false));	//Wissen/Kenntnisse
								smpp.add(new Boolean(false));	//Fertigkeiten
								smpp.add(new Boolean(false));	//Einstellungen
								smpp.add(new Boolean(false));	//Mini-PAs
								osce.add(new Boolean(false));	//Wissen/Kenntnisse
								osce.add(new Boolean(true));	//Fertigkeiten
								osce.add(new Boolean(false));	//Einstellungen
								osce.add(new Boolean(true));	//Mini-PAs
							}
							else{
								if(rdbtnsem_5.isSelected() || rdbtnsem_6.isSelected()){
									mc.add(new Boolean(true));		//Wissen/Kenntnisse
									mc.add(new Boolean(false));		//Fertigkeiten
									mc.add(new Boolean(false));		//Einstellungen
									mc.add(new Boolean(false));		//Mini-PAs
									smpp.add(new Boolean(false));	//Wissen/Kenntnisse
									smpp.add(new Boolean(false));	//Fertigkeiten
									smpp.add(new Boolean(false));	//Einstellungen
									smpp.add(new Boolean(false));	//Mini-PAs
									osce.add(new Boolean(false));	//Wissen/Kenntnisse
									osce.add(new Boolean(false));	//Fertigkeiten
									osce.add(new Boolean(false));	//Einstellungen
									osce.add(new Boolean(false));	//Mini-PAs
								}
								else{
									if(rdbtnsem_7.isSelected() || rdbtnsem_8.isSelected() || rdbtnsem_9.isSelected()){
										mc.add(new Boolean(true));		//Wissen/Kenntnisse
										mc.add(new Boolean(false));		//Fertigkeiten
										mc.add(new Boolean(false));		//Einstellungen
										mc.add(new Boolean(false));		//Mini-PAs
										smpp.add(new Boolean(false));	//Wissen/Kenntnisse
										smpp.add(new Boolean(false));	//Fertigkeiten
										smpp.add(new Boolean(false));	//Einstellungen
										smpp.add(new Boolean(false));	//Mini-PAs
										osce.add(new Boolean(false));	//Wissen/Kenntnisse
										osce.add(new Boolean(true));	//Fertigkeiten
										osce.add(new Boolean(false));	//Einstellungen
										osce.add(new Boolean(false));	//Mini-PAs
									}
								}
							}
							lernzielnator.readFileUni7(file, mc, smpp, osce);
							prefs.putInt("x", getBounds().x);
							prefs.putInt("y", getBounds().y);
							prefs.putInt("w", getBounds().width);
							prefs.putInt("h", getBounds().height);
							LoadingSemesterSelection.this.dispose();
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
						LoadingSemesterSelection.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
