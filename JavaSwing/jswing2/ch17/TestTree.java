// TestTree.java
// A simple test to see how we can build a tree and populate it.  We build
// the tree structure up by hand in this case.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class TestTree extends JFrame {

  JTree tree;
  DefaultTreeModel treeModel;

  public TestTree() {
    super("Tree Test Example");
    setSize(400, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void init() {
    // Build up a bunch of TreeNodes. We use DefaultMutableTreeNode because the
    // DefaultTreeModel can use it to build a complete tree.
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    DefaultMutableTreeNode subroot = new DefaultMutableTreeNode("SubRoot");
    DefaultMutableTreeNode leaf1 = new DefaultMutableTreeNode("Leaf 1");
    DefaultMutableTreeNode leaf2 = new DefaultMutableTreeNode("Leaf 2");
    
    // Build our tree model starting at the root node, and then make a JTree out
    // of that.
    treeModel = new DefaultTreeModel(root);
    tree = new JTree(treeModel);

    // Build the tree up from the nodes we created.
    treeModel.insertNodeInto(subroot, root, 0);
    // Or, more succinctly:
    subroot.add(leaf1);
    root.add(leaf2);

    // Display it.
    getContentPane().add(tree, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    TestTree tt = new TestTree();
    tt.init();
    tt.setVisible(true);
  }
}
