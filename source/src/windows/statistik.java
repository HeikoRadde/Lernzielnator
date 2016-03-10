package windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.gg.piechart.PieChart;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;




public class statistik extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			statistik dialog = new statistik();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public statistik(final JFrame frame, final mainWindow mainWindow,int semester, int module, int veranstaltungen, int lernziele, int rot, int gelb, int gruen, int grau, int MC, int SMPP, int OSCE, int karteikarten, int ausarbeitung, int lerngruppen, int notes, int relevant, Color ok, Color mittel, Color schlecht, Color neutral ) {
		setBounds(100, 100, 600, 383);
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
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						statistik.this.dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		String text = new String("Insgesamt " + Integer.toString(lernziele) + " Lernziele, davon:\n  "
				+Integer.toString(gruen) + " vollständig bearbeitet (grün)\n  " 
				+ Integer.toString(gelb) + " teilweise bearbeitet (gelb)\n  "
				+ Integer.toString(rot) + " nicht bearbeitet (rot)\n  "
				+ Integer.toString(grau) + " nicht relevant (grau)\n  "
				+ ", in " + Integer.toString(veranstaltungen) + " Veranstaltungen in\n  " + Integer.toString(module) + " Modulen in " + Integer.toString(semester) + " Semestern.\n"
				+ "Von den Lernzielen sind:\n  " 
				+ Integer.toString(MC) + " MC,\n  "
				+ Integer.toString(SMPP) + " SMPP,\n  "
				+ Integer.toString(OSCE) + " OSCE,\n  "
				+ Integer.toString(karteikarten) + " mit Kartiekarten,\n  "
				+ Integer.toString(ausarbeitung) + " mit Ausarbeitung,\n  "
				+ Integer.toString(lerngruppen) + " mit Lerngruppen,\n  "
				+ Integer.toString(notes) + " mit Notiz\n  und "
				+ Integer.toString(relevant) + " relevant");
		
		
		
		contentPanel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel textPanel = new JPanel();
		contentPanel.add(textPanel);
		textPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textPanel.add(textArea);
		textArea.setText(text);
		textArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (textArea);
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textPanel.add(scroll);
	          
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
