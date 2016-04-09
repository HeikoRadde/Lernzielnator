package util;

import javax.swing.JTree;
import javax.swing.text.Position.Bias;
import javax.swing.tree.TreePath;

public class CustomJTree extends JTree {

	private static final long serialVersionUID = -512003161317390106L;

	//Disables the automatic selection by first letter in JTree
	@Override
	public TreePath getNextMatch(String prefix, int startingRow, Bias bias) {
		// TODO Auto-generated method stub
		//return super.getNextMatch(prefix, startingRow, bias);
		return null;
	}
	
	
}
