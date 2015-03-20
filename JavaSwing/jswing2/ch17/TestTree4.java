// TestTree4.java
// Another test to see how we can build a tree and customize its icons.
// This example does not affect the icons of other trees.
//
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;

public class TestTree4 extends JFrame {

  JTree tree1, tree2;
  DefaultTreeModel treeModel;

  public TestTree4() {
    super("Custom Icon Example");
    setSize(350, 450);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Build the hierarchy of containers & objects
    String[] schoolyard = {"School", "Playground", "Parking Lot", "Field"};
    String[] mainstreet = {"Grocery", "Shoe Shop", "Five & Dime", 
                           "Post Office"};
    String[] highway = {"Gas Station", "Convenience Store"};
    String[] housing = {"Victorian_blue", "Faux Colonial", 
                        "Victorian_white"};
    String[] housing2 = {"Mission", "Ranch", "Condo"};
    Hashtable homeHash = new Hashtable();
    homeHash.put("Residential 1", housing);
    homeHash.put("Residential 2", housing2);

    Hashtable cityHash = new Hashtable();
    cityHash.put("School grounds", schoolyard);
    cityHash.put("Downtown", mainstreet);
    cityHash.put("Highway", highway);
    cityHash.put("Housing", homeHash);

    Hashtable worldHash = new Hashtable();
    worldHash.put("My First VRML World", cityHash);
    
    // Build our tree out of our big hashtable
    tree1 = new JTree(worldHash);
    tree2 = new JTree(worldHash);

    DefaultTreeCellRenderer renderer = 
      (DefaultTreeCellRenderer)tree2.getCellRenderer();
    renderer.setClosedIcon(new ImageIcon("door.closed.gif"));
    renderer.setOpenIcon(new ImageIcon("door.open.gif"));
    renderer.setLeafIcon(new ImageIcon("world.gif"));

    JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
      tree1, tree2);

    getContentPane().add(pane, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    TestTree4 tt = new TestTree4();
    tt.setVisible(true);
  }
}
