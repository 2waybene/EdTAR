// TextActionExample.java
// A simple TextAction example.
//
import javax.swing.*;
import javax.swing.text.*;

public class TextActionExample {
  public static void main(String[] args) {

    // Create a text area.
    JTextArea ta = new JTextArea();
    ta.setLineWrap(true);

    // Add all actions to the menu (split into two menus to make it more usable).
    Action[] actions = ta.getActions();
    JMenuBar menubar = new JMenuBar();
    JMenu actionmenu = new JMenu("Actions");
    menubar.add(actionmenu);

    JMenu firstHalf = new JMenu("1st Half");
    JMenu secondHalf = new JMenu("2nd Half");
    actionmenu.add(firstHalf);
    actionmenu.add(secondHalf);

    int mid = actions.length/2;
    for (int i=0; i<mid; i++) {
      firstHalf.add(actions[i]);
    }
    for (int i=mid; i<actions.length; i++) {
      secondHalf.add(actions[i]);
    }

    // Show it . . .
    JFrame f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(ta);
    f.setJMenuBar(menubar);
    f.setSize(300, 200);
    f.setVisible(true);
  }
}
