// ChartTester.java
// A test harness for the various table charting classes.  (see
// ChartPainter.java, PieChartPainter.java, TableChart.java and
// TableChartPopup.java)
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class ChartTester extends JFrame {

  public ChartTester() {
    super("Simple JTable Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    

    TableModel tm = new AbstractTableModel() {
      String data[][] = {
        {"Ron", "0.00", "68.68", "77.34", "78.02"},
        {"Ravi", "0.00", "70.89", "64.17", "75.00"},
        {"Maria", "76.52", "71.12", "75.68", "74.14"},
        {"James", "70.00", "15.72", "26.40", "38.32"},
        {"Ellen", "80.32", "78.16", "83.80", "85.72"}
      };
      String headers[] = { "", "Q1", "Q2", "Q3", "Q4" };
      public int getColumnCount() { return headers.length; }
      public int getRowCount() { return data.length; }
      public String getColumnName(int col) { return headers[col]; }
      public Class getColumnClass(int col) {
        return (col == 0) ? String.class : Number.class;
      }
      
      public boolean isCellEditable(int row, int col) { return true; }
      public Object getValueAt(int row, int col) { return data[row][col]; }
      public void setValueAt(Object value, int row, int col) {
        data[row][col] = (String)value;
        fireTableRowsUpdated(row,row);
      }
    };

    JTable jt = new JTable(tm);
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);

    final TableChartPopup tcp = new TableChartPopup(tm);
    JButton button = new JButton("Show me a chart of this table");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        tcp.setVisible(true);
      }
    } );
    getContentPane().add(button, BorderLayout.SOUTH);
  }

  public static void main(String args[]) {
    ChartTester ct = new ChartTester();
    ct.setVisible(true);
  }
}
