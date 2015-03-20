// FrameClose2.java
// A demonstration of the WindowListener approach to closing JFrames.
//
import javax.swing.JFrame;
import java.awt.event.*;

public class FrameClose2 {
  public static void main(String[] args) {
    JFrame mainFrame = new JFrame();

    // Exit app when frame is closed.
    mainFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent ev) {
        System.exit(0);
      }
    });

    mainFrame.setSize(320, 240);
    mainFrame.setVisible(true);
  }
}
