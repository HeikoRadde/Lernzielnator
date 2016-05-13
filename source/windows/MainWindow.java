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


/*
Zeichen 	Unicode
------------------------------
�, � 		\u00c4, \u00e4
�, � 		\u00d6, \u00f6
�, � 		\u00dc, \u00fc
� 			\u00df
 */

package windows;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import util.CustomDefaultMutableTreeNode;
import util.CustomJTree;
import util.MyTreeCellRenderer;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import data.Lernziel;
import data.Lernzielnator;
import data.Modul;
import data.Semester;
import data.Veranstaltung;
import util.CustomFileFilter;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 3588379553001585268L;
	private Lernzielnator aktLernzielnator;
	private java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/mainWindow");
	
	private Color ok = Color.GREEN;
	private Color mittel = Color.ORANGE;
	private Color schlecht = Color.RED;
	private Color neutral = Color.GRAY;
	
	private boolean showRed = true;
	private boolean showYellow = true;
	private boolean showGreen = true;
	private boolean showGrey = true;
	private boolean showLerngruppe = true;
	private boolean showEmptyNodes = true;	
	
	public Boolean changed = new Boolean(false);
	public CustomDefaultMutableTreeNode aktNode;
	private JPopupMenu popup = new JPopupMenu();
	
	private static JFrame frmLernzielnator;
	private CustomJTree tree;
	CustomDefaultMutableTreeNode top;
	private JTextArea textField_Description;
	private JTextArea textField_Notes;
	private JTextArea textField_LzDimension;
	private JTextArea textField_LzKognitionsdimension;
	private JCheckBox chckbxMc;
	private JCheckBox chckbxSmpp;
	private JCheckBox chckbxOsce;
	private JCheckBox chckbxRelevant;
	private JCheckBox chckbxLerngruppe;
	private JCheckBox chckbxKarteikarten;
	private JCheckBox chckbxAusarbeitung;
	private JCheckBoxMenuItem chckbxmntmAlleLernziele;
	private JCheckBoxMenuItem chckbxmntmRoteLernziele;
	private JCheckBoxMenuItem chckbxmntmGelbeLernziele;
	private JCheckBoxMenuItem chckbxmntmGrneLernziele;
	private JCheckBoxMenuItem chckbxmntmGraueLernziele;
	private JCheckBoxMenuItem chckbxmntmLernzieleMitLerngruppen;
	private JCheckBoxMenuItem chckbxmntmLeereNodes;
	private JMenuItem popUpNew;
	private JMenuItem popUpEdit;
	private JMenuItem popUpDelete;
	private JMenuItem popUpUp;
	private JMenuItem popUpDown;
	private JMenuItem popUpSort;
	private JMenuBar menuBar;
	private JMenu mnData;
	private JMenuItem mntmLaden;
	private JMenuItem mntmSpeichern;
	private JMenu mnExportieren;
	private JMenuItem mntmSemester_1;
	private JMenuItem mntmModul_1;
	private JMenuItem mntmVeranstaltung_1;
	private JMenuItem mntmLernziel_1;
	private JMenuItem mntmImportieren;
	private JMenuItem mntmSuchen;
	private JMenu mnEdit;
	private JMenuItem mntmLsche;
	private JMenuItem mntmEditiere;
	private JMenuItem mntmUp;
	private JMenuItem mntmDown;
	private JMenuItem mntmSort;
	private JMenu mnNeu;
	private JMenuItem mntmSemester;
	private JMenuItem mntmModul;
	private JMenuItem mntmVeranstaltung;
	private JMenuItem mntmLernziel;
	private JMenu mnAnsicht;
	private JMenuItem mntmAnzeigen;
	private JMenuItem mntmCollapsNodes;
	private JMenuItem mntmExpandNodes;
	private JMenu mnQuestion;
	private JMenuItem mntmAbout;
	private JMenuItem mntmStatistik;
	private JMenuItem mntmAnleitung;
	private JMenuItem mntmEinstellungen;
	
	/**
	 * Create the application.
	 */
	public MainWindow(Lernzielnator lernzielnator) {
		aktLernzielnator = lernzielnator;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrmLernzielnator(new JFrame());
		getFrmLernzielnator().addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==83){	//key: S --> Save
				      if(aktLernzielnator.getAktFile() != ""){
				    	  if(aktLernzielnator.getSemesterListeSize() > 0){
				    		  try{
				    			  File fileToBeSaved = new File(aktLernzielnator.getAktFile());
				    			  aktLernzielnator.saveFile(fileToBeSaved);
				    		  }
				    		  catch(NullPointerException e1){
				    			  
				    		  }
				    	  }
				    	  else{
				    		JOptionPane errorPane = new JOptionPane(null);
				    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
				    		errorPane.setMessage("Keine Daten zum Speichern vorhanden.");
				    		JDialog dialogError = errorPane.createDialog("Error");
				    		//Display Error Message					
						    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
						    dialogError.setVisible(true);
				    	  }
				      }
				      else{
				    	  if(aktLernzielnator.getSemesterListeSize() > 0){
								final JFileChooser fc = new JFileChooser();
								fc.setFileFilter(new CustomFileFilter());
								int returnVal = fc.showSaveDialog(getFrmLernzielnator());

						        if (returnVal == JFileChooser.APPROVE_OPTION) {
									File fileToBeSaved = fc.getSelectedFile();
									//aktFile = 
									if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
									    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
									}
									try{
										aktLernzielnator.setAktFile(fileToBeSaved.getAbsolutePath());
						            }
						            catch(SecurityException e2){
						            	
						            }
									aktLernzielnator.saveFile(fileToBeSaved);
						        }
							}
							else{
								//Display Error Message
								JOptionPane errorPane = new JOptionPane(null);
					    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
								errorPane.setMessage("Keine Daten zum Speichern vorhanden.");
					    		JDialog dialogError = errorPane.createDialog("Error");
							    //configure
							    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
							    dialogError.setVisible(true);
							}
				      }
				    }
			}
		});
		getFrmLernzielnator().setTitle("Lernzielnator");
		getFrmLernzielnator().setBounds(prefs.getInt("x", 100), prefs.getInt("y", 100), prefs.getInt("w", 650), prefs.getInt("h", 550));
		getFrmLernzielnator().addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        if (JOptionPane.showConfirmDialog(getFrmLernzielnator(), 
		            "Hast du gespeichert, was du speichern wolltest?", "Wirklich schlie\u00dfen?", 
		            JOptionPane.YES_NO_OPTION,
		            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		        	prefs = java.util.prefs.Preferences.userRoot().node("lernzielnator/preferences/mainWindow");
					prefs.putInt("x", getFrmLernzielnator().getBounds().x);
					prefs.putInt("y", getFrmLernzielnator().getBounds().y);
					prefs.putInt("w", getFrmLernzielnator().getBounds().width);
					prefs.putInt("h", getFrmLernzielnator().getBounds().height);
		            System.exit(0);
		        }
		    }
		});
		getFrmLernzielnator().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getFrmLernzielnator().getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

		JPanel panelMain = new JPanel();
		getFrmLernzielnator().getContentPane().add(panelMain);
		panelMain.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panelLeft = new JPanel();
		panelMain.add(panelLeft);
		panelLeft.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panelLeft.add(scrollPane);
		
		tree = new CustomJTree();
		tree.setBackground(Color.WHITE);
		tree.setCellRenderer(new MyTreeCellRenderer());
		javax.swing.ToolTipManager.sharedInstance().registerComponent(tree);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				if (node == null){	//Nothing is selected.
					return;
				}
				switch(node.getType()){
				case 0:
					clearInfos();
					aktLernzielnator.setAktLernziel(null);
					break;
				case 1:
					clearInfos();
					aktLernzielnator.setAktLernziel(null);
					break;
				case 2:
					clearInfos();
					aktLernzielnator.setAktLernziel(null);
					break;
				case 3:
					//save changed Notes
					if(changed){
						changed = false;
						aktLernzielnator.getAktLernziel().setNotes(textField_Notes.getText());
					}
					//displaying Lernziel
					updateInfos(node.getLernziel());
					break;
				}
			}
		});
		
		/*
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
		+ "[<--]   --> Eintrag entfernen\n"
		*/		
		//Shortcut Speichern
		@SuppressWarnings("serial")
		Action sPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(aktLernzielnator.getSemesterListeSize() > 0){
					try{
						if( (aktLernzielnator.getAktFile() != null) && (!aktLernzielnator.getAktFile().isEmpty()) ){
							File fileToBeSaved = new File(aktLernzielnator.getAktFile());
							aktLernzielnator.saveFile(fileToBeSaved);
							//Display Info Message
							JOptionPane pane = new JOptionPane(null);
						    //configure
							pane.setMessageType(JOptionPane.INFORMATION_MESSAGE );
							pane.setMessage("Gespeichert unter:\n" + aktLernzielnator.getAktFile());					
						    JDialog dialogSaved = pane.createDialog("Gespeichert");
						    dialogSaved.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogSaved.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogSaved.getBounds().height/2) );
						    dialogSaved.setVisible(true);
						}
						else{
							JFileChooser fc = new JFileChooser();
							if(aktLernzielnator.getAktFile() != null && (!aktLernzielnator.getAktFile().isEmpty())){
								fc = new JFileChooser(aktLernzielnator.getAktFile());
							}
							fc.setFileFilter(new CustomFileFilter());					
							int returnVal = fc.showSaveDialog(getFrmLernzielnator());

					        if (returnVal == JFileChooser.APPROVE_OPTION) {
								File fileToBeSaved = fc.getSelectedFile();
								
								if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
								    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
								}
								try{
									aktLernzielnator.setAktFile(fileToBeSaved.getAbsolutePath());
					            }
					            catch(SecurityException e1){
					            	
					            }
								aktLernzielnator.saveFile(fileToBeSaved);
								//Display Info Message
								JOptionPane pane = new JOptionPane(null);
							    //configure
								pane.setMessageType(JOptionPane.INFORMATION_MESSAGE );
								pane.setMessage("Gespeichert unter:\n" + aktLernzielnator.getAktFile());					
							    JDialog dialogSaved = pane.createDialog("Gespeichert");
							    dialogSaved.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogSaved.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogSaved.getBounds().height/2) );
							    dialogSaved.setVisible(true);
					        }
						}
					}
					catch(NullPointerException e1){
						
					}
				}
			}		
		};
		KeyStroke strokeS = KeyStroke.getKeyStroke("S");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeS,  "sPressed");
		tree.getActionMap().put("sPressed", sPressedAction);
		
		//Shortcut Importieren
		@SuppressWarnings("serial")
		Action iPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if(aktLernzielnator.getAktFile() != null && (!aktLernzielnator.getAktFile().isEmpty())){
					fc = new JFileChooser(aktLernzielnator.getAktFile());
				}
				fc.setFileFilter(new CustomFileFilter());
				
				int returnVal = fc.showOpenDialog(getFrmLernzielnator());
	
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            aktLernzielnator.readFile(file);
		        }		
			}		
		};
		KeyStroke strokeI = KeyStroke.getKeyStroke("I");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeI,  "iPressed");
		tree.getActionMap().put("iPressed", iPressedAction);
		
		//Shortcut Karteikarten
		@SuppressWarnings("serial")
		Action kPressedAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomDefaultMutableTreeNode aktNode = (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				try{
					switch(aktNode.getType()){
					case 1:	//Modul selected
						if (JOptionPane.showConfirmDialog(getFrmLernzielnator(), 
					            "M\u00f6chtest du wirklich alle Eintr\u00e4ge dieses Moduls �ndern?", "Wirklich \u00e4ndern?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							for(int j = 0; j < aktNode.getChildCount(); j++){
								CustomDefaultMutableTreeNode aktVerNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(j);
								for(int i = 0; i < aktVerNode.getChildCount(); i++){
									CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktVerNode.getChildAt(i);
									aktChildNode.getLernziel().setKarteikarten(!(aktChildNode.getLernziel().isKarteikarten()));
									aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
									try{	//Change coloring
										if(aktLernzielnator.getAktLernziel().isRelevant()){
											if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
												aktChildNode.setColor(ok);
											}
											else
											{
												if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
													aktChildNode.setColor(mittel);
												}
												else
												{
													aktChildNode.setColor(schlecht);
												}
											}
										}
										else
										{
											aktChildNode.setColor(neutral);
										}
										((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
									}catch(NumberFormatException e1){
										System.out.println("NumberFormatException");
									}
								}
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 2:	//Veranstaltung selected
						for(int i = 0; i < aktNode.getChildCount(); i++){
							CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(i);
							aktChildNode.getLernziel().setKarteikarten(!(aktChildNode.getLernziel().isKarteikarten()));
							aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
							try{	//Change coloring
								if(aktLernzielnator.getAktLernziel().isRelevant()){
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										aktChildNode.setColor(ok);
									}
									else
									{
										if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
											aktChildNode.setColor(mittel);
										}
										else
										{
											aktChildNode.setColor(schlecht);
										}
									}
								}
								else
								{
									aktChildNode.setColor(neutral);
								}
								((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
							}catch(NumberFormatException e1){
								System.out.println("NumberFormatException");
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 3:	//Lernziel selected
						aktNode.getLernziel().setKarteikarten(!(aktNode.getLernziel().isKarteikarten()));
						chckbxKarteikarten.setSelected(aktNode.getLernziel().isKarteikarten());
						try{	//Change coloring
							if(aktLernzielnator.getAktLernziel().isRelevant()){
								if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
									((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
								}
								else
								{
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
									}
									else
									{
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
									}
								}
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
							}
							((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
						}catch(NumberFormatException e1){
							System.out.println("NumberFormatException");
						}
						break;
					}
				}
				catch(NullPointerException e1) {
					System.out.println("NullPointerException");
				}				
			}		
		};
		KeyStroke strokeK = KeyStroke.getKeyStroke("2");
		tree.getInputMap(JComponent.WHEN_FOCUSED).put(strokeK, "kPressed");
		tree.getActionMap().put("kPressed", kPressedAction);
		
		//Shortcut Ausarbeitung
		@SuppressWarnings("serial")
		Action aPressedAction = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomDefaultMutableTreeNode aktNode = (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				try{
					switch(aktNode.getType()){
					case 1:	//Modul selected
						if (JOptionPane.showConfirmDialog(getFrmLernzielnator(), 
					            "M\u00f6chtest du wirklich alle Eintr\u00e4ge dieses Moduls �ndern?", "Wirklich \u00e4ndern?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							for(int j = 0; j < aktNode.getChildCount(); j++){
								CustomDefaultMutableTreeNode aktVerNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(j);
								for(int i = 0; i < aktVerNode.getChildCount(); i++){
									CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktVerNode.getChildAt(i);
									aktChildNode.getLernziel().setAusarbeitung(!(aktChildNode.getLernziel().isAusarbeitung()));
									aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
									try{	//Change coloring
										if(aktLernzielnator.getAktLernziel().isRelevant()){
											if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
												aktChildNode.setColor(ok);
											}
											else
											{
												if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
													aktChildNode.setColor(mittel);
												}
												else
												{
													aktChildNode.setColor(schlecht);
												}
											}
										}
										else
										{
											aktChildNode.setColor(neutral);
										}
										((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
									}catch(NumberFormatException e1){
										System.out.println("NumberFormatException");
									}
								}
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 2:	//Veranstaltung selected
						for(int i = 0; i < aktNode.getChildCount(); i++){
							CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(i);
							aktChildNode.getLernziel().setAusarbeitung(!(aktChildNode.getLernziel().isAusarbeitung()));
							aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
							try{	//Change coloring
								if(aktLernzielnator.getAktLernziel().isRelevant()){
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										aktChildNode.setColor(ok);
									}
									else
									{
										if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
											aktChildNode.setColor(mittel);
										}
										else
										{
											aktChildNode.setColor(schlecht);
										}
									}
								}
								else
								{
									aktChildNode.setColor(neutral);
								}
								((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
							}catch(NumberFormatException e1){
								System.out.println("NumberFormatException");
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 3:	//Lernziel selected
						aktNode.getLernziel().setAusarbeitung(!(aktNode.getLernziel().isAusarbeitung()));
						chckbxAusarbeitung.setSelected(aktNode.getLernziel().isAusarbeitung());
						try{	//Change coloring
							if(aktLernzielnator.getAktLernziel().isRelevant()){
								if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
									((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
								}
								else
								{
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
									}
									else
									{
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
									}
								}
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
							}
							((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
						}catch(NumberFormatException e1){
							System.out.println("NumberFormatException");
						}
						break;
					}
				}
				catch(NullPointerException e1) {
					System.out.println("NullPointerException");
				}				
			}			
		};
		KeyStroke strokeA = KeyStroke.getKeyStroke("1");
		tree.getInputMap(JComponent.WHEN_FOCUSED).put(strokeA, "aPressed");
		tree.getActionMap().put("aPressed", aPressedAction);
		
		//Shortcut Lerngruppe
		@SuppressWarnings("serial")
		Action lPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				CustomDefaultMutableTreeNode aktNode = (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				try{
					switch(aktNode.getType()){
					case 1:	//Modul selected
						if (JOptionPane.showConfirmDialog(getFrmLernzielnator(), 
					            "M\u00f6chtest du wirklich alle Eintr\u00e4ge dieses Moduls �ndern?", "Wirklich \u00e4ndern?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							for(int j = 0; j < aktNode.getChildCount(); j++){
								CustomDefaultMutableTreeNode aktVerNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(j);
								for(int i = 0; i < aktVerNode.getChildCount(); i++){
									CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktVerNode.getChildAt(i);
									aktChildNode.getLernziel().setLerngruppe(!(aktChildNode.getLernziel().isLerngruppe()));
									aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
									try{	//Change coloring
										if(aktLernzielnator.getAktLernziel().isRelevant()){
											if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
												aktChildNode.setColor(ok);
											}
											else
											{
												if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
													aktChildNode.setColor(mittel);
												}
												else
												{
													aktChildNode.setColor(schlecht);
												}
											}
										}
										else
										{
											aktChildNode.setColor(neutral);
										}
										int index = aktChildNode.getParent().getIndex(aktChildNode);
										if(aktLernzielnator.getAktLernziel().isLerngruppe()){
											aktChildNode.setUserObject(Integer.toString(index) + " L");
										}
										else{
											aktChildNode.setUserObject(Integer.toString(index));
										}
										((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
									}catch(NumberFormatException e1){
										System.out.println("NumberFormatException");
									}
								}
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 2:	//Veranstaltung selected
						for(int i = 0; i < aktNode.getChildCount(); i++){
							CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(i);
							aktChildNode.getLernziel().setLerngruppe(!(aktChildNode.getLernziel().isLerngruppe()));
							aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
							try{	//Change coloring
								if(aktLernzielnator.getAktLernziel().isRelevant()){
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										aktChildNode.setColor(ok);
									}
									else
									{
										if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
											aktChildNode.setColor(mittel);
										}
										else
										{
											aktChildNode.setColor(schlecht);
										}
									}
								}
								else
								{
									aktChildNode.setColor(neutral);
								}
								int index = aktChildNode.getParent().getIndex(aktChildNode);
								if(aktLernzielnator.getAktLernziel().isLerngruppe()){
									aktChildNode.setUserObject(Integer.toString(index) + " L");
								}
								else{
									aktChildNode.setUserObject(Integer.toString(index));
								}
								((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
							}catch(NumberFormatException e1){
								System.out.println("NumberFormatException");
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 3:	//Lernziel selected
						aktNode.getLernziel().setLerngruppe(!(aktNode.getLernziel().isLerngruppe()));
						chckbxLerngruppe.setSelected(aktNode.getLernziel().isAusarbeitung());
						try{	//Change coloring
							if(aktLernzielnator.getAktLernziel().isRelevant()){
								if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
									((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
								}
								else
								{
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
									}
									else
									{
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
									}
								}
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
							}
							int index =((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getParent().getIndex(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
							if(aktLernzielnator.getAktLernziel().isLerngruppe()){
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setUserObject(Integer.toString(index) + " L");
							}
							else{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setUserObject(Integer.toString(index));
							}
							((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
						}catch(NumberFormatException e1){
							System.out.println("NumberFormatException");
						}
						break;
					}
				}
				catch(NullPointerException e1) {
					System.out.println("NullPointerException");
				}				
			}			
		};
		KeyStroke strokeL = KeyStroke.getKeyStroke("3");
		tree.getInputMap(JComponent.WHEN_FOCUSED).put(strokeL, "lPressed");
		tree.getActionMap().put("lPressed", lPressedAction);
		
		//Shortcut Relevant
		@SuppressWarnings("serial")
		Action rPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				CustomDefaultMutableTreeNode aktNode = (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
				try{
					switch(aktNode.getType()){
					case 1:	//Modul selected
						if (JOptionPane.showConfirmDialog(getFrmLernzielnator(), 
					            "M\u00f6chtest du wirklich alle Eintr\u00e4ge dieses Moduls �ndern?", "Wirklich \u00e4ndern?", 
					            JOptionPane.YES_NO_OPTION,
					            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
							for(int j = 0; j < aktNode.getChildCount(); j++){
								CustomDefaultMutableTreeNode aktVerNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(j);
								for(int i = 0; i < aktVerNode.getChildCount(); i++){
									CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktVerNode.getChildAt(i);
									aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
									aktChildNode.getLernziel().setRelevant(!(aktChildNode.getLernziel().isRelevant()));
									try{	//Change coloring
										if(aktLernzielnator.getAktLernziel().isRelevant()){
											if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
												aktChildNode.setColor(ok);
											}
											else
											{
												if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
													aktChildNode.setColor(mittel);
												}
												else
												{
													aktChildNode.setColor(schlecht);
												}
											}
										}
										else
										{
											aktChildNode.setColor(neutral);
										}
										((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
									}catch(NumberFormatException e1){
										System.out.println("NumberFormatException");
									}
								}
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 2:	//Veranstaltung selected
						for(int i = 0; i < aktNode.getChildCount(); i++){
							CustomDefaultMutableTreeNode aktChildNode = (CustomDefaultMutableTreeNode) aktNode.getChildAt(i);
							aktLernzielnator.setAktLernziel(aktChildNode.getLernziel());
							aktChildNode.getLernziel().setRelevant(!(aktChildNode.getLernziel().isRelevant()));
							try{	//Change coloring
								if(aktLernzielnator.getAktLernziel().isRelevant()){
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										aktChildNode.setColor(ok);
									}
									else
									{
										if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
											aktChildNode.setColor(mittel);
										}
										else
										{
											aktChildNode.setColor(schlecht);
										}
									}
								}
								else
								{
									aktChildNode.setColor(neutral);
								}
								((DefaultTreeModel)tree.getModel()).nodeChanged(aktChildNode);
							}catch(NumberFormatException e1){
								System.out.println("NumberFormatException");
							}
						}
						aktLernzielnator.setAktLernziel(null);
						break;
					case 3:	//Lernziel selected
						aktNode.getLernziel().setRelevant(!(aktNode.getLernziel().isRelevant()));
						chckbxRelevant.setSelected(aktNode.getLernziel().isRelevant());
						try{	//Change coloring
							if(aktLernzielnator.getAktLernziel().isRelevant()){
								if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
									((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
								}
								else
								{
									if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
									}
									else
									{
										((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
									}
								}
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
							}
							((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
						}catch(NumberFormatException e1){
							System.out.println("NumberFormatException");
						}
						break;
					}
				}
				catch(NullPointerException e1) {
					System.out.println("NullPointerException");
				}				
			}			
		};
		KeyStroke strokeR = KeyStroke.getKeyStroke("R");
		tree.getInputMap(JComponent.WHEN_FOCUSED).put(strokeR, "rPressed");
		tree.getActionMap().put("rPressed", rPressedAction);
		
		//Shortcut Statistik
		@SuppressWarnings("serial")
		Action tPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				openStatistik();
			}		
		};
		KeyStroke strokeT = KeyStroke.getKeyStroke("T");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeT, "tPressed");
		tree.getActionMap().put("tPressed", tPressedAction);
		
		//Schortcut search
		@SuppressWarnings("serial")
		Action fPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(!tree.isSelectionEmpty()){
					Search searchWindow = new Search(getFrmLernzielnator(), MainWindow.this, aktLernzielnator);
					searchWindow.setVisible(true);
				}
			}		
		};
		KeyStroke stokeF = KeyStroke.getKeyStroke("F");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(stokeF, "fPressed");
		tree.getActionMap().put("fPressed", fPressedAction);
		
		//Shortcut Up
		@SuppressWarnings("serial")
		Action uPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.up();
			}		
		};
		KeyStroke strokeU = KeyStroke.getKeyStroke("U");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeU, "uPressed");
		tree.getActionMap().put("uPressed", uPressedAction);
		
		//Shortcut Down
		@SuppressWarnings("serial")
		Action jPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.down();
			}		
		};
		KeyStroke strokeJ = KeyStroke.getKeyStroke("J");
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeJ, "jPressed");
		tree.getActionMap().put("jPressed", jPressedAction);
		
		//Shortcut delete
		@SuppressWarnings("serial")
		Action delPressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(!tree.isSelectionEmpty()){
					deleteNode();
				}
			}		
		};
		KeyStroke strokeDel = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeDel, "delPressed");
		tree.getActionMap().put("delPressed", delPressedAction);
		
		//Shortcut delete with backspace
		@SuppressWarnings("serial")
		Action del2PressedAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				if(!tree.isSelectionEmpty()){
					deleteNode();
				}
			}		
		};
		KeyStroke strokeDel2 = KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0);
		tree.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(strokeDel2, "del2Pressed");
		tree.getActionMap().put("del2Pressed", del2PressedAction);
		
		//configure popup-menu
		popUpNew = new JMenuItem("Neu");
		popUpNew.setToolTipText("Eine neue Node anh\u00e4ngen");
		popUpNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				newEntryDynamic();
			}
		});
		popup.add(popUpNew);
		popUpEdit = new JMenuItem("Edit");
		popUpEdit.setToolTipText("Editiere die ausgew\u00e4lte Node.");
		popUpEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editNode();
			}
		});
		popup.add(popUpEdit);
		popUpDelete = new JMenuItem("L\u00f6schen");
		popUpDelete.setToolTipText("L\u00f6sche die ausgew\u00e4lte Node.");
		popUpDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNode();
			}
		});
		popup.add(popUpDelete);
		popUpUp = new JMenuItem("Hoch");
		popUpUp.setToolTipText("Bewege die ausgew\u00e4lte Node um eine Position nach oben.");
		popUpUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.up();				
			}			
		});
		popup.add(popUpUp);
		popUpDown = new JMenuItem("Runter");
		popUpDown.setToolTipText("Bewege die ausgew\u00e4lte Node um eine Position nach unten.");
		popUpDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.down();				
			}
		});
		popup.add(popUpDown);
		popUpSort = new JMenuItem("Sortieren");
		popUpSort.setToolTipText("Sortiert alle zu dieser Node geh\u00f6rende Eintr\u00e4ge");
		popUpSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				sort();
			}
		});
		popup.add(popUpSort);
		// add MouseListener to tree
		MouseAdapter ma = new MouseAdapter() {
			private void myPopupEvent(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				CustomJTree tree = (CustomJTree)e.getSource();
				TreePath path = tree.getPathForLocation(x, y);
				if (path == null)
					return;	
				popup.show(tree, x, y);
			}
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) myPopupEvent(e);
			}
		};		
		tree.addMouseListener(ma);
		
		top = new CustomDefaultMutableTreeNode("Semester");
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(top));
		scrollPane.setViewportView(tree);
		
		JPanel panelRight = new JPanel();
		panelMain.add(panelRight);
		panelRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelRightTop = new JPanel();
		panelRight.add(panelRightTop);
		panelRightTop.setLayout(new GridLayout(0, 1, 0, 0));
		
		textField_Description = new JTextArea();
		textField_Description.setMargin(new Insets(10,5,10,5));
		textField_Description.setEditable(false);
		textField_Description.setLineWrap(true);
		textField_Description.setWrapStyleWord(true);
		JScrollPane scrollDescr = new JScrollPane (textField_Description);
		scrollDescr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    panelRightTop.add(scrollDescr);
		textField_Description.setColumns(10);
		
		JPanel panelRightMiddle = new JPanel();
		panelRight.add(panelRightMiddle);
		panelRightMiddle.setLayout(new GridLayout(0, 1, 0, 0));
		
		textField_Notes = new JTextArea();
		textField_Notes.setMargin(new Insets(10,5,10,5));
		textField_Notes.setLineWrap(true);
		textField_Notes.setWrapStyleWord(true);
		textField_Notes.getDocument().addDocumentListener(new DocumentListener(){
			@Override
			public void changedUpdate(DocumentEvent e){
				changed = true;
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changed = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changed = true;
			}
		});
		JScrollPane scrollNotes = new JScrollPane (textField_Notes);
		scrollNotes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelRightMiddle.add(scrollNotes);
		
		JPanel panelRightBottom = new JPanel();
		panelRight.add(panelRightBottom);
		panelRightBottom.setLayout(new GridLayout(1, 1, 0, 0));
		
		JPanel panelBottomRightLeft = new JPanel();
		panelRightBottom.add(panelBottomRightLeft);
		panelBottomRightLeft.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelBRLTop = new JPanel();
		panelBottomRightLeft.add(panelBRLTop);
		panelBRLTop.setLayout(new GridLayout(0, 1, 0, 0));
		
		chckbxMc = new JCheckBox("MC");
		chckbxMc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*try{
					Integer.parseInt(tree.getLastSelectedPathComponent().toString());
					chckbxMc.setSelected( aktLernziel.isMC() );
				}catch(NumberFormatException e){
					chckbxMc.setSelected(false);
				}*/	//--> was the same code for SMPP and OSCE
				chckbxMc.setSelected( aktLernzielnator.getAktLernziel().isMC() );
			}
		});
		panelBRLTop.add(chckbxMc);
		
		chckbxSmpp = new JCheckBox("SMPP");
		chckbxSmpp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxSmpp.setSelected(aktLernzielnator.getAktLernziel().isSMPP());				
			}
		});
		panelBRLTop.add(chckbxSmpp);
		
		chckbxOsce = new JCheckBox("OSCE");
		chckbxOsce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxOsce.setSelected(aktLernzielnator.getAktLernziel().isOSCE());
			}
		});
		panelBRLTop.add(chckbxOsce);
		
		JLabel label = new JLabel("");
		panelBRLTop.add(label);
		
		chckbxRelevant = new JCheckBox("pr\u00FCfungsrelevant");
		chckbxRelevant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					aktLernzielnator.getAktLernziel().setRelevant(chckbxRelevant.isSelected());
					if(aktLernzielnator.getAktLernziel().isRelevant()){
						if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
							((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
						}
						else
						{
							if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
							}
						}
					}
					else
					{
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
					}
					((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
				}catch(NumberFormatException e1){
					
				}
			}
		});
		panelBRLTop.add(chckbxRelevant);
		
		JPanel panelBRLBottom = new JPanel();
		panelBottomRightLeft.add(panelBRLBottom);
		panelBRLBottom.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel label_1 = new JLabel("");
		panelBRLBottom.add(label_1);
		
		chckbxLerngruppe = new JCheckBox("Lerngruppe");
		chckbxLerngruppe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					aktLernzielnator.getAktLernziel().setLerngruppe(chckbxLerngruppe.isSelected());
					if(aktLernzielnator.getAktLernziel().isRelevant()){
						if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
							((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
						}
						else
						{
							if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
							}
						}
					}
					else
					{
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
					}
					int index =((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).getParent().getIndex(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
					if(aktLernzielnator.getAktLernziel().isLerngruppe()){
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setUserObject(Integer.toString(index) + " L");
					}
					else{
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setUserObject(Integer.toString(index));
					}
					((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
				}catch(NumberFormatException e1){
					
				}
				
			}
		});
		
		chckbxAusarbeitung = new JCheckBox("Ausarbeitung");
		chckbxAusarbeitung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					aktLernzielnator.getAktLernziel().setAusarbeitung(chckbxAusarbeitung.isSelected());
					if(aktLernzielnator.getAktLernziel().isRelevant()){
						if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
							((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
						}
						else
						{
							if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
							}
						}
					}
					else
					{
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
					}
					((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
				}catch(NumberFormatException e1){
					
				}
			}
		});
		panelBRLBottom.add(chckbxAusarbeitung);
		
		chckbxKarteikarten = new JCheckBox("Karteikarten");
		chckbxKarteikarten.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					aktLernzielnator.getAktLernziel().setKarteikarten(chckbxKarteikarten.isSelected());
					if(aktLernzielnator.getAktLernziel().isRelevant()){
						if( (aktLernzielnator.getAktLernziel().isKarteikarten()) && (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
							((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(ok);
						}
						else
						{
							if( (aktLernzielnator.getAktLernziel().isKarteikarten()) ^ (aktLernzielnator.getAktLernziel().isAusarbeitung()) ){
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(mittel);
							}
							else
							{
								((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(schlecht);
							}
						}
					}
					else
					{
						((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()).setColor(neutral);
					}
					((DefaultTreeModel)tree.getModel()).nodeChanged(((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent()));
				}catch(NumberFormatException e1){
					
				}
			}
		});
		panelBRLBottom.add(chckbxKarteikarten);
		panelBRLBottom.add(chckbxLerngruppe);
		
		JPanel panelBottomRightRight = new JPanel();
		panelRightBottom.add(panelBottomRightRight);
		panelBottomRightRight.setLayout(new GridLayout(0, 1, 0, 0));
		
		textField_LzDimension = new JTextArea();
		textField_LzDimension.setMargin(new Insets(10,5,10,5));
		textField_LzDimension.setEditable(false);
		textField_LzDimension.setLineWrap(true);
		textField_LzDimension.setWrapStyleWord(true);
		JScrollPane scrollLzDim = new JScrollPane (textField_LzDimension);
		scrollLzDim.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelBottomRightRight.add(scrollLzDim);
		
		textField_LzKognitionsdimension = new JTextArea();
		textField_LzKognitionsdimension.setMargin(new Insets(10,5,10,5));
		textField_LzKognitionsdimension.setEditable(false);
		textField_LzKognitionsdimension.setLineWrap(true);
		textField_LzKognitionsdimension.setWrapStyleWord(true);
		JScrollPane scrollLzKogDim = new JScrollPane (textField_LzKognitionsdimension);
		scrollLzKogDim.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelBottomRightRight.add(scrollLzKogDim);
		
		menuBar = new JMenuBar();
		getFrmLernzielnator().setJMenuBar(menuBar);
		
		mnData = new JMenu("Data");
		menuBar.add(mnData);
		
		mntmLaden = new JMenuItem("Laden");
		mntmLaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if(aktLernzielnator.getAktFile() != null && (!aktLernzielnator.getAktFile().isEmpty())){
					fc = new JFileChooser(aktLernzielnator.getAktFile());
				}
				fc.setFileFilter(new CustomFileFilter());
				int returnVal = fc.showOpenDialog(getFrmLernzielnator());

		        if (returnVal == JFileChooser.APPROVE_OPTION){
		            File file = fc.getSelectedFile();
		            aktLernzielnator.setAktFile(file.getAbsolutePath());
		            try{
		            	aktLernzielnator.setAktFile(file.getAbsolutePath());
		            }
		            catch(SecurityException e1){
		            	
		            }
		            aktLernzielnator.readFile(file);
		            aktLernzielnator.setAktFile(file.getAbsolutePath());
		        }				
			}
		});
		mnData.add(mntmLaden);
		
		mntmSpeichern = new JMenuItem("Speichern");
		mntmSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(aktLernzielnator.getSemesterListeSize() > 0){					
					JFileChooser fc = new JFileChooser();
					try{
						if(aktLernzielnator.getAktFile() != null && (!aktLernzielnator.getAktFile().isEmpty())){
							fc = new JFileChooser(aktLernzielnator.getAktFile());
						}
					}
					catch(NullPointerException e){
						
					}
					fc.setFileFilter(new CustomFileFilter());					
					int returnVal = fc.showSaveDialog(getFrmLernzielnator());

			        if (returnVal == JFileChooser.APPROVE_OPTION) {
						File fileToBeSaved = fc.getSelectedFile();
						aktLernzielnator.setAktFile(fileToBeSaved.getAbsolutePath());
						
						if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
						    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
						}
						try{
							aktLernzielnator.setAktFile(fileToBeSaved.getAbsolutePath());
			            }
			            catch(SecurityException e1){
			            	
			            }
						aktLernzielnator.saveFile(fileToBeSaved);
			        }
				}
				else{
					//Display Error Message
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Keine Daten zum Speichern vorhanden.");
		    		JDialog dialogError = errorPane.createDialog("Error");
				    //configure					
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnData.add(mntmSpeichern);
		
		mnExportieren = new JMenu("Exportieren");
		mnData.add(mnExportieren);
		
		mntmSemester_1 = new JMenuItem("Semester");
		mntmSemester_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node != null){
					if(node.getPath().length == 2){
						final JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new CustomFileFilter());
						int returnVal = fc.showSaveDialog(getFrmLernzielnator());

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File fileToBeSaved = fc.getSelectedFile();
							
							if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
							    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
							}
							aktLernzielnator.exportSemester(fileToBeSaved, node.getSemester());
				        }
					}
					else{
						error = 2;
					}
				}
				else{
					error = 1;
				}
				if(error > 0){
					//Display Error Message
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Bitte ein Semester ausw\u00e4hlen!");		
		    		JDialog dialogError = errorPane.createDialog("Error");
				    //configure
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnExportieren.add(mntmSemester_1);
		
		mntmModul_1 = new JMenuItem("Modul");
		mntmModul_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node != null){
					if(node.getPath().length == 3){
						final JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new CustomFileFilter());
						int returnVal = fc.showSaveDialog(getFrmLernzielnator());

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File fileToBeSaved = fc.getSelectedFile();
							
							if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
							    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
							}
							aktLernzielnator.exportModul(fileToBeSaved, ((CustomDefaultMutableTreeNode)node.getParent()).getSemester(), node.getModul());
				        }
					}
					else{
						error = 2;
					}
				}
				else{
					error = 1;
				}
				if(error > 0){
					//Display Error Message
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Bitte ein Modul ausw\u00e4hlen!");
		    		JDialog dialogError = errorPane.createDialog("Error");
				    //configure
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnExportieren.add(mntmModul_1);
		
		mntmVeranstaltung_1 = new JMenuItem("Veranstaltung");
		mntmVeranstaltung_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node != null){
					if(node.getPath().length == 4){
						final JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new CustomFileFilter());
						int returnVal = fc.showSaveDialog(getFrmLernzielnator());

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File fileToBeSaved = fc.getSelectedFile();
							
							if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
							    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
							}
							aktLernzielnator.exportVeranstaltung(fileToBeSaved,((CustomDefaultMutableTreeNode)node.getParent().getParent()).getSemester(), ((CustomDefaultMutableTreeNode)node.getParent()).getModul(), node.getVeranstaltung());
				        }
					}
					else{
						error = 2;
					}
				}
				else{
					error = 1;
				}
				if(error > 0){
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Bitte eine Veranstaltung ausw\u00e4hlen!");	
		    		JDialog dialogError = errorPane.createDialog("Error");
					//Display Error Message				
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnExportieren.add(mntmVeranstaltung_1);
		
		mntmLernziel_1 = new JMenuItem("Lernziel");
		mntmLernziel_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node != null){
					if(node.getPath().length == 5){
						final JFileChooser fc = new JFileChooser();
						fc.setFileFilter(new CustomFileFilter());
						int returnVal = fc.showSaveDialog(getFrmLernzielnator());

						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File fileToBeSaved = fc.getSelectedFile();
							
							if(!fc.getSelectedFile().getAbsolutePath().endsWith(".csv")){
							    fileToBeSaved = new File(fc.getSelectedFile() + ".csv");
							}
							aktLernzielnator.exportLernziel(fileToBeSaved,((CustomDefaultMutableTreeNode)node.getParent().getParent().getParent()).getSemester(), ((CustomDefaultMutableTreeNode)node.getParent().getParent()).getModul(), ((CustomDefaultMutableTreeNode)node.getParent()).getVeranstaltung(), node.getLernziel());
				        }
					}
					else{
						error = 2;
					}
				}
				else{
					error = 1;
				}
				if(error > 0){
					//display error
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Bitte ein Lernziel ausw\u00e4hlen!");
		    		JDialog dialogError = errorPane.createDialog("Error");					
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnExportieren.setToolTipText("Exportiert Teile des Datensatzes in eine .csv Datei, OHNE pers\u00F6nliche Angaben");
		mnExportieren.add(mntmLernziel_1);
		
		mntmImportieren = new JMenuItem("Importieren");
		mntmImportieren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				if(aktLernzielnator.getAktFile() != null && (!aktLernzielnator.getAktFile().isEmpty())){
					fc = new JFileChooser(aktLernzielnator.getAktFile());
				}
				fc.setFileFilter(new CustomFileFilter());
				
				int returnVal = fc.showOpenDialog(getFrmLernzielnator());
	
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            aktLernzielnator.readFile(file);
		        }
		        
			}
		});
		mntmImportieren.setToolTipText("F\u00fcgt Inhalt einer Datei dem Datensatz hinzu");
		mnData.add(mntmImportieren);
		
		mntmSuchen = new JMenuItem("Suchen");
		mntmSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search searchWindow = new Search(frmLernzielnator, MainWindow.this, aktLernzielnator);
				searchWindow.setVisible(true);
			}
		});
		mnData.add(mntmSuchen);
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmLsche = new JMenuItem("L\u00F6sche");
		mntmLsche.setToolTipText("L\u00f6sche die ausgew\u00e4hlte Node");
		mntmLsche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNode();
			}
		});
		mnEdit.add(mntmLsche);
		
		mntmEditiere = new JMenuItem("Editiere");
		mntmEditiere.setToolTipText("Editiert die ausgew\u00e4hlte Node");
		mntmEditiere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editNode();
			}
		});
		mnEdit.add(mntmEditiere);
		
		mntmUp = new JMenuItem("Hoch");
		mntmUp.setToolTipText("Bewegt die ausgew\u00e4hlte Node um eine Position nach oben");
		mntmUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.up();
			}
		});
		mnEdit.add(mntmUp);
		
		mntmDown = new JMenuItem("Runter");
		mntmDown.setToolTipText("Bewegt die ausgew\u00e4hlte Node um eine Position nach unten");
		mntmDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aktLernzielnator.down();
			}
		});
		mnEdit.add(mntmDown);
		
		mntmSort = new JMenuItem("Sortieren");
		mntmSort.setToolTipText("Sortiert alle zu dieser Node geh\u00f6rende Eintr\u00e4ge");
		mntmSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sort sortDialog = new Sort(getFrmLernzielnator(), MainWindow.this, aktLernzielnator);
				sortDialog.setVisible(true);
			}
		});
		mnEdit.add(mntmSort);
		
		mnNeu = new JMenu("Neu");
		menuBar.add(mnNeu);
		
		mntmSemester = new JMenuItem("Semester");
		mntmSemester.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Semester semester = new Semester();
				AddSemester addSemesterDialog = new AddSemester(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, semester);
				addSemesterDialog.setVisible(true);				
			}
		});
		mnNeu.add(mntmSemester);
		
		mntmModul = new JMenuItem("Modul");
		mntmModul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(aktLernzielnator.getSemesterListeSize() != 0){
					Modul modul = new Modul();
					AddModul addModulDialog = new AddModul(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, modul);
					addModulDialog.setVisible(true);
				}
				else
				{
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					errorPane.setMessage("Keine Semester vorhanden.\nBitte zuerst Semesteer hinzuf\u00fcgen!");
		    		JDialog dialogError = errorPane.createDialog("Error");
					//Display Error Message
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}			
			}
		});
		mnNeu.add(mntmModul);
		
		mntmVeranstaltung = new JMenuItem("Veranstaltung");
		mntmVeranstaltung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				boolean modulListEmpty = true;
				if(aktLernzielnator.getSemesterListeSize() != 0){
					for(int i = 0; i < aktLernzielnator.getSemesterListeSize(); i++){
						if(aktLernzielnator.getModulListeSize(i) != 0 ){
							modulListEmpty = false;
							break;
						}
					}
					if(!modulListEmpty){
						Veranstaltung veranstaltung = new Veranstaltung();
						AddVeranstaltung addVeranstaltungDialog = new AddVeranstaltung(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, veranstaltung);
						addVeranstaltungDialog.setVisible(true);
					}
					else{
						error = 2;
					}
				}
				else{
					error = 1;
				}
				if(error > 0){
					//display error
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					switch(error){
					case 1:
						errorPane.setMessage("Keine Semester vorhanden!");
						break;
					case 2:
						errorPane.setMessage("Keine Module vorhanden!");
						break;
					}
		    		JDialog dialogError = errorPane.createDialog("Error");
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnNeu.add(mntmVeranstaltung);
		
		mntmLernziel = new JMenuItem("Lernziel");
		mntmLernziel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int error = -1;
				CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node != null){
					if(node.getPath().length == 4){	//asures that a Veranstaltungs-node is selected
						Lernziel lernziel = new Lernziel();
						Veranstaltung veranstaltung = getParentVeranstaltungOfTree(node);
						AddLernziel addLernzielDialog = new AddLernziel(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, veranstaltung, lernziel);
						addLernzielDialog.setVisible(true);
						updateTree();
					}
					else{
						error = 2;
					}
				}
				else
				{
					error = 1;
				}
				if(error > 0){
					//display error
					JOptionPane errorPane = new JOptionPane(null);
		    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
					switch(error){
					case 1:
						errorPane.setMessage("Die Veranstaltung, zu der das Lernziel geh\u00f6rt, auw\u00e4hlen!");
						break;
					case 2:
						errorPane.setMessage("Die Veranstaltung, zu der das Lernziel geh\u00f6rt, auw\u00e4hlen!");
						break;
					}
		    		JDialog dialogError = errorPane.createDialog("Error");
				    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
				    dialogError.setVisible(true);
				}
			}
		});
		mnNeu.add(mntmLernziel);
		
		mnAnsicht = new JMenu("Ansicht");
		menuBar.add(mnAnsicht);
		
		mntmAnzeigen = new JMenuItem("Anzeigen:");
		mntmAnzeigen.setEnabled(false);
		mnAnsicht.add(mntmAnzeigen);
		
		chckbxmntmAlleLernziele = new JCheckBoxMenuItem("Alle Lernziele");
		chckbxmntmAlleLernziele.setSelected(true);
		chckbxmntmAlleLernziele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxmntmAlleLernziele.isSelected()){
					chckbxmntmRoteLernziele.setSelected(true);
					chckbxmntmGelbeLernziele.setSelected(true);
					chckbxmntmGrneLernziele.setSelected(true);
					chckbxmntmLernzieleMitLerngruppen.setSelected(true);
					showRed = true;
					showYellow = true;
					showGreen = true;
					showGrey = true;
					showLerngruppe = true;
					updateTree();
				}
				else{
					chckbxmntmRoteLernziele.setSelected(false);
					chckbxmntmGelbeLernziele.setSelected(false);
					chckbxmntmGrneLernziele.setSelected(false);
					chckbxmntmLernzieleMitLerngruppen.setSelected(false);
					showRed = false;
					showYellow = false;
					showGreen = false;
					showGrey = false;
					showLerngruppe = false;
					updateTree();
				}
			}
		});
		mnAnsicht.add(chckbxmntmAlleLernziele);
		
		chckbxmntmRoteLernziele = new JCheckBoxMenuItem("Rote Lernziele");
		chckbxmntmRoteLernziele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showRed = chckbxmntmRoteLernziele.isSelected();
				if(!showRed){
					chckbxmntmAlleLernziele.setSelected(false);
				}
				else{
					if(showRed && showYellow && showGreen && showGrey && showLerngruppe){
						chckbxmntmAlleLernziele.setSelected(true);
					}
				}
				updateTree();
			}
		});
		chckbxmntmRoteLernziele.setSelected(true);
		mnAnsicht.add(chckbxmntmRoteLernziele);
		
		chckbxmntmGelbeLernziele = new JCheckBoxMenuItem("Gelbe Lernziele");
		chckbxmntmGelbeLernziele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showYellow = chckbxmntmGelbeLernziele.isSelected();
				if(!showYellow){
					chckbxmntmAlleLernziele.setSelected(false);
				}
				else{
					if(showRed && showYellow && showGreen && showGrey && showLerngruppe){
						chckbxmntmAlleLernziele.setSelected(true);
					}
				}
				updateTree();
			}
		});
		chckbxmntmGelbeLernziele.setSelected(true);
		mnAnsicht.add(chckbxmntmGelbeLernziele);
		
		chckbxmntmGrneLernziele = new JCheckBoxMenuItem("Gr\u00FCne Lernziele");
		chckbxmntmGrneLernziele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGreen = chckbxmntmGrneLernziele.isSelected();
				if(!showGreen){
					chckbxmntmAlleLernziele.setSelected(false);
				}
				else{
					if(showRed && showYellow && showGreen && showGrey && showLerngruppe){
						chckbxmntmAlleLernziele.setSelected(true);
					}
				}
				updateTree();
			}
		});
		chckbxmntmGrneLernziele.setSelected(true);
		mnAnsicht.add(chckbxmntmGrneLernziele);
		
		chckbxmntmLernzieleMitLerngruppen = new JCheckBoxMenuItem("Lernziele mit Lerngruppen");
		chckbxmntmLernzieleMitLerngruppen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showLerngruppe = chckbxmntmLernzieleMitLerngruppen.isSelected();
				if(!showLerngruppe){
					chckbxmntmAlleLernziele.setSelected(false);
				}
				else{
					if(showRed && showYellow && showGreen && showGrey && showLerngruppe){
						chckbxmntmAlleLernziele.setSelected(true);
					}
				}
				updateTree();
			}
		});
		

		chckbxmntmGraueLernziele = new JCheckBoxMenuItem("Graue Lernziele");
		chckbxmntmGraueLernziele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGrey = chckbxmntmGraueLernziele.isSelected();
				if(!showGrey){
					chckbxmntmAlleLernziele.setSelected(false);
				}
				else{
					if(showRed && showYellow && showGreen && showGrey && showLerngruppe){
						chckbxmntmAlleLernziele.setSelected(true);
					}
				}
				updateTree();
			}
		});
		chckbxmntmGraueLernziele.setSelected(true);
		mnAnsicht.add(chckbxmntmGraueLernziele);
		chckbxmntmLernzieleMitLerngruppen.setSelected(true);
		mnAnsicht.add(chckbxmntmLernzieleMitLerngruppen);
		
		chckbxmntmLeereNodes = new JCheckBoxMenuItem("Leere Nodes");
		chckbxmntmLeereNodes.setToolTipText("Zeige Nodes, welche leer sind. Endweder auf Grund der obrigen Anzeigeeinstellungen, oder wegen fehlen Daten.");
		chckbxmntmLeereNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEmptyNodes = chckbxmntmLeereNodes.isSelected();
				updateTree();
			}
		});
		chckbxmntmLeereNodes.setSelected(true);
		mnAnsicht.add(chckbxmntmLeereNodes);
		
		mntmCollapsNodes = new JMenuItem("Nodes verkleinern");
		mntmCollapsNodes.setEnabled(true);
		mntmCollapsNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				treeCollapsAll(tree, new TreePath(top));
			}
		});
		mnAnsicht.add(mntmCollapsNodes);
		
		mntmExpandNodes = new JMenuItem("Nodes vergr\u00f6\u00dfern");
		mntmExpandNodes.setEnabled(true);
		mntmExpandNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				treeExpandAll(tree, new TreePath(top));
			}
		});
		mnAnsicht.add(mntmExpandNodes);
		
		
		mnQuestion = new JMenu("?");
		menuBar.add(mnQuestion);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				About aboutWindow = new About(getFrmLernzielnator(), aktLernzielnator);
				aboutWindow.setVisible(true);
			}
		});
		mnQuestion.add(mntmAbout);
		
		mntmStatistik = new JMenuItem("Statistik");
		mntmStatistik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openStatistik();
			}
		});
		mnQuestion.add(mntmStatistik);
		
		mntmAnleitung = new JMenuItem("Anleitung");
		mntmAnleitung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Instruction instructionWindow = new Instruction(getFrmLernzielnator(), aktLernzielnator);
				instructionWindow.setVisible(true);
			}
		});
		mnQuestion.add(mntmAnleitung);
		
		mntmEinstellungen = new JMenuItem("Einstellungen");
		mntmEinstellungen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preferences einstellungenWindow = new Preferences(getFrmLernzielnator(), MainWindow.this, aktLernzielnator);
				einstellungenWindow.setVisible(true);
			}
		});
		mnQuestion.add(mntmEinstellungen);
		
		updateFontSize();
	}
	
	
	public CustomJTree getTree(){
		return tree;
	}
	
	public CustomDefaultMutableTreeNode getSelectedTreeNode(){
		return (CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent();
	}
	
	public void updateTree(){
		top.removeAllChildren();
		if(aktLernzielnator.getSemesterListeSize() > 0){			
			//Adding the Semester
			for(int i = 0; i < aktLernzielnator.getSemesterListeSize(); i++){
				String sem;
				if(aktLernzielnator.getSemester(i).isWS())
				{
					sem = new String("WS" + aktLernzielnator.getSemester(i).getSemesterYear().toString());
				}
				else
				{
					sem = new String("SS" + aktLernzielnator.getSemester(i).getSemesterYear().toString());
				}
				CustomDefaultMutableTreeNode newChildSem = new CustomDefaultMutableTreeNode(sem);
				newChildSem.setSemester(aktLernzielnator.getSemester(i));
				newChildSem.setType(0);
				newChildSem.setColor(Color.BLACK);
				top.add(newChildSem);
				//Adding Module
				for(int j = 0; j < aktLernzielnator.getModulListeSize(i); j++){
					String mod;
					boolean hasChildVer = false;
					mod = new String("M" + aktLernzielnator.getModul(i, j).getModulNumber().toString() );
					CustomDefaultMutableTreeNode newChildMod = new CustomDefaultMutableTreeNode(mod);
					newChildMod.setColor(Color.BLACK);
					newChildMod.setModul(aktLernzielnator.getModul(i, j));
					newChildMod.setType(1);
					newChildSem.add(newChildMod);
					//Adding Veranstaltungen
					for(int k = 0; k < aktLernzielnator.getVeranstaltungsListeSize(i, j); k++){
						String ver;
						boolean hasChildLz = false;
						hasChildVer = true;
						ver = new String("MW" + Integer.toString(aktLernzielnator.getVeranstaltung(i, j, k).getVeranstaltungWoche()) + " " + aktLernzielnator.getVeranstaltung(i, j, k).getVeranstaltungTitel());
						CustomDefaultMutableTreeNode newChildVer = new CustomDefaultMutableTreeNode(ver);
						newChildVer.setVeranstaltung(aktLernzielnator.getVeranstaltung(i, j, k));
						newChildVer.setType(2);
						newChildVer.setColor(Color.BLACK);
						newChildMod.add(newChildVer);
						//Adding Lernziele
						for(int l = 0; l < aktLernzielnator.getLernzielListeSize(i, j, k) ; l++){
							Lernziel lernziel = aktLernzielnator.getLernziel(i, j, k, l);
							String lz;
							boolean display = false;
							hasChildLz = true;
							if(lernziel.isLerngruppe()){
								lz = new String(Integer.toString(l) + " L");
							}
							else{
								lz = new String(Integer.toString(l) );
							}
							
							CustomDefaultMutableTreeNode newChildLz = new CustomDefaultMutableTreeNode(lz);
							newChildLz.setLernziel(aktLernzielnator.getLernziel(i, j, k, l));
							newChildLz.setType(3);
							if(lernziel.isRelevant()){
								if( (lernziel.isKarteikarten()) && (lernziel.isAusarbeitung()) ){
									newChildLz.setColor(ok);
									if(showGreen){
										display = true;
									}
								}
								else
								{
									if( (lernziel.isKarteikarten()) ^ (lernziel.isAusarbeitung()) ){
										newChildLz.setColor(mittel);
										if(showYellow){
											display = true;
										}
									}
									else
									{
										newChildLz.setColor(schlecht);
										if(showRed){
											display = true;
										}
									}
								}
							}
							else
							{
								newChildLz.setColor(neutral);
								if(showGrey){
									display = true;
								}
							}
							
							if(lernziel.isLerngruppe()){
								if(showLerngruppe){
									display = true;
								}
								else{
									display = false;
								}
							}
							if(display){
								newChildVer.add(newChildLz);
							}							
						}
						if((newChildVer.getChildCount() == 0) && hasChildLz && (!showEmptyNodes)){
							newChildMod.remove(newChildVer);
						}
					}
					if((newChildMod.getChildCount() == 0) && hasChildVer && (!showEmptyNodes)){
						newChildSem.remove(newChildMod);
					}
				}
			}
		}
		tree.updateUI();
		treeExpandAll(tree, new TreePath(top));
	}
	
	public void sortAllSelect(boolean semester, boolean module, boolean veranstaltungen){
		if(!((!semester) && (!module) && (!veranstaltungen))){
			for(int sem = 0; sem < aktLernzielnator.getSemesterListeSize(); sem++){
				for(int mod = 0; mod < aktLernzielnator.getModulListeSize(sem); mod++){
					for(int ver = 0; ver < aktLernzielnator.getVeranstaltungsListeSize(sem, mod); ver++){
						if(veranstaltungen){
							if(aktLernzielnator.getLernzielListeSize(sem, mod, ver) > 1){
								aktLernzielnator.sortVeranstaltung(aktLernzielnator.getVeranstaltung(sem, mod, ver), 0, aktLernzielnator.getLernzielListeSize(sem, mod, ver)-1);
							}
						}
					}
					if(module){
						if(aktLernzielnator.getVeranstaltungsListeSize(sem, mod) > 1){
							aktLernzielnator.sortModul(aktLernzielnator.getModul(sem, mod), 0, aktLernzielnator.getVeranstaltungsListeSize(sem, mod)-1);
						}
					}					
				}
				if(semester){
					if(aktLernzielnator.getModulListeSize(sem) > 1){
						aktLernzielnator.sortSemester(aktLernzielnator.getSemester(sem), 0, aktLernzielnator.getModulListeSize(sem)-1);						
					}
				}
			}
			if(semester){
				if(aktLernzielnator.getSemesterListeSize() > 1){
					aktLernzielnator.sortTop(0, aktLernzielnator.getSemesterListeSize()-1);					
				}
			}
			updateTree();
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void treeExpandAll(CustomJTree tree, TreePath parent) {
	    TreeNode node = (TreeNode) parent.getLastPathComponent();
	    if (node.getChildCount() >= 0) {
	      for (Enumeration e = node.children(); e.hasMoreElements();) {
	        TreeNode n = (TreeNode) e.nextElement();
	        TreePath path = parent.pathByAddingChild(n);
	        treeExpandAll(tree, path);
	      }
	    }
	    tree.expandPath(parent);
	  }
	
	@SuppressWarnings("rawtypes")
	private void treeCollapsAll(CustomJTree tree, TreePath parent) {
	    TreeNode node = (TreeNode) parent.getLastPathComponent();
	    if (node.getChildCount() >= 0) {
	      for (Enumeration e = node.children(); e.hasMoreElements();) {
	        TreeNode n = (TreeNode) e.nextElement();
	        TreePath path = parent.pathByAddingChild(n);
	        treeCollapsAll(tree, path);
	      }
	    }
	    tree.collapsePath(parent);
	  }
	
	private void updateInfos(Lernziel lernziel){
		textField_Description.setText(lernziel.getLzDescription());
		textField_Notes.setText(lernziel.getLzNotes());
		textField_LzDimension.setText(lernziel.getLzDimension().toString());
		textField_LzKognitionsdimension.setText(lernziel.getLzKognitionsdimension().toString());
		chckbxMc.setSelected(lernziel.isMC());
		chckbxSmpp.setSelected(lernziel.isSMPP());
		chckbxOsce.setSelected(lernziel.isOSCE());
		chckbxRelevant.setSelected(lernziel.isRelevant());
		chckbxLerngruppe.setSelected(lernziel.isLerngruppe());
		chckbxKarteikarten.setSelected(lernziel.isKarteikarten());
		chckbxAusarbeitung.setSelected(lernziel.isAusarbeitung());
		
		aktLernzielnator.setAktLernziel(lernziel);
	}
	
	@SuppressWarnings("unused")
	private void updateInfos(Semester aktSemester, int modulNr, int veranstNr, int lzNr){
		aktLernzielnator.setAktLernziel(aktSemester.getSemesterModul(modulNr).getModulVeranstaltung(veranstNr).getVeranstaltungLz(lzNr));
		
		textField_Description.setText(aktLernzielnator.getAktLernziel().getLzDescription());
		textField_Notes.setText(aktLernzielnator.getAktLernziel().getLzNotes());
		textField_LzDimension.setText(aktLernzielnator.getAktLernziel().getLzDimension().toString());
		textField_LzKognitionsdimension.setText(aktLernzielnator.getAktLernziel().getLzKognitionsdimension().toString());
		chckbxMc.setSelected(aktLernzielnator.getAktLernziel().isMC());
		chckbxSmpp.setSelected(aktLernzielnator.getAktLernziel().isOSCE());
		chckbxOsce.setSelected(aktLernzielnator.getAktLernziel().isSMPP());
		chckbxRelevant.setSelected(aktLernzielnator.getAktLernziel().isRelevant());
		chckbxLerngruppe.setSelected(aktLernzielnator.getAktLernziel().isLerngruppe());
		chckbxKarteikarten.setSelected(aktLernzielnator.getAktLernziel().isKarteikarten());
		chckbxAusarbeitung.setSelected(aktLernzielnator.getAktLernziel().isAusarbeitung());
	}
	
	private void clearInfos(){
		textField_Description.setText("");
		textField_Notes.setText("");
		textField_LzDimension.setText("");
		textField_LzKognitionsdimension.setText("");
		chckbxMc.setSelected(false);
		chckbxSmpp.setSelected(false);
		chckbxOsce.setSelected(false);
		chckbxRelevant.setSelected(false);
		chckbxKarteikarten.setSelected(false);
		chckbxAusarbeitung.setSelected(false);
	}
	
	private Veranstaltung getParentVeranstaltungOfTree(CustomDefaultMutableTreeNode node){		
		boolean ws;
		int semesterYear;
		int modulNr;
		int veranstaltungsWeek;
		String veranstaltungsTitle;
		String veranstaltungBez = new String(node.toString());
		String modulBez = new String(node.getParent().toString());
		String semesterBez = new String(node.getParent().getParent().toString());
		
		if(semesterBez.contains("WS")){
			ws = true;
		}
		else{
			ws = false;
		}
		semesterYear = Integer.parseInt(semesterBez.replaceAll("[^\\d.]", ""));
		modulNr = Integer.parseInt(modulBez.replaceAll("[^\\d.]", ""));
		veranstaltungsWeek = Integer.parseInt(veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")).replaceAll("[^\\d.]", ""));
		String firstWord = null;
		if(veranstaltungBez.contains(" ")){
		   firstWord = veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")); 
		}
		veranstaltungsTitle = new String(veranstaltungBez.substring(firstWord.length()+1));
		
		//getting Veranstaltung
		Semester semester;
		Modul modul;
		Veranstaltung veranstaltung;
		int i,j,k;
		
		for(i = 0; i < aktLernzielnator.getSemesterListeSize(); i++){
			if( (aktLernzielnator.getSemester(i).isWS() == ws) && (aktLernzielnator.getSemester(i).getSemesterYear() == semesterYear) ){
				break;
			}
		}
		semester = aktLernzielnator.getSemester(i);
		for(j = 0; j < aktLernzielnator.getModulListeSize(i); j++){
			if(semester.getSemesterModul(j).getModulNumber() == modulNr ){
				break;
			}
		}
		modul = semester.getSemesterModul(j);
		for (k = 0; k < aktLernzielnator.getVeranstaltungsListeSize(i, j); k++){
			if( (modul.getModulVeranstaltung(k).getVeranstaltungTitel().equals(veranstaltungsTitle)) && (modul.getModulVeranstaltung(k).getVeranstaltungWoche() == veranstaltungsWeek) ){
				break;
			}
		}
		veranstaltung = modul.getModulVeranstaltung(k);		
								
		return veranstaltung;
	}
	
	private void editNode(){
		Integer error = new Integer(-1);
		CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(node != null){
			if(node.getPath().length == 2){
				//semester
				EditSemester editSemesterDialog = new EditSemester(getFrmLernzielnator(), MainWindow.this, node.getSemester(), aktLernzielnator);
				editSemesterDialog.setVisible(true);
			}
			else{
				if(node.getPath().length == 3){
					//modul
					EditModul editModulDialog = new EditModul(getFrmLernzielnator(), MainWindow.this, node.getModul(), aktLernzielnator);
					editModulDialog.setVisible(true);
				}
				else{
					if(node.getPath().length == 4){
						//veranstaltung
						EditVeranstaltung editVeranstaltungDialog = new EditVeranstaltung(getFrmLernzielnator(), MainWindow.this, node.getVeranstaltung(), aktLernzielnator);
						editVeranstaltungDialog.setVisible(true);
					}
					else{
						if(node.getPath().length == 5){
							//lernziel
							EditLernziel editLernzielDialog = new EditLernziel(getFrmLernzielnator(), MainWindow.this, node.getLernziel(), aktLernzielnator);
							editLernzielDialog.setVisible(true);
						}
						else{
							error = 2;
						}
					}
				}
			}
		}
		else{
			error = 1;
		}
		if(error > 0){
			//display error
			JOptionPane errorPane = new JOptionPane(null);
    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
			switch(error){
			case 1:
				errorPane.setMessage("Bitte eine Node ausw\u00e4hlen!");
				break;
			case 2:
				errorPane.setMessage("Diese Node ist nicht editierbar.");
				break;
			}
    		JDialog dialogError = errorPane.createDialog("Error");
		    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
	}
	

	private void deleteNode() {
		CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(node != null){
			switch(node.getPath().length){
			case 1:
				//alles
				Integer i = new Integer(0);
				for(i = aktLernzielnator.getSemesterListeSize()-1; i >= 0; i--){
					aktLernzielnator.removeSemester(i);
				}
				break;
			case 2:
				//semester
				aktLernzielnator.removeSemester(node.getParent().getIndex(node));
				node.removeFromParent();
				break;
			case 3:
				int sem = node.getParent().getParent().getIndex(node.getParent());
				aktLernzielnator.getSemester(sem).removeSemesterModule(node.getModul());
				break;
			case 4:
				//Veranstaltung
				int mod = node.getParent().getParent().getIndex(node.getParent());
				sem = node.getParent().getParent().getParent().getIndex(node.getParent().getParent());
				aktLernzielnator.getModul(sem, mod).removeModulVeranstaltung(node.getVeranstaltung());
				break;
			case 5:
				//Lernziel
				int ver = node.getParent().getParent().getIndex(node.getParent());
				mod = node.getParent().getParent().getParent().getIndex(node.getParent().getParent());
				sem = node.getParent().getParent().getParent().getParent().getIndex(node.getParent().getParent().getParent());
				aktLernzielnator.getVeranstaltung(sem, mod, ver).removeVeranstaltungLernziel(node.getLernziel());
				break;
			}
			clearInfos();
			updateTree();
		}
		else{
			JOptionPane errorPane = new JOptionPane(null);
    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
			errorPane.setMessage("Keine Node ausgew\u00e4hlt!");
    		JDialog dialogError = errorPane.createDialog("Error");
		    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}		
	}
	
	private void sort(){	//calling quicksort-functions wich are using the Hoare partition scheme
		Integer error = new Integer(-1);
		CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(node != null){
			if(node.getPath().length == 1){
				//alles
				aktLernzielnator.sortTop(0, aktLernzielnator.getSemesterListeSize()-1);
				updateTree();
			}
			else{
				if(node.getPath().length == 2){
					//semester
					aktLernzielnator.sortSemester(node.getSemester(), 0, node.getSemester().getModulListeSize()-1);
					updateTree();
				}
				else{
					if(node.getPath().length == 3){
						//modul
						aktLernzielnator.sortModul(node.getModul(), 0, node.getModul().getVerListeSize()-1);
						updateTree();
					}
					else{
						if(node.getPath().length == 4){
							//veranstaltung
							aktLernzielnator.sortVeranstaltung(node.getVeranstaltung(), 0, node.getVeranstaltung().getLerListeSize()-1 );
							updateTree();
						}
						else{
							if(node.getPath().length == 5){
								//lernziel
								error = 3;
							}
							else{
								error = 2;
							}
						}
					}
				}
			}
		}
		else{
			error = 1;
		}
		if(error > 0){
			//display error
			JOptionPane errorPane = new JOptionPane(null);
    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
			switch(error){
			case 1:
				errorPane.setMessage("Bitte eine Node ausw\u00e4hlen!");
				break;
			case 3:
				errorPane.setMessage("Lernziele haben keine Sub-Nodes, welche sortiert werden k\u00f6nnten.");
				break;
			}
    		JDialog dialogError = errorPane.createDialog("Error");
		    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
	}	
	
	private void newEntryDynamic(){		//adds a new Entry to the DataSet depending on which TreeNode is selected
		Integer error = new Integer(-1);
		CustomDefaultMutableTreeNode node = (CustomDefaultMutableTreeNode) tree.getLastSelectedPathComponent();	//is parent-Node of new Node
		if(node != null){
			if(node.getPath().length == 1){
				//neues Semester
				Semester semester = new Semester();
				AddSemester addSemesterDialog = new AddSemester(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, semester);
				addSemesterDialog.setVisible(true);
			}
			else{
				if(node.getPath().length == 2){
					//neues Modul
					int sem = node.getParent().getIndex(node);
					Modul modul = new Modul();
					AddModul addModulDialog = new AddModul(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, modul, sem);
					addModulDialog.setVisible(true);
				}
				else{
					if(node.getPath().length == 3){
						//neue Veranstaltung
						int mod = node.getParent().getIndex(node);
						int sem = node.getParent().getParent().getIndex(node.getParent());
						Veranstaltung veranstaltung = new Veranstaltung();
						AddVeranstaltung addVeranstaltungDialog = new AddVeranstaltung(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, veranstaltung, mod, sem);
						addVeranstaltungDialog.setVisible(true);
					}
					else{
						if(node.getPath().length == 4){
							//neues Lernziel
							Lernziel lernziel = new Lernziel();
							AddLernziel addLernzielDialog = new AddLernziel(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, node.getVeranstaltung(), lernziel);
							addLernzielDialog.setVisible(true);
						}
						else{
							error = 2;
						}
					}
				}
			}
		}
		else{
			error = 1;
		}
		if(error > 0){
			//display error
			JOptionPane errorPane = new JOptionPane(null);
    		errorPane.setMessageType(JOptionPane.ERROR_MESSAGE );
			switch(error){
			case 1:
				errorPane.setMessage("Bitte eine Node ausw\u00e4hlen!");
				break;
			case 2:
				errorPane.setMessage("An Lernzielen kann nichts angehangen werden.");
			}
    		JDialog dialogError = errorPane.createDialog("Error");
		    dialogError.setLocation(getFrmLernzielnator().getLocation().x +  + (getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , getFrmLernzielnator().getLocation().y + (getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
	}

	public JFrame getFrmLernzielnator() {
		return frmLernzielnator;
	}

	public static void setFrmLernzielnator(JFrame frmLernzielnator) {
		MainWindow.frmLernzielnator = frmLernzielnator;
	}
	
	public CustomDefaultMutableTreeNode getLastSelectedPathComponent(){
		return ((CustomDefaultMutableTreeNode)tree.getLastSelectedPathComponent());
	}
	
	private void openStatistik(){
		int semester = 0;
		int module = 0;
		int veranstaltungen = 0;
		int lernziele = 0;
		int rot = 0;
		int gelb = 0;
		int gruen = 0;
		int grau = 0;
		int MC = 0;
		int SMPP = 0;
		int OSCE = 0;
		int karteikarten = 0;
		int ausarbeitung = 0;
		int lerngruppen = 0;
		int notes = 0;
		int relevant = 0;
		Semester sem;
		Modul mod;
		Veranstaltung ver;
		Lernziel ler;
		
		for(int i = 0; i < aktLernzielnator.getSemesterListeSize(); i ++){
			semester++;
			sem = aktLernzielnator.getSemester(i);
			for(int j = 0; j < aktLernzielnator.getModulListeSize(i); j++){
				module++;
				mod = sem.getSemesterModul(j);
				for(int k = 0; k < aktLernzielnator.getVeranstaltungsListeSize(i, j); k++){
					veranstaltungen++;
					ver = mod.getModulVeranstaltung(k);
					for(int l = 0; l < aktLernzielnator.getLernzielListeSize(i, j, k); l++){
						lernziele++;
						ler = ver.getVeranstaltungLz(l);
						if(ler.isMC()){
							MC++;
						}
						if(ler.isSMPP()){
							SMPP++;
						}
						if(ler.isOSCE()){
							OSCE++;
						}
						if(ler.isKarteikarten()){
							karteikarten++;
						}
						if(ler.isAusarbeitung()){
							ausarbeitung++;
						}
						if(ler.isLerngruppe()){
							lerngruppen++;
						}
						if(!(ler.getLzNotes().isEmpty())){
							notes++;
						}
						if(ler.isRelevant()){
							relevant++;
							if( (ler.isKarteikarten()) && (ler.isAusarbeitung()) ){
								gruen++;
							}
							else
							{
								if( (ler.isKarteikarten()) ^ (ler.isAusarbeitung()) ){
									gelb++;
								}
								else
								{
									rot++;
								}
							}
						}
						else
						{
							grau++;
						}
					}
				}
			}
		}
		//JFrame frame, final mainWindow mainWindow,int semester, int module, int veranstaltungen, int lernziele, int rot, int gelb, int gruen, int grau, int MC, int SMPP, int OSCE, int karteikarten, int ausarbeitung, int lerngruppen, int notes, int relevant, Color ok, Color mittel, Color schlecht, Color neutral
		Statistik statistikDialog = new Statistik(getFrmLernzielnator(), MainWindow.this, aktLernzielnator, semester, module, veranstaltungen, lernziele, rot, gelb, gruen, grau, MC, SMPP, OSCE, karteikarten, ausarbeitung, lerngruppen, notes, relevant, ok, mittel, schlecht, neutral );
		statistikDialog.setVisible(true);
	}
	
	public void updateFontSize(){
		int fontSize = aktLernzielnator.getFontSize();
		Font newStandFontBold = new Font("Tahoma", Font.BOLD, fontSize);
		Font newStandFontPlain = new Font("Tahoma", Font.PLAIN, fontSize);
		//Font newDialFont = new Font("Dialog", Font.PLAIN, fontSize);
		
		tree.setFont(newStandFontPlain);
		textField_Description.setFont(newStandFontPlain);
		textField_Notes.setFont(newStandFontPlain);
		textField_LzDimension.setFont(newStandFontPlain);
		textField_LzKognitionsdimension.setFont(newStandFontPlain);
		chckbxMc.setFont(newStandFontPlain);
		chckbxSmpp.setFont(newStandFontPlain);
		chckbxOsce.setFont(newStandFontPlain);
		chckbxRelevant.setFont(newStandFontPlain);
		chckbxLerngruppe.setFont(newStandFontPlain);
		chckbxKarteikarten.setFont(newStandFontPlain);
		chckbxAusarbeitung.setFont(newStandFontPlain);
		chckbxmntmAlleLernziele.setFont(newStandFontBold);
		chckbxmntmRoteLernziele.setFont(newStandFontBold);
		chckbxmntmGelbeLernziele.setFont(newStandFontBold);
		chckbxmntmGrneLernziele.setFont(newStandFontBold);
		chckbxmntmGraueLernziele.setFont(newStandFontBold);
		chckbxmntmLernzieleMitLerngruppen.setFont(newStandFontBold);
		chckbxmntmLeereNodes.setFont(newStandFontBold);
		popUpNew.setFont(newStandFontPlain);
		popUpEdit.setFont(newStandFontPlain);
		popUpDelete.setFont(newStandFontPlain);
		popUpUp.setFont(newStandFontPlain);
		popUpDown.setFont(newStandFontPlain);
		popUpSort.setFont(newStandFontPlain);
		menuBar.setFont(newStandFontBold);
		mnData.setFont(newStandFontBold);
		mntmLaden.setFont(newStandFontBold);
		mntmSpeichern.setFont(newStandFontBold);
		mnExportieren.setFont(newStandFontBold);
		mntmSemester_1.setFont(newStandFontBold);
		mntmModul_1.setFont(newStandFontBold);
		mntmVeranstaltung_1.setFont(newStandFontBold);
		mntmLernziel_1.setFont(newStandFontBold);
		mntmImportieren.setFont(newStandFontBold);
		mntmSuchen.setFont(newStandFontBold);
		mnEdit.setFont(newStandFontBold);
		mntmLsche.setFont(newStandFontBold);
		mntmEditiere.setFont(newStandFontBold);
		mntmUp.setFont(newStandFontBold);
		mntmDown.setFont(newStandFontBold);
		mntmSort.setFont(newStandFontBold);
		mnNeu.setFont(newStandFontBold);
		mntmSemester.setFont(newStandFontBold);
		mntmModul.setFont(newStandFontBold);
		mntmVeranstaltung.setFont(newStandFontBold);
		mntmLernziel.setFont(newStandFontBold);
		mnAnsicht.setFont(newStandFontBold);
		mntmAnzeigen.setFont(newStandFontBold);
		mntmCollapsNodes.setFont(newStandFontBold);
		mntmExpandNodes.setFont(newStandFontBold);
		mnQuestion.setFont(newStandFontBold);
		mntmAbout.setFont(newStandFontBold);
		mntmStatistik.setFont(newStandFontBold);
		mntmAnleitung.setFont(newStandFontBold);
		mntmEinstellungen.setFont(newStandFontBold);
		
		setUIFont(new javax.swing.plaf.FontUIResource("Tahoma",Font.PLAIN,fontSize));
	}
	
	private static void setUIFont(javax.swing.plaf.FontUIResource f){
	    Enumeration<Object> keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get(key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put(key, f);
	    }
	} 
	
}
