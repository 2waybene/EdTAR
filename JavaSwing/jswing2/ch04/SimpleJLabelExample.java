// SimpleJLabelExample.java
// A quick application to show a simple JLabel.
//
import javax.swing.*;

public class SimpleJLabelExample {
  public static void main(String[] args) {
    JLabel label = new JLabel("A Very Simple Text Label");

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(label); // adds to CENTER
    frame.pack();
    frame.setVisible(true);
  }
}
