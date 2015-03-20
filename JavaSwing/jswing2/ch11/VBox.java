// VBox.java
// A quick test of the BoxLayout manager using the Box utility class.
// This box is laid out vertically.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VBox extends JFrame {

  public VBox() {
    super("Vertical Box Test Frame");
    setSize(200, 100);
    Panel box = new Panel();

    // Use BoxLayout.X_AXIS below if you want a horizontal box
    box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS)); 
    setContentPane(box);
    for (int i = 0; i < 3; i++) {
      Button b = new Button("B" + i);
      box.add(b);
    }
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    VBox bt = new VBox();
  }
}
