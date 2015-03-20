//IconEditor.java
// An editor for use with the JSpinner component.  This editor can display
// an icon from a list stored in the spinner.
//
import javax.swing.*;
import javax.swing.event.*;

public class IconEditor extends JLabel implements ChangeListener {

  JSpinner spinner;
  Icon icon;

  public IconEditor(JSpinner s) {
    super((Icon)s.getValue(), CENTER);
    icon = (Icon)s.getValue();
    spinner = s;
    spinner.addChangeListener(this);
  }

  public void stateChanged(ChangeEvent ce) {
    icon = (Icon)spinner.getValue();
    setIcon(icon);
  }

  public JSpinner getSpinner() { return spinner; }
  public Icon getIcon() { return icon; }
}
