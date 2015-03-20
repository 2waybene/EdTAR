// SampleUndoableEdit.java
// A simple (?) example of an undoable edit.
//
import javax.swing.undo.*;
import java.util.*;

public class SampleUndoableEdit extends AbstractUndoableEdit {

  private boolean isSignificant;
  private boolean isReplacer;
  private int number;
  private boolean allowAdds;
  private Vector addedEdits;
  private UndoableEdit replaced;

  // Create a new edit with an identifying number. The boolean arguments define
  // the edit's behavior.
  public SampleUndoableEdit(int number, boolean allowAdds,
                            boolean isSignificant,
                            boolean isReplacer) {
    this.number = number;
    this.allowAdds = allowAdds;
    if (allowAdds)
      addedEdits = new Vector();
    this.isSignificant = isSignificant;
    this.isReplacer = isReplacer;
  }

  // "Undo" the edit by printing a message to the screen.
  public void undo() throws CannotUndoException {
    super.undo();
    System.out.print("Undo " + number);
    dumpState();
  }

  // "Redo" the edit by printing a message to the screen.
  public void redo() throws CannotRedoException {
    super.redo();
    System.out.print("Redo " + number);
    dumpState();
  }

  // If allowAdds is true, we store the input edit. If not, just return false.
  public boolean addEdit(UndoableEdit anEdit) {
    if (allowAdds) {
      addedEdits.addElement(anEdit);
      return true;
    }
    else
      return false;
  }

  // If isReplacer is true, we store the edit we are replacing.
  public boolean replaceEdit(UndoableEdit anEdit) {
    if (isReplacer) {
      replaced = anEdit;
      return true;
    }
    else
      return false;
  }

  // Significance is based on constructor parameter.
  public boolean isSignificant() {
    return isSignificant;
  }

  // Just return our identifier.
  public String toString() {
    return "<" + number + ">";
  }

  // Debug output.
  public void dumpState() {
    if (allowAdds && addedEdits.size() > 0) {
      Enumeration e = addedEdits.elements();
      System.out.print(" (absorbed: ");
      while (e.hasMoreElements()) {
        System.out.print(e.nextElement());
      }
      System.out.print(")");
    }
    if (isReplacer && replaced != null) {
      System.out.print(" (replaced: " + replaced + ")");
    }
    System.out.println();
  }
}
