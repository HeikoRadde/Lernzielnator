package data;
import java.util.ArrayList;

public class modul {
	Integer modulNumber;
	ArrayList<veranstaltung> veranstaltungen = new ArrayList<veranstaltung>();
	
	public modul(){
		modulNumber = new Integer(-1);
	}
	
	public modul(int Number){
		modulNumber = new Integer(Number);
	}
	
	//Set_Add_Remove
	
	public void setModulNumber(int Number){
		modulNumber = Number;
	}
	
	public boolean addModulVerantaltung(veranstaltung addThis){
		return veranstaltungen.add(addThis);
	}
	
	public boolean removeModulVeranstaltung(veranstaltung removeThis){
		return veranstaltungen.remove(removeThis);
	}
	
	//Get
	
	public Integer getModulNumber(){
		return modulNumber;
	}
	
	public veranstaltung getModulVeranstaltung(int index) throws IndexOutOfBoundsException{
		return veranstaltungen.get(index);
	}
	
	public int getVerListeSize(){
		return veranstaltungen.size();
	}
	
	public int searchVeranstaltung(int week, String title){
		int i;
		for (i = 0; i < modul.this.veranstaltungen.size(); i++){
			if( (modul.this.getModulVeranstaltung(i).getVeranstaltungTitel().equals(title)) && (modul.this.getModulVeranstaltung(i).getVeranstaltungWoche().equals(week)) ){
				return i;
			}
		}
		return -1;
	}
}
