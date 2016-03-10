package util;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class customFileFilter extends FileFilter {

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = Utils.getExtension(f);
		if (extension != null) {
			if (extension.equals(Utils.csv)){
				return true;
			} 
			else {
					return false;
			}
		}
	
		return false;
	}

	@Override
	public String getDescription() {
		return "Nur .csv Dateien";
	}
	
	
}
