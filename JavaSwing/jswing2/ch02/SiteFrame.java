// SiteFrame.java
// A simple extension of the JInternalFrame class that contains a list
// object. Elements of the list represent HTML pages for a web site.
//
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class SiteFrame extends JInternalFrame {

  JList nameList;
  SiteManager parent;
  // Hardcode the pages of our "site" to keep things simple
  String[] pages = {"index.html", "page1.html", "page2.html"};

  public SiteFrame(String name, SiteManager sm) {
    super("Site: " + name, true, true, true);
    parent = sm;
    setBounds(50,50,250,100);

    nameList = new JList(pages);
    nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    nameList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent lse) {
        // We know this is the list, so pop up the page.
        if (!lse.getValueIsAdjusting()) {
          parent.addPageFrame((String)nameList.getSelectedValue());
        }
      }
    });
    Container contentPane = getContentPane();
    contentPane.add(nameList, BorderLayout.CENTER);
  }
}
