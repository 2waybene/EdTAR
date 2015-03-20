// MultiHighlight.java
// An example of highlighting multiple, discontiguous regions of a text
// component.
//

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.BorderLayout;

public class MultiHighlight implements ActionListener {

  private JTextComponent comp;
  private String charsToHighlight;

  public MultiHighlight(JTextComponent c, String chars) {
    comp = c;
    charsToHighlight = chars;
  }

  public void actionPerformed(ActionEvent e) {
    // highlight all characters that appear in charsToHighlight
    Highlighter h = comp.getHighlighter();
    h.removeAllHighlights();
    String text = comp.getText().toUpperCase();

    for (int j=0; j < text.length(); j+=1) {
      char ch = text.charAt(j);
      if (charsToHighlight.indexOf(ch) >= 0) try {
        h.addHighlight(j, j+1, DefaultHighlighter.DefaultPainter);
      } catch (BadLocationException ble) { }
    }
  }

  public static void main(String args[]) {
    JFrame frame = new JFrame("MultiHighlight");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JTextArea area = new JTextArea(5, 20);
    area.setText("This is the story\nof the hare who\nlost his spectacles.");
    frame.getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);

    JButton b = new JButton("Highlight All Vowels");
    b.addActionListener(new MultiHighlight(area, "aeiouAEIOU"));
    frame.getContentPane().add(b, BorderLayout.SOUTH);
    frame.pack();
    frame.setVisible(true);
   }
}
