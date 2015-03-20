// SimpleInternalFrame.java
// A quick demonstration of setting up an Internal Frame in an application.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleInternalFrame extends Frame {

  JLayeredPane desktop;
  JInternalFrame internalFrame;

  public SimpleInternalFrame() {
    super("");
    setSize(500,400);

    // Set up the layered pane
    desktop = new JDesktopPane();
    desktop.setOpaque(true);
    add(desktop, BorderLayout.CENTER);

    internalFrame = new JInternalFrame("Meow", 
			 true, true, true, true);
    internalFrame.setBounds(50, 50, 200, 100);
    //internalFrame.getContentPane().setLayout(new BorderLayout());
    internalFrame.getContentPane().add(new JLabel(new ImageIcon("images/pussin.jpg")));
    internalFrame.setVisible(true);
    
    desktop.add(internalFrame, new Integer(1));    
  }

  public static void main(String args[]) {
    SimpleInternalFrame sif = new SimpleInternalFrame();
    sif.setVisible(true);
  }

}

