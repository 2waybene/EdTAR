// SimpleTab.java
// A quick test of the JTabbedPane component.
//
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleTab extends JFrame {

  JTabbedPane jtp;

  public SimpleTab() {
    super("JTabbedPane");
    setSize(200, 200);
    Container contents = getContentPane();
    jtp = new JTabbedPane();
    jtp.addTab("Tab1", new JLabel("This is Tab One"));
    jtp.addTab("Tab2", new JButton("This is Tab Two"));
    jtp.addTab("Tab3", new JCheckBox("This is Tab Three"));
    contents.add(jtp);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String args[]) {
    new SimpleTab();
  }
}
