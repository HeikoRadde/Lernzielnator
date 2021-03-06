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
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import com.gg.piechart.PieChart;

import data.Lernzielnator;




public class Statistik extends JDialog {

	private static final long serialVersionUID = -2926036103319280452L;
	private final JPanel contentPanel = new JPanel();
	private Preferences prefs = Preferences.userRoot().node("lernzielnator/preferences/statistik");

	/**
	 * Create the dialog.
	 */
	public Statistik(final JFrame frame, final MainWindow mainWindow, Lernzielnator lernzielnator, int semester, int module, int veranstaltungen, int lernziele, int rot, int gelb, int gruen, int grau, int MC, int SMPP, int OSCE, int karteikarten, int ausarbeitung, int lerngruppen, int notes, int relevant, Color ok, Color mittel, Color schlecht, Color neutral ) {
		int fontSize = lernzielnator.getFontSize();
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		setFont(newDialFont);
		setBounds(100, 100, 600, 380);
		setLocationRelativeTo(frame);
		setBounds(prefs.getInt("x", getX()), prefs.getInt("y", getY()), prefs.getInt("w", 600), prefs.getInt("h", 380));
		getContentPane().setLayout(new BorderLayout());
		setTitle("Statistik");
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(newStandFontPlain);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						prefs.putInt("x", getBounds().x);
						prefs.putInt("y", getBounds().y);
						prefs.putInt("w", getBounds().width);
						prefs.putInt("h", getBounds().height);
						Statistik.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		String text = new String("Insgesamt " + Integer.toString(lernziele) + " Lernziele, davon:\n  "
				+Integer.toString(gruen) + " vollst\u00e4ndig bearbeitet (gr\u00fcn)\n  " 
				+ Integer.toString(gelb) + " teilweise bearbeitet (gelb)\n  "
				+ Integer.toString(rot) + " nicht bearbeitet (rot)\n  "
				+ Integer.toString(grau) + " nicht relevant (grau),\n  "
				+ " in " + Integer.toString(veranstaltungen) + " Veranstaltungen in\n  " + Integer.toString(module) + " Modulen in " + Integer.toString(semester) + " Semestern.\n"
				+ "\n"
				+ "Von den Lernzielen sind:\n  " 
				+ Integer.toString(MC) + " MC,\n  "
				+ Integer.toString(SMPP) + " SMPP,\n  "
				+ Integer.toString(OSCE) + " OSCE,\n  "
				+ Integer.toString(karteikarten) + " mit Kartiekarten,\n  "
				+ Integer.toString(ausarbeitung) + " mit Ausarbeitung,\n  "
				+ Integer.toString(lerngruppen) + " mit Lerngruppen,\n  "
				+ Integer.toString(notes) + " mit Notizen\n  und "
				+ Integer.toString(relevant) + " relevant");
		
		
		
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel textPanel = new JPanel();
		contentPanel.add(textPanel);
		textPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(newStandFontPlain);
		textArea.setLineWrap(true);
		textPanel.add(textArea);
		textArea.setText(text);
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (textArea);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textPanel.add(scroll);
	          
	    //TODO: fix white stripe (due to inaccuracy)
		JPanel piePanel = new JPanel();
		ArrayList<Double> values = new ArrayList<Double>();		
		values.add(new Double(((double)gruen*100)/(double)lernziele));
		values.add(new Double(((double)gelb*100)/(double)lernziele));
		values.add(new Double(((double)rot*100)/(double)lernziele));
		values.add(new Double(((double)grau*100)/(double)lernziele));
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(ok);
		colors.add(mittel);
		colors.add(schlecht);
		colors.add(neutral);
		PieChart pieChart = new PieChart(values, colors);
		piePanel.add(pieChart);
		pieChart.setLayout(new GridLayout(0, 1, 0, 0));
		piePanel.setLayout(new GridLayout(0, 1, 0, 0));
		contentPanel.add(piePanel);
		
		
    }
}
