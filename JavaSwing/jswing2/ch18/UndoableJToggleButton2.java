// UndoableJToggleButton2.java
// Sample undoable toggle button class using UndoableEditSupport.
// 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;

public class UndoableJToggleButton2 extends JToggleButton {

  private UndoableEditSupport support;

  // For this example, we'll provide just one constructor.
  public UndoableJToggleButton2(String txt) {
    super(txt);
    support = new UndoableEditSupport(this);
  }

  // Add an UndoableEditListener using our support object.
  public void addUndoableEditListener(UndoableEditListener l) {
    support.addUndoableEditListener(l);
  }

  // Remove an UndoableEditListener using our support object.
  public void removeUndoableEditListener(UndoableEditListener l) {
    support.addUndoableEditListener(l);
  }

  // Override this method to call the super implementation first (to fire the
  // action event) and then fire a new UndoableEditEvent to our listeners using
  // our support object.
  protected void fireActionPerformed(ActionEvent ev) {

    // Fire the ActionEvent as usual.
    super.fireActionPerformed(ev);

    support.postEdit(new UndoableToggleEdit(this));
  }
}
