/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */

package my.pr.utils;

import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author prando
 */
public class OTree {
    
    
    public OTree() {
    
    }
    
    
    
    
    
    public static void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        if (node.getChildCount() > 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        
        if (expand) {
            tree.expandPath(parent);
        }
        else {
            tree.collapsePath(parent);
        }
    } 
    
}
