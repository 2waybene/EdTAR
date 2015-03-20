// PlainIconFactory.java
//
package plain;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import java.io.Serializable;

public class PlainIconFactory {
  private static Icon radioButtonIcon;
  private static Icon checkBoxIcon; // implemention trimmed from example

  // Provide access to the single RadioButtonIcon instance.
  public static Icon getRadioButtonIcon() {
    if (radioButtonIcon == null) {
      radioButtonIcon = new RadioButtonIcon();
    }
    return radioButtonIcon;
  }

  // An icon for rendering the default radio button icon.
  private static class RadioButtonIcon implements Icon, UIResource, Serializable
  {
    private static final int size = 15;

    public int getIconWidth() { return size; }
    public int getIconHeight() { return size; }

    public void paintIcon(Component c, Graphics g, int x, int y) {

      // Get the button & model containing the state we are supposed to show
      AbstractButton b = (AbstractButton)c;
      ButtonModel model = b.getModel();

      // If the button is being pressed (& armed), change the BG color
      // (NOTE: Could also do something different if the button is disabled)

      if (model.isPressed() && model.isArmed()) {
        g.setColor(UIManager.getColor("RadioButton.pressed"));
        g.fillOval(x, y, size-1, size-1);
      }

      // Draw an outer circle
      g.setColor(UIManager.getColor("RadioButton.foreground"));
      g.drawOval(x, y, size-1, size-1);

      // Fill a small circle inside if the button is selected
      if (model.isSelected()) {
        g.fillOval(x+4, y+4, size-8, size-8);
      }
    }
  }
}
