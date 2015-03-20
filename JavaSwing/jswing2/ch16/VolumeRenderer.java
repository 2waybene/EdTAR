// VolumeRenderer.java
// A slider renderer for volume values in a table.
//
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;

public class VolumeRenderer extends JSlider implements TableCellRenderer {

  public VolumeRenderer() {
    super(SwingConstants.HORIZONTAL);
    // set a starting size...some 1.2/1.3 systems need this
    setSize(115,15);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus,
                                                 int row,int column) {
    if (value == null) {
      return this;
    }
    if (value instanceof Volume) {
      setValue(((Volume)value).getVolume());
    }
    else {
      setValue(0);
    }
    return this;
  }
}
