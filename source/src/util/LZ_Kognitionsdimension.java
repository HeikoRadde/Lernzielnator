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

public enum LZ_Kognitionsdimension {
	none{
		public String toString(){
			return "";
		}
	}, erinnern{
		public String toString(){
			return "erinnern";
		}
	}, verstehen{
		public String toString(){
			return "verstehen";
		}
	}, analysieren{
		public String toString(){
			return "analysieren";
		}
	}, evaluieren{
		public String toString(){
			return "evaluieren";
		}
	}, erzeugen{
		public String toString(){
			return "erzeugen";
		}
	};
	public static LZ_Kognitionsdimension toLzKognitionsdimension(int index){
		switch(index){
		case 0:
			return none;
		case 1:
			return erinnern;
		case 2:
			return verstehen;
		case 3:
			return analysieren;
		case 4:
			return evaluieren;
		case 5:
			return erzeugen;
		}
		return none;
	}
	public static LZ_Kognitionsdimension parse(String string){
		switch(string){
		case "erinnern":
			return erinnern;
		case "verstehen":
			return verstehen;
		case "analysieren":
			return analysieren;
		case "evaluieren":
			return evaluieren;
		case "erzeugen":
			return erzeugen;
		default: return none; 
		}
	}
}
