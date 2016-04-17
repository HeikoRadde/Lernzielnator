package windows;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.GridLayout;
import java.awt.Insets;

public class instruction extends JFrame {
	private static final long serialVersionUID = -3036324155651516259L;

	private JPanel contentPane;
	
	private String txtImportData = new String("Dieses Programm kann vom einzelnen Lernziel bis zum ganzen Semester alles importieren, was in einer .csv Datei gespeichert ist.\n"
			+ "Hierzu lädst du zuerst das gewünschte Modul als Excel-Datei von der Lernzielplattform herunter. "
			+ "Nun gehst du auf „Speichern unter“ und wählst als Dateityp „CSV“ aus.\n"
			+ "(Achte darauf, nicht den Eintrag mit „MS-DOS“ zu nehmen).");
	
	private String txtShortcuts = new String("Praktisch! Schnell! Cool!\n"
			+ "[S] --> Speichern\n"
			+ "[i] --> Importieren\n"
			+ "[1] --> \"Ausarbeitung\" aktivieren/deaktivieren\n"
			+ "[2] --> \"Karteikarten\" aktivieren/deaktivieren\n"
			+ "[3] --> \"Lerngruppe\" aktivieren/deaktivieren\n"
			+ "[R] --> \"Relevant\" aktivieren/deaktivieren\n"
			+ "[T] --> \"Statistik\" anzeigen\n"
			+ "[U] --> Eintrag um 1 nach oben verschieben\n"
			+ "[J] --> Eintrag um 1 nach unten verschieben\n"
			+ "\n"
			+ "[Entf] --> Eintrag entfernen\n"
			+ "[<--]   --> Eintrag entfernen\n");
	
	private String txtLoadData = new String("Am Einfachsten speicherst du alles immer in derselben .csv Datei, die du nennen kannst, wie du willst.\n"
			+ "Dafür will diese Datei dann auch nicht von dir gelöscht werden. "
			+ "Sondern jedes Mal wieder geladen.");
	
	private String txtExport = new String("Möchtest du von einzelnen Semestern/Modulen deinen Lernfortschritt, "
			+ "deine Notizen oder selbst erstellten Lernziele mit deinen Kommilitonen teilen, kannst du sie als .csv exportieren.");
	
	private String txtMisc = new String("Der kleine weiße Streigen im Pie-Chart:\n"
			+ "Da das Programm nicht rundet sondern immer einige Ziffern hinter dem Komma abschneidet, kommt man bei der Addition aller Zahlen in seinem schönen Tortendiagramm nie auf die vollen 100%.\n"
			+ "\n"
			+ "Heiko hat für seine ~5600 Zeilen Code zwei Tafeln Schokolade bekommen.");
		//TODO: Update Lines of code ^
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		String components[] = { "IMPORT", "LADEN", "SHORTCUTS", "EXPORT", "MISC."};
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane);
		
		JTextArea txtInfo = new JTextArea();
		//Font times = new Font("Times New Roman", Font.PLAIN, txtInfo.getFont().getSize());
		//txtInfo.setFont(times);
		txtInfo.setLineWrap(true);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setMargin(new Insets(10,10,10,10));
		txtInfo.setText("Wilkommen im Hilfe-Men\u00FC.\r\nBitte w\u00E4hle links ein Thema aus, \u00FCber das du mehr erfahren m\u00F6chtest.\nWenn du neu hier bist, solltet du als Erstes Daten importieren");
		splitPane.setRightComponent(txtInfo);
		JList<Object> list = new JList<Object>(components);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = list.getSelectedIndex();
				switch(index){
				case 0:
					txtInfo.setText(txtImportData);
					break;
				case 1:
					txtInfo.setText(txtLoadData);
					break;
				case 2:
					txtInfo.setText(txtShortcuts);
					break;
				case 3:
					txtInfo.setText(txtExport);
					break;
				case 4:
					txtInfo.setText(txtMisc);
					break;
				}
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		splitPane.setLeftComponent(list);
	}

	
	
	
}
