// TreeDropTarget.java
// A quick DropTarget that's looking for drops from draggable JTrees.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class TreeDropTarget implements DropTargetListener {

  DropTarget target;
  JTree targetTree;

  public TreeDropTarget(JTree tree) {
    targetTree = tree;
    target = new DropTarget(targetTree, this);
  }

  /*
   * Drop Event Handlers
   */
  private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
    Point p = dtde.getLocation();
    DropTargetContext dtc = dtde.getDropTargetContext();
    JTree tree = (JTree)dtc.getComponent();
    TreePath path = tree.getClosestPathForLocation(p.x, p.y);
    return (TreeNode)path.getLastPathComponent();
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    TreeNode node = getNodeForEvent(dtde);
    if (node.isLeaf()) {
      dtde.rejectDrag();
    }
    else {
      // start by supporting move operations
      //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
      dtde.acceptDrag(dtde.getDropAction());
    }
  }

  public void dragOver(DropTargetDragEvent dtde) {
    TreeNode node = getNodeForEvent(dtde);
    if (node.isLeaf()) {
      dtde.rejectDrag();
    }
    else {
      // start by supporting move operations
      //dtde.acceptDrag(DnDConstants.ACTION_MOVE);
      dtde.acceptDrag(dtde.getDropAction());
    }
  }

  public void dragExit(DropTargetEvent dte) { }
  public void dropActionChanged(DropTargetDragEvent dtde) { }

  public void drop(DropTargetDropEvent dtde) {
    Point pt = dtde.getLocation();
    DropTargetContext dtc = dtde.getDropTargetContext();
    JTree tree = (JTree)dtc.getComponent();
    TreePath parentpath = tree.getClosestPathForLocation(pt.x, pt.y);
    DefaultMutableTreeNode parent = 
      (DefaultMutableTreeNode)parentpath.getLastPathComponent();
    if (parent.isLeaf()) {
      dtde.rejectDrop();
      return;
    }

    try {
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
	if (tr.isDataFlavorSupported(flavors[i])) {
	  dtde.acceptDrop(dtde.getDropAction());
	  TreePath p = (TreePath)tr.getTransferData(flavors[i]);
	  DefaultMutableTreeNode node = 
	    (DefaultMutableTreeNode)p.getLastPathComponent();
	  DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
	  model.insertNodeInto(node, parent, 0);
	  dtde.dropComplete(true);
	  return;
	}
      }
      dtde.rejectDrop();
    } catch (Exception e) {
      e.printStackTrace();
      dtde.rejectDrop();
    }
  }
} 
