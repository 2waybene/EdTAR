// HBoxWithGlue.java
// A quick test of the box layout manager using the Box utility class.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HBoxWithGlue extends JFrame {

  public HBoxWithGlue() {
    super("Box & Glue Frame");
    setSize(350, 100);
    Box box = Box.createHorizontalBox();
    setContentPane(box);
    box.add(Box.createHorizontalGlue());
    for (int i = 0; i < 3; i++) {
      Button b = new Button("B" + i);
      box.add(b);
    }
    box.add(Box.createHorizontalGlue());
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    HBoxWithGlue bt = new HBoxWithGlue();
  }
}
