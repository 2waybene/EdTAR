// PasteAction.java
// A simple Action that pastes text into a PageFrame object.
//
import java.awt.event.ActionEvent;
import javax.swing.*;

public class PasteAction extends AbstractAction {
  SiteManager manager;

  public PasteAction(SiteManager sm) {
    super("", new ImageIcon("paste.gif"));
    manager = sm;
  }

  public void actionPerformed(ActionEvent ae) {
    JInternalFrame currentFrame = manager.getCurrentFrame();
    if (currentFrame == null) { return; }
    // cannot cut or paste sites
    if (currentFrame instanceof SiteFrame) { return; } 
    ((PageFrame)currentFrame).pasteText();
  }
}
