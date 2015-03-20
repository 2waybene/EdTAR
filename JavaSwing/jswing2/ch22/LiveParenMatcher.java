// LiveParenMatcher.java
// Like ParenMatcher but continuously colors as the user edits the document.

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class LiveParenMatcher extends ParenMatcher implements DocumentListener {

  public LiveParenMatcher() {
    super();
    getDocument().addDocumentListener(this);
  }

  public void changedUpdate(DocumentEvent de) {
    // no insertion or deletion, so do nothing
  }

  public void insertUpdate(DocumentEvent de) {
    SwingUtilities.invokeLater(this); // will call run()
  }

  public void removeUpdate(DocumentEvent de) {
    SwingUtilities.invokeLater(this); // will call run()
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("LiveParenMatcher");
    frame.setContentPane(new JScrollPane(new LiveParenMatcher()));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 200);
    frame.setVisible(true);
  }

  // ---- finished example from "The DocumentListener Interface" ----

  // ---- begin example from "The DocumentEvent Interface" ----
  //      (method renamed to insertUpdate_2)
  
  public void insertUpdate_2(DocumentEvent de) {
    Document doc = de.getDocument();
    int offset = de.getOffset();
    int length = de.getLength();
    String inserted = "";
    try {
      inserted = doc.getText(offset, length);
    } catch (BadLocationException ble) { }

    for (int j=0; j < inserted.length(); j+=1) {
      char ch = inserted.charAt(j);
      if (ch == '(' || ch == '[' || ch == '{' ||
          ch == ')' || ch == ']' || ch == '}'  ) {
        SwingUtilities.invokeLater(this); // will call run()
        return; // no need to check further
      }
    }
  }

  // ---- begin example from "The Segment Class" ----
  //      (method renamed to insertUpdate_3)

  public void insertUpdate_3(DocumentEvent de) {
    Document doc = de.getDocument();
    int offset = de.getOffset();
    int length = de.getLength();
    Segment seg = new Segment();
    try {
      doc.getText(offset, length, seg); // text placed in Segment
    } catch (BadLocationException ble) { }

    // iterate through the Segment
    for (char ch = seg.first(); ch != seg.DONE; ch = seg.next())
      if (ch == '(' || ch == '[' || ch == '{' ||
          ch == ')' || ch == ']' || ch == '}'  ) {
        SwingUtilities.invokeLater(this); // will call run()
        return; // no need to check further
      }
  }

  // ---- begin example from "The ElementIterator Class" ----
  //      (method renamed to removeUpdate_2)

  public void removeUpdate_2(DocumentEvent de) {
    // print some debugging information before matching the parens
    ElementIterator iter = new ElementIterator(de.getDocument());

    for (Element elem = iter.first(); elem != null; elem = iter.next()) {
      DocumentEvent.ElementChange change = de.getChange(elem);
      if (change != null) { // null means there was no change in elem
        System.out.println("Element "+elem.getName() + " (depth " +
            iter.depth()+") changed its children: " +
            change.getChildrenRemoved().length+" children removed, " +
            change.getChildrenAdded().length+" children added.\n");
      }
    }
    SwingUtilities.invokeLater(this); // will call run()
  }

}
