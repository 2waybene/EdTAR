// KeymapExample.java
// A simple example showing how to add Actions for KeyStrokes.
//
import javax.swing.*;
import javax.swing.text.*;
import java.util.Hashtable;
import java.awt.event.*;
import java.awt.BorderLayout;

public class KeymapExample {
  public static void main(String[] args) {

    // start with a simple JTextArea, get its Keymap to use as our parent,
    // and create a new map called "KeymapExampleMap"
    JTextArea area = new JTextArea(6, 32);
    Keymap parent = area.getKeymap();
    Keymap newmap = JTextComponent.addKeymap("KeymapExampleMap", parent);

    // add CTRL-U: change current word to upper case (our own action)
    KeyStroke u = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK);
    Action actionU = new UpWord(); // an inner class (defined below)
    newmap.addActionForKeyStroke(u, actionU);

    // get all the actions JTextArea provides for us
    Action actionList[] = area.getActions();
    // put them in a Hashtable so we can retreive them by Action.NAME
    Hashtable lookup = new Hashtable();
    for (int j=0; j < actionList.length; j+=1)
      lookup.put(actionList[j].getValue(Action.NAME), actionList[j]);

    // add CTRL-L: select current line (action provided for us)
    KeyStroke L = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK);
    Action actionL = (Action)lookup.get(DefaultEditorKit.selectLineAction);
    newmap.addActionForKeyStroke(L, actionL);

    // add CTRL-W: select current word (action provided for us)
    KeyStroke W = KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK);
    Action actionW = (Action)lookup.get(DefaultEditorKit.selectWordAction);
    newmap.addActionForKeyStroke(W, actionW);

    // set the JTextArea's Keymap to be our new map
    area.setKeymap(newmap);

    // show the TextPane
    JFrame f = new JFrame("KeymapExample");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.getContentPane().add(new JScrollPane(area), BorderLayout.CENTER);
    area.setText("This is the story\nof the hare who\nlost his spectacles.");
    f.pack();
    f.setVisible(true);
  }

  // begin inner class
  public static class UpWord extends TextAction {
    public UpWord() {
      super("uppercase-word-action");
    }

    public void actionPerformed(ActionEvent e) {
      // change current word (or selected words) to upper case
      JTextComponent comp = getTextComponent(e);
      if (comp == null) return;
      Document doc = comp.getDocument();
      int start = comp.getSelectionStart();
      int end = comp.getSelectionEnd();
      try {
        int left = javax.swing.text.Utilities.getWordStart(comp, start);
        int right = javax.swing.text.Utilities.getWordEnd(comp, end);
        String word = doc.getText(left, right-left);
        doc.remove(left, right-left);
        doc.insertString(left, word.toUpperCase(), null);
        comp.setSelectionStart(start); // restore previous position/selection
        comp.setSelectionEnd(end);
      } catch (BadLocationException ble) { return; }
    }
  } // end inner class
}
