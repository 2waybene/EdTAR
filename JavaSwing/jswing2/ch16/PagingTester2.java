// PagingTester2.java
// A quick application that demonstrates the PagingModel.  This version has
// an input field for dynamically altering the size of a page.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class PagingTester2 extends JFrame {

  public PagingTester2() {
    super("Paged JTable Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    final PagingModel pm = new PagingModel();
    final JTable jt = new JTable(pm);

    // Use our own custom scrollpane.
    JScrollPane jsp = PagingModel.createPagingScrollPaneForTable(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);

    // Property features testing
    JPanel p = new JPanel();
    p.add(new JLabel("Page Size: "));
    final JTextField tf = new JTextField("100", 6);
    p.add(tf);
    tf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        pm.setPageSize(Integer.parseInt(tf.getText()));
      }
    });
    getContentPane().add(p, BorderLayout.SOUTH);
  }

  public static void main(String args[]) {
    PagingTester2 pt = new PagingTester2();
    pt.setVisible(true);
  }
}
