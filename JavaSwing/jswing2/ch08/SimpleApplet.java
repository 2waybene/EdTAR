// SimpleApplet.java
// An example of the JApplet class.  For use with the applet.html file.
//
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class SimpleApplet extends JApplet {
  public void init() {
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(2, 2, 2, 2));
    p.add(new JLabel("Username"));
    p.add(new JTextField());
    p.add(new JLabel("Password"));
    p.add(new JPasswordField());
    Container content = getContentPane();
    content.setLayout(new GridBagLayout()); // Used to center the panel
    content.add(p);
  }
}
