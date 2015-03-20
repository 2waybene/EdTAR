// UberHandler.java
// A TransferHandler that will accept any drop at all.  If a text area is
// registered, debugging information will be sent there.  Otherwise, all
// debug information will be sent to stdout.
//
 
import javax.swing.*;
import java.awt.event.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;
import java.io.*;

public class UberHandler extends TransferHandler {
  JTextArea output;

  public void TransferHandler() { }

  public boolean canImport(JComponent dest, DataFlavor[] flavors) {
    // you bet we can!
    return true;
  }

  public boolean importData(JComponent src, Transferable transferable) {
    // Ok, here's the tricky part...
    println("Receiving data from " + src);
    println("Transferable object is: " + transferable);
    println("Valid data flavors: ");
    DataFlavor[] flavors = transferable.getTransferDataFlavors();
    DataFlavor listFlavor = null;
    DataFlavor objectFlavor = null;
    DataFlavor readerFlavor = null;
    int lastFlavor = flavors.length - 1;

    // Check the flavors and see if we find one we like.
    // If we do, save it.
    for (int f = 0; f <= lastFlavor; f++) {
      println("  " + flavors[f]);
      if (flavors[f].isFlavorJavaFileListType()) {
        listFlavor = flavors[f];
      }
      if (flavors[f].isFlavorSerializedObjectType()) {
        objectFlavor = flavors[f];
      }
      if (flavors[f].isRepresentationClassReader()) {
        readerFlavor = flavors[f];
      }
    }

    // Ok, now try to display the content of the drop.
    try {
      DataFlavor bestTextFlavor = DataFlavor.selectBestTextFlavor(flavors);
      BufferedReader br = null;
      String line = null;
      if (bestTextFlavor != null) {
        println("Best text flavor: " + bestTextFlavor.getMimeType());
        println("Content:");
        Reader r = bestTextFlavor.getReaderForText(transferable);
        br = new BufferedReader(r);
        line = br.readLine();
        while (line != null) {
          println(line);
          line = br.readLine();
        }
        br.close();
      }
      else if (listFlavor != null) {
        java.util.List list = (java.util.List)transferable.getTransferData(listFlavor);
        println(list);
      }
      else if (objectFlavor != null) {
        println("Data is a java object:\n" + transferable.getTransferData(objectFlavor));
      }
      else if (readerFlavor != null) {
        println("Data is an InputStream:");
        br = new BufferedReader((Reader)transferable.getTransferData(readerFlavor));
        line = br.readLine();
        while (line != null) {
          println(line);
        }
        br.close();
      }
      else {
        // Don't know this flavor type yet...
        println("No text representation to show.");
      }
      println("\n\n");
    }
    catch (Exception e) {
      println("Caught exception decoding transfer:");
      println(e);
      return false;
    }
    return true;
  }

  public void exportDone(JComponent source, Transferable data, int action) {
    // Just let us know when it occurs...
    System.err.println("Export Done.");
  }

  public void setOutput(JTextArea jta) {
    output = jta;
  }

  protected void print(Object o) {
    print(o.toString());
  }

  protected void print(String s) {
    if (output != null) {
      output.append(s);
    }
    else {
      System.out.println(s);
    }
  }

  protected void println(Object o) {
    println(o.toString());
  }

  protected void println(String s) {
    if (output != null) {
      output.append(s);
      output.append("\n");
    }
    else {
      System.out.println(s);
    }
  }

  protected void println() {
    println("");
  }

  public static void main(String args[]) {
    JFrame frame = new JFrame("Debugging Drop Zone");
    frame.setSize(500,300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextArea jta = new JTextArea();
    frame.getContentPane().add(new JScrollPane(jta));
    UberHandler uh = new UberHandler();
    uh.setOutput(jta);
    jta.setTransferHandler(uh);

    frame.setVisible(true);
  }
}
