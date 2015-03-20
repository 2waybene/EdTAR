// MaxLengthDocument.java
// An extension of PlainDocument that restricts the length of its content.

import javax.swing.*;
import javax.swing.text.*;

public class MaxLengthDocument extends PlainDocument {

  private int max;

  // create a Document with a specified max length
  public MaxLengthDocument(int maxLength) {
    max = maxLength;
  }

  // don't allow an insertion to exceed the max length
  public void insertString(int offset, String str, AttributeSet a)
              throws BadLocationException {
    if (getLength() + str.length() > max)
         java.awt.Toolkit.getDefaultToolkit().beep();
    else super.insertString(offset, str, a);
  }

  // We’d need to override replace() as well if running under version 1.4

  // a sample main() that demonstrates using MaxLengthDocument with a JTextField
  // (note: new JFormattedTextField(new MaskFormatter("*****")) would be easier)
  public static void main(String[] args) {

    Document doc = new MaxLengthDocument(5); // set maximum length to 5
    JTextField field = new JTextField(doc, "", 8);

    JPanel flowPanel = new JPanel();
    flowPanel.add(field);
    JFrame frame = new JFrame("MaxLengthDocument demo");
    frame.setContentPane(flowPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(160, 80);
    frame.setVisible(true);
  }
}
