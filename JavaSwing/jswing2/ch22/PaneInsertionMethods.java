// PaneInsertionMethods.java
// Show how Icons, Components, and text can be added to a JTextPane.
//

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class PaneInsertionMethods {

  public static void main(String[] args) {

    final JTextPane pane = new JTextPane();

    // button to insert some text
    JButton textButton = new JButton("Insert Text");
    textButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        pane.replaceSelection("text");
      }
    });

    // button to insert an icon
    final ImageIcon icon = new ImageIcon("bluepaw.gif");
    JButton iconButton = new JButton(icon);
    iconButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        pane.insertIcon(icon);
      }
    });

    // button to insert a button
    JButton buttonButton = new JButton("Insert Button");
    buttonButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        pane.insertComponent(new JButton("Click Me"));
      }
    });

    // layout
    JPanel buttons = new JPanel();
    buttons.add(textButton);
    buttons.add(iconButton);
    buttons.add(buttonButton);

    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    frame.getContentPane().add(pane, BorderLayout.CENTER);
    frame.getContentPane().add(buttons, BorderLayout.SOUTH);
    frame.setSize(360,180);
    frame.setVisible(true);
  }
} 
