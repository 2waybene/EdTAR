// AnimationApplet.java
// The classic animation applet rewritten to use an animated GIF.
//
import javax.swing.*;

public class AnimationApplet extends JApplet {
  public void init() {
    ImageIcon icon = new ImageIcon("images/rolling.gif");  // animated gif
    getContentPane().add(new JLabel(icon));
  }
}
