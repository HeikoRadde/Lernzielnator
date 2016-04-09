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