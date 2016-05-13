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

package util;
import javax.swing.tree.DefaultMutableTreeNode;

import data.Lernziel;
import data.Modul;
import data.Semester;
import data.Veranstaltung;

import java.awt.Color;

public class CustomDefaultMutableTreeNode extends DefaultMutableTreeNode {

	private static final long serialVersionUID = 8927303080835231863L;
	private Color color;
	boolean isSemester = false;
	boolean isModul = false;
	boolean isVeranstaltung = false;
	boolean isLernziel = false;
	
	Semester semester;
	Modul modul;
	Veranstaltung veranstaltung;
	Lernziel lernziel;
	
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
	
	public void setSemester(Semester newSemester){
		semester = newSemester;
	}
	
	public Semester getSemester(){
		return semester;
	}
	
	public void setModul(Modul newModul){
		modul = newModul;
	}
	
	public Modul getModul(){
		return modul;
	}
	
	public void setVeranstaltung(Veranstaltung newVeranstaltung){
		veranstaltung = newVeranstaltung;
	}
	
	public Veranstaltung getVeranstaltung(){
		return veranstaltung;
	}
	
	public void setLernziel(Lernziel newLernziel){
		lernziel = newLernziel;
	}
	
	public Lernziel getLernziel(){
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
