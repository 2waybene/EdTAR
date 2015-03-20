// DefaultButtonExample.java
// Example using defaultButton and JRootPane.setDefaultButton()
//

import javax.swing.*;
import java.awt.*;

public class DefaultButtonExample {
  public static void main(String[] args) {

    // Create some buttons
    JButton ok = new JButton("OK");
    JButton cancel = new JButton("Cancel");
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(ok);
    buttonPanel.add(cancel);

    JLabel msg = new JLabel("Is this OK?", JLabel.CENTER);

    // Create a frame, get its root pane, and set the OK button as the
    // default. This button is pressed if we press the Enter key while the
    // frame has focus.
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JRootPane root = f.getRootPane();
    root.setDefaultButton(ok);

    // Layout and Display
    Container content = f.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(msg, BorderLayout.CENTER);
    content.add(buttonPanel, BorderLayout.SOUTH);
    f.setSize(200,100);
    f.setVisible(true);
  }
}
