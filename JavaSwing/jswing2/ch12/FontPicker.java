// FontPicker.java
// A quick test of the FontChooser dialog.  (see FontChooser.java)
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.*;

public class FontPicker extends JFrame {

  Color c;

  public FontPicker() {
    super("JColorChooser Test Frame");
    setSize(200,100);
    final JButton go = new JButton("Show FontChooser");
    go.addActionListener(new ActionListener() {
      final FontChooser chooser = new FontChooser(FontPicker.this);
      boolean first = true;
      public void actionPerformed(ActionEvent e) {
        chooser.setVisible(true);
        // If we got a real font choice, then update our go button
        if (chooser.getNewFont() != null) {
          go.setFont(chooser.getNewFont());
          go.setForeground(chooser.getNewColor());
        }
      }
    });
    getContentPane().add(go);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String args[]) {
    FontPicker fp = new FontPicker();
    fp.setVisible(true);
  }
}
