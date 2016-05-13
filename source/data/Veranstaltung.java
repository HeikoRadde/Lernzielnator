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

package data;
import java.util.ArrayList;

public class Veranstaltung {
	Integer week;
	String title;
	ArrayList<Lernziel> lernziele = new ArrayList<Lernziel>();
	
	public Veranstaltung(){
		week = new Integer(-1);
		title = new String("");
	}
	
	public Veranstaltung(int Week, String Title){
		week = new Integer(Week);
		title = new String(Title);
	}
	
	//Set_Add_Remove
	
	public void setVeranstaltungWoche(int Week){
		week = Week;
	}
	
	public void setVeranstaltungTitel(String Title){
		title = Title;
	}
	
	public boolean addVeranstaltungLernziel(Lernziel LZ){
		return lernziele.add(LZ);
	}
	
	public boolean removeVeranstaltungLernziel(Lernziel LZ){
		return lernziele.remove(LZ);
	}
	
	//Get
	
	public Integer getVeranstaltungWoche(){
		return week;
	}
	
	public String getVeranstaltungTitel(){
		return title;
	}
	
	public Lernziel getVeranstaltungLz(int index) throws IndexOutOfBoundsException{
		return lernziele.get(index);
	}
	
	public int getLerListeSize(){
		return lernziele.size();
	}
	
	public int searchLernziel(Veranstaltung veranstaltung, String description){
		int i;
		for (i = 0; i < veranstaltung.lernziele.size(); i++){
			if(veranstaltung.getVeranstaltungLz(i).getLzDescription().equals(description)){
				return i;
			}
		}
		return -1;
	}
	
	public boolean countainsLernziel(String description){
		int i;
		for (i = 0; i < Veranstaltung.this.lernziele.size(); i++){
			if(Veranstaltung.this.getVeranstaltungLz(i).getLzDescription().equals(description)){
				return true;
			}
		}
		return false;
	}
}
