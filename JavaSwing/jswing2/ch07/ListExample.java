//  ListExample.java
// An example of the JList component in action.  This program uses a custom
// renderer (BookCellRenderer.java) to show a list of books with icons of their
// covers.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ListExample extends JPanel {

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

    private JList booklist = new JList(books);

    public ListExample() {
        setLayout(new BorderLayout());
        JButton button = new JButton("Print");
        button.addActionListener(new PrintListener());

        booklist = new JList(books);
        booklist.setCellRenderer(new BookCellRenderer());
        booklist.setVisibleRowCount(4);
        JScrollPane pane = new JScrollPane(booklist);

        add(pane, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("List Example");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new ListExample());
         frame.pack();
         frame.setVisible(true);
    }

    // An inner class to respond to clicks on the Print button
    class PrintListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selected[] = booklist.getSelectedIndices();
            System.out.println("Selected Elements:  ");

            for (int i=0; i < selected.length; i++) {
                BookEntry element =
                  (BookEntry)booklist.getModel().getElementAt(selected[i]);
                System.out.println("  " + element.getTitle());
            }
        }
    }
}
