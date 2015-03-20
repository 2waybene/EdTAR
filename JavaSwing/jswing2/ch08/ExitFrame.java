// ExitFrame.java
// A very simple extension of JFrame that defaults to EXIT_ON_CLOSE for
// its close operation. Relies on the 1.3 or higher SDK.
//
import javax.swing.JFrame;
import java.awt.event.WindowEvent;

public class ExitFrame extends JFrame {

  public ExitFrame() {
    super();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public ExitFrame(String title) {
    super(title);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
