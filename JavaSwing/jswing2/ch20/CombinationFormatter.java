// CombinationFormatter.java
// Input: string of form "15-45-22" (any number of hyphen-delimeted numbers)
// <br>Output: int array
//
import javax.swing.*;
import javax.swing.text.*;

public class CombinationFormatter extends DefaultFormatter {

  public CombinationFormatter() {
    setOverwriteMode(false);
  }

  public Object stringToValue(String string) throws java.text.ParseException {
    // input: string of form "15-45-22" (any number of hyphen-delimeted numbers)
    // output: int array
    String s[] = string.split("-");
    int a[] = new int[s.length];
    for (int j=0; j<a.length; j+=1)
      try {
        a[j] = Integer.parseInt(s[j]);
      } catch (NumberFormatException nfe) {
        throw new java.text.ParseException(s[j] + " is not an int", 0);
      }
    return a;
  }

  public String valueToString(Object value) throws java.text.ParseException {
    //  input: int array
    // output: string of numerals separated by hyphens
    if (value == null) return null;
    if (! (value instanceof int[]))
      throw new java.text.ParseException("expected int[]", 0);
    int a[] = (int[])value;
    StringBuffer sb = new StringBuffer();
    for (int j=0; j < a.length; j+=1) {
      if (j > 0) sb.append('-');
      sb.append(a[j]);
    }
    return sb.toString();
  }

  protected Action[] getActions() {
    Action[] actions = { new CombinationIncrementer("increment", 1),
                         new CombinationIncrementer("decrement", -1) };
    return actions;
  }

  // begin inner class ----------------------------------------

  public class CombinationIncrementer extends AbstractAction {
    protected int delta;

    public CombinationIncrementer(String name, int delta) { // constructor
      super(name); // 'name' must match something in the component's InputMap
                   // or else this Action will not get invoked automatically.
                   // Valid names include: "reset-field-edit", "increment",
                   // "decrement", and "unselect" (see appendix B)
      this.delta = delta;
    }

    public void actionPerformed(java.awt.event.ActionEvent ae) {
      JFormattedTextField ftf = getFormattedTextField(); // from AbstractFormtter
      if (ftf == null) return;
      String text = ftf.getText();
      if (text == null) return;
      int pos = ftf.getCaretPosition();

      int hyphenCount = 0;
      for (int j=0; j < pos; j+=1) // how many hyphens precede the caret?
        if (text.charAt(j) == '-') hyphenCount += 1;
      try {
        int a[] = (int[])stringToValue(text);
        a[hyphenCount] += delta; // change the number at caret position
        if (a[hyphenCount] < 0) a[hyphenCount] = 0;
        String newText = valueToString(a);
        ftf.setText(newText); // does not retain caret position
        if ((text.charAt(pos) == '-') && (newText.length() < text.length()) )
          pos -= 1; // don't let caret move past '-' when '10' changes to '9'
        ftf.setCaretPosition(pos);
      } catch (Exception e) { return; }
    }
  }
  // end inner class  ----------------------------------------

  public static void main(String argv[]) {
    // a demo main() to show how CombinationFormatter could be used
    int comb1[] = { 35, 11, 19 };
    int comb2[] = { 10, 20, 30 };

    final JFormattedTextField field1 =
      new JFormattedTextField(new CombinationFormatter());
    field1.setValue(comb1);

    final JFormattedTextField field2 =
      new JFormattedTextField(new CombinationFormatter());
    field2.setValue(comb2);

    JPanel pan = new JPanel();
    pan.add(new JLabel("Change the combination from"));
    pan.add(field1);
    pan.add(new JLabel("to"));
    pan.add(field2);

    JButton b = new JButton("Submit");
    b.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent ae) {
        try {
          field1.commitEdit(); // make sure current edit (if any) gets committed
          field2.commitEdit();
        } catch (java.text.ParseException pe) { }
        int oldc[] = (int[])field1.getValue();
        int newc[] = (int[])field2.getValue();
        //
        // code to validate oldc[] and change to newc[] goes here
        //
      }
    });
    pan.add(b);

    JFrame f = new JFrame("CombinationFormatter Demo");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(pan);
    f.setSize(360, 100);
    f.setVisible(true);
  }    
}
