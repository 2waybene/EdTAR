// MixerModel.java
// An audio mixer table data model. This model contains the following columns:
// <br> + Track name (String)
// <br> + Track start time (String)
// <br> + Track stop time (String)
// <br> + Left channel volume (Volume, 0 . . 100)
// <br> + Right channel volume (Volume, 0 . . 100)
//
import javax.swing.table.*;

public class MixerModel extends AbstractTableModel {

  String headers[] = {"Track", "Start", "Stop", 
                      "Left Volume", "Right Volume"};
  Class columnClasses[] = {String.class, String.class, String.class,
                           Volume.class, Volume.class};
  Object  data[][] = {
    {"Bass", "0:00:000", "1:00:000", new Volume(56), new Volume(56)},
    {"Strings", "0:00:000", "0:52:010", new Volume(72), new Volume(52)},
    {"Brass", "0:08:000", "1:00:000", new Volume(99), new Volume(0)},
    {"Wind", "0:08:000", "1:00:000", new Volume(0), new Volume(99)},
  };

  public int getRowCount() { return data.length; }
  public int getColumnCount() { return headers.length; }
  public Class getColumnClass(int c) { return columnClasses[c]; }
  public String getColumnName(int c) { return headers[c]; }
  public boolean isCellEditable(int r, int c) { return true; }
  public Object getValueAt(int r, int c) { return data[r][c]; }

  // Ok, do something extra here so that if we get a String object back (from a
  // text field editor) we can still store that as a valid Volume object.  If
  // it's just a string, then stick it directly into our data array.
  public void setValueAt(Object value, int r, int c) {
    if (c >= 3) { ((Volume)data[r][c]).setVolume(value);}
    else {data[r][c] = value;}
  }

  // A quick debugging utility to dump out the contents of our data structure
  public void dump() {
    for (int i = 0; i < data.length; i++) {
      System.out.print("|");
      for (int j = 0; j < data[0].length; j++) {
        System.out.print(data[i][j] + "|");
      }
      System.out.println();
    }
  }
}
