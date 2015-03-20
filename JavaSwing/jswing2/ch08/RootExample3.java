// RootExample3.java
// Similar to RootExample2, but this version uses the setJMenuBar() method
// from JFrame to attach the menu.  No (direct) interaction with JRootPane
// is needed.
//
import javax.swing.*;
import java.awt.*;

public class RootExample3 extends JFrame {
  public RootExample3() {
    super("RootPane Menu Demo");
    setSize(220,100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create a menu bar
    JMenuBar bar = new JMenuBar();
    JMenu menu = new JMenu("File");
    bar.add(menu);
    menu.add("Open");
    menu.add("Close");
    setJMenuBar(bar);

    // Add a button to the content pane
    getContentPane().add(new JButton("Hello World"));
  }

  public static void main(String[] args) {
    RootExample3 re3 = new RootExample3();
    re3.setVisible(true);
  }
}
