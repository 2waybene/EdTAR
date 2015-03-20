// MyMetalScrollBarUI.java
// A simple extension of MetalScrollBarUI that draws the thumb as a solid
// black rectangle.
//
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class MyMetalScrollBarUI extends MetalScrollBarUI
{
  // Create our own scrollbar UI!
  public static ComponentUI createUI( JComponent c ) {
    return new MyMetalScrollBarUI();
  }

  // This method paints the scroll thumb.  We’ve just taken the 
  // MetalScrollBarUI code and stripped out all the
  // interesting painting code, replacing it with code that paints a
  // black box.
  protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds)
  {
    if (!c.isEnabled()) { return; }

    g.translate( thumbBounds.x, thumbBounds.y );
    if ( scrollbar.getOrientation() == JScrollBar.VERTICAL ) {
      if ( !isFreeStanding ) {
        thumbBounds.width += 2;
      }
      g.setColor( Color.black );
      g.fillRect( 0, 0, thumbBounds.width - 2, thumbBounds.height - 1 );
      if ( !isFreeStanding ) {
        thumbBounds.width -= 2;
      }
    }
    else  { // HORIZONTAL
      if ( !isFreeStanding ) {
        thumbBounds.height += 2;
      }
      g.setColor( Color.black );
      g.fillRect( 0, 0, thumbBounds.width - 1, thumbBounds.height - 2 );
      if ( !isFreeStanding ) {
        thumbBounds.height -= 2;
      }
    }
    g.translate( -thumbBounds.x, -thumbBounds.y );
  }
}
