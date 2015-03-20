// RootExample2.java
// Another example of interacting with the root pane.  We set the menubar
// for the frame directly through the root pane in this example.
//
import javax.swing.*;
import java.awt.*;

public class RootExample2 {
  public static void main(String[] args) {
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JRootPane root = f.getRootPane();

    // Create a menu bar
    JMenuBar bar = new JMenuBar();
    JMenu menu = new JMenu("File");
    bar.add(menu);
    menu.add("Open");
    menu.add("Close");
    root.setJMenuBar(bar);

    // Add a button to the content pane
    root.getContentPane().add(new JButton("Hello World"));

    // Display the UI
    f.pack();
    f.setVisible(true);
  }
}
