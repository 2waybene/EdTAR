// FSTree.java
// A sample component for dragging & dropping a collection of files
// into a tree.
//

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.util.List;
import java.util.Iterator;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

public class FSTree extends JTree {

  public FSTree() { super(); init(); }
  public FSTree(TreeModel newModel) { super(newModel); init(); }
  public FSTree(TreeNode root) { super(root); init(); }
  public FSTree(TreeNode root, boolean asks) { super(root, asks); init(); }

  private void init() {
    // We don't want to export anything from this tree, only import
    setDragEnabled(false);
    setTransferHandler(new FSTransfer());
  }

  public class FSTransfer extends TransferHandler {
    public boolean importData(JComponent comp, Transferable t) {
      // Make sure we have the right starting points
      if (!(comp instanceof FSTree)) {
        return false;
      }
      if (!t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
        return false;
      }

      // Grab the tree, its model and the root node
      FSTree tree = (FSTree)comp;
      DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
      DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
      try {
        List data = (List)t.getTransferData(DataFlavor.javaFileListFlavor);
        Iterator i = data.iterator();
        while (i.hasNext()) {
          File f = (File)i.next();
          root.add(new DefaultMutableTreeNode(f.getName()));
        }
        model.reload();
        return true;
      }
      catch (UnsupportedFlavorException ufe) {
        System.err.println("Ack! we should not be here.\nBad Flavor.");
      }
      catch (IOException ioe) {
        System.out.println("Something failed during import:\n" + ioe);
      }
      return false;
    }
    
    // We only support file lists on FSTrees...
    public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
      if (comp instanceof FSTree) {
        for (int i = 0; i < transferFlavors.length; i++) {
          if (!transferFlavors[i].equals(DataFlavor.javaFileListFlavor)) {
            return false;
          }
        }
        return true;
      }
      return false;
    }
  }
}

