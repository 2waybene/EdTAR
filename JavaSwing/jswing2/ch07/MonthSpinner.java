// MonthSpinner.java
// An example of JSpinner with a custom editor that allows the user to spin
// through dates of the mm/yy format.
//

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;

public class MonthSpinner extends JFrame {

  public MonthSpinner() {
    super("Month Spinner");
    setSize(200,100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Container c = getContentPane();
    c.setLayout(new FlowLayout(FlowLayout.LEFT, 4,4));

    c.add(new JLabel("Expiration Date:"));
    Date today = new Date();
    // Start the spinner today, but don't set a min or max date
    // The increment should be a month
    JSpinner s = new JSpinner(new SpinnerDateModel(today, 
                 null, null, Calendar.MONTH));
    JSpinner.DateEditor de = new JSpinner.DateEditor(s, "MM/yy");
    s.setEditor(de);
    c.add(s);

    setVisible(true);
  }

  public static void main(String args[]) {
    new MonthSpinner();
  }
}
