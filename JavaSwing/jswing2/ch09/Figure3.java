// Figure3.java
// An example that shows how to do a few interesting things using 
// JInternalFrames, JDesktopPane, and DesktopManager.
//
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.beans.*;

public class Figure3 extends JFrame {

  private JDesktopPane desk;

  public Figure3(String title) {
    super(title);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create a desktop and set it as the content pane. Don't set the layered
    // pane, since it needs to hold the menubar too.
    desk = new JDesktopPane();
    setContentPane(desk);

  }

  private void addFrame(int number) {
	  JInternalFrame f = new JInternalFrame("Frame " + number,true,true,true,true);
	
	  f.setBounds(number * 10 - 5, number * 10 - 5, 250, 150);
	  desk.add(f, 1);
	  f.setVisible(true);
  }

  // A simple test program.
  public static void main(String[] args) {
    Figure3 td = new Figure3("");

    td.setSize(300, 220);
    td.setVisible(true);
    for (int i = 1; i <= 4; i++) {
      td.addFrame(i);
    }
  }
}
