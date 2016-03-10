package data;
import java.util.ArrayList;

public class semester {	
	Integer year;
	Boolean ws;
	ArrayList<modul> module = new ArrayList<modul>();
	
	public semester(){
		year = new Integer(-1);
		ws = new Boolean(false);
	}
	
	public semester(int Year, boolean WS){
		year = new Integer(Year);
		ws = new Boolean(WS);
	}
	
	//Set_Add_Remove
	
	public boolean addSemesterModul(modul addThis){
		return module.add(addThis);		
	}
	
	public boolean removeSemesterModule(modul removeThis){
		return module.remove(removeThis);
	}
	
	public void setWS(){
		ws = true;
	}
	
	public void setSS(){
		ws = false;
	}
	
	public void setSemesterYear(int newyear){
		year = newyear;
	}
	
	//Get
	
	public Integer getSemesterYear(){
		return year;
	}
	
	public boolean isWS(){
		return ws;
	}
	
	public boolean isSS(){
		return (!(ws));
	}
	
	public modul getSemesterModul(int index) throws IndexOutOfBoundsException{
		return module.get(index);
	}
	
	public int getModulListeSize(){
		return module.size();
	}
	
	public int searchModul(int modulNr){
		int i;
		for(i = 0; i < semester.this.module.size(); i++){
			if( semester.this.getSemesterModul(i).getModulNumber().equals(modulNr) ){
				return i;
			}
		}
		return -1;
	}
	
}

