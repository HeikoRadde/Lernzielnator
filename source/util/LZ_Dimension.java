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

public enum LZ_Dimension {
	none(0){
		public String toString(){
			return "";
		}
	},
	WissenKenntnisse(1){
		public String toString() {
	          return "Wissen/Kenntnisse (kognitiv)";
		}
	}, 
	Fertigkeiten(2){
		public String toString(){
			return "Fertigkeiten (psychomotorisch)";
		}
	}, Einstellungen(3){
		public String toString(){
			return "Einstellungen (emotional/reflektiv)";
		}
	}, MiniPa(4){
		public String toString(){
			return "Mini-PA";
		}
	};
	
	private int value;
	
	private LZ_Dimension(int value){
		this.value = value;
	};
	
	public static LZ_Dimension toLzDimension(int index){
		switch(index){
		case 0:
			return none;
		case 1:
			return WissenKenntnisse;
		case 2:
			return Fertigkeiten;
		case 3:
			return Einstellungen;
		case 4:
			return MiniPa;
		}
		return none;
	}
	public static LZ_Dimension parse(String string){
		switch(string){
		case "Wissen/Kenntnisse (kognitiv)":
			return WissenKenntnisse;
		case "Fertigkeiten (psychomotorisch)":
			return Fertigkeiten;
		case "Einstellungen (emotional/reflektiv)":
			return Einstellungen;
		case "Mini-PA":
			return MiniPa;
		default:
			return none;
		}
	}
	public int toInt(){
		return value;
	}
}
