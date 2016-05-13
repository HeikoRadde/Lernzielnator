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

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class SetPruefungLernzielkat extends JDialog {

	private static final long serialVersionUID = -5041574036055955568L;

	private final JPanel contentPanel = new JPanel();

	private JCheckBox chckbxKeineMC;
	private JCheckBox chckbxWissenkenntnisseMC;
	private JCheckBox chckbxFertigkeitenMC;
	private JCheckBox chckbxEinstellungenMC;
	private JCheckBox chckbxMinipaMC;
	private JCheckBox chckbxKeineSMPP;
	private JCheckBox chckbxWissenkenntnisseSMPP;
	private JCheckBox chckbxFertigkeitenSMPP;
	private JCheckBox chckbxEinstellungenSMPP;
	private JCheckBox chckbxMinipaSMPP;
	private JCheckBox chckbxKeineOSCE;
	private JCheckBox chckbxWissenkenntnisseOSCE;
	private JCheckBox chckbxFertigkeitenOSCE;
	private JCheckBox chckbxEinstellungenOSCE;
	private JCheckBox chckbxMinipaOSCE;
	
	private java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/setpruefunglerzielkat");
	
	/**
	 * Create the dialog.
	 */
	public SetPruefungLernzielkat(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, File file) {
		setTitle("Zuordnung LZ-Dimension zu Pr\u00FCfungsformaten");
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 440, 300);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 440), prefs.getInt("h", 300));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 3, 0, 0));
		JPanel panelMC = new JPanel();
		contentPanel.add(panelMC);
		panelMC.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel lblMc = new JLabel("MC");
		lblMc.setFont(newStandFontPlain);
		panelMC.add(lblMc);
		chckbxKeineMC = new JCheckBox("keine");
		chckbxKeineMC.setFont(newStandFontPlain);
		chckbxKeineMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxKeineMC.isSelected()){
					chckbxWissenkenntnisseMC.setSelected(false);
					chckbxFertigkeitenMC.setSelected(false);
					chckbxEinstellungenMC.setSelected(false);
					chckbxMinipaMC.setSelected(false);
				}
			}
		});
		chckbxKeineMC.setSelected(true);
		panelMC.add(chckbxKeineMC);
		chckbxWissenkenntnisseMC = new JCheckBox("Wissen/Kenntnisse");
		chckbxWissenkenntnisseMC.setFont(newStandFontPlain);
		chckbxWissenkenntnisseMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxWissenkenntnisseMC.isSelected()){
					chckbxKeineMC.setSelected(false);
				}
			}
		});
		panelMC.add(chckbxWissenkenntnisseMC);
		chckbxFertigkeitenMC = new JCheckBox("Fertigkeiten");
		chckbxFertigkeitenMC.setFont(newStandFontPlain);
		chckbxFertigkeitenMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFertigkeitenMC.isSelected()){
					chckbxKeineMC.setSelected(false);
				}
			}
		});
		panelMC.add(chckbxFertigkeitenMC);
		chckbxEinstellungenMC = new JCheckBox("Einstellungen");
		chckbxEinstellungenMC.setFont(newStandFontPlain);
		chckbxEinstellungenMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxEinstellungenMC.isSelected()){
					chckbxKeineMC.setSelected(false);
				}
			}
		});
		panelMC.add(chckbxEinstellungenMC);
		chckbxMinipaMC = new JCheckBox("Mini-PA");
		chckbxMinipaMC.setFont(newStandFontPlain);
		chckbxMinipaMC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxMinipaMC.isSelected()){
					chckbxKeineMC.setSelected(false);
				}
			}
		});
		panelMC.add(chckbxMinipaMC);
		JPanel panelSMPP = new JPanel();
		contentPanel.add(panelSMPP);
		panelSMPP.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel lblSmpp = new JLabel("SMPP");
		lblSmpp.setFont(newStandFontPlain);
		panelSMPP.add(lblSmpp);
		
		chckbxKeineSMPP = new JCheckBox("keine");
		chckbxKeineSMPP.setFont(newStandFontPlain);
		chckbxKeineSMPP.setSelected(true);
		chckbxKeineSMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxKeineSMPP.isSelected()){
					chckbxWissenkenntnisseSMPP.setSelected(false);
					chckbxFertigkeitenSMPP.setSelected(false);
					chckbxEinstellungenSMPP.setSelected(false);
					chckbxMinipaSMPP.setSelected(false);
				}
			}
		});
		panelSMPP.add(chckbxKeineSMPP);
		
		chckbxWissenkenntnisseSMPP = new JCheckBox("Wissen/Kenntnisse");
		chckbxWissenkenntnisseSMPP.setFont(newStandFontPlain);
		chckbxWissenkenntnisseSMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxWissenkenntnisseSMPP.isSelected()){
					chckbxKeineSMPP.setSelected(false);
				}
			}
		});
		panelSMPP.add(chckbxWissenkenntnisseSMPP);
		
		chckbxFertigkeitenSMPP = new JCheckBox("Fertigkeiten");
		chckbxFertigkeitenSMPP.setFont(newStandFontPlain);
		chckbxFertigkeitenSMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFertigkeitenSMPP.isSelected()){
					chckbxKeineSMPP.setSelected(false);
				}
			}
		});
		panelSMPP.add(chckbxFertigkeitenSMPP);
		
		chckbxEinstellungenSMPP = new JCheckBox("Einstellungen");
		chckbxEinstellungenSMPP.setFont(newStandFontPlain);
		chckbxEinstellungenSMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxEinstellungenSMPP.isSelected()){
					chckbxKeineSMPP.setSelected(false);
				}
			}
		});
		panelSMPP.add(chckbxEinstellungenSMPP);
		
		chckbxMinipaSMPP = new JCheckBox("Mini-PA");
		chckbxMinipaSMPP.setFont(newStandFontPlain);
		chckbxMinipaSMPP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxMinipaSMPP.isSelected()){
					chckbxKeineSMPP.setSelected(false);
				}
			}
		});
		panelSMPP.add(chckbxMinipaSMPP);
		JPanel panelOSCE = new JPanel();
		contentPanel.add(panelOSCE);
		panelOSCE.setLayout(new GridLayout(0, 1, 0, 0));
		JLabel lblOsce = new JLabel("OSCE");
		lblOsce.setFont(newStandFontPlain);
		panelOSCE.add(lblOsce);
		
		chckbxKeineOSCE = new JCheckBox("keine");
		chckbxKeineOSCE.setFont(newStandFontPlain);
		chckbxKeineOSCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxKeineOSCE.isSelected()){
					chckbxWissenkenntnisseOSCE.setSelected(false);
					chckbxFertigkeitenOSCE.setSelected(false);
					chckbxEinstellungenOSCE.setSelected(false);
					chckbxMinipaOSCE.setSelected(false);
				}
			}
		});
		chckbxKeineOSCE.setSelected(true);
		panelOSCE.add(chckbxKeineOSCE);
		
		chckbxWissenkenntnisseOSCE = new JCheckBox("Wissen/Kenntnisse");
		chckbxWissenkenntnisseOSCE.setFont(newStandFontPlain);
		chckbxWissenkenntnisseOSCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxWissenkenntnisseOSCE.isSelected()){
					chckbxKeineOSCE.setSelected(false);
				}
			}
		});
		panelOSCE.add(chckbxWissenkenntnisseOSCE);
		
		chckbxFertigkeitenOSCE = new JCheckBox("Fertigkeiten");
		chckbxFertigkeitenOSCE.setFont(newStandFontPlain);
		chckbxFertigkeitenOSCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxFertigkeitenOSCE.isSelected()){
					chckbxKeineOSCE.setSelected(false);
				}
			}
		});
		panelOSCE.add(chckbxFertigkeitenOSCE);
		
		chckbxEinstellungenOSCE = new JCheckBox("Einstellungen");
		chckbxEinstellungenOSCE.setFont(newStandFontPlain);
		chckbxEinstellungenOSCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxEinstellungenOSCE.isSelected()){
					chckbxKeineOSCE.setSelected(false);
				}
			}
		});
		panelOSCE.add(chckbxEinstellungenOSCE);
		
		chckbxMinipaOSCE = new JCheckBox("Mini-PA");
		chckbxMinipaOSCE.setFont(newStandFontPlain);
		chckbxMinipaOSCE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxMinipaOSCE.isSelected()){
					chckbxKeineOSCE.setSelected(false);
				}
			}
		});
		panelOSCE.add(chckbxMinipaOSCE);
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
						if(chckbxKeineMC.isSelected()){
							mc.add(new Boolean(false));
							mc.add(new Boolean(false));
							mc.add(new Boolean(false));
							mc.add(new Boolean(false));
						}
						else{
							if(chckbxWissenkenntnisseMC.isSelected()){
								mc.add(new Boolean(true));
							}
							else{
								mc.add(new Boolean(false));
							}
							if(chckbxFertigkeitenMC.isSelected()){
								mc.add(new Boolean(true));
							}
							else{
								mc.add(new Boolean(false));
							}
							if(chckbxEinstellungenMC.isSelected()){
								mc.add(new Boolean(true));
							}
							else{
								mc.add(new Boolean(false));
							}
							if(chckbxMinipaMC.isSelected()){
								mc.add(new Boolean(true));
							}
							else{
								mc.add(new Boolean(false));
							}
						}

						ArrayList<Boolean> smpp = new ArrayList<>();
						if(chckbxKeineSMPP.isSelected()){
							smpp.add(new Boolean(false));
							smpp.add(new Boolean(false));
							smpp.add(new Boolean(false));
							smpp.add(new Boolean(false));
						}
						else{
							if(chckbxWissenkenntnisseSMPP.isSelected()){
								smpp.add(new Boolean(true));
							}
							else{
								smpp.add(new Boolean(false));
							}
							if(chckbxFertigkeitenSMPP.isSelected()){
								smpp.add(new Boolean(true));
							}
							else{
								smpp.add(new Boolean(false));
							}
							if(chckbxEinstellungenSMPP.isSelected()){
								smpp.add(new Boolean(true));
							}
							else{
								smpp.add(new Boolean(false));
							}
							if(chckbxMinipaSMPP.isSelected()){
								smpp.add(new Boolean(true));
							}
							else{
								smpp.add(new Boolean(false));
							}
						}

						ArrayList<Boolean>osce = new ArrayList<>();
						if(chckbxKeineOSCE.isSelected()){
							osce.add(new Boolean(false));
							osce.add(new Boolean(false));
							osce.add(new Boolean(false));
							osce.add(new Boolean(false));
						}
						else{
							if(chckbxWissenkenntnisseOSCE.isSelected()){
								osce.add(new Boolean(true));
							}
							else{
								osce.add(new Boolean(false));
							}
							if(chckbxFertigkeitenOSCE.isSelected()){
								osce.add(new Boolean(true));
							}
							else{
								osce.add(new Boolean(false));
							}
							if(chckbxEinstellungenOSCE.isSelected()){
								osce.add(new Boolean(true));
							}
							else{
								osce.add(new Boolean(false));
							}
							if(chckbxMinipaOSCE.isSelected()){
								osce.add(new Boolean(true));
							}
							else{
								osce.add(new Boolean(false));
							}
						}
						lernzielnator.readFileUni7(file, mc, smpp, osce);
						prefs.putInt("x", getBounds().x);
						prefs.putInt("y", getBounds().y);
						prefs.putInt("w", getBounds().width);
						prefs.putInt("h", getBounds().height);
						SetPruefungLernzielkat.this.dispose();
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
						SetPruefungLernzielkat.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
