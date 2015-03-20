// AlignmentExample.java
// A simple demonstration of text alignment in JLabels.
//
import javax.swing.*;
import java.awt.*;

public class AlignmentExample {
  public static void main(String[] args) {

    // Create the labels and set alignment
    JLabel label1 = new JLabel("BottomRight", SwingConstants.RIGHT);
    JLabel label2 = new JLabel("CenterLeft", SwingConstants.LEFT);
    JLabel label3 = new JLabel("TopCenter", SwingConstants.CENTER);
    label1.setVerticalAlignment(SwingConstants.BOTTOM);
    label2.setVerticalAlignment(SwingConstants.CENTER);
    label3.setVerticalAlignment(SwingConstants.TOP);

    // Add borders to the labels . . . more on Borders later in the book!
    label1.setBorder(BorderFactory.createLineBorder(Color.black));
    label2.setBorder(BorderFactory.createLineBorder(Color.black));
    label3.setBorder(BorderFactory.createLineBorder(Color.black));

    // Put it all together . . .
    JFrame frame = new JFrame("AlignmentExample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel p = new JPanel(new GridLayout(3, 1, 8, 8));
    p.add(label1);
    p.add(label2);
    p.add(label3);
    p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    frame.setContentPane(p);
    frame.setSize(200,200);
    frame.setVisible(true);
  }
}
