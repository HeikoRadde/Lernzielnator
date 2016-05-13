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
