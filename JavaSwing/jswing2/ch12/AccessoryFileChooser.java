// AccessoryFileChooser.java
// An example of the JFileChooser class in action with an accessory.  This
// accessory (see AudioAccessory.java) will play simple audio files within
// the file chooser.
//
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class AccessoryFileChooser extends JFrame {
  JFileChooser chooser = null;
  JLabel statusbar;

  public AccessoryFileChooser() {
    super("Accessory Test Frame");
    setSize(350, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    Container c = getContentPane();
    c.setLayout(new FlowLayout());
    
    JButton accButton = new JButton("Accessory");
    statusbar = new JLabel("Output of your selection will go here");
    chooser = new JFileChooser();
    AudioAccessory aa = new AudioAccessory();
    chooser.setAccessory(aa);
    chooser.addPropertyChangeListener(aa);  // to receive selection changes
    chooser.addActionListener(aa);   // to receive approve/cancel button events

    accButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        int option = chooser.showOpenDialog(AccessoryFileChooser.this);
        if (option == JFileChooser.APPROVE_OPTION) {
          statusbar.setText("You chose " + 
           ((chooser.getSelectedFile()!=null)?
            chooser.getSelectedFile().getName():"nothing"));
        }
        else {
          statusbar.setText("You canceled.");
        }
      }
    });
    c.add(accButton);
    c.add(statusbar);
  }

  public static void main(String args[]) {
    AccessoryFileChooser afc = new AccessoryFileChooser();
    afc.setVisible(true);
  }
}
