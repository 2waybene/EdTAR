// ColorTable.java
// A table that allows the user to pick a color from a pulldown list.  This
// is accomplished by using the DefaultCellRenderer and DefaultCellEditor
// classes.
// 
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ColorTable extends JFrame {
  ColorName colors[] = {
    new ColorName("Red"), new ColorName("Green"), new ColorName("Blue"),
    new ColorName("Black"), new ColorName("White")
  };

  public ColorTable() {
    super("Table With DefaultCellEditor Example");
    setSize(500,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JTable table = new JTable(new AbstractTableModel() {
        ColorName data[] = {
          colors[0], colors[1], colors[2], colors[3], colors[4],
          colors[0], colors[1], colors[2], colors[3], colors[4]
        };
        public int getColumnCount() { return 3; }
        public int getRowCount() { return 10;  }
        public Object getValueAt(int r, int c) {
          switch (c) {
          case 0:  return (r + 1) + ".";
          case 1:  return "Some pithy quote #" + r;
          case 2:  return data[r];
          }
          return "Bad Column";
        }
        public Class getColumnClass(int c) {
          if (c == 2) return ColorName.class;
          return String.class;
        }
        // Make Column 2 editable...
        public boolean isCellEditable(int r, int c) {
          return c == 2;
        }
        public void setValueAt(Object value, int r, int c) {
          data[r] = (ColorName)value;
        }
      });

    table.setDefaultEditor(ColorName.class,
                           new DefaultCellEditor(new JComboBox(colors)));
    table.setDefaultRenderer(ColorName.class,
                             new DefaultTableCellRenderer());
    table.setRowHeight(20);
    getContentPane().add(new JScrollPane(table));
  }

  public static void main(String args[]) {
    ColorTable ex = new ColorTable();
    ex.setVisible(true);
  }

  public class ColorName {
    String cname;
    public ColorName(String name) { cname = name; }
    public String toString() { return cname; }
  }
}
