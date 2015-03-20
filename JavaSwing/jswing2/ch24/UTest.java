// UTest.java
// A test frame work for the UberHandler drop handler.  This version has
// no fancy Unicode characters for ease of use.  (Note that "ease of use"
// only applys to humans...the Java tools are quite happy with Unicode
// characters.  Not all text editors are, though...)
//
import javax.swing.*;

public class UTest {

  public static void main(String args[]) {
    JFrame frame = new JFrame("Debugging Drop Zone");
    frame.setSize(500,300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextArea jta = new JTextArea();
    frame.getContentPane().add(new JScrollPane(jta));
    UberHandler uh = new UberHandler();
    uh.setOutput(jta);
    jta.setTransferHandler(uh);

    frame.setVisible(true);
  }
}

