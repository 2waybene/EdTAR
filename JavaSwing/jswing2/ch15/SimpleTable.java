// SimpleTable.java
// A test of the JTable class using default table models and a convenience 
// constructor.
//
import java.awt.*;
import javax.swing.*;

public class SimpleTable extends JFrame {

  public SimpleTable() {
    super("Simple JTable Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JTable jt = new JTable(new String[][] { {"This", "is"}, {"a", "Test"} },
                           new String[] {"Column", "Header"});
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    SimpleTable st = new SimpleTable();
    st.setVisible(true);
  }
}
