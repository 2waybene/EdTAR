// VolumeEditor.java
// A slider Editor for volume values in a table.
//
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class VolumeEditor extends JSlider implements TableCellEditor {

  public OkCancel helper = new OkCancel();
  protected transient Vector listeners;
  protected transient int originalValue;
  protected transient boolean editing;

  public VolumeEditor() {
    super(SwingConstants.HORIZONTAL);
    listeners = new Vector();
  }

  // Inner class for the ok/cancel popup window that displays below the
  // active scrollbar.  It's position will have to be determined by the
  // editor when getTableCellEditorComponent() is called.
  public class OkCancel extends JWindow {
    private JButton okB = new JButton(new ImageIcon("accept.gif"));
    private JButton cancelB = new JButton(new ImageIcon("decline.gif"));
    private int w = 50;
    private int h = 24;

    public OkCancel() {
      setSize(w,h);
      setBackground(Color.yellow);
      JPanel p = new JPanel(new GridLayout(0,2));
      //p.setBorder(BorderFactory.createLineBorder(Color.gray));
      //okB.setBorder(null);
      //cancelB.setBorder(null);
      p.add(okB);
      p.add(cancelB);
      setContentPane(p);

      okB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          stopCellEditing();
        }
      });

      cancelB.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
          cancelCellEditing();
        }
      });
    }
  } 

  public Component getTableCellEditorComponent(JTable table,Object value,
                                               boolean isSelected,
                                               int row,int column) {
    if (value == null) {
      return this;
    }
    if (value instanceof Volume) {
      setValue(((Volume)value).getVolume());
    }
    else {
      setValue(0);
    }
    table.setRowSelectionInterval(row, row);
    table.setColumnSelectionInterval(column, column);
    originalValue = getValue();
    editing = true;
    Point p = table.getLocationOnScreen();
    Rectangle r = table.getCellRect(row, column, true);
    helper.setLocation(r.x + p.x + getWidth() - 50, r.y + p.y + getHeight());
    helper.setVisible(true);
    return this;
  }

  // CellEditor methods
  public void cancelCellEditing() {
    fireEditingCanceled();
    editing = false;
    helper.setVisible(false);
  }

  public Object getCellEditorValue() {return new Integer(getValue());}

  public boolean isCellEditable(EventObject eo) {return true;}
  
  public boolean shouldSelectCell(EventObject eo) {
    return true;
  }

  public boolean stopCellEditing() {
    fireEditingStopped();
    editing = false;
    helper.setVisible(false);
    return true;
  }

  public void addCellEditorListener(CellEditorListener cel) {
    listeners.addElement(cel);
  }
  
  public void removeCellEditorListener(CellEditorListener cel) {
    listeners.removeElement(cel);
  }

  protected void fireEditingCanceled() {
    setValue(originalValue);
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingCanceled(ce);
    }
  }

  protected void fireEditingStopped() {
    ChangeEvent ce = new ChangeEvent(this);
    for (int i = listeners.size() - 1; i >= 0; i--) {
      ((CellEditorListener)listeners.elementAt(i)).editingStopped(ce);
    }
  }
}
