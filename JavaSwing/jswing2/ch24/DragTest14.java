// DragTest14.java
// A simple (?) test of the drag capabilities built into the JDK 1.4.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class DragTest14 extends JFrame {

  JList jl;
  String[] items = {"Java", "C", "C++", "Lisp", "Perl", "Python"};

  public DragTest14() {
    super("Drag Test 1.4");
    setSize(200,150);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    jl = new JList(items);
    jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jl.setDragEnabled(true);

    getContentPane().add(new JScrollPane(jl), BorderLayout.CENTER);
    setVisible(true);
  }

  public static void main(String args[]) {
    new DragTest14();
  }
} 
