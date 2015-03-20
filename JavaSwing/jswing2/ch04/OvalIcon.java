// OvalIcon.java
// A simple icon implementation that draws ovals.
//
import javax.swing.*;
import java.awt.*;

public class OvalIcon implements Icon {

  private int width, height;

  public OvalIcon(int w, int h) {
    width = w;
    height = h;
  }

  public void paintIcon(Component c, Graphics g, int x, int y) {
    g.drawOval(x, y, width-1, height-1);
  }

  public int getIconWidth() { return width; }
  public int getIconHeight() { return height; }
}
