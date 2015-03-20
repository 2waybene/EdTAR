// SimpleApplet2.java
// A contrived example of how to add components to an applet after it has
// been initialized.  Code to disable the event queue checking is included
// (but commented out) for use with older plug-ins.
//
import javax.swing.*;
import java.awt.*;

public class SimpleApplet2 extends JApplet {
  public SimpleApplet2() {
    // Suppress warning message on older versions if needed...
    // getRootPane().putClientProperty("defeatSystemEventQueueCheck",
    // Boolean.TRUE);
  }

  public void start() {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() { // run in the event thread . . .
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2, 2, 2, 2));
        p.add(new JLabel("Username"));
        p.add(new JTextField());
        p.add(new JLabel("Password"));
        p.add(new JPasswordField());
        Container content = getContentPane();
        content.setLayout(new GridBagLayout()); // used to center the panel
        content.add(p);
        validate();
      }
    });
  }
}
