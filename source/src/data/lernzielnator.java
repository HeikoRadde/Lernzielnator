package data;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import util.CustomDefaultMutableTreeNode;
import util.LZ_Dimension;
import util.LZ_Kognitionsdimension;
import windows.mainWindow;

public class lernzielnator {
	private ArrayList<semester> semesterListe = new ArrayList<semester>();
	private lernziel aktLernziel;
	
	private Color ok = Color.GREEN;
	private Color mittel = Color.ORANGE;
	private Color schlecht = Color.RED;
	private Color neutral = Color.GRAY;
	
	private String aktFile;
	private static mainWindow window;
	private static lernzielnator aktLernzielnator;
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				aktLernzielnator = new lernzielnator();
				try {
					window = new mainWindow(aktLernzielnator);
					window.getFrmLernzielnator().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				checkVersion();
			}
		});
	}
	
	public lernzielnator(){
		
	}
	
	public void setAktLernziel(lernziel newLernziel){
		aktLernziel = newLernziel;
	}
	
	public lernziel getAktLernziel(){
		return aktLernziel;
	}
	
	public String getAktFile(){
		return aktFile;
	}
	
	public void setAktFile(String newFile){
		aktFile = newFile;
	}
	
	
	public boolean readFile(File file){		
		String csvFile = new String( file.getAbsolutePath() );
		BufferedReader br = null;
		String line = new String("");
		String cvsSplitBy = new String(";");
		boolean foundData = false;
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] inputs = line.split(cvsSplitBy);
				if(inputs[0].equals("Modul")){
					if(inputs.length == 10){	//Datei von Uni
						readFileUni(file);
						foundData = true;
					}
					else if(inputs.length == 15){	//Datei von Programm
						readFileOwn(file);
						foundData = true;
					}
					break;
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if( !(foundData) ){
			//Display Error Message
			JOptionPane pane = new JOptionPane(null);
		    //configure
			pane.setMessageType(JOptionPane.ERROR_MESSAGE );
			pane.setMessage("Die Datei beinh‰llt keine verwertbaren Daten!");
		    JDialog dialogError = pane.createDialog("Error");
		    dialogError.setLocation(window.getFrmLernzielnator().getLocation().x +  + (window.getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , window.getFrmLernzielnator().getLocation().y + (window.getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
		window.updateTree();
		return foundData;
	}
	
	//TODO: abs‰tze abfangen
	private void readFileUni(File file){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedReader br = null;
		String line = new String("");
		String cvsSplitBy = new String(";");
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();	//disposing the first line
			while ((line = br.readLine()) != null) {
				boolean ws;
				int year;
				int week;
				int modulNr;
				boolean mc = new Boolean(false);
				boolean smpp = new Boolean(false);
				boolean osce = new Boolean(false);
				boolean relevant = new Boolean(true);
				LZ_Dimension LzDimension;
				LZ_Kognitionsdimension LzKognitionsdimension;
				String[] inputs = new String[10];
				String vTitle;
				String description;
				
				inputs = line.split(cvsSplitBy,10);
				modulNr = Integer.parseInt(inputs[0].replaceAll("[^\\d.]", ""));
				if(inputs[1].contains("WiSe") || inputs[1].contains("WS") || inputs[1].contains("ws") || inputs[1].contains("Ws")){
					ws = true;
				}
				else
				{
					ws = false;
				}
				year = Integer.parseInt(inputs[1].replaceAll("[^\\d.]", ""));
				
				week = Integer.parseInt(inputs[2].replaceAll("[^\\d.]", ""));
				
				vTitle = new String(inputs[3]);
				
				if(Array.getLength(inputs) < 5){	//then at least one newline-chartacter in title of veranstaltung & its the first one
					do{
						line = br.readLine();
						inputs = line.split(cvsSplitBy,15);
						String hand = new String(vTitle);
						vTitle = new String(hand + "\n" + inputs[0]);
					} while(Array.getLength(inputs) < 2);
					
					LzDimension = LZ_Dimension.parse(inputs[1]);
					
					LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[2]);
					
					description = new String(inputs[3]);
					
					if(Array.getLength(inputs) < 5){	// also lernziel-description contains newline-characters
						do{
							line = br.readLine();
							inputs = line.split(cvsSplitBy,15);
							String hand = new String(description);
							description = new String(hand + "\n" + inputs[0]);
						} while(Array.getLength(inputs) < 2);
						
						if( inputs[1].equals("x") ){
							mc = true;
						}
						
						if( inputs[2].equals("x") ){
							smpp = true;
						}
						
						if( inputs[3].equals("x") ){
							osce = true;
						}
					}
					else{	//end if contains newline-character in description
						if( inputs[4].equals("x") ){
							mc = true;
						}
						
						if( inputs[5].equals("x") ){
							smpp = true;
						}
						
						if( inputs[6].equals("x") ){
							osce = true;
						}
					}
				}	//end if contains newline-character in title
				else{
					if(Array.getLength(inputs) < 8){	//then at least one newline-chartacter in description of veranstaltung & its the first one
						
						LzDimension = LZ_Dimension.parse(inputs[4]);
						
						LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[5]);
						
						description = new String(inputs[6]);
						
						do{
							line = br.readLine();
							inputs = line.split(cvsSplitBy,15);
							String hand = new String(description);
							description = new String(hand + "\n" + inputs[0]);
						} while(inputs.length < 2);
												
						if( inputs[1].equals("x") ){
							mc = true;
						}
						
						if( inputs[2].equals("x") ){
							smpp = true;
						}
						
						if( inputs[3].equals("x") ){
							osce = true;
						}
					}	//end if contains newline-character only in description
					else{
						//contains no newline-character
						LzDimension = LZ_Dimension.parse(inputs[4]);
						
						LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[5]);
						
						description = new String(inputs[6]);
						
						if( inputs[7].equals("x") ){
							mc = true;
						}
						
						if( inputs[8].equals("x") ){
							smpp = true;
						}
						
						if( inputs[9].equals("x") ){
							osce = true;
						}
					}
				}
				
				if((!mc) && (!smpp) && (!osce)){
					relevant = false;
				}
				
				int semesterId = searchSemester(ws, year);
				if(semesterId >= 0){
					int modulId = semesterListe.get(semesterId).searchModul(modulNr);
					if(modulId >= 0){
						int veranstaltungId = semesterListe.get(semesterId).getSemesterModul(modulId).searchVeranstaltung(week, vTitle);
						if(veranstaltungId >= 0){
							if( !(semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).countainsLernziel(description)) ){
								//nur Lernziel ist neu
								lernziel newLz = new lernziel(description, mc, smpp, osce, LzDimension, LzKognitionsdimension);
								newLz.setRelevant(relevant);
								semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).addVeranstaltungLernziel(newLz);
							}
						}
						else{
							//alles einschlieﬂlich Veranstaltung ist neu
							veranstaltung newVs = new veranstaltung(week, vTitle);
							semesterListe.get(semesterId).getSemesterModul(modulId).addModulVerantaltung(newVs);
							veranstaltungId = semesterListe.get(semesterId).getSemesterModul(modulId).veranstaltungen.indexOf(newVs);
							lernziel newLz = new lernziel(description, mc, smpp, osce, LzDimension, LzKognitionsdimension);
							newLz.setRelevant(relevant);
							semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).addVeranstaltungLernziel(newLz);
						}
					}
					else{
						//alles einschlieﬂlich Modul ist neu
						modul newModul = new modul(modulNr);
						modulId = semesterListe.get(semesterId).module.size();
						semesterListe.get(semesterId).addSemesterModul(newModul);
						modulNr = semesterListe.get(semesterId).module.indexOf(newModul);
						veranstaltung newVs = new veranstaltung(week, vTitle);
						semesterListe.get(semesterId).getSemesterModul(modulId).addModulVerantaltung(newVs);
						lernziel newLz = new lernziel(description, mc, smpp, osce, LzDimension, LzKognitionsdimension);
						newLz.setRelevant(relevant);
						semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(0).addVeranstaltungLernziel(newLz);
					}
				}
				else{
					//alles einschlieﬂlich Semester ist neu
					semester newSem = new semester(year, ws);
					semesterListe.add(newSem);
					semesterId = semesterListe.indexOf(newSem);
					modul newModul = new modul(modulNr);
					semesterListe.get(semesterId).addSemesterModul(newModul);
					veranstaltung newVs = new veranstaltung(week, vTitle);
					semesterListe.get(semesterId).getSemesterModul(0).addModulVerantaltung(newVs);
					lernziel newLz = new lernziel(description, mc, smpp, osce, LzDimension, LzKognitionsdimension);
					newLz.setRelevant(relevant);
					semesterListe.get(semesterId).getSemesterModul(0).getModulVeranstaltung(0).addVeranstaltungLernziel(newLz);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void readFileOwn(File file){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedReader br = null;
		String line = new String("");
		String cvsSplitBy = new String(";");
		
		try {
			br = new BufferedReader(new FileReader(csvFile));
			line = br.readLine();	//disposing the first line
			while ((line = br.readLine()) != null) {
				boolean ws;
				int year;
				int week;
				int modulNr;
				boolean mc = new Boolean(false);
				boolean smpp = new Boolean(false);
				boolean osce = new Boolean(false);
				LZ_Dimension LzDimension;
				LZ_Kognitionsdimension LzKognitionsdimension;
				boolean karteikarten = new Boolean (false);
				boolean ausarbeitung  = new Boolean (false);
				boolean lerngruppe  = new Boolean (false);
				boolean relevant = new Boolean(true);
				String[] inputs = new String[15];
				String description;
				String notes;
				String vTitle;
				
				inputs = line.split(cvsSplitBy,15);
				modulNr = Integer.parseInt(inputs[0].replaceAll("[^\\d.]", ""));
				if(inputs[1].contains("WiSe") || inputs[1].contains("WS") || inputs[1].contains("ws") || inputs[1].contains("Ws")){
					ws = true;
				}
				else
				{
					ws = false;
				}
				year = Integer.parseInt(inputs[1].replaceAll("[^\\d.]", ""));
				week = Integer.parseInt(inputs[2].replaceAll("[^\\d.]", ""));		
				vTitle = new String(inputs[3]);
				if(Array.getLength(inputs) < 5){	//then at least one newline-chartacter in title of veranstaltung & its the first one
					do{
						line = br.readLine();
						inputs = line.split(cvsSplitBy,15);
						String hand = new String(vTitle);
						vTitle = new String(hand + "\n" + inputs[0]);
					} while(Array.getLength(inputs) < 2);
					
					LzDimension = LZ_Dimension.parse(inputs[1]);
					
					LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[2]);
					
					description = new String(inputs[3]);
					
					if(Array.getLength(inputs) < 5){	// also lernziel-description contains newline-characters
						do{
							line = br.readLine();
							inputs = line.split(cvsSplitBy,15);
							String hand = new String(description);
							description = new String(hand + "\n" + inputs[0]);
						} while(Array.getLength(inputs) < 2);
						
						if( inputs[1].equals("x") ){
							mc = true;
						}
						
						if( inputs[2].equals("x") ){
							smpp = true;
						}
						
						if( inputs[3].equals("x") ){
							osce = true;
						}
						
						notes = new String(inputs[4]);
						
						if(Array.getLength(inputs) < 6){	//also notes contains newline-characters
							do{
								line = br.readLine();
								inputs = line.split(cvsSplitBy,15);
								String hand = new String(notes);
								notes = new String(hand + "\n" + inputs[0]);
							} while(Array.getLength(inputs) < 2);
							
							if( inputs[1].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[2].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[3].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[4].equals("x") ){
								relevant = true;
							}
						}//end if notes contains newline-characters
						else{//if no newline-character in notes, but ones in title and description
							if( inputs[5].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[6].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[7].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[8].equals("x") ){
								relevant = true;
							}
						}
						
					}//end if lernziel-description contains newline-characters
					else{
						//only title of veranstaltung with newline-character
						if( inputs[4].equals("x") ){
							mc = true;
						}
						
						if( inputs[5].equals("x") ){
							smpp = true;
						}
						
						if( inputs[6].equals("x") ){
							osce = true;
						}
						
						notes = new String(inputs[7]);
						

						if(Array.getLength(inputs) < 9){	//the  also newline-character in notes
							do{
								line = br.readLine();
								inputs = line.split(cvsSplitBy,15);
								String hand = new String(notes);
								notes = new String(hand + "\n" + inputs[0]);
							} while(Array.getLength(inputs) < 2);
							
							if( inputs[1].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[2].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[3].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[4].equals("x") ){
								relevant = true;
							}							
						}//end if title and notes contain newline-character
						else{	//only title contains newline-character
							if( inputs[8].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[9].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[10].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[11].equals("x") ){
								relevant = true;
							}
						}						
					}					
				}//end if title of veranstaltung contains newline-charactetr
				else{
					if(Array.getLength(inputs) < 8){	//then at least one newline-chartacter in description of veranstaltung & its the first one
						
						LzDimension = LZ_Dimension.parse(inputs[4]);
						
						LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[5]);
						
						description = new String(inputs[6]);
						
						do{
							line = br.readLine();
							inputs = line.split(cvsSplitBy,15);
							String hand = new String(description);
							description = new String(hand + "\n" + inputs[0]);
						} while(inputs.length < 2);
												
						if( inputs[1].equals("x") ){
							mc = true;
						}
						
						if( inputs[2].equals("x") ){
							smpp = true;
						}
						
						if( inputs[3].equals("x") ){
							osce = true;
						}
						
						notes = new String(inputs[4]);
						
						if(Array.getLength(inputs) < 6){	//then at least one newline-character in notes
							do{
								line = br.readLine();
								inputs = line.split(cvsSplitBy,15);
								String hand = new String(notes);
								notes = new String(hand + "\n" + inputs[0]);
							} while(inputs.length < 2);
							
							if( inputs[1].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[2].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[3].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[4].equals("x") ){
								relevant = true;
							}
						}
						else{
							if( inputs[5].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[6].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[7].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[8].equals("x") ){
								relevant = true;
							}						
						}
					}
					else{						
						if(Array.getLength(inputs) < 12){	//then at least one newline-chartacter in notes & its the first one
							LzDimension = LZ_Dimension.parse(inputs[4]);
							
							LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[5]);					

							description = new String(inputs[6]);
							
							if( inputs[7].equals("x") ){
								mc = true;
							}
							
							if( inputs[8].equals("x") ){
								smpp = true;
							}
							
							if( inputs[9].equals("x") ){
								osce = true;
							}
							
							notes = new String(inputs[10]);
							
							do{
								line = br.readLine();
								inputs = line.split(cvsSplitBy,15);
								String hand = new String(notes);
								notes = new String(hand + "\n" + inputs[0]);
							} while(inputs.length < 2);
							
							if( inputs[1].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[2].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[3].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[4].equals("x") ){
								relevant = true;
							}
						}
						else{
							//no newline-character
							
							LzDimension = LZ_Dimension.parse(inputs[4]);
							
							LzKognitionsdimension = LZ_Kognitionsdimension.parse(inputs[5]);					

							description = new String(inputs[6]);
							
							if( inputs[7].equals("x") ){
								mc = true;
							}
							
							if( inputs[8].equals("x") ){
								smpp = true;
							}
							
							if( inputs[9].equals("x") ){
								osce = true;
							}
							
							notes = new String(inputs[10]);
							
							if( inputs[11].equals("x") ){
								karteikarten = true;
							}
							
							if( inputs[12].equals("x") ){
								ausarbeitung = true;
							}
							
							if( inputs[13].equals("x") ){
								lerngruppe = true;
							}
							
							if( inputs[14].equals("x") ){
								relevant = true;
							}
						}						
					}	
				}
				
				
				
				
				int semesterId = searchSemester(ws, year);
				if(semesterId >= 0){
					int modulId = semesterListe.get(semesterId).searchModul(modulNr);
					if(modulId >= 0){
						int veranstaltungId = semesterListe.get(semesterId).getSemesterModul(modulId).searchVeranstaltung(week, vTitle);
						if(veranstaltungId >= 0){
							if( !(semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).countainsLernziel(inputs[6])) ){
								//nur Lernziel ist neu
								lernziel newLz = new lernziel(description, notes, mc, smpp, osce, LzDimension, karteikarten, ausarbeitung, lerngruppe, LzKognitionsdimension, relevant);
								newLz.setRelevant(relevant);
								semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).addVeranstaltungLernziel(newLz);
							}
						}
						else{
							//alles einschlieﬂlich Veranstaltung ist neu
							veranstaltung newVs = new veranstaltung(week, vTitle);
							semesterListe.get(semesterId).getSemesterModul(modulId).addModulVerantaltung(newVs);
							veranstaltungId = semesterListe.get(semesterId).getSemesterModul(modulId).veranstaltungen.indexOf(newVs);
							lernziel newLz = new lernziel(description, notes, mc, smpp, osce, LzDimension, karteikarten, ausarbeitung, lerngruppe, LzKognitionsdimension, relevant);
							newLz.setRelevant(relevant);
							semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(veranstaltungId).addVeranstaltungLernziel(newLz);
						}
					}
					else{
						//alles einschlieﬂlich Modul ist neu
						modul newModul = new modul(modulNr);
						modulId = semesterListe.get(semesterId).module.size();
						semesterListe.get(semesterId).addSemesterModul(newModul);
						modulNr = semesterListe.get(semesterId).module.indexOf(newModul);
						veranstaltung newVs = new veranstaltung(week, vTitle);
						semesterListe.get(semesterId).getSemesterModul(modulId).addModulVerantaltung(newVs);
						lernziel newLz = new lernziel(description, notes, mc, smpp, osce, LzDimension, karteikarten, ausarbeitung, lerngruppe, LzKognitionsdimension, relevant);
						newLz.setRelevant(relevant);
						semesterListe.get(semesterId).getSemesterModul(modulId).getModulVeranstaltung(0).addVeranstaltungLernziel(newLz);
					}
				}
				else{
					//alles einschlieﬂlich Semester ist neu
					semester newSem = new semester(year, ws);
					semesterListe.add(newSem);
					semesterId = semesterListe.indexOf(newSem);
					modul newModul = new modul(modulNr);
					semesterListe.get(semesterId).addSemesterModul(newModul);
					veranstaltung newVs = new veranstaltung(week, vTitle);
					semesterListe.get(semesterId).getSemesterModul(0).addModulVerantaltung(newVs);
					lernziel newLz = new lernziel(description, notes, mc, smpp, osce, LzDimension, karteikarten, ausarbeitung, lerngruppe, LzKognitionsdimension, relevant);
					newLz.setRelevant(relevant);
					semesterListe.get(semesterId).getSemesterModul(0).getModulVeranstaltung(0).addVeranstaltungLernziel(newLz);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void saveFile(File file){
		/*
		 * Integer modulNumber;								 Modul
		 * Integer year;									|
		 * Boolean ws;										|akad. Periode
		 * Integer week;									 Woche
		 * String title;									 Veranstaltung: Titel
		 * LZ_Dimension lzDimension;						 LZ-Dimension
		 * LZ_Kognitionsdimension lzKognitionsdimension;	 LZ-Kognitionsdimension
		 * String description;								 Lernziel
		 * boolean mc;										 MC
		 * boolean smpp;									 SMPP
		 * boolean osce;									 OSCE
		 * String notes;									 Notizen
		 * boolean karteikarten;							 Karteikarten
		 * boolean ausarbeitung;							 Ausarbeitung
		 * boolean lerngruppe;								 Lerngruppe
		 * boolean relevant;								 relevant
		 * */
		String csvFile = new String( file.getAbsolutePath() );
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(csvFile));
			bw.write("Modul;akad. Periode;Woche;Veranstaltung: Titel;LZ-Dimension;LZ-Kognitionsdimension;Lernziel;MC;SMPP;OSCE;Notizen;Karteikarten;Ausarbeitung;Lerngruppe;relevant");
			bw.newLine();
			
			String aktLine;
			
			for(int sem = 0; sem < semesterListe.size(); sem++){
				for(int mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
					for(int ver = 0; ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size(); ver++){
						for(int lz = 0; lz < semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.size(); lz++){
							aktLine = new String(
									"M" + Integer.toString(semesterListe.get(sem).getSemesterModul(mod).getModulNumber()) + ";" //Modul
									+ (semesterListe.get(sem).isWS()?"WiSe":"SoSe") + Integer.toString(semesterListe.get(sem).getSemesterYear()) + ";"	//akad. Periode
									+ "MW " + Integer.toString(semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche()) + ";"	//Woche
									+ semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel() + ";"	// Veranstaltung: Titel
									+ semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDimension().toString() + ";"	//LZ-Dimension
									+ semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzKognitionsdimension().toString() + ";"	//LZ-Kognitionsdimension
									+ semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDescription() + ";"	//Lernziel
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isMC()?"x":"") + ";"	//MC
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isSMPP()?"x":"") + ";"	//SMPP
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isOSCE()?"x":"") + ";"	//OSCE
									+ semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzNotes() + ";"	//Notizen
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isKarteikarten()?"x":"") + ";"	//Karteikarten
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isAusarbeitung()?"x":"") + ";"	//Ausarbeitung
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isLerngruppe()?"x":"") + ";"	//Lerngruppe
									+ (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isRelevant()?"x":"") );	//relevant
							bw.write(aktLine);
							bw.newLine();
						}
					}
				}
			}
			
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void exportSemester(File file, semester semester){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(csvFile));
			bw.write("Modul;akad. Periode;Woche;Veranstaltung: Titel;LZ-Dimension;LZ-Kognitionsdimension;Lernziel;MC;SMPP;OSCE;Notizen;Karteikarten;Ausarbeitung;Lerngruppe;relevant");
			bw.newLine();
			
			String aktLine;
			for(int mod = 0; mod < semester.module.size(); mod++){
				for(int ver = 0; ver < semester.getSemesterModul(mod).veranstaltungen.size(); ver++){
					for(int lz = 0; lz < semester.getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.size(); lz++){
						aktLine = new String(
								"M" + Integer.toString(semester.getSemesterModul(mod).getModulNumber()) + ";" //Modul
								+ (semester.isWS()?"WiSe":"SoSe") + Integer.toString(semester.getSemesterYear()) + ";"	//akad. Periode
								+ "MW " + Integer.toString(semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche()) + ";"	//Woche
								+ semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel() + ";"	// Veranstaltung: Titel
								+ semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDimension().toString() + ";"	//LZ-Dimension
								+ semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzKognitionsdimension().toString() + ";"	//LZ-Kognitionsdimension
								+ semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDescription() + ";"	//Lernziel
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isMC()?"x":"") + ";"	//MC
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isSMPP()?"x":"") + ";"	//SMPP
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isOSCE()?"x":"") + ";"	//OSCE
								+ semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzNotes() + ";"	//Notizen
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isKarteikarten()?"x":"") + ";"	//Karteikarten
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isAusarbeitung()?"x":"") + ";"	//Ausarbeitung
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isLerngruppe()?"x":"") + ";"	//Lerngruppe
								+ (semester.getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz).isRelevant()?"x":"") );	//relevant
						bw.write(aktLine);
						bw.newLine();
					}
				}
			}
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void exportModul(File file, semester semester, modul modul){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(csvFile));
			bw.write("Modul;akad. Periode;Woche;Veranstaltung: Titel;LZ-Dimension;LZ-Kognitionsdimension;Lernziel;MC;SMPP;OSCE;Notizen;Karteikarten;Ausarbeitung;Lerngruppe;relevant");
			bw.newLine();
			
			String aktLine;
			
			for(int ver = 0; ver < modul.veranstaltungen.size(); ver++){
				for(int lz = 0; lz < modul.getModulVeranstaltung(ver).lernziele.size(); lz++){
					aktLine = new String(
							"M" + Integer.toString(modul.getModulNumber()) + ";" //Modul
							+ (semester.isWS()?"WiSe":"SoSe") + Integer.toString(semester.getSemesterYear()) + ";"	//akad. Periode
							+ "MW " + Integer.toString(modul.getModulVeranstaltung(ver).getVeranstaltungWoche()) + ";"	//Woche
							+ modul.getModulVeranstaltung(ver).getVeranstaltungTitel() + ";"	// Veranstaltung: Titel
							+ modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDimension().toString() + ";"	//LZ-Dimension
							+ modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzKognitionsdimension().toString() + ";"	//LZ-Kognitionsdimension
							+ modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzDescription() + ";"	//Lernziel
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isMC()?"x":"") + ";"	//MC
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isSMPP()?"x":"") + ";"	//SMPP
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isOSCE()?"x":"") + ";"	//OSCE
							+ modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).getLzNotes() + ";"	//Notizen
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isKarteikarten()?"x":"") + ";"	//Karteikarten
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isAusarbeitung()?"x":"") + ";"	//Ausarbeitung
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isLerngruppe()?"x":"") + ";"	//Lerngruppe
							+ (modul.getModulVeranstaltung(ver).getVeranstaltungLz(lz).isRelevant()?"x":"") );	//relevant
					bw.write(aktLine);
					bw.newLine();
				}
			}			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void exportVeranstaltung(File file, semester semester, modul modul, veranstaltung veranstaltung){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(csvFile));
			bw.write("Modul;akad. Periode;Woche;Veranstaltung: Titel;LZ-Dimension;LZ-Kognitionsdimension;Lernziel;MC;SMPP;OSCE;Notizen;Karteikarten;Ausarbeitung;Lerngruppe;relevant");
			bw.newLine();
			
			String aktLine;
			
			
			for(int lz = 0; lz < veranstaltung.lernziele.size(); lz++){
				aktLine = new String(
						"M" + Integer.toString(modul.getModulNumber()) + ";" //Modul
						+ (semester.isWS()?"WiSe":"SoSe") + Integer.toString(semester.getSemesterYear()) + ";"	//akad. Periode
						+ "MW " + Integer.toString(veranstaltung.getVeranstaltungWoche()) + ";"	//Woche
						+ veranstaltung.getVeranstaltungTitel() + ";"	// Veranstaltung: Titel
						+ veranstaltung.getVeranstaltungLz(lz).getLzDimension().toString() + ";"	//LZ-Dimension
						+ veranstaltung.getVeranstaltungLz(lz).getLzKognitionsdimension().toString() + ";"	//LZ-Kognitionsdimension
						+ veranstaltung.getVeranstaltungLz(lz).getLzDescription() + ";"	//Lernziel
						+ (veranstaltung.getVeranstaltungLz(lz).isMC()?"x":"") + ";"	//MC
						+ (veranstaltung.getVeranstaltungLz(lz).isSMPP()?"x":"") + ";"	//SMPP
						+ (veranstaltung.getVeranstaltungLz(lz).isOSCE()?"x":"") + ";"	//OSCE
						+ veranstaltung.getVeranstaltungLz(lz).getLzNotes() + ";"	//Notizen
						+ (veranstaltung.getVeranstaltungLz(lz).isKarteikarten()?"x":"") + ";"	//Karteikarten
						+ (veranstaltung.getVeranstaltungLz(lz).isAusarbeitung()?"x":"") + ";"	//Ausarbeitung
						+ (veranstaltung.getVeranstaltungLz(lz).isLerngruppe()?"x":"") + ";"	//Lerngruppe
						+ (veranstaltung.getVeranstaltungLz(lz).isRelevant()?"x":"") );	//relevant
				bw.write(aktLine);
				bw.newLine();
			}		
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void exportLernziel(File file, semester semester, modul modul, veranstaltung veranstaltung, lernziel lernziel){
		String csvFile = new String( file.getAbsolutePath() );
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(csvFile));
			bw.write("Modul;akad. Periode;Woche;Veranstaltung: Titel;LZ-Dimension;LZ-Kognitionsdimension;Lernziel;MC;SMPP;OSCE;Notizen;Karteikarten;Ausarbeitung;Lerngruppe;relevant");
			bw.newLine();
			
			String aktLine;
			
			
			
			aktLine = new String(
					"M" + Integer.toString(modul.getModulNumber()) + ";" //Modul
					+ (semester.isWS()?"WiSe":"SoSe") + Integer.toString(semester.getSemesterYear()) + ";"	//akad. Periode
					+ "MW " + Integer.toString(veranstaltung.getVeranstaltungWoche()) + ";"	//Woche
					+ veranstaltung.getVeranstaltungTitel() + ";"	// Veranstaltung: Titel
					+ lernziel.getLzDimension().toString() + ";"	//LZ-Dimension
					+ lernziel.getLzKognitionsdimension().toString() + ";"	//LZ-Kognitionsdimension
					+ lernziel.getLzDescription() + ";"	//Lernziel
					+ (lernziel.isMC()?"x":"") + ";"	//MC
					+ (lernziel.isSMPP()?"x":"") + ";"	//SMPP
					+ (lernziel.isOSCE()?"x":"") + ";"	//OSCE
					+ lernziel.getLzNotes() + ";"	//Notizen
					+ (lernziel.isKarteikarten()?"x":"") + ";"	//Karteikarten
					+ (lernziel.isAusarbeitung()?"x":"") + ";"	//Ausarbeitung
					+ (lernziel.isLerngruppe()?"x":"") + ";"	//Lerngruppe
					+ (lernziel.isRelevant()?"x":"") );	//relevant
			bw.write(aktLine);
			bw.newLine();			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	private int searchSemester(boolean ws, int year){
		int i;		
		for(i = 0; i < semesterListe.size(); i++){
			if( (semesterListe.get(i).isWS() == ws) && (semesterListe.get(i).getSemesterYear() == year) ){
				return i;
			}
		}
		return -1;
	}
	
	public semester getSemester(int index){
		return semesterListe.get(index);
	}
	
	public void removeSemester(int index){
		semesterListe.remove(index);
	}
	
	public int getSemesterListeSize(){
		return semesterListe.size();
	}
	
	public modul getModul(int indexSemester, int indexModul){
		return semesterListe.get(indexSemester).getSemesterModul(indexModul);
	}
	
	public int getModulListeSize(int indexSemester){
		return semesterListe.get(indexSemester).module.size();
	}
	
	public veranstaltung getVeranstaltung(int indexSemester, int indexModul, int indexVeranstaltung){
		return semesterListe.get(indexSemester).getSemesterModul(indexModul).getModulVeranstaltung(indexVeranstaltung);
	}
	
	public int getVeranstaltungsListeSize(int indexSemester, int indexModul){
		return semesterListe.get(indexSemester).getSemesterModul(indexModul).veranstaltungen.size();
	}
	
	public lernziel getLernziel(int indexSemester, int indexModul, int indexVeranstaltung, int indexLernziel){
		return semesterListe.get(indexSemester).getSemesterModul(indexModul).getModulVeranstaltung(indexVeranstaltung).getVeranstaltungLz(indexLernziel);
	}
	
	public int getLernzielListeSize(int indexSemester, int indexModul, int indexVeranstaltung){
		return semesterListe.get(indexSemester).getSemesterModul(indexModul).getModulVeranstaltung(indexVeranstaltung).lernziele.size();
	}
	
	public void addSemester(semester semester){
		if(semester.getSemesterYear() != -1){
			semesterListe.add(semester);
			window.updateTree();
		}
	}
	
	public void addModul(int semester, boolean ws, modul modul){
		if(modul.getModulNumber() != -1){
			if(!(semesterListe.isEmpty())){
				semester aktSemester;
				int i = -1;
				do{	//finding the selected semester
					i++;
					aktSemester = semesterListe.get(i);					
				}while( (aktSemester.getSemesterYear() != semester) && (aktSemester.isWS() != ws) );
				semesterListe.get(i).addSemesterModul(modul);
				window.updateTree();
			}
		}
	}
	
	public void addVeranstaltung(modul modul, veranstaltung veranstaltung){
		if(veranstaltung.getVeranstaltungWoche() != -1){
			modul.addModulVerantaltung(veranstaltung);
			window.updateTree();
		}
	}
	
	public void addLernziel(veranstaltung veranstaltung, lernziel lernziel){
		if(!(lernziel.getLzDescription().isEmpty())){
			veranstaltung.addVeranstaltungLernziel(lernziel);
		}
	}
	
	
	public void sortTop(int left, int right){	//sorts the semesters in the list by year and ws/ss
		if(left < right){
			int p = sortTopPartition(left, right);
			sortTopPartition(left, p);
			sortTopPartition(p+1, right);
		}
	}
	
	private int sortTopPartition(int left, int right){
		int i = left-1;
		int j = right+1;
		semester pivot = semesterListe.get(left);
		do{
			do{
				i++;
			}while(sortSemesterComparing(semesterListe.get(i), pivot) < 0 );
			do{
				j--;
			}while(sortSemesterComparing(semesterListe.get(j), pivot) > 0 );
			if(i >= j){
				return j;
			}
			//swap
			semester hand = semesterListe.get(i);
			semesterListe.set(i, semesterListe.get(j));
			semesterListe.set(j,hand);
		}while(true); 
	}

	private int sortSemesterComparing(semester semesterA, semester semesterB){
		if(semesterA.isWS()){
			if(semesterB.isWS()){
				//both WS
				if(semesterA.getSemesterYear() < semesterB.getSemesterYear()){
					return -1;
				}
				else{
					if(semesterA.getSemesterYear() == semesterB.getSemesterYear()){
						return 0;
					}
					else{
						return 1;
					}
				}
			}
			else{
				//A is WS, B is SS
				if(semesterA.getSemesterYear() < semesterB.getSemesterYear()){
					return -1;
				}
				else{
					if(semesterA.getSemesterYear() == semesterB.getSemesterYear()){
						return -1;//SS vor WS (SS2015 --> WS2015 --> SS2016 --> WS2016)
					}
					else{
						return 1;
					}
				}
			}
		}
		else{
			if(semesterB.isSS()){
				//both SS
				if(semesterA.getSemesterYear() < semesterB.getSemesterYear()){
					return -1;
				}
				else{
					if(semesterA.getSemesterYear() == semesterB.getSemesterYear()){
						return 0;
					}
					else{
						return 1;
					}
				}
			}
			else{
				//A is SS, B is WS
				if(semesterA.getSemesterYear() < semesterB.getSemesterYear()){
					return -1;
				}
				else{
					if(semesterA.getSemesterYear() == semesterB.getSemesterYear()){
						return 1; //SS vor WS (SS2015 --> WS2015 --> SS2016 --> WS2016)
					}
					else{
						return 1;
					}
				}
			}
		}
	}
	
	public void sortSemester(semester semester, int left, int right){	//sorts the modules of a semester by number
		if(left < right){
			int p = sortSemesterPartition(semester, left, right);
			sortSemester(semester, left, p);
			sortSemester(semester, p+1, right);
		}
	}
	
	private int sortSemesterPartition(semester semester, int left, int right){
		int i = left-1;
		int j = right+1;
		modul pivot = semester.getSemesterModul(left);
		do{
			do{
				i++;
			}while( semester.getSemesterModul(i).getModulNumber().compareTo(pivot.getModulNumber()) < 0 );
			do{
				j--;
			}while( (semester.getSemesterModul(j).getModulNumber().compareTo(pivot.getModulNumber()) > 0) );
			if(i >= j){
				return j;
			}
			//swap
			modul hand = semester.getSemesterModul(i);
			semester.module.set(i, semester.getSemesterModul(j));
			semester.module.set(j,hand);
		}while(true); 
	}
		
	public void sortModul(modul modul, int left, int right){	//sorts the veranstaltungen in a modul by title
		if(left < right){
			int p = sortModulPartition(modul, left, right);
			sortModul(modul, left, p);
			sortModul(modul, p+1, right);
		}
	}
	
	public int sortModulPartition(modul modul, int left, int right){
		int i = left-1;
		int j = right+1;
		veranstaltung pivot = modul.getModulVeranstaltung(left);
		do{
			do{
				i++;
			}while( modul.getModulVeranstaltung(i).getVeranstaltungWoche().compareTo(pivot.getVeranstaltungWoche()) < 0 );
			do{
				j--;
			}while( (modul.getModulVeranstaltung(j).getVeranstaltungWoche().compareTo(pivot.getVeranstaltungWoche()) > 0) );
			if(i >= j){
				return j;
			}
			//swap
			veranstaltung hand = modul.getModulVeranstaltung(i);
			modul.veranstaltungen.set(i, modul.getModulVeranstaltung(j));
			modul.veranstaltungen.set(j,hand);
		}while(true); 
	}
	
	public void sortVeranstaltung(veranstaltung veranstaltung, int left, int right){	//sorts the lernziele in a veranstaltung by description (alphabetical)
		if(left < right){
			int p = sortVeranstaltungPartition(veranstaltung, left, right);
			sortVeranstaltung(veranstaltung, left, p);
			sortVeranstaltung(veranstaltung, p+1, right);
		}
	}
	
	private Integer sortVeranstaltungPartition(veranstaltung veranstaltung, int left, int right){
		int i = left-1;
		int j = right+1;
		lernziel pivot = veranstaltung.getVeranstaltungLz(left);
		do{
			do{
				i++;
			}while( veranstaltung.getVeranstaltungLz(i).description.compareToIgnoreCase(pivot.getLzDescription()) < 0 );
			do{
				j--;
			}while( (veranstaltung.getVeranstaltungLz(j).description.compareToIgnoreCase(pivot.getLzDescription()) > 0) );
			if(i >= j){
				return j;
			}
			//swap
			lernziel hand = veranstaltung.getVeranstaltungLz(i);
			veranstaltung.lernziele.set(i, veranstaltung.getVeranstaltungLz(j));
			veranstaltung.lernziele.set(j,hand);
		}while(true);
	}
	
	public void up(){
		Integer error = new Integer(-1);
		CustomDefaultMutableTreeNode node = window.getLastSelectedPathComponent();
		if(node != null){
			if(node.getPath().length == 2){
				//semester
				Integer sem = new Integer(0);
				for(sem = 0; sem < semesterListe.size(); sem++){
					if( (Integer.parseInt(node.toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.toString().contains("WS"))?true:false)) ){
						break;
					}
				}
				if(sem > 0) {
					semester semesterToMove = semesterListe.get(sem);
					semesterListe.set(sem, semesterListe.get(sem-1));
					semesterListe.set(sem-1, semesterToMove);
				}
				else{
					error = 3;
				}
			}
			else{
				if(node.getPath().length == 3){
					//modul
					Integer sem = new Integer(0);
					for(sem = 0; sem < semesterListe.size(); sem++){
						if( (Integer.parseInt(node.getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().toString().contains("WS"))?true:false)) ){
							break;
						}
					}
					Integer mod = new Integer(0);
					for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
						if(Integer.parseInt(node.toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
							break;
						}
					}
					if(mod > 0){
						modul modulToMove = semesterListe.get(sem).getSemesterModul(mod);
						semesterListe.get(sem).module.set(mod,semesterListe.get(sem).getSemesterModul(mod-1));
						semesterListe.get(sem).module.set(mod-1,modulToMove);
					}
					else{
						error = 3;
					}
				}
				else{
					if(node.getPath().length == 4){
						//veranstaltung
						Integer sem = new Integer(0);
						for(sem = 0; sem < semesterListe.size(); sem++){
							if( (Integer.parseInt(node.getParent().getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().getParent().toString().contains("WS"))?true:false)) ){
								break;
							}
						}
						Integer mod = new Integer(0);
						for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
							if(Integer.parseInt(node.getParent().toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
								break;
							}
						}
						Integer ver = new Integer(0);
						
						Integer veranstaltungsWeek = new Integer(0);
						String veranstaltungsTitle = new String("");
						
						for (ver = 0; ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size(); ver++){
							String veranstaltungBez = new String(node.toString());
							String firstWord = null;
							firstWord = veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")); 
							
							veranstaltungsTitle = new String(veranstaltungBez.substring(firstWord.length()+1));
							veranstaltungsWeek = Integer.parseInt(veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")).replaceAll("[^\\d.]", ""));
							if( (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel().equals(veranstaltungsTitle)) && (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche().equals(veranstaltungsWeek)) ){
								break;
							}
						}
						if(ver > 0){
							veranstaltung veranstaltungToMove = semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver);
							semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.set(ver,semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver-1));
							semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.set(ver-1,veranstaltungToMove);
						}
						else{
							error = 3;
						}
					}
					else{
						if(node.getPath().length == 5){
							//lernziel
							Integer sem = new Integer(0);
							for(sem = 0; sem < semesterListe.size(); sem++){
								if( (Integer.parseInt(node.getParent().getParent().getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().getParent().getParent().toString().contains("WS"))?true:false)) ){
									break;
								}
							}
							Integer mod = new Integer(0);
							for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
								if(Integer.parseInt(node.getParent().getParent().toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
									break;
								}
							}
							Integer ver = new Integer(0);										
							for (ver = 0; ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size(); ver++){
								String veranstaltungBez = new String(node.getParent().toString());
								String firstWord = null;
								firstWord = veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")); 
								
								String veranstaltungsTitle = new String(veranstaltungBez.substring(firstWord.length()+1));
								//Integer veranstaltungsWeek = Integer.parseInt(firstWord.replaceAll("[^\\d.]", ""));
								Integer veranstaltungsWeek = Integer.parseInt(veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")).replaceAll("[^\\d.]", ""));
								if( (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel().equals(veranstaltungsTitle)) && (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche().equals(veranstaltungsWeek)) ){
									break;
								}
							}
							
							Integer lz = new Integer(0);
							for(lz = 0; lz < semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.size(); lz++){
								if(lz.equals(Integer.parseInt(node.toString().replaceAll("[^\\d.]", ""))) ){
									break;
								}
							}
							if(lz > 0){
								lernziel lernzielToMove = semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz);
								semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.set(lz,semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz-1));
								semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.set(lz-1,lernzielToMove);
							}
							else{
								error = 3;
							}
						}
						else{
							error = 2;
						}
					}
				}
			}
			window.updateTree();
		}
		else{
			error = 1;
		}
		if(error > 0){
			//Display Error Message
			JOptionPane pane = new JOptionPane(null);
		    //configure
			pane.setMessageType(JOptionPane.ERROR_MESSAGE );
			switch(error){
			case 1:
				pane.setMessage("Bitte eine Node ausw‰hlen!");
				break;
			case 2:
				pane.setMessage("Diese Node ist nicht bewegbar.");
				break;
			case 3:
				pane.setMessage("Diese Node ist nicht nach oben bewegbar.");
			}
		    JDialog dialogError = pane.createDialog("Error");
		    dialogError.setLocation(window.getFrmLernzielnator().getLocation().x +  + (window.getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , window.getFrmLernzielnator().getLocation().y + (window.getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
	}
	
	public void down(){
		Integer error = new Integer(-1);
		CustomDefaultMutableTreeNode node = window.getLastSelectedPathComponent();
		if(node != null){
			if(node.getPath().length == 2){
				//semester
				Integer sem = new Integer(0);
				for(sem = 0; sem < semesterListe.size(); sem++){
					if( (Integer.parseInt(node.toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.toString().contains("WS"))?true:false)) ){
						break;
					}
				}
				if(sem < semesterListe.size()-1) {
					semester semesterToMove = semesterListe.get(sem);
					semesterListe.set(sem, semesterListe.get(sem+1));
					semesterListe.set(sem+1, semesterToMove);
				}
				else{
					error = 3;
				}
			}
			else{
				if(node.getPath().length == 3){
					//modul
					Integer sem = new Integer(0);
					for(sem = 0; sem < semesterListe.size(); sem++){
						if( (Integer.parseInt(node.getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().toString().contains("WS"))?true:false)) ){
							break;
						}
					}
					Integer mod = new Integer(0);
					for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
						if(Integer.parseInt(node.toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
							break;
						}
					}
					if(mod < semesterListe.get(sem).module.size()-1){
						modul modulToMove = semesterListe.get(sem).getSemesterModul(mod);
						semesterListe.get(sem).module.set(mod,semesterListe.get(sem).getSemesterModul(mod+1));
						semesterListe.get(sem).module.set(mod+1,modulToMove);
					}
					else{
						error = 3;
					}
				}
				else{
					if(node.getPath().length == 4){
						//veranstaltung
						Integer sem = new Integer(0);
						for(sem = 0; sem < semesterListe.size(); sem++){
							if( (Integer.parseInt(node.getParent().getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().getParent().toString().contains("WS"))?true:false)) ){
								break;
							}
						}
						Integer mod = new Integer(0);
						for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
							if(Integer.parseInt(node.getParent().toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
								break;
							}
						}
						Integer ver = new Integer(0);
						
						Integer veranstaltungsWeek = new Integer(0);
						String veranstaltungsTitle = new String("");
						
						for (ver = 0; ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size(); ver++){
							String veranstaltungBez = new String(node.toString());
							String firstWord = null;
							firstWord = veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")); 
							
							veranstaltungsTitle = new String(veranstaltungBez.substring(firstWord.length()+1));
							veranstaltungsWeek = Integer.parseInt(veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")).replaceAll("[^\\d.]", ""));
							if( (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel().equals(veranstaltungsTitle)) && (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche().equals(veranstaltungsWeek)) ){
								break;
							}
						}
						if(ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size()-1){
							veranstaltung veranstaltungToMove = semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver);
							semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.set(ver,semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver+1));
							semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.set(ver+1,veranstaltungToMove);
						}
						else{
							error = 3;
						}
					}
					else{
						if(node.getPath().length == 5){
							//lernziel
							Integer sem = new Integer(0);
							for(sem = 0; sem < semesterListe.size(); sem++){
								if( (Integer.parseInt(node.getParent().getParent().getParent().toString().replaceAll("[^\\d.]", "")) == semesterListe.get(sem).getSemesterYear()) && (semesterListe.get(sem).isWS() == ((node.getParent().getParent().getParent().toString().contains("WS"))?true:false)) ){
									break;
								}
							}
							Integer mod = new Integer(0);
							for(mod = 0; mod < semesterListe.get(sem).module.size(); mod++){
								if(Integer.parseInt(node.getParent().getParent().toString().replaceAll("[^\\d.]", "")) == (semesterListe.get(sem).getSemesterModul(mod).getModulNumber())){
									break;
								}
							}
							Integer ver = new Integer(0);										
							for (ver = 0; ver < semesterListe.get(sem).getSemesterModul(mod).veranstaltungen.size(); ver++){
								String veranstaltungBez = new String(node.getParent().toString());
								String firstWord = null;
								firstWord = veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")); 
								
								String veranstaltungsTitle = new String(veranstaltungBez.substring(firstWord.length()+1));
								//Integer veranstaltungsWeek = Integer.parseInt(firstWord.replaceAll("[^\\d.]", ""));
								Integer veranstaltungsWeek = Integer.parseInt(veranstaltungBez.substring(0, veranstaltungBez.indexOf(" ")).replaceAll("[^\\d.]", ""));
								if( (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungTitel().equals(veranstaltungsTitle)) && (semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungWoche().equals(veranstaltungsWeek)) ){
									break;
								}
							}
							
							Integer lz = new Integer(0);
							for(lz = 0; lz < semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.size(); lz++){
								if(lz.equals(Integer.parseInt(node.toString().replaceAll("[^\\d.]", ""))) ){
									break;
								}
							}
							if(lz < semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.size()-1){
								lernziel lernzielToMove = semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz);
								semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.set(lz,semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).getVeranstaltungLz(lz+1));
								semesterListe.get(sem).getSemesterModul(mod).getModulVeranstaltung(ver).lernziele.set(lz+1,lernzielToMove);
							}
							else{
								error = 3;
							}
						}
						else{
							error = 2;
						}
					}
				}
			}
			window.updateTree();
		}
		else{
			error = 1;
		}
		if(error > 0){
			//Display Error Message
			JOptionPane pane = new JOptionPane(null);
		    //configure
			pane.setMessageType(JOptionPane.ERROR_MESSAGE );
			switch(error){
			case 1:
				pane.setMessage("Bitte eine Node ausw‰hlen!");
				break;
			case 2:
				pane.setMessage("Diese Node ist nicht bewegbar.");
				break;
			case 3:
				pane.setMessage("Diese Node ist nicht nach unten bewegbar.");
			}
		    JDialog dialogError = pane.createDialog("Error");
		    dialogError.setLocation(window.getFrmLernzielnator().getLocation().x +  + (window.getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , window.getFrmLernzielnator().getLocation().y + (window.getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
		    dialogError.setVisible(true);
		}
	}
	
	
	
	
	private static void checkVersion(){
		String site="https://raw.githubusercontent.com/HeikoRadde/Lernzielnator/master/aktVersion.txt";
		String filename="temp.txt";		
		try {
			URL url=new URL(site);
			HttpsURLConnection connection =
		        (HttpsURLConnection) url.openConnection();
			float totalDataRead=0;
			java.io.BufferedInputStream in = new java.io.BufferedInputStream(connection.getInputStream());
			java.io.FileOutputStream fos = new java.io.FileOutputStream(filename);
			java.io.BufferedOutputStream bout = new BufferedOutputStream(fos,1024);
			byte[] data = new byte[1024];
			int i=0;
			while((i=in.read(data,0,1024))>=0){
				totalDataRead=totalDataRead+i;
				bout.write(data,0,i);
			}	
			bout.close();
			in.close();
			File file = new File("temp.txt");
			BufferedReader br = null;
			String line1 = new String("");
			String line2 = new String("");
			
			br = new BufferedReader(new FileReader(file));
			line1 = br.readLine();
			line2 = br.readLine();
			br.close();
			file.delete();
			if( Float.parseFloat(line1.replaceAll("[^\\d.]", "")) != 1.01 ){	//TODO: ACHTUNG: Versionsnummer immer aktualisieren!!!!! Auch unten im Text und im About (mainWindow.java)
				//Display Error Message
				JTextArea textarea = new JTextArea("Sie nutzen gerade die Version 1.01.\nDie aktuelle Version ist " + Float.toString((Float.parseFloat(line1.replaceAll("[^\\d.]", "")))) + "\n" + line2);			    
				textarea.setEditable(false);
				JOptionPane pane = new JOptionPane(textarea);
				pane.setMessageType(JOptionPane.ERROR_MESSAGE );				
				JDialog dialogError = pane.createDialog("Neue Version vorhanden");
			    dialogError.setLocation(window.getFrmLernzielnator().getLocation().x +  + (window.getFrmLernzielnator().getWidth()/2) - (dialogError.getBounds().width/2) , window.getFrmLernzielnator().getLocation().y + (window.getFrmLernzielnator().getHeight()/2) - (dialogError.getBounds().height/2) );
			    dialogError.setVisible(true);
			}
		}
		catch(Exception e)
		{
			
		}		
	}
	
	
	
}
