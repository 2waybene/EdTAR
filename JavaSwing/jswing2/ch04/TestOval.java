// TestOval.java
// A simple application to test the functionality of the OvalIcon class.
//
import javax.swing.*;
import java.awt.*;

public class TestOval {
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel label1 = new JLabel(new OvalIcon(20,50));
    JLabel label2 = new JLabel(new OvalIcon(50,20));
    JLabel label3 = new JLabel
      ("Round!", new OvalIcon(60,60), SwingConstants.CENTER);
    label3.setHorizontalTextPosition(SwingConstants.CENTER);

    Container c = f.getContentPane();
    c.setLayout(new FlowLayout());
    c.add(label1);
    c.add(label2);
    c.add(label3);
    f.pack();
    f.setVisible(true);
  }
}
