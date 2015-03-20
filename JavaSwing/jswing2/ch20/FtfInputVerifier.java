// FtfInputVerifier.java
// An example of using an InputVerifier with a formatted textfield.
//
import javax.swing.*;
import javax.swing.text.*;

public class FtfInputVerifier {

  public static void main(String argv[]) {

    java.net.URL u = null;
    try {
       u = new java.net.URL("http://www.ora.com/");
    } catch (java.net.MalformedURLException ignored) { }

    // create two identical JFormattedTextFields
    JFormattedTextField ftf1 = new JFormattedTextField(u);
    JFormattedTextField ftf2 = new JFormattedTextField(u);

    // and set an InputVerifier on one of them
    ftf2.setInputVerifier(new InputVerifier() {
      public boolean verify(JComponent input) {
        if (!(input instanceof JFormattedTextField))
          return true; // give up focus
        return ((JFormattedTextField)input).isEditValid();
    } });

    JPanel p = new JPanel(new java.awt.GridLayout(0, 2, 3, 8));
    p.add(new JLabel("plain JFormattedTextField:", JLabel.RIGHT));
    p.add(ftf1);
    p.add(new JLabel("FTF with InputVerifier:", JLabel.RIGHT));
    p.add(ftf2);
    p.add(new JLabel("plain JTextField:", JLabel.RIGHT));
    p.add(new JTextField(u.toString()));
    p.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

    JFrame f = new JFrame("FtfInputVerifier");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(new JLabel("Try to delete the colon in each field.",
            JLabel.CENTER), java.awt.BorderLayout.NORTH);
    f.getContentPane().add(p, java.awt.BorderLayout.CENTER);
    f.pack();
    f.setVisible(true);
  }
}
