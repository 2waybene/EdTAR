// EmailTreeCellEditor.java
// An editor that actually manages two separate editors: one for folders
// (nodes) that uses a combobox; and one for files (leaves) that uses a
// textfield.
//
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.tree.*;

public class EmailTreeCellEditor implements TreeCellEditor {

  EditorComboBox nodeEditor;
  EmailEditor leafEditor;
  CellEditor currentEditor;

  static String[] emailTypes = { "Home", "Work", "Pager", "Spam" };

    public EmailTreeCellEditor() {

        EmailEditor tf = new EmailEditor();
        EditorComboBox cb = new EditorComboBox(emailTypes);

        nodeEditor = cb;
        leafEditor = tf;
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        if (leaf) { 
          currentEditor = leafEditor;
          leafEditor.setText(value.toString());
        }
        else {
          currentEditor = nodeEditor;
          nodeEditor.setSelectedItem(
              ((DefaultMutableTreeNode)value).getUserObject());
        }
        return (Component)currentEditor;
    }

    public Object getCellEditorValue() {
      return currentEditor.getCellEditorValue();
    }

    // All cells are editable in this example...
    public boolean isCellEditable(EventObject event) {
      return true;
    }

    public boolean shouldSelectCell(EventObject event) {
      return currentEditor.shouldSelectCell(event);
    }

    public boolean stopCellEditing() {
      return currentEditor.stopCellEditing();
    }

    public void cancelCellEditing() {
      currentEditor.cancelCellEditing();
    }

    public void addCellEditorListener(CellEditorListener l) {
      nodeEditor.addCellEditorListener(l);
      leafEditor.addCellEditorListener(l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
      nodeEditor.removeCellEditorListener(l);
      leafEditor.removeCellEditorListener(l);
    }
}
