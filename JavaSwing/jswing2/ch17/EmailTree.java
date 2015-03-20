// EmailTree.java
// A Simple test to see how we can build a tree and populate it.  This
// application also uses custom renderers and editors.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;

public class EmailTree extends JFrame {

  JTree tree;
  String[][] addresses = {
    {"paul@work.domain", "ptw@work.domain", "other@volunteer.domain"},
    {"paul@home.domain"},
    {"damian@work.domain", "damian@bigisp.domain"},
    {"paged@pager.domain"},
    {"damian@home.domain", "mosh@home.domain"},
    {"angela@home.com"}
  };

  public EmailTree() {
    super("Hashtable Test");
    setSize(400, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);  // 1.3 & higher
    // addWindowListener(new BasicWindowMonitor());  // 1.1 & 1.2
  }

  public void init() {
    Hashtable h = new Hashtable();
    Hashtable paul = new Hashtable();
    paul.put("Work", addresses[0]);
    paul.put("Home", addresses[1]);
    Hashtable damian = new Hashtable();
    damian.put("Work", addresses[2]);
    damian.put("Pager", addresses[3]);
    damian.put("Home", addresses[4]);
    Hashtable angela = new Hashtable();
    angela.put("Home", addresses[5]);
    h.put("Paul", paul);
    h.put("Damian", damian);
    h.put("Angela", angela);
    tree = new JTree(h);

    DefaultTreeCellRenderer renderer = 
      (DefaultTreeCellRenderer)tree.getCellRenderer();
    renderer.setOpenIcon(new ImageIcon("mailboxdown.gif"));
    renderer.setClosedIcon(new ImageIcon("mailboxup.gif"));
    renderer.setLeafIcon(new ImageIcon("letter.gif"));
    EmailTreeCellEditor emailEditor = new EmailTreeCellEditor();
    DefaultTreeCellEditor editor = new DefaultTreeCellEditor(
      tree, renderer, emailEditor);
    tree.setCellEditor(editor);
    tree.setEditable(true);

    getContentPane().add(tree, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    EmailTree tt = new EmailTree();
    tt.init();
    tt.setVisible(true);
  }
}
