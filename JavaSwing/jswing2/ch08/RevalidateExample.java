// RevalidateExample.java
// An example of using the revalidate() method to dynamically update the
// appearance of a component.  This example changes the size of a button's
// font on the fly.
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RevalidateExample extends JFrame {

  public RevalidateExample() {
    super("Revalidation Demo");
    setSize(300,150);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create a single button
    Font font = new Font("Dialog", Font.PLAIN, 10);
    final JButton b = new JButton("Add");
    b.setFont(font);

    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    c.add(b);

    // Increase the size of the button's font each time it's clicked
    b.addActionListener(new ActionListener() {
      int size = 10;

      public void actionPerformed(ActionEvent ev) {
        b.setFont(new Font("Dialog", Font.PLAIN, ++size));
        b.revalidate();   // invalidates the button & validates its root pane
      }
    });
  }

  public static void main(String[] args) {
    RevalidateExample re = new RevalidateExample();
    re.setVisible(true);
  }
}
