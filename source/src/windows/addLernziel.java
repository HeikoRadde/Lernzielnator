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

import data.lernziel;
import data.lernzielnator;
import data.veranstaltung;
import util.LZ_Dimension;
import util.LZ_Kognitionsdimension;

import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addLernziel extends JDialog {
	private static final long serialVersionUID = 4034840577384463260L;
	private final JPanel contentPanel = new JPanel();
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
	public addLernziel(final JFrame frame, final mainWindow mainWindow, lernzielnator lernzielnator, veranstaltung veranstaltung, lernziel lernziel) {
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("neues Lernziel");
		setBounds(100, 100, 555, 320);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel textPanel = new JPanel();
		textPanel.setBounds(10, 11, 519, 155);
		contentPanel.add(textPanel);
		textPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtDescription = new JTextArea();
		txtDescription.setLineWrap(true);
		txtDescription.setText(lernziel.getLzDescription());
		txtDescription.setToolTipText("Die Beschreibung des Lernzieles\r\n");
		JScrollPane scrollDesc = new JScrollPane (txtDescription);
		scrollDesc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textPanel.add(scrollDesc);
		
		txtNotes = new JTextArea();
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
		chckbxMC.setBounds(6, 0, 97, 23);
		panelBottomLeft.add(chckbxMC);
		
		chckbxSMPP = new JCheckBox("SMPP");
		chckbxSMPP.setBounds(6, 26, 97, 23);
		panelBottomLeft.add(chckbxSMPP);
		
		chckbxOSCE = new JCheckBox("OSCE");
		chckbxOSCE.setBounds(6, 52, 97, 23);
		panelBottomLeft.add(chckbxOSCE);
		
		chckbxLerngruppe = new JCheckBox("Lerngruppe");
		chckbxLerngruppe.setBounds(101, 52, 152, 23);
		panelBottomLeft.add(chckbxLerngruppe);
		
		chckbxAusarbeitung = new JCheckBox("Ausarbeitung");
		chckbxAusarbeitung.setBounds(101, 26, 152, 23);
		panelBottomLeft.add(chckbxAusarbeitung);
		
		chckbxKarteikarten = new JCheckBox("Karteikarten");
		chckbxKarteikarten.setBounds(101, 0, 152, 23);
		panelBottomLeft.add(chckbxKarteikarten);
		
		JPanel panelBottomRight = new JPanel();
		panelBottom.add(panelBottomRight);
		panelBottomRight.setLayout(null);
		
		cmBxLzDimension = new JComboBox<Object>();
		cmBxLzDimension.setModel(new DefaultComboBoxModel<Object>(new String[] {"*none*", "Wissen/Kenntnisse(kognitiv)", "Fertigkeiten(psychomotorisch)", "Einstellungen(emotional/reflektiv)", "Mini-PA"}));
		cmBxLzDimension.setBounds(0, 0, 259, 20);
		panelBottomRight.add(cmBxLzDimension);
		
		cmBxLzKognitionsdimension = new JComboBox<Object>();
		cmBxLzKognitionsdimension.setModel(new DefaultComboBoxModel<Object>(new String[] {"*none*", "erinnern", "verstehen", "analysieren", "evaluieren", "erzeugen"}));
		cmBxLzKognitionsdimension.setBounds(0, 31, 259, 20);
		panelBottomRight.add(cmBxLzKognitionsdimension);
		
		chckbxNotRelevant = new JCheckBox("nicht relevant");
		chckbxNotRelevant.setToolTipText("Wird automatisch gesetzt, wenn LZ nicht f\u00FCr MC oder SMPP relevant ist.\r\nKann manuell gesetzt werden.\r\nNicht relevante LZ's werden im Fortschritt nicht mitgez\u00E4hlt.");
		chckbxNotRelevant.setBounds(0, 54, 253, 23);
		panelBottomRight.add(chckbxNotRelevant);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
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
							addLernziel.this.dispose();
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
							pane.setMessage("Bitte eine Beschreibung eingeben");
						    JDialog dialogError = pane.createDialog("Error");
						    //dialogError.setLocation(contentPanel.getLocation().x  + (contentPanel.getWidth()/2)  , contentPanel.getLocation().y + (contentPanel.getHeight()/2)   );
						    dialogError.setLocationRelativeTo(addLernziel.this);
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
						addLernziel.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
