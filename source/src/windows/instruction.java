package windows;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class instruction extends JFrame {

	private JPanel contentPane;
	
	private String txtImportData = new String("Dieses Programm kann Lernziele, Veranstaltungen, Module und Semester importieren, wenn sie in einer .csv Datei gespeichert sind. "
			+ "Um Daten zu importieren m�ssen Sie also zun�chst eine.csv Datei erstellen.\n"
			+ "Hierzu laden Sie als erstes das Modul als Excel-Datei herunter. �ffnen Sie die Datei Excel oder einem anderen Programm zur Tabellenerstellung. "
			+ "Nun m�ssen Sie die Datei neu speichern. W�hlen Sie hierzu \"Speichern\" oder \"Speichern unter\" aus. "
			+ "Bei �lteren Excel-Versionen m�ssen Sie nun eventuell \"Andere Formate\" ausw�hlen."
			+ "Im nun erscheinenden Dialog w�hlen Sie unter Dateityp \"CSV\" aus. "
			+ "Achten Sie darauf, dass Sie nicht den Eintrag mit \"MS-DOS\" ausw�hlen, hier w�rden die Umlaute nicht korrekt gespeichert.\n"
			+ "Nun haben Sie die Tabelle im .csv-Format exportiert und k�nnen sie nun entweder �ber den Men�eintrag \"Laden\" laden oder �ber \"Importieren\" zum vorhandenen Datensatz hinzuf�gen.");
	
	private String txtShortcuts = new String("Dieses Programm unterst�tzt mehrere Shortcuts. "
			+ "Das bedeutet, dass Sie nicht immer �ber das Men� die Daten bearbeiten m�ssen."
			+ "Folgende Shortcuts besitzt dieses Programm:\n"
			+ "[S] --> Speichern\n"
			+ "[i] --> Importieren\n"
			+ "[K] --> \"Karteikarten\" aktivieren/deaktivieren\n"
			+ "[A] --> \"Ausarbeitung\" aktivieren/deaktivieren\n"
			+ "[L] --> \"Lerngruppe\" aktivieren/deaktivieren\n"
			+ "[R] --> \"Relevant\" aktivieren/deaktivieren");
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instruction frame = new instruction();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public instruction(final JFrame frame) {
		this.setLocationRelativeTo(frame);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		String components[] = { "Import von Daten", "Shortcuts"};
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane);
		
		JTextArea txtInfo = new JTextArea();
		//Font times = new Font("Times New Roman", Font.PLAIN, txtInfo.getFont().getSize());
		//txtInfo.setFont(times);
		txtInfo.setLineWrap(true);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setText("Wilkommen im Hilfe-Men�.\r\nBitte w\u00E4hlen Sie links ein Thema aus, \u00FCber das Sie mehr erfahren wollen.");
		splitPane.setRightComponent(txtInfo);
		JList list = new JList(components);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = list.getSelectedIndex();
				switch(index){
				case 0:
					txtInfo.setText(txtImportData);
					break;
				case 1:
					txtInfo.setText(txtShortcuts);
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		splitPane.setLeftComponent(list);
	}

}
