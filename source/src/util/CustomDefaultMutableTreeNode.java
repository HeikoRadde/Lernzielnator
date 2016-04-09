package util;
import javax.swing.tree.DefaultMutableTreeNode;

import data.lernziel;
import data.modul;
import data.semester;
import data.veranstaltung;

import java.awt.Color;

public class CustomDefaultMutableTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 8927303080835231863L;
	private Color color;
	boolean isSemester = false;
	boolean isModul = false;
	boolean isVeranstaltung = false;
	boolean isLernziel = false;
	
	semester semester;
	modul modul;
	veranstaltung veranstaltung;
	lernziel lernziel;
	
	public CustomDefaultMutableTreeNode(Object userObject){
		super(userObject);
		color = new Color(0,0,0);		
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setSemester(semester newSemester){
		semester = newSemester;
	}
	
	public semester getSemester(){
		return semester;
	}
	
	public void setModul(modul newModul){
		modul = newModul;
	}
	
	public modul getModul(){
		return modul;
	}
	
	public void setVeranstaltung(veranstaltung newVeranstaltung){
		veranstaltung = newVeranstaltung;
	}
	
	public veranstaltung getVeranstaltung(){
		return veranstaltung;
	}
	
	public void setLernziel(lernziel newLernziel){
		lernziel = newLernziel;
	}
	
	public lernziel getLernziel(){
		return lernziel;
	}
	
	public void setType(Integer type){// 0 -> Semester, 1 -> Modul, 2 -> Veranstaltung, 3 -> Lernziel
		isSemester = false;
		isModul = false;
		isVeranstaltung = false;
		isLernziel = false;
		switch(type){
		case 0:
			isSemester = true;
			break;
		case 1:
			isModul = true;
			break;
		case 2:
			isVeranstaltung = true;
			break;
		case 3:
			isLernziel = true;
			break;
		}
	}
	
	public Integer getType(){// 0 -> Semester, 1 -> Modul, 2 -> Veranstaltung, 3 -> Lernziel, 		-1 -> nothing of the previous
		if(isSemester){
			return 0;
		}
		if(isModul){
			return 1;
		}
		if(isVeranstaltung){
			return 2;
		}
		if(isLernziel){
			return 3;
		}
		return -1;
	}
	
	
	
}
