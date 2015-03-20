// TableChartPopup.java
// A popup framework for showing a chart of table data.  This class also
// turns on tooltips for the chart.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class TableChartPopup extends JFrame {

  public TableChartPopup(TableModel tm) {
    super("Table Chart");
    setSize(300,200);
    TableChart tc = new TableChart(tm);
    getContentPane().add(tc, BorderLayout.CENTER);

    // Use the next line to turn on tooltips:
    ToolTipManager.sharedInstance().registerComponent(tc);
  }
}
