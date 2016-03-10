package util;

public enum LZ_Dimension {
	none{
		public String toString(){
			return "";
		}
	},
	WissenKenntnisse{
		public String toString() {
	          return "Wissen/Kenntnisse (kognitiv)";
		}
	}, 
	Fertigkeiten{
		public String toString(){
			return "Fertigkeiten (psychomotorisch)";
		}
	}, Einstellungen{
		public String toString(){
			return "Einstellungen (emotional/reflektiv)";
		}
	}, MiniPa{
		public String toString(){
			return "Mini-PA";
		}
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
}
