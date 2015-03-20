// SortTreeDemo.java
// This class creates a tree model using the SortTreeModel with
// a File hierarchy as input.
//
 
import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;

public class SortTreeDemo extends JFrame {
  public SortTreeDemo(String startDir) {
    super("SortTreeModel Demonstration");
    setSize(300, 400);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    PrettyFile f = new PrettyFile(startDir);
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(f);
    SortTreeModel model = new SortTreeModel(root, new TreeStringComparator());
    fillModel(model, root);

    JTree tree = new JTree(model);
    getContentPane().add(new JScrollPane(tree));
  }

  protected void fillModel(SortTreeModel model, DefaultMutableTreeNode current) {
    PrettyFile pf = (PrettyFile)current.getUserObject();
    File f = pf.getFile();
    if (f.isDirectory()) {
      String files[] = f.list();
      // ignore "." files
      for (int i = 0; i < files.length; i++) {
        if (files[i].startsWith(".")) continue;
        PrettyFile tmp = new PrettyFile(pf, files[i]);
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(tmp);
        model.insertNodeInto(node, current);
        if (tmp.getFile().isDirectory()) {
          fillModel(model, node);
        }
      }
    }
  }

  public class PrettyFile {
    File f;
    public PrettyFile(String s) { f = new File(s); }
    public PrettyFile(PrettyFile pf, String s) { f = new File(pf.f, s); }
    public File getFile() { return f; }
    public String toString() { return f.getName(); }
  }

  public static void main(String args[]) {
    SortTreeDemo demo = new SortTreeDemo(args.length == 1 ? args[0] : ".");
    demo.setVisible(true);
  }
}
