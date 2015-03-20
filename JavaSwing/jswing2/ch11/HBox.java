// HBox.java
// A quick test of the BoxLayout manager using the Box utility class.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HBox extends JFrame {

  public HBox() {
    super("Horizontal Box Test Frame");
    setSize(200, 100);
    Panel box = new Panel();

    // Use BoxLayout.Y_AXIS below if you want a vertical box
    box.setLayout(new BoxLayout(box, BoxLayout.X_AXIS)); 
    setContentPane(box);
    for (int i = 0; i < 3; i++) {
      Button b = new Button("B" + i);
      box.add(b);
    }
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    HBox bt = new HBox();
  }
}
