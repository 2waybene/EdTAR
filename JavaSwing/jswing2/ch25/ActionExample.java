//  ActionExampleButton.java
// An example of getting the Accessible information from a Button object.
// (You could see this information by attaching an AssistiveExample object
// to the button.  See BigExample.java for an example of that attachment.)
//
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.accessibility.*;

public class ActionExample extends Button
     implements ActionListener, Accessible {

     public ActionExample() {
         super("Press this Button");
         addActionListener(this);
     }

     public AccessibleContext getAccessibleContext() {
         return (new ActionAccessibleContext());
     }

     public void actionPerformed(ActionEvent e) {
         System.out.println("The button was pressed!");
     }

     public void processActionEvent(ActionEvent e) {
         super.processActionEvent(e);
     }

     // This class contains the accessible context for the component. Many
     // abstract methods simply call the SwingUtilities class to get the job
     // done; this is advised if you can get away with it. Otherwise, see the
     // source code for SwingUtilities.
     class ActionAccessibleContext extends AccessibleContext {

        public ActionAccessibleContext() {
            super();
            setAccessibleName("Button");
            setAccessibleDescription("Press the Button");
        }

        public AccessibleRole getAccessibleRole() {
            // Fill in whatever role you want here
            return (AccessibleRole.AWT_COMPONENT);
        }

        public AccessibleStateSet getAccessibleStateSet() {
            return SwingUtilities.
                         getAccessibleStateSet(ActionExample.this);
        }

        public int getAccessibleIndexInParent() {
            return SwingUtilities.
                         getAccessibleIndexInParent(ActionExample.this);
        }

        public int getAccessibleChildrenCount() {
            return SwingUtilities.
                         getAccessibleChildrenCount(ActionExample.this);
        }

        public Accessible getAccessibleChild(int i) {
            return SwingUtilities.getAccessibleChild(ActionExample.this, i);
        }

        public Locale getLocale() {
            //  Ask the component what its locale is
            return ActionExample.this.getLocale();
        }

        public AccessibleAction getAccessibleAction() {
            return new AccessAction();
        }
     }
 
     // This class implements the AccessibleAction interface. Essentially, there
     // is only one action that is the equivalent of pushing the button.
     class AccessAction implements AccessibleAction {

        final int NUMBER_OF_ACTIONS = 1;
        final String DESCRIPTION = "Presses the button";

        public int getAccessibleActionCount() {
            return NUMBER_OF_ACTIONS;
        }

        public String getAccessibleActionDescription(int i) {
            if (i == 0)
                return (DESCRIPTION);
            else
                return null;
        }

        public boolean doAccessibleAction(int i) {
            if (i == 0) {
                // Simulate pressing a button
                ActionExample.this.processActionEvent(new ActionEvent(this,
                     ActionEvent.ACTION_PERFORMED, 
                     ActionExample.this.getActionCommand()));
                return true; 
            } else
                return false;
        }
     }

     public static void main(String s[]) {

        ActionExample example = new ActionExample();
        
        JFrame frame = new JFrame("AccessibleAction Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(example, BorderLayout.CENTER);
        frame.setSize(100, 100); 
        frame.setVisible(true);
    }
}
