// GestureTest.java
// A simple (?) test of the DragGesture classes to see if we
// can recognize a simple drag gesture.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class GestureTest extends JFrame implements DragGestureListener {

  DragSource ds;
  JList jl;
  String[] items = {"Java", "C", "C++", "Lisp", "Perl", "Python"};

  public GestureTest() {
    super("Gesture Test");
    setSize(200,150);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        System.exit(0);
      }
    });
    jl = new JList(items);
    jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    getContentPane().add(new JScrollPane(jl), BorderLayout.CENTER);

    ds = new DragSource();
    DragGestureRecognizer dgr = ds.createDefaultDragGestureRecognizer(
			  jl, DnDConstants.ACTION_COPY, this);
    setVisible(true);
  }

  public void dragGestureRecognized(DragGestureEvent dge) {
    System.out.println("Drag Gesture Recognized!");
  }

  public static void main(String args[]) {
    new GestureTest();
  }
} 
