// PlainLookAndFeel.java
//
package plain;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

public class PlainLookAndFeel extends BasicLookAndFeel {
  public String getDescription() { return "The Plain Look and Feel"; }
  public String getID() { return "Plain"; }
  public String getName() { return "Plain"; }
  public boolean isNativeLookAndFeel() { return false; }
  public boolean isSupportedLookAndFeel() { return true; }
  // . . .

    protected void initClassDefaults(UIDefaults table) {
	Object[] classes = {
	    "SliderUI", PlainSliderUI.class.getName()
	};

	table.putDefaults(classes);
    }
}
