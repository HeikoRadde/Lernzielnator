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
