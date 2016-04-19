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
			+ "Hierzu lädst du zuerst das gew\u00fcnschte Modul als Excel-Datei von der Lernzielplattform herunter. "
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
			+ "Daf\u00fcr will diese Datei dann auch nicht von dir gel\u00f6scht werden. "
			+ "Sondern jedes Mal wieder geladen.");
	
	private String txtExport = new String("M\u00f6chtest du von einzelnen Semestern/Modulen deinen Lernfortschritt, "
			+ "deine Notizen oder selbst erstellten Lernziele mit deinen Kommilitonen teilen, kannst du sie als .csv exportieren.");
	
	private String txtMisc = new String("Der kleine wei\u00dfe Streigen im Pie-Chart:\n"
			+ "Da das Programm nicht rundet sondern immer einige Ziffern hinter dem Komma abschneidet, kommt man bei der Addition aller Zahlen in seinem sch\u00f6nen Tortendiagramm nie auf die vollen 100%.\n"
			+ "\n"
			+ "Heiko hat f\u00fcr seine ~5700 Zeilen Code zwei Tafeln Schokolade bekommen.");
		//TODO: Update Lines of code ^

	/**
	 * Create the frame.
	 */
	public instruction(final JFrame frame) {
		this.setLocationRelativeTo(frame);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(frame);
		
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
