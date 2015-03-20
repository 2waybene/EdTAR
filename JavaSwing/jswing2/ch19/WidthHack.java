// WidthHack.java
// A hack to make a JTextField _really_ 2 columns wide
//

import javax.swing.*;

public class WidthHack {

  public static void main(String[] args) {

    JTextField tf = new JTextField("mm");
    tf.setPreferredSize( tf.getPreferredSize() );
    tf.setText(""); // Empty the field.

    JPanel pHacked = new JPanel();
    pHacked.setBorder(new javax.swing.border.TitledBorder("hacked 2 columns"));
    pHacked.add(tf);

    JPanel pStock = new JPanel();
    pStock.setBorder(new javax.swing.border.TitledBorder("stock 2 columns"));
    pStock.add( new JTextField(2) );

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new java.awt.GridLayout(0, 1));
    frame.getContentPane().add(pHacked);
    frame.getContentPane().add(pStock);
    frame.setSize(150, 150);
    frame.setVisible(true);
    tf.requestFocus();
  }
}
