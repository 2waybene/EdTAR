// CompassScroller.java
// A simple ActionListener that can move the view of a viewport
// north, south, east and west by specified units.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.JViewport;

public class CompassScroller implements ActionListener {

  public static final String NORTH = "North";
  public static final String SOUTH = "South";
  public static final String EAST  = "East";
  public static final String WEST  = "West";

  private JViewport viewport;
  private Point p;

  public CompassScroller(JViewport viewport) {
    this.viewport = viewport;
    p = new Point();
  }

  public void actionPerformed(ActionEvent ae) {
    Dimension dv = viewport.getViewSize();
    Dimension de = viewport.getExtentSize();
    String command = ae.getActionCommand();
    if (command == NORTH) {
      if (p.y > 9) {
	p.y -= 10;
      }
    }
    else if (command == SOUTH) {
      if (p.y + de.height < dv.height) {
	p.y += 10;
      }
    }
    else if (command == EAST) {
      if (p.x + de.width < dv.width) {
	p.x += 10;
      }
    }
    else if (command == WEST) {
      if (p.x > 9) {
	p.x -= 10;
      }
    }
    viewport.setViewPosition(p);
  }
}
