// SortingColumnModel.java
// A simple extension of the DefaultTableColumnModel class that sorts
// incoming columns alphabetically.  This version is case sensitive.
//
import javax.swing.table.*;

public class SortingColumnModel extends DefaultTableColumnModel {

  public void addColumn(TableColumn tc) {
    super.addColumn(tc);
    int newIndex = sortedIndexOf(tc);
    if (newIndex != tc.getModelIndex()) {
      moveColumn(tc.getModelIndex(), newIndex);
    }
  }

  protected int sortedIndexOf(TableColumn tc) {
    // just do a linear search for now
    int stop = getColumnCount();
    String name = tc.getHeaderValue().toString();

    for (int i = 0; i < stop; i++) {
      if (name.compareTo(getColumn(i).getHeaderValue().toString()) <= 0) {
        return i;
      }
    }
    return stop;
  }
}
