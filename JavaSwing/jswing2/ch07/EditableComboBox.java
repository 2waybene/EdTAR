//  EditableComboBox.java
// A fancy example of JComboBox with a custom renderer and editor used to
// display a list of JLabel objects that include both text and icons.
//
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class EditableComboBox extends JPanel {

    private BookEntry books[] = {
           new BookEntry("Ant: The Definitive Guide", "covers/ant.gif"),
           new BookEntry("Database Programming with JDBC and Java",
                         "covers/jdbc.gif"),
           new BookEntry("Developing Java Beans", "covers/beans.gif"),
           new BookEntry("Developing JSP Custom Tag Libraries",
                         "covers/jsptl.gif"),
           new BookEntry("Java 2D Graphics", "covers/java2d.gif"),
           new BookEntry("Java and XML", "covers/jxml.gif"),
           new BookEntry("Java and XSLT", "covers/jxslt.gif"),
           new BookEntry("Java and SOAP", "covers/jsoap.gif"),
           new BookEntry("Java and XML Data Binding", "covers/jxmldb.gif"),
           new BookEntry("Java Cookbook", "covers/jcook.gif"),
           new BookEntry("Java Cryptography", "covers/jcrypto.gif"),
           new BookEntry("Java Distributed Computing", "covers/jdist.gif"),
           new BookEntry("Java I/O", "covers/javaio.gif"),
           new BookEntry("Java in a Nutshell", "covers/javanut.gif"),
           new BookEntry("Java Management Extensions", "covers/jmx.gif"),
           new BookEntry("Java Message Service", "covers/jms.gif"),
           new BookEntry("Java Network Programming", "covers/jnetp.gif"),
           new BookEntry("Java Performance Tuning", "covers/jperf.gif"),
           new BookEntry("Java RMI", "covers/jrmi.gif"),
           new BookEntry("Java Security", "covers/jsec.gif"),
           new BookEntry("JavaServer Pages", "covers/jsp.gif"),
           new BookEntry("Java Servlet Programming", "covers/servlet.gif"),
           new BookEntry("Java Swing", "covers/swing.gif"),
           new BookEntry("Java Threads", "covers/jthread.gif"),
           new BookEntry("Java Web Services", "covers/jws.gif"),
           new BookEntry("Learning Java", "covers/learnj.gif")
    };

    Map bookMap = new HashMap();

    public EditableComboBox() {
      // Build a mapping from book titles to their entries
      for (int i = 0 ; i < books.length; i++) {
        bookMap.put(books[i].getTitle(), books[i]);
      }

      setLayout(new BorderLayout()); 

      JComboBox bookCombo = new JComboBox(books);
      bookCombo.setEditable(true);
      bookCombo.setEditor(
        new ComboBoxEditorExample(bookMap, books[0]));
      bookCombo.setMaximumRowCount(4);
      bookCombo.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              System.out.println("You chose " + ((JComboBox)e.getSource()).
                                                getSelectedItem()  + "!"); 
          }
      });
      bookCombo.setActionCommand("Hello");
      add(bookCombo, BorderLayout.CENTER);
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("Combo Box Example");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new EditableComboBox());
         frame.pack();
         frame.setVisible(true);
    }
}
