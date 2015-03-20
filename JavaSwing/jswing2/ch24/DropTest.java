// DropTest.java
// A simple drag & drop tester application.
//

import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class DropTest extends JFrame implements DropTargetListener {

  DropTarget dt;
  JTextArea ta;

  public DropTest() {
    super("Drop Test");
    setSize(300,300);
    addWindowListener(new BasicWindowMonitor());

    getContentPane().add(
        new JLabel("Drop a list from your file chooser here:"),
	BorderLayout.NORTH);
    ta = new JTextArea();
    ta.setBackground(Color.white);
    getContentPane().add(ta, BorderLayout.CENTER);

    // Set up our text area to recieve drops...
    // This class will handle drop events
    dt = new DropTarget(ta, this);
    setVisible(true);
  }

  public void dragEnter(DropTargetDragEvent dtde) {
    System.out.println("Drag Enter");
  }

  public void dragExit(DropTargetEvent dte) {
    System.out.println("Source: " + dte.getSource());
    System.out.println("Drag Exit");
  }

  public void dragOver(DropTargetDragEvent dtde) {
    System.out.println("Drag Over");
  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
    System.out.println("Drop Action Changed");
  }

  public void drop(DropTargetDropEvent dtde) {
    try {
      // Ok, get the dropped object and try to figure out what it is
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
	System.out.println("Possible flavor: " + flavors[i].getMimeType());
	// Check for file lists specifically
	if (flavors[i].isFlavorJavaFileListType()) {
	  // Great!  Accept copy drops...
	  dtde.acceptDrop(DnDConstants.ACTION_COPY);
	  ta.setText("Successful file list drop.\n\n");
	  
	  // And add the list of file names to our text area
	  java.util.List list = (java.util.List)tr.getTransferData(flavors[i]);
	  for (int j = 0; j < list.size(); j++) {
	    ta.append(list.get(j) + "\n");
	  }

	  // If we made it this far, everything worked.
	  dtde.dropComplete(true);
	  return;
	}
      }
      // Hmm, the user must not have dropped a file list
      System.out.println("Drop failed: " + dtde);
      dtde.rejectDrop();
    } catch (Exception e) {
      e.printStackTrace();
      dtde.rejectDrop();
    }
  }

  public static void main(String args[]) {
    new DropTest();
  }
} 
