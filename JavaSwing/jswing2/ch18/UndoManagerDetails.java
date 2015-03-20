// UndoManagerDetails.java
// An example that shows lots of little UndoManager details
//
import javax.swing.undo.*;

public class UndoManagerDetails {
  public static void main(String[] args) {
    UndoManager mgr = new UndoManager();

    // Show how insignificant edits are skipped over.
    //
    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(1, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(2, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(3, false, false, false));
    mgr.addEdit(new SampleUndoableEdit(4, false, false, false));

    System.out.println("--------------------------");
    System.out.println("Insignificant edit example");
    System.out.println("--------------------------");
    mgr.undo();
    mgr.redo();
    System.out.println(mgr.canRedo()); // No more sig. edits

    // Show how edits that call add/replace are used.
    //
    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(5, true,  true, false));
    mgr.addEdit(new SampleUndoableEdit(6, false, true, false));
    System.out.println("----------------------------------");
    System.out.println("Absorbed (by addEdit) edit example");
    System.out.println("----------------------------------");
    mgr.undo();
    mgr.discardAllEdits();

    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(1, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(2, false, true, true));
    System.out.println("--------------------------------------");
    System.out.println("Absorbed (by replaceEdit) edit example");
    System.out.println("--------------------------------------");
    mgr.undo();
    System.out.println(mgr.canUndo());

    // Show how changing limit works.
    mgr.discardAllEdits();

    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(1, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(2, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(3, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(4, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(5, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(6, false, true, false));
    System.out.println("----------------------");
    System.out.println("Changing limit example");
    System.out.println("----------------------");
    mgr.undo();
    mgr.undo();
    mgr.undo(); // Now 3 undoable, 3 redoable
    mgr.setLimit(4); // Now 2 undoable, 2 redoable!
    while (mgr.canUndo())
      mgr.undo();
    while (mgr.canRedo())
      mgr.redo();

    // undoOrRedo example
    mgr.discardAllEdits();
    mgr.setLimit(1);

    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(1, false, true, false));
    System.out.println("------------------");
    System.out.println("undoOrRedo example");
    System.out.println("------------------");
    System.out.println(mgr.getUndoOrRedoPresentationName());
    mgr.undoOrRedo();
    System.out.println(mgr.getUndoOrRedoPresentationName());
    mgr.undoOrRedo();

    // Show how UndoManager becomes a CompositeEdit.
    mgr.discardAllEdits();
    mgr.setLimit(100);

    //                                 #  adds?  sig?  replace?
    mgr.addEdit(new SampleUndoableEdit(1, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(2, false, true, false));
    mgr.addEdit(new SampleUndoableEdit(3, false, true, false));
    System.out.println("------------------------------");
    System.out.println("Transform to composite example");
    System.out.println("------------------------------");
    mgr.end();
    mgr.undo();
    mgr.redo();

    // Show that adds are no longer allowed. Note that addEdit() returns true in
    // pre-JDK 1.2 Swing releases. This is fixed in JDK 1.2.
    System.out.println(mgr.addEdit(
     new SampleUndoableEdit(4, false, true, false)));
    mgr.undo(); // note that edit 4 is not there
  }
}
