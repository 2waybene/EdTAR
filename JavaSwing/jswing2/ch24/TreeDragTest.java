// TreeDragTest.java
// A simple starting point for testing the DnD JTree code.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.tree.*;

public class TreeDragTest extends JFrame {

  TreeDragSource ds;
  TreeDropTarget dt;
  JTree tree;

  public TreeDragTest() {
    super("Rearrangeable Tree");
    setSize(300,200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // If you want autoscrolling, use this line:
    tree = new AutoScrollingJTree();
    // Otherwise, use this line:
    //tree = new JTree();
    getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);

    // If we only support move operations...
    //ds = new TreeDragSource(tree, DnDConstants.ACTION_MOVE);
    ds = new TreeDragSource(tree, DnDConstants.ACTION_COPY_OR_MOVE);
    dt = new TreeDropTarget(tree);
    setVisible(true);
  }

  public class AutoScrollingJTree extends JTree implements Autoscroll {
    private int margin = 12;

    public AutoScrollingJTree() { super(); }

    public void autoscroll(Point p) {
      int realrow = getRowForLocation(p.x, p.y);
      Rectangle outer = getBounds();
      realrow = (p.y + outer.y <= margin ? 
		 realrow < 1 ? 0 : realrow - 1 : 
		 realrow < getRowCount() - 1 ? realrow + 1 : realrow);
      scrollRowToVisible(realrow);
    }

    public Insets getAutoscrollInsets() {
      Rectangle outer = getBounds();
      Rectangle inner = getParent().getBounds();
      return new Insets(
        inner.y - outer.y + margin, inner.x - outer.x + margin,
	outer.height - inner.height - inner.y + outer.y + margin,
	outer.width - inner.width - inner.x + outer.x + margin);
    }

    // Use this method if you want to see the boundaries of the
    // autoscroll active region

    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Rectangle outer = getBounds();
      Rectangle inner = getParent().getBounds();
      g.setColor(Color.red);
      g.drawRect(-outer.x + 12, -outer.y + 12,
		 inner.width - 24, inner.height - 24);
    }

  }

  public static void main(String args[]) {
    new TreeDragTest();
  }
} 
