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
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class MyTreeCellRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -1357647823552475720L;

	@Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);
        // If the node is a leaf
        if (leaf ) {	//paint custom color
            setForeground(((CustomDefaultMutableTreeNode) value).getColor());
            if(((CustomDefaultMutableTreeNode)value).isLernziel){
                String tooltip = ((CustomDefaultMutableTreeNode)value).getLernziel().getLzDescription();
                this.setToolTipText(tooltip);
            }
            else{
                String tooltip = "";
                this.setToolTipText(tooltip);            	
            }
        }
        else
        {
        	if(value instanceof CustomDefaultMutableTreeNode){
        		setForeground(((CustomDefaultMutableTreeNode) value).getColor());
        	}
            String tooltip = "";
            this.setToolTipText(tooltip);
        }        

        return this;
    }
}