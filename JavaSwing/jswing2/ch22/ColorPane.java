// ColorPane.java
// A simple extension of JTextPane that allows the user to easily append
// colored text to the document.
//

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;

public class ColorPane extends JTextPane {

  public void appendNaive(Color c, String s) { // naive implementation
    // bad: instiantiates a new AttributeSet object on each call
    SimpleAttributeSet aset = new SimpleAttributeSet();
    StyleConstants.setForeground(aset, c);
  
    int len = getText().length();
    setCaretPosition(len); // place caret at the end (with no selection)
    setCharacterAttributes(aset, false);
    replaceSelection(s); // there is no selection, so inserts at caret
  }

  public void append(Color c, String s) { // better implementation--uses StyleContext
    StyleContext sc = StyleContext.getDefaultStyleContext();
    AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                                        StyleConstants.Foreground, c);
  
    int len = getDocument().getLength(); // same value as getText().length();
    setCaretPosition(len);  // place caret at the end (with no selection)
    setCharacterAttributes(aset, false);
    replaceSelection(s); // there is no selection, so inserts at caret
  }

  public static void main(String argv[]) {

    ColorPane pane = new ColorPane();
    for (int n=1; n <= 400; n+=1) {
      if (isPrime(n)) {
        pane.append(Color.red, String.valueOf(n)+' ');
      } else if (isPerfectSquare(n)) {
        pane.append(Color.blue, String.valueOf(n)+' ');
      } else {
        pane.append(Color.black, String.valueOf(n)+' ');
      }
    }

    JFrame f = new JFrame("ColorPane example");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setContentPane(new JScrollPane(pane));
    f.setSize(600, 400);
    f.setVisible(true);
  }

  public static boolean isPrime(int n) {
    if (n < 2) return false;
    double max = Math.sqrt(n);
    for (int j=2; j <= max; j+=1)
      if (n % j == 0) return false; // j is a factor
    return true;
  }
  
  public static boolean isPerfectSquare(int n) {
    int j = 1;
    while (j*j < n  &&  j*j > 0)
      j += 1;
    return (j*j == n);
  }

}
