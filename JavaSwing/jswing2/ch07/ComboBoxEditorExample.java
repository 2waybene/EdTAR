//  ComboBoxEditorExample.java
// A custom combobox editor for use with the EditableComboBox class.
//
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class ComboBoxEditorExample implements ComboBoxEditor
{
    Map map;
    ImagePanel panel;
    ImageIcon questionIcon;
 
    public ComboBoxEditorExample(Map m, BookEntry defaultChoice) {
        map = m;
        panel = new ImagePanel(defaultChoice); 
        questionIcon = new ImageIcon("question.gif"); 
    }

    public void setItem(Object anObject)
    {
        if (anObject != null) {
            panel.setText(anObject.toString());
            BookEntry entry = (BookEntry)map.get(anObject.toString());
            if (entry != null)
                panel.setIcon(entry.getImage());
            else
                panel.setIcon(questionIcon);
        } 
    }

    public Component getEditorComponent() { return panel; }
    public Object getItem() { return panel.getText(); }
    public void selectAll() { panel.selectAll(); }

    public void addActionListener(ActionListener l) {
        panel.addActionListener(l); 
    }

    public void removeActionListener(ActionListener l) {
        panel.removeActionListener(l); 
    }

    //  We create our own inner class to handle setting and
    //  repainting the image and the text.
    class ImagePanel extends JPanel {
       
        JLabel imageIconLabel;
        JTextField textField;

        public ImagePanel(BookEntry initialEntry) {
            setLayout(new BorderLayout());

            imageIconLabel = new JLabel(initialEntry.getImage());
            imageIconLabel.setBorder(new BevelBorder(BevelBorder.RAISED));

            textField = new JTextField(initialEntry.getTitle());
            textField.setColumns(45);
            textField.setBorder(new BevelBorder(BevelBorder.LOWERED));

            add(imageIconLabel, BorderLayout.WEST);
            add(textField, BorderLayout.EAST);
        }

        public void setText(String s) { textField.setText(s); }
        public String getText() { return (textField.getText()); }

        public void setIcon(Icon i) {
            imageIconLabel.setIcon(i);
            repaint();
        }

        public void selectAll() { textField.selectAll(); }

        public void addActionListener(ActionListener l) {
            textField.addActionListener(l); 
        }
        public void removeActionListener(ActionListener l) {
            textField.removeActionListener(l); 
        }
    }
} 
