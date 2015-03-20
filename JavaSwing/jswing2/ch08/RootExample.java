// RootExample.java
// An example of interacting directly with the JRootPane of a JFrame.
//
import java.awt.*;
import javax.swing.*;

public class RootExample {
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JRootPane root = f.getRootPane();          // XXX Pay attention to these
    Container content = root.getContentPane(); // XXX lines. They get more
    content.add(new JButton("Hello"));         // XXX explanation in the book.
    f.pack();
    f.setVisible(true);
  }
}
