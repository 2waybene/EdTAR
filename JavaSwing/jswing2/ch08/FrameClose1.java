// FrameClose1.java
// A demonstration of the SDK 1.3 EXIT_ON_CLOSE behavior for JFrames.
//
import javax.swing.JFrame;

public class FrameClose1 {
  public static void main(String[] args) {
    JFrame mainFrame = new JFrame();

    // Exit app when frame is closed.
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setSize(320, 240);
    mainFrame.setVisible(true);
  }
}
