package windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.BackingStoreException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import data.Lernzielnator;
import java.awt.GridLayout;

public class Preferences extends JDialog {

	private static final long serialVersionUID = -6034966964720998868L;
	private final JPanel contentPanel = new JPanel();
	private java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/preferences");
	private JTextField txtFontSize;
	JLabel lblSchriftgre;
	JButton btnSchrWdh;
	JButton btnFenstWdh;
	JButton btnAllWdh;
	JButton okButton;
	JButton cancelButton;

	/**
	 * Create the dialog.
	 */
	public Preferences(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 300, 210);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 300), prefs.getInt("h", 210));
		setTitle("Einstellungen");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		btnSchrWdh = new JButton("Urspr\u00FCngliche Schriftgr\u00F6\u00DFe wiederherstellen");
		btnSchrWdh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				schriftWdh(mainWindow, lernzielnator);
			}
		});
		contentPanel.setLayout(new GridLayout(4, 1, 5, 5));
		
		JPanel contentPanelTop = new JPanel();
		contentPanel.add(contentPanelTop);
		contentPanelTop.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel contentPanelTopL = new JPanel();
		contentPanelTop.add(contentPanelTopL);
		contentPanelTopL.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblSchriftgre = new JLabel("Schriftgr\u00F6\u00DFe");
		contentPanelTopL.add(lblSchriftgre);
		lblSchriftgre.setFont(newStandFontPlain);
		
		JPanel contentPanelTopR = new JPanel();
		contentPanelTop.add(contentPanelTopR);
		contentPanelTopR.setLayout(new GridLayout(0, 1, 0, 0));
		
		txtFontSize = new JTextField();
		contentPanelTopR.add(txtFontSize);
		txtFontSize.setFont(newStandFontPlain);
		txtFontSize.setColumns(10);
		contentPanel.add(btnSchrWdh);
		
		btnFenstWdh = new JButton("Urspr\u00FCngliche Fenstergr\u00F6\u00DFen wiederherstellen");
		btnFenstWdh.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				FensterWdh(frame, mainWindow, lernzielnator);
			}
		});
		contentPanel.add(btnFenstWdh);
		
		btnAllWdh = new JButton("Alles wiederherstellen");
		btnAllWdh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				schriftWdh(mainWindow, lernzielnator);
				FensterWdh(frame, mainWindow, lernzielnator);
			}
		});
		contentPanel.add(btnAllWdh);
		setLocationRelativeTo(frame);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setFont(newStandFontPlain);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							lernzielnator.setFontSize(Integer.parseInt(txtFontSize.getText()));
							mainWindow.updateFontSize();
						}catch (NumberFormatException e){
							
						}
						prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/preferences");
						prefs.putInt("x", getBounds().x);
						prefs.putInt("y", getBounds().y);
						prefs.putInt("w", getBounds().width);
						prefs.putInt("h", getBounds().height);
						Preferences.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setFont(newStandFontPlain);
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Preferences.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	private void schriftWdh(final MainWindow mainWindow, Lernzielnator lernzielnator){
		lernzielnator.setFontSize(12);
		mainWindow.updateFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, 12);
		Font newDialFont = new Font("Dialog", Font.PLAIN, 12);
		setFont(newDialFont);
		txtFontSize.setFont(newStandFontPlain);
		lblSchriftgre.setFont(newStandFontPlain);
		btnSchrWdh.setFont(newStandFontPlain);
		btnFenstWdh.setFont(newStandFontPlain);
		btnAllWdh.setFont(newStandFontPlain);
		okButton.setFont(newStandFontPlain);
		cancelButton.setFont(newStandFontPlain);
	}
	
	private void FensterWdh(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator){
		setBounds(100, 100, 300, 210);
		setLocationRelativeTo(frame);
		mainWindow.getFrmLernzielnator().setBounds(100, 100, 650, 550);
		java.util.prefs.Preferences del = java.util.prefs.Preferences.userRoot().node("lernzielnator");
		try {
			del.removeNode();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
}
