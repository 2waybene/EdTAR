// SecretLabel.java
// An extension of the JLabel class that listens to mouse clicks and converts
// them to ActionEvents, which in turn are reported via an EventListenersList 
// object
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SecretLabel extends JLabel {

  public SecretLabel(String msg) {
    super(msg);
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent me) {
        fireActionPerformed(new ActionEvent(SecretLabel.this, 
                                            ActionEvent.ACTION_PERFORMED, 
                                            "SecretMessage"));
      }
    });
  }

  public void addActionListener(ActionListener l) {
    // We'll just use the listenerList we inherit from JComponent.
    listenerList.add(ActionListener.class, l);
  }

  public void removeActionListener(ActionListener l) {
    listenerList.remove(ActionListener.class, l);
  }

  protected void fireActionPerformed(ActionEvent ae) {
    Object[] listeners = listenerList.getListeners(ActionListener.class);
    for (int i = 0; i < listeners.length; i++) {
      ((ActionListener)listeners[i]).actionPerformed(ae);
    }
  }
}
