// TextComponentSampler.java
// An example of several text components including password fields and
// formatted fields.
// 
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.border.*;
import java.awt.*;

public class TextComponentSampler extends JFrame {

  public static String word = "portmeiron";
  public static String markup =
    "Questions are <font size=\"+1\" color=\"blue\">a burden</font> to others,\n" +
    "answers <font size=\"+2\" color=\"red\">a prison</font> for oneself.";

  public TextComponentSampler() {
    super("TextComponentSampler");

    JTextField tf = new JTextField(word, 12);
    JPasswordField pf = new JPasswordField(word, 12);

    MaskFormatter formatter = null;
    try { formatter = new MaskFormatter("UUUUU");
        } catch (java.text.ParseException ex) { }
    JFormattedTextField ftf = new JFormattedTextField(formatter);
    ftf.setColumns(12);
    ftf.setValue(word);

    JTextArea ta1 = new JTextArea(markup);
    JScrollPane scroll1 = new JScrollPane(ta1);

    JTextArea ta2 = new JTextArea(markup);
    ta2.setLineWrap(true);
    ta2.setWrapStyleWord(true);
    JScrollPane scroll2 = new JScrollPane(ta2);

    JTextPane tp = new JTextPane();
    tp.setText(markup);
    // Create an AttributeSet with which to change color and font.
    SimpleAttributeSet attrs = new SimpleAttributeSet();
    StyleConstants.setForeground(attrs, Color.blue);
    StyleConstants.setFontFamily(attrs, "Serif");
    // Apply the AttributeSet to a few blocks of text.
    StyledDocument sdoc = tp.getStyledDocument();
    sdoc.setCharacterAttributes(14, 29, attrs, false);
    sdoc.setCharacterAttributes(51, 7, attrs, false);
    sdoc.setCharacterAttributes(78, 28, attrs, false);
    sdoc.setCharacterAttributes(114, 7, attrs, false);
    JScrollPane scroll3 = new JScrollPane(tp);

    JEditorPane ep1 = new JEditorPane("text/plain", markup);
    JScrollPane scroll4 = new JScrollPane(ep1);

    JEditorPane ep2 = new JEditorPane("text/html", markup);
    JScrollPane scroll5 = new JScrollPane(ep2);

    // Done creating text components; now lay them out and make them pretty.
    JPanel panel_tf = new JPanel();
    JPanel panel_pf = new JPanel();
    JPanel panel_ftf = new JPanel();
    panel_tf.add(tf);
    panel_pf.add(pf);
    panel_ftf.add(ftf);

    panel_tf.setBorder(new TitledBorder("JTextField"));
    panel_pf.setBorder(new TitledBorder("JPasswordField"));
    panel_ftf.setBorder(new TitledBorder("JFormattedTextField"));
    scroll1.setBorder(new TitledBorder("JTextArea (line wrap off)"));
    scroll2.setBorder(new TitledBorder("JTextArea (line wrap on)"));
    scroll3.setBorder(new TitledBorder("JTextPane"));
    scroll4.setBorder(new TitledBorder("JEditorPane (text/plain)"));
    scroll5.setBorder(new TitledBorder("JEditorPane (text/html)"));

    JPanel pan = new JPanel(new FlowLayout(FlowLayout.LEFT));
    pan.add(panel_tf);
    pan.add(panel_pf);
    pan.add(panel_ftf);

    Container contentPane = getContentPane();
    contentPane.setLayout(new GridLayout(2, 3, 8, 8));

    contentPane.add(pan);
    contentPane.add(scroll1);
    contentPane.add(scroll2);
    contentPane.add(scroll3);
    contentPane.add(scroll4);
    contentPane.add(scroll5);
  }

  public static void main(String args[]) {
    JFrame frame = new TextComponentSampler();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 450);
    frame.setVisible(true);
  }
}
