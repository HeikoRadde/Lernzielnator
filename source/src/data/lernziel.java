package data;
import util.LZ_Dimension;
import util.LZ_Kognitionsdimension;

public class lernziel {
	String description;
	String notes;
	boolean mc;
	boolean smpp;
	boolean osce;
	boolean karteikarten;
	boolean ausarbeitung;
	boolean lerngruppe;
	boolean relevant;
	LZ_Dimension lzDimension;
	LZ_Kognitionsdimension lzKognitionsdimension;
	
	
	public lernziel(){
		description = new String("");
		notes = new String("");
		mc = false;
		smpp = false;
		osce = false;
		karteikarten = false;
		ausarbeitung = false;
		lerngruppe = false;
		lzDimension = LZ_Dimension.WissenKenntnisse;
		lzKognitionsdimension =  LZ_Kognitionsdimension.none;
		relevant = false;
	}
	
	public lernziel(String Text, String Notes, boolean MC, boolean SMPP, boolean OSCE,LZ_Dimension LzDimension, boolean Karteikarten, boolean Ausarbeitung, boolean Lerngruppe, LZ_Kognitionsdimension LzKognitionsdimension, boolean Relevant ){
		description = new String(Text);
		notes = new String(Notes);
		mc = MC;
		smpp = SMPP;
		osce = OSCE;
		karteikarten = Karteikarten;
		ausarbeitung = Ausarbeitung;
		lerngruppe = Lerngruppe;
		lzDimension = LzDimension;
		lzKognitionsdimension = LzKognitionsdimension;
		relevant = Relevant;
	}
	
	public lernziel(String Text, boolean MC, boolean SMPP, boolean OSCE,LZ_Dimension LzDimension, LZ_Kognitionsdimension LzKognitionsdimension){
		description = new String(Text);
		notes = new String("");
		mc = MC;
		smpp = SMPP;
		osce = OSCE;
		lzDimension = LzDimension;
		lzKognitionsdimension = LzKognitionsdimension;
	}
	
	//Set_Add_Remove
	
	public void setLzDescription(String Description){
		description = Description;
	}
	
	public void setNotes(String Notes){
		notes = Notes;
	}
	
	public void setLzMC(boolean MC){
		mc = MC;
	}
	
	public void setLzSMPP(boolean SMPP){
		smpp = SMPP;
	}
	
	public void setLzOSCE(boolean OSCE){
		osce = OSCE;
	}
	
	public void setKarteikarten(boolean Karteikarten){
		karteikarten = Karteikarten;
	}
	
	public void setAusarbeitung(boolean Ausarbeitung){
		ausarbeitung = Ausarbeitung;
	}
	
	public void setLerngruppe(boolean Lerngruppe){
		lerngruppe = Lerngruppe;
	}
	
	public void setRelevant(boolean Relevant){
		relevant = Relevant;
	}
	
	public void setLzDimension(LZ_Dimension LzDimension){
		lzDimension = LzDimension;
	}
	
	public void setLzKognitionsdimension(LZ_Kognitionsdimension LzKognitionsdimension){
		lzKognitionsdimension = LzKognitionsdimension;
	}
	
	//Get
	
	public String getLzDescription(){
		return description;
	}
	
	public String getLzNotes(){
		return notes;
	}
	
	public boolean isMC(){
		return mc;
	}
	
	public boolean isSMPP(){
		return smpp;
	}
	
	public boolean isOSCE(){
		return osce;
	}
	
	public boolean isKarteikarten(){
		return karteikarten;
	}
	
	public boolean isAusarbeitung(){
		return ausarbeitung;
	}
	
	public boolean isLerngruppe(){
		return lerngruppe;
	}
	
	public boolean isRelevant(){
		return relevant;
	}
	
	public LZ_Dimension getLzDimension(){
		return lzDimension;
	}
	
	public LZ_Kognitionsdimension getLzKognitionsdimension(){
		return lzKognitionsdimension;
	}
}
