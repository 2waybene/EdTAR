// CutAction.java
// A simple Action that copies text from a PageFrame object.
//
import java.awt.event.ActionEvent;
import javax.swing.*;

public class CutAction extends AbstractAction {
  SiteManager manager;

  public CutAction(SiteManager sm) {
    super("", new ImageIcon("cut.gif"));
    manager = sm;
  }

  public void actionPerformed(ActionEvent ae) {
    JInternalFrame currentFrame = manager.getCurrentFrame();
    if (currentFrame == null) { return; }
    // cannot cut or paste sites
    if (currentFrame instanceof SiteFrame) { return; } 
    ((PageFrame)currentFrame).cutText();
  }
}
