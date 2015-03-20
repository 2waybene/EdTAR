// MetalModExample.java
// An example of modifying an existing L&F.  This example replaces the
// standard scrollbar UI with a custom one.
//
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MetalModExample {
  public static void main(String[] args) {
      // Make sure we're using the Metal L&F, since the example needs it
      try {
          UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      }
      catch (Exception e) {
          System.err.println("Metal is not available on this platform?!");
          e.printStackTrace();
          System.exit(1);
      }
    JComponent before = makeExamplePane();

    // Replace the MetalScrollBarUI with our own!
    UIManager.put("ScrollBarUI", "MyMetalScrollBarUI");

    JComponent after = makeExamplePane();

    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container c = f.getContentPane();
    c.setLayout(new GridLayout(2, 1, 0, 1));
    c.add(before);
    c.add(after);
    f.setSize(450, 400);
    f.setVisible(true);
  }

  // Create a scroll pane with a text area in it.
  public static JComponent makeExamplePane() {
    JTextArea text = new JTextArea();

    try {
      text.read(new FileReader("MetalModExample.java"), null);
    }
    catch (IOException ex) {}

    JScrollPane scroll = new JScrollPane(text);
    return scroll;
  }
}
