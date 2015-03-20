// UndoStyleFrame.java
// Add undo support to the StyleFrame example.  This example only
// retains the most recent edit, to keep things simple.

import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import java.awt.event.*;

public class UndoStyleFrame extends StyleFrame {

  protected UndoAct undoAction = new UndoAct(); // an Action for undo
  protected RedoAct redoAction = new RedoAct(); // an Action for redo

  public UndoStyleFrame() {
    super();
    setTitle("UndoStyleFrame");

    // register the Actions as undo listeners (we inherited textPane)
    textPane.getDocument().addUndoableEditListener(undoAction);
    textPane.getDocument().addUndoableEditListener(redoAction);

    // create menu for undo/redo
    JMenu editMenu = new JMenu("Edit");
    editMenu.add(new JMenuItem(undoAction));
    editMenu.add(new JMenuItem(redoAction));
    menuBar.add(editMenu); // we inherited menuBar from superclass

    // create buttons for undo/redo
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(new JButton(undoAction));
    buttonPanel.add(new JButton(redoAction));
    getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
  }

  // begin inner classes ------------
  
  public class UndoAct extends AbstractAction implements UndoableEditListener {
    private UndoableEdit edit;

    public UndoAct() {
      super("Undo");
      setEnabled(false);
    }
    public void updateEnabled() {
      setEnabled(edit.canUndo());
    }
    public void undoableEditHappened(UndoableEditEvent event) {
      edit = event.getEdit();
      putValue(NAME, edit.getUndoPresentationName());
      updateEnabled();
    }
    public void actionPerformed(ActionEvent ae) {
      edit.undo();
      updateEnabled(); // disable undo
      redoAction.updateEnabled(); // enable redo
    }
  }
  
  public class RedoAct extends AbstractAction implements UndoableEditListener {
    private UndoableEdit edit;

    public RedoAct() {
      super("Redo");
      setEnabled(false);
    }
    public void updateEnabled() {
      setEnabled(edit.canRedo());
    }
    public void undoableEditHappened(UndoableEditEvent event) {
      edit = event.getEdit();
      putValue(NAME, edit.getRedoPresentationName());
      updateEnabled();
    }
    public void actionPerformed(ActionEvent ae) {
      edit.redo();
      updateEnabled(); // disable redo
      undoAction.updateEnabled(); // enable undo
    }
  }
  
  // end inner classes ------------

  public static void main(String[] args) {
    JFrame frame = new UndoStyleFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}
