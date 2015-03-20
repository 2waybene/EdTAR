// PlainBorders.java
//
package plain;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

public class PlainBorders {
  // An inner class for JButton borders.
  public static class ButtonBorder extends AbstractBorder implements UIResource
  {
    private Border raised;  // use this one by default
    private Border lowered; // use this one when pressed

    // Create the border.
    public ButtonBorder() {
      raised = BorderFactory.createRaisedBevelBorder();
      lowered = BorderFactory.createLoweredBevelBorder();
    }

    // Define the insets (in terms of one of the others).
    public Insets getBorderInsets(Component c) {
      return raised.getBorderInsets(c);
    }

    // Paint the border according to the current state.
    public void paintBorder(Component c, Graphics g, int x, int y,
        int width, int height) {

      AbstractButton b = (AbstractButton)c;
      ButtonModel model = b.getModel();

      if (model.isPressed() && model.isArmed()) {
        lowered.paintBorder(c, g, x, y, width, height);
      }
      else {
        raised.paintBorder(c, g, x, y, width, height);
      }
    }
  }
}
