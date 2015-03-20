// OverlayTest.java
// A test of the OverlayLayout manager allowing experimentation.
//

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OverlayTest extends JFrame {

    public OverlayTest() {
        super("OverlayLayout Test");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final Container c = getContentPane();
        c.setLayout(new GridBagLayout());

        final JPanel p1 = new GridPanel();
        final OverlayLayout overlay = new OverlayLayout(p1);
        p1.setLayout(overlay);

        final JButton jb1 = new JButton("B1");
        final JButton jb2 = new JButton("B2");
        final JButton jb3 = new JButton("B3");

        Dimension b1 = new Dimension(60, 50);
        Dimension b2 = new Dimension(80, 40);
        Dimension b3 = new Dimension(100, 60);

        jb1.setMinimumSize(b1);
        jb1.setMaximumSize(b1);
        jb1.setPreferredSize(b1);
        jb2.setMinimumSize(b2);
        jb2.setMaximumSize(b2);
        jb2.setPreferredSize(b2);
        jb3.setMinimumSize(b3);
        jb3.setMaximumSize(b3);
        jb3.setPreferredSize(b3);

        SimpleReporter reporter = new SimpleReporter();
        jb1.addActionListener(reporter);
        jb2.addActionListener(reporter);
        jb3.addActionListener(reporter);

        p1.add(jb1);
        p1.add(jb2);
        p1.add(jb3);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(2,6));
        p2.add(new JLabel("B1 X", JLabel.CENTER));
        p2.add(new JLabel("B1 Y", JLabel.CENTER));
        p2.add(new JLabel("B2 X", JLabel.CENTER));
        p2.add(new JLabel("B2 Y", JLabel.CENTER));
        p2.add(new JLabel("B3 X", JLabel.CENTER));
        p2.add(new JLabel("B3 Y", JLabel.CENTER));
        p2.add(new JLabel(""));

        final JTextField x1 = new JTextField("0.0", 4); // Button1 x alignment
        final JTextField y1 = new JTextField("0.0", 4); // Button1 y alignment
        final JTextField x2 = new JTextField("0.0", 4); 
        final JTextField y2 = new JTextField("0.0", 4); 
        final JTextField x3 = new JTextField("0.0", 4); 
        final JTextField y3 = new JTextField("0.0", 4); 

        p2.add(x1);
        p2.add(y1);
        p2.add(x2);
        p2.add(y2);
        p2.add(x3);
        p2.add(y3);


        GridBagConstraints constraints = new GridBagConstraints();
        c.add(p1, constraints);

        constraints.gridx = 1;
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                jb1.setAlignmentX(
                    Float.valueOf(x1.getText().trim()).floatValue());
                jb1.setAlignmentY(
                    Float.valueOf(y1.getText().trim()).floatValue());
                jb2.setAlignmentX(
                    Float.valueOf(x2.getText().trim()).floatValue());
                jb2.setAlignmentY(
                    Float.valueOf(y2.getText().trim()).floatValue());
                jb3.setAlignmentX(
                    Float.valueOf(x3.getText().trim()).floatValue());
                jb3.setAlignmentY(
                    Float.valueOf(y3.getText().trim()).floatValue());

                p1.revalidate();
            }
        });
        c.add(updateButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        c.add(p2, constraints);
    }

    public static void main(String args[]) {
        OverlayTest ot = new OverlayTest();
        ot.setVisible(true);
    }

    public class SimpleReporter implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            System.out.println(ae.getActionCommand());
        }
    }

    public class GridPanel extends JPanel {
        public void paint(Graphics g) {
            super.paint(g);
            int w = getSize().width;
            int h = getSize().height;

            g.setColor(Color.red);
            g.drawRect(0,0,w-1,h-1);
            g.drawLine(w/2,0,w/2,h);
            g.drawLine(0,h/2,w,h/2);
        }
    }
}
