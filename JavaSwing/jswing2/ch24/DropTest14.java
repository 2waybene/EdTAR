// DropTest14.java
// A simple drop tester application for JDK 1.4 Swing components.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class DropTest14 extends JFrame {

  JTextArea ta;

  public DropTest14() {
    super("Drop Test 1.4");
    setSize(300,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    getContentPane().add(
        new JLabel("Drop the choice from your JList here:"),
	BorderLayout.NORTH);
    ta = new JTextArea();
    ta.setBackground(Color.white);
    getContentPane().add(ta, BorderLayout.CENTER);

    // Set up our text area to recieve drops...
    // This class will handle drop events
    
    setVisible(true);
  }

  public static void main(String args[]) {
    new DropTest14();
  }
} 
