// CompassButtons.java
// A demonstration of the SpringLayout class.  This application puts
// directional buttons on a panel and keeps them close to the edges of
// the panel regardless of the panel's size.
//

import javax.swing.*;
import java.awt.*;

public class CompassButtons extends JFrame {

  JButton nb = new JButton("North");
  JButton sb = new JButton("South");
  JButton eb = new JButton("East");
  JButton wb = new JButton("West");
  JViewport viewport = new JViewport();

  public CompassButtons(String terrain) {
    super("SpringLayout Compass Demo");
    setSize(500,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    SpringLayout sl = new SpringLayout();
    Container c = getContentPane();
    c.setLayout(sl);

    int offset = 50;  // gap between buttons and outside edge
    int w      = 80;  // width of buttons
    int h      = 26;  // height of buttons
    int border =  3;  // border around viewport

    Spring offsetS     = Spring.constant(offset);
    Spring borderS     = Spring.constant(border);
    Spring widthS      = Spring.constant(w);
    Spring halfWidthS  = FractionSpring.half(widthS);
    Spring heightS     = Spring.constant(h);
    Spring halfHeightS = FractionSpring.half(heightS);
    Spring leftEdgeS   = sl.getConstraint(SpringLayout.WEST, c);
    Spring topEdgeS    = sl.getConstraint(SpringLayout.NORTH, c);
    Spring rightEdgeS  = sl.getConstraint(SpringLayout.EAST, c); 
    Spring bottomEdgeS = sl.getConstraint(SpringLayout.SOUTH, c); 
    Spring xCenterS    = FractionSpring.half(rightEdgeS);
    Spring yCenterS    = FractionSpring.half(bottomEdgeS);
    Spring leftBorder  = Spring.sum(leftEdgeS, borderS);
    Spring topBorder   = Spring.sum(topEdgeS, borderS);
    
    Spring northX = Spring.sum(xCenterS, Spring.minus(halfWidthS));
    Spring southY = Spring.sum(bottomEdgeS, Spring.minus(Spring.sum(heightS,
                                                                    offsetS)));
    Spring eastX = Spring.sum(rightEdgeS, Spring.minus(Spring.sum(widthS,
                                                                  offsetS)));
    Spring eastY = Spring.sum(yCenterS, Spring.minus(halfHeightS));

    c.add(nb, new SpringLayout.Constraints(northX, offsetS, widthS, heightS));
    c.add(sb, new SpringLayout.Constraints(northX, southY, widthS, heightS));

    c.add(wb);
    sl.getConstraints(wb).setX(offsetS);
    sl.getConstraints(wb).setY(eastY);
    sl.getConstraints(wb).setWidth(widthS);
    sl.getConstraints(wb).setHeight(heightS);
    
    c.add(eb);
    sl.getConstraints(eb).setX(eastX);
    sl.getConstraints(eb).setY(eastY);
    sl.getConstraints(eb).setWidth(widthS);
    sl.getConstraints(eb).setHeight(heightS);

    c.add(viewport); // this sets a bounds of (0,0,pref_width,pref_height)
    // The order here is important...need to have a valid width and height
    // in place before binding the (x,y) location
    sl.putConstraint(SpringLayout.SOUTH, viewport, Spring.minus(borderS), 
                     SpringLayout.SOUTH, c);
    sl.putConstraint(SpringLayout.EAST, viewport, Spring.minus(borderS), 
                     SpringLayout.EAST, c);
    sl.putConstraint(SpringLayout.NORTH, viewport, topBorder, 
                     SpringLayout.NORTH, c);
    sl.putConstraint(SpringLayout.WEST, viewport, leftBorder, 
                     SpringLayout.WEST, c);

    ImageIcon icon = new ImageIcon(getClass().getResource(terrain));
    viewport.setView(new JLabel(icon));

    // Hook up the buttons.  See the CompassScroller class (on-line) for details
    // on controlling the viewport.
    nb.setActionCommand(CompassScroller.NORTH);
    sb.setActionCommand(CompassScroller.SOUTH);
    wb.setActionCommand(CompassScroller.WEST);
    eb.setActionCommand(CompassScroller.EAST);
    CompassScroller scroller = new CompassScroller(viewport);
    nb.addActionListener(scroller);
    sb.addActionListener(scroller);
    eb.addActionListener(scroller);
    wb.addActionListener(scroller);

    setVisible(true);
  }

  public static void main(String args[]) {
    new CompassButtons(args.length == 1 ? args[0] : "terrain.gif");
  }
}
