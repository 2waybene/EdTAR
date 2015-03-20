// FileTable.java
// A test frame for the custom table model, FileModel.
//

import java.awt.*;
import javax.swing.*;
import java.util.Date;
import java.io.File;

public class FileTable extends JFrame {

  public FileTable() {
    super("Custom TableModel Test");
    setSize(300, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    FileModel fm = new FileModel();
    JTable jt = new JTable(fm);
    jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jt.setColumnSelectionAllowed(true);

    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    FileTable ft = new FileTable();
    ft.setVisible(true);
  }
}
