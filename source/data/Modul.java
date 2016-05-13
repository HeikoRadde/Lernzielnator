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

public class Modul {
	Integer modulNumber;
	ArrayList<Veranstaltung> veranstaltungen = new ArrayList<Veranstaltung>();
	
	public Modul(){
		modulNumber = new Integer(-1);
	}
	
	public Modul(int Number){
		modulNumber = new Integer(Number);
	}
	
	//Set_Add_Remove
	
	public void setModulNumber(int Number){
		modulNumber = Number;
	}
	
	public boolean addModulVerantaltung(Veranstaltung addThis){
		return veranstaltungen.add(addThis);
	}
	
	public boolean removeModulVeranstaltung(Veranstaltung removeThis){
		return veranstaltungen.remove(removeThis);
	}
	
	//Get
	
	public Integer getModulNumber(){
		return modulNumber;
	}
	
	public Veranstaltung getModulVeranstaltung(int index) throws IndexOutOfBoundsException{
		return veranstaltungen.get(index);
	}
	
	public int getVerListeSize(){
		return veranstaltungen.size();
	}
	
	public int searchVeranstaltung(int week, String title){
		int i;
		for (i = 0; i < Modul.this.veranstaltungen.size(); i++){
			if( (Modul.this.getModulVeranstaltung(i).getVeranstaltungTitel().equals(title)) && (Modul.this.getModulVeranstaltung(i).getVeranstaltungWoche().equals(week)) ){
				return i;
			}
		}
		return -1;
	}
}
