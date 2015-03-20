// FactoryDemo.java
// Demo 1: field with different formats with focus and without.<br>
// Demo 2: Change the format of a field when the user presses a button.
//
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import java.text.ParseException;

public class FactoryDemo {

  public static JPanel demo1(){
    // Demo 1: field with different formats with focus and without

    JPanel pan = new JPanel(new BorderLayout());
    pan.setBorder(new TitledBorder("Demo 1: format toggles with focus"));

    MaskFormatter withFocus = null, withoutFocus = null;
    try { withFocus = new MaskFormatter("LLLL");
          withoutFocus = new MaskFormatter("UUUU");
        } catch (ParseException pe) { }

    DefaultFormatterFactory factory =
        new DefaultFormatterFactory(withoutFocus, null, withFocus);

    JFormattedTextField field = new JFormattedTextField(factory);
    field.setValue("Four");
    pan.add(field, BorderLayout.CENTER);

    return pan;
  }
  
  public static JPanel demo2(){
    // Demo 2: Change the format of a field when the user presses a button.
    // We can't call field.setFormatter() because that's a protected method.
    // (Plus it wouldn't work anyway. The old factory would replace our new
    // formatter with an "old" one next time the field gains or loses focus.)
    // The thing to do is send a new factory to field.setFormatterFactory().

    JPanel pan = new JPanel(new BorderLayout());
    pan.setBorder(new TitledBorder("Demo 2: change format midstream"));

    MaskFormatter lowercase = null;
    try { lowercase = new MaskFormatter("LLLL");
        } catch (ParseException pe) { }
    final JFormattedTextField field = new JFormattedTextField(lowercase);
    field.setValue("Fore");
    pan.add(field, BorderLayout.CENTER);

    final JButton change = new JButton("change format");
    JPanel changePanel = new JPanel();
    changePanel.add(change);
    pan.add(changePanel, BorderLayout.SOUTH);

    change.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        try {
          field.commitEdit(); // commit current edit (if any)
          MaskFormatter uppercase = new MaskFormatter("UUUU");
          DefaultFormatterFactory factory = new DefaultFormatterFactory(uppercase);
          field.setFormatterFactory(factory);
          change.setEnabled(false);
        } catch (ParseException pe) { }
      }
    });

    return pan;
  }

  public static void main(String argv[]) {
    JFrame f = new JFrame("FactoryDemo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(demo1(), BorderLayout.NORTH);
    f.getContentPane().add(demo2(), BorderLayout.SOUTH);
    f.setSize(240, 160);
    f.setVisible(true);
  }
}
