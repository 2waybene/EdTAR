// UndoableToggleApp4.java
// A sample app showing the use of StateEdit(able).
//
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;

public class UndoableToggleApp4 extends JFrame implements StateEditable {

  private JToggleButton tog;
  private JCheckBox cb;
  private JRadioButton radio;

  private JButton undoButton;
  private JButton redoButton;
  private JButton startButton;
  private JButton endButton;

  private StateEdit edit;

  // Create the main frame and everything in it.
  public UndoableToggleApp4() {

    // Create some toggle buttons (and subclasses).
    tog = new JToggleButton("ToggleButton");
    cb = new JCheckBox("CheckBox");
    radio = new JRadioButton("RadioButton");

    // Add our listener to the buttons.
    SimpleListener sl = new SimpleListener();
    tog.addActionListener(sl);
    cb.addActionListener(sl);
    radio.addActionListener(sl);

    // Lay out the buttons.
    Box buttonBox = new Box(BoxLayout.Y_AXIS);
    buttonBox.add(tog);
    buttonBox.add(cb);
    buttonBox.add(radio);

    // Create undo, redo, start, and end buttons.
    startButton = new JButton("Start");
    endButton = new JButton("End");
    undoButton = new JButton("Undo");
    redoButton = new JButton("Redo");
    startButton.setEnabled(true);
    endButton.setEnabled(false);
    undoButton.setEnabled(false);
    redoButton.setEnabled(false);

    // Add a listener to the start button. It creates a new StateEdit,
    // passing in this frame as the StateEditable.
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        edit = new StateEdit(UndoableToggleApp4.this);
        startButton.setEnabled(false);
        endButton.setEnabled(true);
        //undoButton.setEnabled(edit.canUndo());
        //
        // NOTE: We really don't want to be able to undo until end() is pressed, 
        // but StateEdit does not enforce this for us!
        undoButton.setEnabled(false);
        redoButton.setEnabled(edit.canRedo());
      }
    });

    // Add a listener to the end button. It will call end() on the StateEdit.
    endButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        edit.end();
        startButton.setEnabled(true);
        endButton.setEnabled(false);
        undoButton.setEnabled(edit.canUndo());
        redoButton.setEnabled(edit.canRedo());
      }
    });

    // Add a listener to the undo button. It attempts to call undo() on the
    // current edit, then enables/disables the undo/redo buttons as appropriate.
    undoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        try {
          edit.undo();
        } catch (CannotUndoException ex) { ex.printStackTrace(); }
        finally {
          undoButton.setEnabled(edit.canUndo());
          redoButton.setEnabled(edit.canRedo());
        }
      }
    });

    // Add a redo listener: just like the undo listener.
    redoButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
        try {
          edit.redo();
        } catch (CannotRedoException ex) { ex.printStackTrace(); }
        finally {
          undoButton.setEnabled(edit.canUndo());
          redoButton.setEnabled(edit.canRedo());
        }
      }
    });

    // Lay out the state/end and undo/redo buttons.
    Box undoRedoBox = new Box(BoxLayout.X_AXIS);
    undoRedoBox.add(Box.createGlue());
    undoRedoBox.add(startButton);
    undoRedoBox.add(Box.createHorizontalStrut(2));
    undoRedoBox.add(endButton);
    undoRedoBox.add(Box.createHorizontalStrut(2));
    undoRedoBox.add(undoButton);
    undoRedoBox.add(Box.createHorizontalStrut(2));
    undoRedoBox.add(redoButton);
    undoRedoBox.add(Box.createGlue());

    // Lay out the main frame.
    Container content = getContentPane();
    content.setLayout(new BorderLayout());
    content.add(buttonBox, BorderLayout.CENTER);
    content.add(undoRedoBox, BorderLayout.SOUTH);
    setSize(400, 150);
  }

  public class SimpleListener implements ActionListener {
    // When any toggle button is clicked, we turn off the undo and redo
    // buttons, reflecting the fact that we can only undo/redo the last
    // set of state changes as long as no additional changes have been made.
    public void actionPerformed(ActionEvent ev) {
      undoButton.setEnabled(false);
      redoButton.setEnabled(false);
    }
  }

  // Save the state of the app by storing the current state of the three
  // buttons.  We'll use the buttons themselves as keys and their selected
  // state as values.
  public void storeState(Hashtable ht) {
    ht.put(tog, new Boolean(tog.isSelected()));
    ht.put(cb, new Boolean(cb.isSelected()));
    ht.put(radio, new Boolean(radio.isSelected()));
  }

  // Restore state based on the values we saved when storeState() was called.
  // Note that StateEdit discards any state info that did not change from
  // between the start state and the end state, so we can't assume that the
  // state for all 3 buttons is in the Hashtable.
  public void restoreState(Hashtable ht) {
    Boolean b1 = (Boolean)ht.get(tog);
    if (b1 != null)
      tog.setSelected(b1.booleanValue());
    Boolean b2 = (Boolean)ht.get(cb);
    if (b2 != null)
      cb.setSelected(b2.booleanValue());
    Boolean b3 = (Boolean)ht.get(radio);
    if (b3 != null)
      radio.setSelected(b3.booleanValue());
  }

  // Main program just creates the frame and displays it.
  public static void main(String[] args) {
    JFrame f = new UndoableToggleApp4();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
}
