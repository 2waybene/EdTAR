// ClockLabel.java
// An extension of the JLabel class that listens to events from
// a Timer object to update itself with the current date & time.
//
import java.util.Date;
import java.awt.event.*;
import javax.swing.*;

public class ClockLabel extends JLabel implements ActionListener {

  public ClockLabel() {
    super("" + new Date());
    Timer t = new Timer(1000, this);
    t.start();
  }

  public void actionPerformed(ActionEvent ae) {
    setText((new Date()).toString());
  }
}
