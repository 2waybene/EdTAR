// TreeEvents.java
// An example of using EEL to show off all the events coming from a tree.
// You can add and remove nodes and watch for TreeModelEvents as well.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class TreeEvents extends JFrame implements TreeSelectionListener {

  JButton addB, deleteB;
  JTree tree;
  DefaultMutableTreeNode leadSelection;

  public TreeEvents() {
    super("Tree Event Demo");
    setSize(300,200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    EEL eel = EEL.getInstance();
    eel.addGui();

    tree = new JTree();
    tree.setExpandsSelectedPaths(true);
    tree.setEditable(true);
    getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
    tree.addTreeSelectionListener(eel);
    tree.addTreeSelectionListener(this);
    tree.addTreeExpansionListener(eel);
    tree.addTreeWillExpandListener(eel);
    tree.addPropertyChangeListener(eel);
    tree.getModel().addTreeModelListener(eel);

    addB = new JButton("Add a node");
    deleteB = new JButton("Delete a node");

    JPanel buttonP = new JPanel();
    buttonP.add(addB);
    buttonP.add(deleteB);
    getContentPane().add(buttonP, BorderLayout.SOUTH);

    addB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          String nodeName = JOptionPane.showInputDialog("New node name:");
          if (leadSelection != null) {
            leadSelection.add(new DefaultMutableTreeNode(nodeName));
            ((DefaultTreeModel)tree.getModel()).reload(leadSelection);
          }
          else {
            JOptionPane.showMessageDialog(TreeEvents.this, "No Parent...");
          }
        }
      });

    deleteB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          if (leadSelection != null) {
            DefaultMutableTreeNode parent = 
              (DefaultMutableTreeNode) leadSelection.getParent();
            if (parent == null) {
              JOptionPane.showMessageDialog(TreeEvents.this, 
                                            "Can't delete root");
            }
            else {
              parent.remove(leadSelection);
              leadSelection = null;
              ((DefaultTreeModel)tree.getModel()).reload(parent);
            }
          }
          else {
            JOptionPane.showMessageDialog(TreeEvents.this, "No Selection...");
          }
        }
      });
    eel.showDialog();
  }

  public void valueChanged(TreeSelectionEvent e) {
    TreePath leadPath = e.getNewLeadSelectionPath();
    if (leadPath != null) {
      leadSelection = (DefaultMutableTreeNode)leadPath.getLastPathComponent();
    }
  }

  public static void main(String args[]) {
    TreeEvents te = new TreeEvents();
    te.setVisible(true);
  }
}
