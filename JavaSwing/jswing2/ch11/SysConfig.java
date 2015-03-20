// SysConfig.java
// A demonstration of the JTabbedPane class for displaying and manipulating
// configuration information. The BoxLayout class is used to layout the
// first tab quickly.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SysConfig extends JFrame {
  JTabbedPane config = new JTabbedPane();
  
  public SysConfig() {
    super("JTabbedPane & BoxLayout Demonstration");
    setSize(500,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel configPane = new JPanel();
    configPane.setLayout(new BoxLayout(configPane, BoxLayout.Y_AXIS));
    JTextArea question = new JTextArea("Which of the following options\n" +
                                       "do you have installed?");
    // Ok, now configure the textarea to show up properly inside the box.
    // This is part of the "high art" of Swing...
    question.setEditable(false);
    question.setMaximumSize(new Dimension(300,50));
    question.setAlignmentX(0.0f);
    question.setBackground(configPane.getBackground());

    JCheckBox audioCB = new JCheckBox("Sound Card", true);
    JCheckBox nicCB = new JCheckBox("Ethernet Card", true);
    JCheckBox tvCB = new JCheckBox("Video Out", false);

    configPane.add(Box.createVerticalGlue());
    configPane.add(question);
    configPane.add(audioCB);
    configPane.add(nicCB);
    configPane.add(tvCB);
    configPane.add(Box.createVerticalGlue());

    JLabel audioPane = new JLabel("Audio stuff");
    JLabel nicPane = new JLabel("Networking stuff");
    JLabel tvPane = new JLabel("Video stuff");
    JLabel helpPane = new JLabel("Help information");

    audioCB.addItemListener(new TabManager(audioPane));
    nicCB.addItemListener(new TabManager(nicPane));
    tvCB.addItemListener(new TabManager(tvPane));

    config.addTab("System", null, configPane, "Choose Installed Options");
    config.addTab("Audio", null, audioPane, "Audio system configuration");
    config.addTab("Networking", null, nicPane, "Networking configuration");
    config.addTab("Video", null, tvPane, "Video system configuration");
    config.addTab("Help", null, helpPane, "How Do I...");

    getContentPane().add(config, BorderLayout.CENTER);
  }

  class TabManager implements ItemListener {
    Component tab;
    public TabManager(Component tabToManage) {
      tab = tabToManage;
    }

    public void itemStateChanged(ItemEvent ie) {
      int index = config.indexOfComponent(tab);
      if (index != -1) {
        config.setEnabledAt(index, ie.getStateChange() == ItemEvent.SELECTED);
      }
      SysConfig.this.repaint();
    }
  }

  public static void main(String args[]) {
    SysConfig sc = new SysConfig();
    sc.setVisible(true);
  }
}
