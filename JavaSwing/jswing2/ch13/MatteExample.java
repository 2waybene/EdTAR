//  MatteExample.java
// An example of the MatteBorder class.
//
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class MatteExample extends JPanel {

    public MatteExample() {
        super(true);
        this.setLayout(new GridLayout(1, 2, 5, 5));

        JLabel label1 = new JLabel("Matte Border");
        JLabel label2 = new JLabel("Matte Border (Icon)");

        label1.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);

        Icon icon = new ImageIcon("plant.gif");
        MatteBorder matte = new MatteBorder(35, 35, 35, 35, Color.blue);
        MatteBorder matteicon = new MatteBorder(35, 35, 35, 35, icon);
        label1.setBorder(matte);
        label2.setBorder(matteicon);

        add(label1);
        add(label2);
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("Matte Borders");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(500, 200);
         frame.setContentPane(new MatteExample());
         frame.setVisible(true);
    }
}
