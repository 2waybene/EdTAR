// CopyAction.java
// A simple Action that copies text from a PageFrame object.
//
import java.awt.event.ActionEvent;
import javax.swing.*;

public class CopyAction extends AbstractAction {
  SiteManager manager;

  public CopyAction(SiteManager sm) {
    super("", new ImageIcon("copy.gif"));
    manager = sm;
  }

  public void actionPerformed(ActionEvent ae) {
    JInternalFrame currentFrame = manager.getCurrentFrame();
    if (currentFrame == null) { return; }
    // can't cut or paste sites
    if (currentFrame instanceof SiteFrame) { return; } 
    ((PageFrame)currentFrame).copyText();
  }
}
