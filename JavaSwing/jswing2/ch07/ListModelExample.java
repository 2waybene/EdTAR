//  ListModelExample.java
// An example of JList with a DefaultListModel that we build up at runtime.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ListModelExample extends JPanel {

    JList list;
    DefaultListModel model;
    int counter = 15;

    public ListModelExample() {
        setLayout(new BorderLayout());
        model = new DefaultListModel();
        list = new JList(model);
        JScrollPane pane = new JScrollPane(list);
        JButton addButton = new JButton("Add Element");
        JButton removeButton = new JButton("Remove Element");
        for (int i = 0; i < 15; i++)
            model.addElement("Element " + i);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.addElement("Element " + counter);
                counter++;
            }
        });
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            if (model.getSize() > 0)
                model.removeElementAt(0);
            }
        });

        add(pane, BorderLayout.NORTH);
        add(addButton, BorderLayout.WEST);
        add(removeButton, BorderLayout.EAST);
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("List Model Example");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new ListModelExample());
         frame.setSize(260, 200);
         frame.setVisible(true);
    }
}
