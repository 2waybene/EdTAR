//  MenuElementExample.java
// An example of the JPopupMenu in action.
//

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class MenuElementExample extends JPanel {

    public JPopupMenu popup;
    SliderMenuItem slider;
    int theValue = 0;

    public MenuElementExample() {

        popup = new JPopupMenu(); 
        slider = new SliderMenuItem();

        popup.add(slider);
        popup.add(new JSeparator());

        JMenuItem ticks = new JCheckBoxMenuItem("Slider Tick Marks");
        ticks.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                slider.setPaintTicks(!slider.getPaintTicks());
            }
        });
        JMenuItem labels = new JCheckBoxMenuItem("Slider Labels");
        labels.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                slider.setPaintLabels(!slider.getPaintLabels());
            }
        });
        popup.add(ticks);
        popup.add(labels);
        popup.addPopupMenuListener(new PopupPrintListener());

        addMouseListener(new MousePopupListener());
    }

    // Inner class to check whether mouse events are the popup trigger
    class MousePopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) { checkPopup(e); }
        public void mouseClicked(MouseEvent e) { checkPopup(e); }
        public void mouseReleased(MouseEvent e) { checkPopup(e); }

        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(MenuElementExample.this, e.getX(), e.getY());
            }
        }
    }

    // Inner class to print information in response to popup events
    class PopupPrintListener implements PopupMenuListener {
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) { }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            theValue = slider.getValue();
            System.out.println("The value is now " + theValue);
        }

        public void popupMenuCanceled(PopupMenuEvent e) {
            System.out.println("Popup menu is hidden!");
        }
    }
  
    public static void main(String s[]) {
        JFrame frame = new JFrame("Menu Element Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new MenuElementExample());
        frame.setSize(300, 300);
        frame.setVisible(true);
    }

    // Inner class that defines our special slider menu item
    class SliderMenuItem extends JSlider implements MenuElement {

        public SliderMenuItem() {
            setBorder(new CompoundBorder(new TitledBorder("Control"),
                                  new EmptyBorder(10, 10, 10, 10)));

            setMajorTickSpacing(20); 
            setMinorTickSpacing(10); 
        }

        public void processMouseEvent(MouseEvent e, MenuElement path[],
                                    MenuSelectionManager manager) {}

        public void processKeyEvent(KeyEvent e, MenuElement path[],
                                    MenuSelectionManager manager) {}

        public void menuSelectionChanged(boolean isIncluded) {}
 
        public MenuElement[] getSubElements() {return new MenuElement[0];}

        public Component getComponent() {return this;}
    }
}
