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

import data.Lernzielnator;

import javax.swing.event.ListSelectionEvent;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.prefs.Preferences;

public class Instruction extends JFrame {
	private static final long serialVersionUID = -3036324155651516259L;
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/instructions");

	private JPanel contentPane;
	
	private String txtImportData = new String("Dieses Programm kann vom einzelnen Lernziel bis zum ganzen Semester alles importieren, was in einer .csv Datei gespeichert ist.\n"
			+ "Hierzu l\u00e4dst du zuerst das gew\u00fcnschte Modul (oder alle deines aktuellen Semesters) als Excel-Datei von der Lernzielplattform herunter (vergesse nicht den Ansichtsmodus auf \"Lernziele\" zu \u00e4ndern). "
			+ "Nun gehst du auf \"Speichern unter\" und w\u00e4hlst als Dateityp \"CSV\" aus.\n"
			+ "(Achte darauf, nicht den Eintrag mit \"MS-DOS\" zu nehmen).");
	
	private String txtShortcuts = new String("Praktisch! Schnell! Cool!\n"
			+ "[S] --> Speichern\n"
			+ "[i] --> Importieren\n"
			+ "[1] --> \"Ausarbeitung\" aktivieren/deaktivieren\n"
			+ "[2] --> \"Karteikarten\" aktivieren/deaktivieren\n"
			+ "[3] --> \"Lerngruppe\" aktivieren/deaktivieren\n"
			+ "[R] --> \"Relevant\" aktivieren/deaktivieren\n"
			+ "[T] --> \"Statistik\" anzeigen\n"
			+ "[F] --> Suchfunktion\n"
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
	
	private String txtMisc = new String("Woche/Modul 0:\n"
			+ "Nicht alle Veranstaltungen finden f\u00fcr alle Studenten des Semesters in der gleichen Woche statt. Auch gibt es Veranstaltungen, bei welchen die Woche nicht festgelegt ist. Wenn dies der Fall ist, legt das Programm die Veranstaltung in die Woche \"0\"."
			+ "Genauso geht das Programm vor, wenn es Module findet, welche keine Nummer haben.\n"
			+ "\n"
			+ "Der kleine wei\u00dfe Streigen im Pie-Chart:\n"
			+ "Da das Programm nicht rundet sondern immer einige Ziffern hinter dem Komma abschneidet, kommt man bei der Addition aller Zahlen in seinem sch\u00f6nen Tortendiagramm nie auf die vollen 100%.\n"
			+ "\n"
			+ "Heiko hat f\u00fcr seine ~5400 Zeilen Code zwei Tafeln Schokolade bekommen.");
		//TODO: Update Lines of code ^

	/**
	 * Create the frame.
	 */
	public Instruction(final JFrame frame, Lernzielnator lernzielnator) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setAlwaysOnTop(true);
		setBounds(100, 100, 500, 400);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 500), prefs.getInt("h", 400));
		
		Instruction.this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				prefs.putInt("x", getBounds().x);
				prefs.putInt("y", getBounds().y);
				prefs.putInt("w", getBounds().width);
				prefs.putInt("h", getBounds().height);
	            Instruction.this.dispose();
		    }
		});
		Instruction.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		String components[] = { "IMPORT", "LADEN", "SHORTCUTS", "EXPORT", "MISC."};
		contentPane.setLayout(new GridLayout(1, 1, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane);
		
		JTextArea txtInfo = new JTextArea();
		txtInfo.setFont(newStandFontPlain);
		txtInfo.setLineWrap(true);
		txtInfo.setWrapStyleWord(true);
		txtInfo.setMargin(new Insets(10,10,10,10));
		txtInfo.setText("Wilkommen im Hilfe-Men\u00FC.\r\nBitte w\u00E4hle links ein Thema aus, \u00FCber das du mehr erfahren m\u00F6chtest.\nWenn du neu hier bist, solltet du als Erstes Daten importieren");
		splitPane.setRightComponent(txtInfo);
		JList<Object> list = new JList<Object>(components);
		list.setFont(newStandFontPlain);
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
