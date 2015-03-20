// ImageLabelExample.java
// Variations on a text & icon label.  The icon and text are aligned in
// different ways for each label.
//
import javax.swing.*;
import java.awt.*;

public class ImageLabelExample {

  private static Icon icon = new ImageIcon("images/smile.gif");

  public static void main(String[] args) {
    JLabel[] labels= new JLabel[9];

    labels[0] = makeLabel(JLabel.TOP, JLabel.LEFT);
    labels[1] = makeLabel(JLabel.TOP, JLabel.CENTER);
    labels[2] = makeLabel(JLabel.TOP, JLabel.RIGHT);
    labels[3] = makeLabel(JLabel.CENTER, JLabel.LEFT);
    labels[4] = makeLabel(JLabel.CENTER, JLabel.CENTER);
    labels[5] = makeLabel(JLabel.CENTER, JLabel.RIGHT);
    labels[6] = makeLabel(JLabel.BOTTOM, JLabel.LEFT);
    labels[7] = makeLabel(JLabel.BOTTOM, JLabel.CENTER);
    labels[8] = makeLabel(JLabel.BOTTOM, JLabel.RIGHT);

    // Disable label 0
    labels[0].setEnabled(false);

    // Disable label 1 with a disabled icon
    labels[1].setDisabledIcon(new ImageIcon("images/no.gif"));
    labels[1].setEnabled(false);

    // Change text gap on labels 2 and 3
    labels[2].setIconTextGap(15);
    labels[3].setIconTextGap(0);

    // Add the labels to a frame and display it
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container c = frame.getContentPane();
    c.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
    for (int i=0;i<9;i++)
      c.add(labels[i]);
    frame.setSize(350,150);
    frame.setVisible(true);
  }

  protected static JLabel makeLabel(int vert, int horiz) {
    JLabel l = new JLabel("Smile", icon, SwingConstants.CENTER);
    l.setVerticalTextPosition(vert);
    l.setHorizontalTextPosition(horiz);
    l.setBorder(BorderFactory.createLineBorder(Color.black));
    return l;
  }
}
