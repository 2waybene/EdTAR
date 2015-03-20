// StyleFrame.java
// A JTextPane with a menu for Style manipulation.

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class StyleFrame extends JFrame implements ActionListener {

  protected StyleBox styleBox;
  protected JTextPane textPane;
  protected JMenuBar menuBar;
  protected JMenu applyStyleMenu, modifyStyleMenu;
  protected JMenuItem createItem;

  public StyleFrame() {
    super("StyleFrame");

    styleBox = new StyleBox();
    textPane = new JTextPane();
    getContentPane().add(new JScrollPane(textPane), BorderLayout.CENTER);

    // set up menu
    menuBar = new JMenuBar();
    JMenu styleMenu = new JMenu("Style");
    menuBar.add(styleMenu);
    setJMenuBar(menuBar);

    applyStyleMenu = new JMenu("Set Logical Style");
    applyStyleMenu.setToolTipText(
        "set the Logical Style for the paragraph at caret location");
    styleMenu.add(applyStyleMenu);

    modifyStyleMenu = new JMenu("Modify Style");
    modifyStyleMenu.setToolTipText(
        "redefine a named Style (will affect paragraphs using that style)");
    styleMenu.add(modifyStyleMenu);

    createItem = new JMenuItem("Create New Style");
    createItem.setToolTipText(
        "define a new Style (which can then be applied to paragraphs)");
    createItem.addActionListener(this);
    styleMenu.add(createItem);

    // add the default style to applyStyleMenu and modifyStyleMenu
    createMenuItems(StyleContext.DEFAULT_STYLE);
  }

  protected void createMenuItems(String styleName) {
    // add 'styleName' to applyStyleMenu and modifyStyleMenu
    JMenuItem applyItem = new JMenuItem(styleName);
    applyItem.addActionListener(this);
    applyStyleMenu.add(applyItem);

    JMenuItem modifyItem = new JMenuItem(styleName);
    modifyItem.addActionListener(this);
    modifyStyleMenu.add(modifyItem);
  }

  public void actionPerformed(ActionEvent e) {
    // determine which menuItem was invoked and process it
    JMenuItem source = (JMenuItem)e.getSource();

    if ( applyStyleMenu.isMenuComponent(source) ) {
      // apply an existing style to the paragraph at the caret position
      String styleName = source.getActionCommand();
      Style style = textPane.getStyle(styleName);
      textPane.setLogicalStyle(style);
    }

    if ( source == createItem ) {
      // define a new Style and add it to the menus
      styleBox.clear();
      int response = JOptionPane.showConfirmDialog(this, styleBox,
          "Style Editor", JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);
      if (response == JOptionPane.OK_OPTION &&
          styleBox.getStyleName().length() > 0) {
        String styleName = styleBox.getStyleName();
        Style style = textPane.addStyle(styleName, null);
        styleBox.fillStyle(style);
        createMenuItems(styleName); // add new Style to the menus
      }  
    }

    if ( modifyStyleMenu.isMenuComponent(source) ) {
      // redefine a Style (will automatically redraw paragraphs using Style)
      String styleName = source.getActionCommand();
      Style style = textPane.getStyle(styleName);
      styleBox.loadFromStyle(style);
      int response = JOptionPane.showConfirmDialog(this, styleBox,
          "Style Editor", JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);
      if (response == JOptionPane.OK_OPTION) styleBox.fillStyle(style);
    }
  }

  public static void main(String[] args) {
    JFrame frame = new StyleFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}
