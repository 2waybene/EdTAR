// TooManyTabs.java
// A demonstration of the new tab wrapping property of JTabbedPane.
//
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class TooManyTabs extends JFrame {

  public TooManyTabs() {
    super("Too Many Tabs Test");
    setSize(200, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container contents = getContentPane();

    JTabbedPane wrap = new JTabbedPane();
    JTabbedPane scroll = new JTabbedPane();
    scroll.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    for (int i = 1; i < 24; i++) {
      String tab = "Tab #" + i;
      wrap.addTab(tab, new JLabel(tab));
      scroll.addTab(tab, new JLabel(tab));
    }
    JTabbedPane top = new JTabbedPane(JTabbedPane.RIGHT);
    top.addTab("Wrap Tabs", wrap);
    top.addTab("Scroll Tabs", scroll);
    contents.add(top);

    setVisible(true);
  }

  public static void main(String args[]) {
    new TooManyTabs();
  }
}
