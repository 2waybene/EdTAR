// StdOutButtonUI.java
// An alternate button resource that dumps accessibility information when
// it gains or loses focus.
//
import java.awt.*;
import java.awt.event.*;
import javax.accessibility.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class StdOutButtonUI extends ButtonUI {

  // Use a single instance of this class for all buttons
  private static StdOutButtonUI instance;

  private AccessListener listener = new AccessListener();

  // Return the single instance. If this is the first time, we create the
  // instance in this method too.
  public static ComponentUI createUI(JComponent c) {
    if (instance == null) {
      instance = new StdOutButtonUI();
    }
    return instance;
  }

  // Add a focus listener so we know when the buttons has focus.
  public void installUI(JComponent c) {
    JButton button = (JButton)c;
    button.addFocusListener(listener);
  }

  // Remove the focus listener.
  public void uninstallUI(JComponent c) {
    JButton button = (JButton)c;
    button.removeFocusListener(listener);
  }

  // Empty paint & update methods. An empty update() is critical!
  public void paint(Graphics g, JComponent c) {
  }

  public void update(Graphics g, JComponent c) {
  }

  public Insets getDefaultMargin(AbstractButton b) {
    return null; // Not called since we’re auxiliary
  }

  // A focus listener. A real L&F would want to do a lot more.
  class AccessListener extends FocusAdapter {

    // We print some accessibility info when we get focus.
    public void focusGained(FocusEvent ev) {
      JButton b = (JButton)ev.getComponent();
      AccessibleContext access = b.getAccessibleContext();
      System.out.print("Focus gained by a ");
      System.out.print(access.getAccessibleRole().toDisplayString());
      System.out.print(" named ");
      System.out.println(access.getAccessibleName());
      System.out.print("Description: ");
      System.out.println(access.getAccessibleDescription());
    }

    // We print some accessibility info when we lose focus.
    public void focusLost(FocusEvent ev) {
      JButton b = (JButton)ev.getComponent();
      AccessibleContext access = b.getAccessibleContext();
      System.out.println("Focus leaving " + access.getAccessibleName());
    }
  }
}
