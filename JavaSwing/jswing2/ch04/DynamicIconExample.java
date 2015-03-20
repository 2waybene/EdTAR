// DynamicIconExample.java
// Example of an icon that changes form.
//
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class DynamicIconExample {
  public static void main(String[] args) {

    // Create a couple of sliders to control the icon size.
    final JSlider width = new JSlider(JSlider.HORIZONTAL, 1, 150, 75);
    final JSlider height = new JSlider(JSlider.VERTICAL, 1, 150, 75);

    // A little icon class that uses the current slider values.
    class DynamicIcon implements Icon {
      public int getIconWidth() { return width.getValue(); }
      public int getIconHeight() { return height.getValue(); }

      public void paintIcon(Component c, Graphics g, int x, int y) {
        g.fill3DRect(x, y, getIconWidth(), getIconHeight(), true);
      }
    };
    Icon icon = new DynamicIcon();
    final JLabel dynamicLabel = new JLabel(icon);

    // A listener to repaint the icon when sliders are adjusted.
    class Updater implements ChangeListener {
      public void stateChanged(ChangeEvent ev) {
        dynamicLabel.repaint();
      }
    };
    Updater updater = new Updater();

    width.addChangeListener(updater);
    height.addChangeListener(updater);

    // Lay it all out.
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container c = f.getContentPane();
    c.setLayout(new BorderLayout());
    c.add(width, BorderLayout.NORTH);
    c.add(height, BorderLayout.WEST);
    c.add(dynamicLabel, BorderLayout.CENTER);
    f.setSize(210,210);
    f.setVisible(true);
  }
}
