// EmailEditor.java
// An extension of JTextField that requires an "@" somewhere in the field.
// Meant to be used as a cell editor within a JTable or JTree.
//
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class EmailEditor extends JTextField implements CellEditor {
  String value = "";
  Vector listeners = new Vector();

  // Mimic all the constructors people expect with text fields.
  public EmailEditor() { this("", 5); }
  public EmailEditor(String s) { this(s, 5); }
  public EmailEditor(int w) { this("", w); }
  public EmailEditor(String s, int w) { 
    super(s, w); 
    // Listen to our own action events so that we know when to stop editing.
    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        if (stopCellEditing()) { fireEditingStopped(); }
      }
    });
  }

  // Implement the CellEditor methods.
  public void cancelCellEditing() { setText(""); }

  // Stop editing only if the user entered a valid value.
  public boolean stopCellEditing() {
    try {
      String tmp = getText();
      int at = tmp.indexOf("@");
      if (at != -1) {
        value = tmp;
        return true;
      }
      return false;
    }
    catch (Exception e) {
      // Something went wrong (most likely we don't have a valid integer).
      return false;
    }
  }

  public Object getCellEditorValue() { return value; }

  // Start editing when the right mouse button is clicked.
  public boolean isCellEditable(EventObject eo) {
    if ((eo == null) || 
        ((eo instanceof MouseEvent) && 
         (((MouseEvent)eo).isMetaDown()))) {
      return true;
    }
    return false;
  }

  public boolean shouldSelectCell(EventObject eo) { return true; }

  // Add support for listeners.
  public void addCellEditorListener(CellEditorListener cel) {
    listeners.addElement(cel);
  }

  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement(cel);
  }

  protected void fireEditingStopped() {
    if (listeners.size() > 0) {
      ChangeEvent ce = new ChangeEvent(this);
      for (int i = listeners.size() - 1; i >= 0; i--) {
        ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
      }
    }
  }
}
