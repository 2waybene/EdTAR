// SimpleButtonGroupExample.java
// A ButtonGroup voting booth.
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleButtonGroupExample {

  public static void main(String[] args) {
    // Some choices
    JRadioButton choice1, choice2, choice3;
    choice1 = new JRadioButton("Bach: Well Tempered Clavier, Book I");
    choice1.setActionCommand("bach1");
    choice2 = new JRadioButton("Bach: Well Tempered Clavier, Book II");
    choice2.setActionCommand("bach2");
    choice3 = new JRadioButton("Shostakovich: 24 Preludes and Fugues");
    choice3.setActionCommand("shostakovich");

    // A group, to ensure that we only vote for one.
    final ButtonGroup group = new ButtonGroup();
    group.add(choice1);
    group.add(choice2);
    group.add(choice3);

    // A simple ActionListener, showing each selection using the ButtonModel
    class VoteActionListener implements ActionListener {
      public void actionPerformed(ActionEvent ev) {
        String choice = group.getSelection().getActionCommand();
        System.out.println("ACTION Choice Selected: " + choice);
      }
    }

    // A simple ItemListener, showing each selection and deselection
    class VoteItemListener implements ItemListener {
      public void itemStateChanged(ItemEvent ev) {
        boolean selected = (ev.getStateChange() == ItemEvent.SELECTED);
        AbstractButton button = (AbstractButton)ev.getItemSelectable();
        System.out.println("ITEM Choice Selected: " + selected +
                           ", Selection: " + button.getActionCommand());
      }
    }

    // Add listeners to each button
    ActionListener alisten = new VoteActionListener();
    choice1.addActionListener(alisten);
    choice2.addActionListener(alisten);
    choice3.addActionListener(alisten);

    ItemListener ilisten = new VoteItemListener();
    choice1.addItemListener(ilisten);
    choice2.addItemListener(ilisten);
    choice3.addItemListener(ilisten);

    // Throw everything together
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container c = frame.getContentPane();
    c.setLayout(new GridLayout(0, 1));
    c.add(new JLabel("Vote for your favorite prelude & fugue cycle"));
    c.add(choice1);
    c.add(choice2);
    c.add(choice3);
    frame.pack();
    frame.setVisible(true);
  }
}
