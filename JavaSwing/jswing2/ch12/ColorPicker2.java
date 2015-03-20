// ColorPicker2.java
// A quick test of the JColorChooser dialog.  This one installs the custom
// GrayScalePanel picker tab.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.colorchooser.*;

public class ColorPicker2 extends JFrame {

  Color c;

  public ColorPicker2() {
    super("JColorChooser Test Frame");
    setSize(200, 100);

    final JButton go = new JButton("Show JColorChoser");
    final Container contentPane = getContentPane();
    go.addActionListener(new ActionListener() {
      final JColorChooser chooser = new JColorChooser();
      boolean first = true;
      public void actionPerformed(ActionEvent e) {
        if (first) {
          first = false;
          GrayScalePanel gsp = new GrayScalePanel();
          chooser.addChooserPanel(gsp);
        }
        JDialog dialog = JColorChooser.createDialog(ColorPicker2.this, 
                         "Demo 2", true, chooser, new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                              c = chooser.getColor();
                            }}, null);
        dialog.setVisible(true);
        contentPane.setBackground(c);
      }
    });
    contentPane.add(go);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public static void main(String args[]) {
    ColorPicker2 cp2 = new ColorPicker2();
    cp2.setVisible(true);
  }
}
