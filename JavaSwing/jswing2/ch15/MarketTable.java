// MarketTable.java
// An application that display stock market data in a JTable.  The table
// uses the MarketDataModel class for its model.  MYOSM should be running
// to make the data dynamic.
//
import java.awt.*;
import javax.swing.*;

public class MarketTable extends JFrame {

  public MarketTable() {
    super("Dynamic Data Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Set up our table model with a 5-second polling delay
    MarketDataModel mdm = new MarketDataModel(5);

    // Pick which stocks we want to watch . . .
    mdm.setStocks(new int[] { 0, 1, 2 });

    // And pop up the table
    JTable jt = new JTable(mdm);
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    MarketTable mt = new MarketTable();
    mt.setVisible(true);
  }
}
