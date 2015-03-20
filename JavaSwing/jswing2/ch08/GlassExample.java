// GlassExample.java
// Show how a glass pane can be used to block mouse (and key!) events.
// Updated in response to discussions with Mark Hansen at Unify.
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GlassExample extends JFrame {
  // We'll use a custom glass pane rather than a generic JPanel.
  FixedGlassPane glass;
  JProgressBar waiter = new JProgressBar(0, 100);
  Timer timer;

  public GlassExample() {
    super("GlassPane Demo");
    setSize(500, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Now set up a few buttons & images for the main application
    JPanel mainPane = new JPanel();
    mainPane.setBackground(Color.white);
    JButton redB = new JButton("Red");
    JButton blueB = new JButton("Blue");
    JButton greenB = new JButton("Green");
    mainPane.add(redB);
    mainPane.add(greenB);
    mainPane.add(blueB);
    mainPane.add(new JLabel(new ImageIcon("oreilly.gif")));

    // Attach the popup debugger to the main app buttons so you
    // see the effect of making a glass pane visible
    PopupDebugger pd = new PopupDebugger(this);
    redB.addActionListener(pd);
    greenB.addActionListener(pd);
    blueB.addActionListener(pd);

    // And last but not least, our button to launch the glass pane
    JButton startB = new JButton("Start the big operation!");
    startB.addActionListener(new ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent A) {
          // manually control the 1.2/1.3 bug work-around
          glass.setNeedToRedispatch(false);
          glass.setVisible(true);
          startTimer();
        }
      });

    Container contentPane = getContentPane();
    contentPane.add(mainPane, BorderLayout.CENTER);
    contentPane.add(startB, BorderLayout.SOUTH);

    // Set up the glass pane with a little message and a progress bar...
    JPanel controlPane = new JPanel(new GridLayout(2,1));
    controlPane.setOpaque(false);
    controlPane.add(new JLabel("Please wait..."));
    controlPane.add(waiter);
    glass = new FixedGlassPane(getJMenuBar(), getContentPane());
    glass.setLayout(new GridLayout(0,1));
    glass.setOpaque(false);
    glass.add(new JLabel()); // padding...
    glass.add(new JLabel());
    glass.add(controlPane);
    glass.add(new JLabel());
    glass.add(new JLabel());
    setGlassPane(glass);
  }

  // A quick method to start up a 10 second timer and update the
  // progress bar
  public void startTimer() {
    if (timer == null) {
      timer = new Timer(1000, new ActionListener() {
          int progress = 0;
          public void actionPerformed(ActionEvent A) {
            progress += 10;
            waiter.setValue(progress);

            // Once we hit 100%, remove the glass pane and reset the
            // progress bar stuff
            if (progress >= 100) {
              progress = 0;
              timer.stop();
              glass.setVisible(false);
              // Again, manually control our 1.2/1.3 bug workaround
              glass.setNeedToRedispatch(true);
              waiter.setValue(0);
            }
          }
        });
    }
    if (timer.isRunning()) {
      timer.stop();
    }
    timer.start();
  }

  // A graphical debugger that pops up anytime a button is pressed
  public class PopupDebugger implements ActionListener {
    private JFrame parent;
    public PopupDebugger(JFrame f) {
      parent = f;
    }
    public void actionPerformed(ActionEvent ae) {
      JOptionPane.showMessageDialog(parent, ae.getActionCommand());
    }
  }

  public static void main(String[] args) {
    GlassExample ge = new GlassExample();
    ge.setVisible(true);
  }
}
