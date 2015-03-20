// SecretTest.java
// A demonstration framework for the EventListenerList-enabled SecretLabel class
//
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SecretTest extends JFrame {

  public SecretTest() {
    super("EventListenerList Demo");
    setSize(200, 100);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    SecretLabel secret = new SecretLabel("Try Clicking Me");
    final JLabel reporter = new JLabel("Event reports will show here...");
    secret.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        reporter.setText("Got it: " + ae.getActionCommand());
      }
    } );
    getContentPane().add(secret, BorderLayout.NORTH);
    getContentPane().add(reporter, BorderLayout.SOUTH);    
  }

  public static void main(String args[]) {
    SecretTest st = new SecretTest();
    st.setVisible(true);
  }
}
