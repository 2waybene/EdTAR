// SpinnerTest.java
// A quick test of various spinners.
//

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class SpinnerTest extends JFrame {

  public SpinnerTest() {
    super("JSpinner Test");
    setSize(300,180);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    Container c = getContentPane();
    c.setLayout(new GridLayout(0,2));

    c.add(new JLabel(" Basic Spinner"));
    c.add(new JSpinner());

    c.add(new JLabel(" Date Spinner"));
    c.add(new JSpinner(new SpinnerDateModel()));

    String weekdays[] = new String[] { "Sunday", "Monday", "Tuesday",
       "Wednesday", "Thursday", "Friday", "Saturday" };
    c.add(new JLabel(" List Spinner"));
    c.add(new JSpinner(new SpinnerListModel(weekdays)));

    c.add(new JLabel(" Number Spinner"));
    c.add(new JSpinner(new SpinnerNumberModel(0, 0, 100, 5)));

    c.add(new JLabel(" Rollover List Spinner"));
    c.add(new JSpinner(new RolloverSpinnerListModel(weekdays)));

    setVisible(true);
  }

  public static void main(String args[]) {
    new SpinnerTest();
  }
}
