//  SelectionMonitor.java
// A graphical list selection monitor.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class SelectionMonitor extends JPanel {

    String label[] = { "Zero","One","Two","Three","Four","Five","Six",
                       "Seven","Eight","Nine","Ten","Eleven","Twelve" };
    JCheckBox checks[] = new JCheckBox[label.length];
    JList list;

    public SelectionMonitor() {
        setLayout(new BorderLayout());

        list = new JList(label);
        JScrollPane pane = new JScrollPane(list);

        //  Format the list and the buttons in a vertical box
        Box rightBox = new Box(BoxLayout.Y_AXIS);
        Box leftBox = new Box(BoxLayout.Y_AXIS);

        //  Monitor all list selections
        list.addListSelectionListener(new RadioUpdater());

        for(int i=0; i < label.length; i++) {
            checks[i] = new JCheckBox("Selection " + i);
            checks[i].setEnabled(false);
            rightBox.add(checks[i]);
        }
        leftBox.add(pane);
        add(rightBox, BorderLayout.EAST);
        add(leftBox, BorderLayout.WEST);
    }

    public static void main(String s[]) {
         JFrame frame = new JFrame("Selection Monitor");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setContentPane(new SelectionMonitor());
         frame.pack();
         frame.setVisible(true);
    }

    // Inner class that responds to selection events to update the buttons
    class RadioUpdater implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            //  If either of these are true, the event can be ignored.
            if ((!e.getValueIsAdjusting()) || (e.getFirstIndex() == -1))
                return;

            //  Change the radio button to match the current selection state
            //  for each list item that reported a change.
            for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
                checks[i].setSelected(
                    ((JList)e.getSource()).isSelectedIndex(i));
            }
        }
    }
}
