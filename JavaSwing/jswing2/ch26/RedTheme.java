// RedTheme.java
// A "Mars" theme for Metal.  All of the primary colors used by Metal are
// set to shades of red.
//
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class RedTheme extends DefaultMetalTheme {
  public String getName() { return "Mars"; }

  private final ColorUIResource primary1 = new ColorUIResource(153, 102, 102);
  private final ColorUIResource primary2 = new ColorUIResource(204, 153, 153);
  private final ColorUIResource primary3 = new ColorUIResource(255, 204, 204);

  protected ColorUIResource getPrimary1() { return primary1; }
  protected ColorUIResource getPrimary2() { return primary2; }
  protected ColorUIResource getPrimary3() { return primary3; }
}
