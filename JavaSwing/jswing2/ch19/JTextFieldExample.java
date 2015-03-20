// JTextFieldExample.java
// An example of setting up a textfield and modifying its horizontal alignment
// at runtime.
//

import javax.swing.*;
import java.awt.event.*;

public class JTextFieldExample {

  public static void main(String[] args) {

    final JTextField tf = new JTextField("press <enter>", 20);
    tf.setHorizontalAlignment(JTextField.RIGHT);

    tf.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
	  int old = tf.getHorizontalAlignment();
	  if (old == JTextField.LEFT) tf.setHorizontalAlignment(JTextField.RIGHT);
	  if (old == JTextField.RIGHT) tf.setHorizontalAlignment(JTextField.CENTER);
	  if (old == JTextField.CENTER) tf.setHorizontalAlignment(JTextField.LEFT);
	}
      });

    JFrame frame = new JFrame("JTextFieldExample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new java.awt.FlowLayout());
    frame.getContentPane().add(tf);
    frame.setSize(275, 75);
    frame.setVisible(true);
    tf.requestFocus();
  }
}
