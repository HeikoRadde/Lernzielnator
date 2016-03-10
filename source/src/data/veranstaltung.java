package data;
import java.util.ArrayList;

public class veranstaltung {
	Integer week;
	String title;
	ArrayList<lernziel> lernziele = new ArrayList<lernziel>();
	
	public veranstaltung(){
		week = new Integer(-1);
		title = new String("");
	}
	
	public veranstaltung(int Week, String Title){
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
	
	public boolean addVeranstaltungLernziel(lernziel LZ){
		return lernziele.add(LZ);
	}
	
	public boolean removeVeranstaltungLernziel(lernziel LZ){
		return lernziele.remove(LZ);
	}
	
	//Get
	
	public Integer getVeranstaltungWoche(){
		return week;
	}
	
	public String getVeranstaltungTitel(){
		return title;
	}
	
	public lernziel getVeranstaltungLz(int index) throws IndexOutOfBoundsException{
		return lernziele.get(index);
	}
	
	public int getLerListeSize(){
		return lernziele.size();
	}
	
	public int searchLernziel(veranstaltung veranstaltung, String description){
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
		for (i = 0; i < veranstaltung.this.lernziele.size(); i++){
			if(veranstaltung.this.getVeranstaltungLz(i).getLzDescription().equals(description)){
				return true;
			}
		}
		return false;
	}
}
