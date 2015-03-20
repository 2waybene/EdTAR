// StyleBox.java
// A control panel that can be used to edit a style's paragraph attributes.

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class StyleBox extends JPanel {

  private static final String[] fonts = {"Monospaced", "Serif", "SansSerif"};
  private static final String[] sizes = {"8", "10", "12", "18", "24", "36"};

  private JTextField nameField;
  private JComboBox fontCombo, sizeCombo;
  private JTextField leftField, rightField, aboveField, belowField;
  private JCheckBox boldCheck, italicCheck;

  public StyleBox() {
    // create the fields and lay them out
    super(new BorderLayout(4, 4));
    JPanel labelPanel = new JPanel(new GridLayout(8, 1, 0, 2));
    JPanel valuePanel = new JPanel(new GridLayout(8, 1, 0, 2));
    add(labelPanel, BorderLayout.WEST);
    add(valuePanel, BorderLayout.CENTER);
    JLabel lab;
    JPanel sidePanel;
    
    lab = new JLabel("Style Name", SwingConstants.RIGHT);
    labelPanel.add(lab);
    nameField = new JTextField();
    lab.setLabelFor(nameField);
    valuePanel.add(nameField);

    lab = new JLabel("Font", SwingConstants.RIGHT);
    labelPanel.add(lab);
    fontCombo = new JComboBox(fonts);
    fontCombo.setEditable(true); // user may enter custom value
    lab.setLabelFor(fontCombo);
    valuePanel.add(fontCombo);

    lab = new JLabel("Size", SwingConstants.RIGHT);
    labelPanel.add(lab);
    sizeCombo = new JComboBox(sizes);
    sizeCombo.setEditable(true); // user may enter custom value
    lab.setLabelFor(sizeCombo);
    sidePanel = new JPanel(new BorderLayout(4, 0));
    sidePanel.add(sizeCombo, BorderLayout.CENTER);
    sidePanel.add(new JLabel("points"), BorderLayout.EAST);
    valuePanel.add(sidePanel);

    lab = new JLabel("Left Indent", SwingConstants.RIGHT);
    labelPanel.add(lab);
    leftField = new JTextField();
    lab.setLabelFor(leftField);
    sidePanel = new JPanel(new BorderLayout(4, 0));
    sidePanel.add(leftField, BorderLayout.CENTER);
    sidePanel.add(new JLabel("points"), BorderLayout.EAST);
    valuePanel.add(sidePanel);

    lab = new JLabel("Right Indent", SwingConstants.RIGHT);
    labelPanel.add(lab);
    rightField = new JTextField();
    lab.setLabelFor(rightField);
    sidePanel = new JPanel(new BorderLayout(4, 0));
    sidePanel.add(rightField, BorderLayout.CENTER);
    sidePanel.add(new JLabel("points"), BorderLayout.EAST);
    valuePanel.add(sidePanel);

    lab = new JLabel("Space Above", SwingConstants.RIGHT);
    labelPanel.add(lab);
    aboveField = new JTextField();
    lab.setLabelFor(aboveField);
    sidePanel = new JPanel(new BorderLayout(4, 0));
    sidePanel.add(aboveField, BorderLayout.CENTER);
    sidePanel.add(new JLabel("points"), BorderLayout.EAST);
    valuePanel.add(sidePanel);

    lab = new JLabel("Space Below", SwingConstants.RIGHT);
    labelPanel.add(lab);
    belowField = new JTextField();
    lab.setLabelFor(belowField);
    sidePanel = new JPanel(new BorderLayout(4, 0));
    sidePanel.add(belowField, BorderLayout.CENTER);
    sidePanel.add(new JLabel("points"), BorderLayout.EAST);
    valuePanel.add(sidePanel);

    boldCheck = new JCheckBox("Bold");
    italicCheck = new JCheckBox("Italic");
    sidePanel = new JPanel(new GridLayout(1, 2));
    sidePanel.add(boldCheck);
    sidePanel.add(italicCheck);
    valuePanel.add(sidePanel);

    clear(); // sets initial values, etc.
  }

  public void clear() {
    // reset all fields (also sets nameField to be editable)
    nameField.setText("");
    nameField.setEditable(true);
    fontCombo.setSelectedIndex(0);
    sizeCombo.setSelectedIndex(2);
    leftField.setText("0.0");
    rightField.setText("0.0");
    aboveField.setText("0.0");
    belowField.setText("0.0");
    boldCheck.setSelected(false);
    italicCheck.setSelected(false);
  }

  public String getStyleName() {
    // return the name of the style
    String name = nameField.getText();
    if (name.length() > 0)
      return name;
    else
      return null;
  }

  public void fillStyle(Style style) {
    // mutate 'style' with the values entered in the fields
    // (no value checking--could throw NumberFormatException)
    String font = (String)fontCombo.getSelectedItem();
    StyleConstants.setFontFamily(style, font);

    String size = (String)sizeCombo.getSelectedItem();
    StyleConstants.setFontSize(style, Integer.parseInt(size));

    String left = leftField.getText();
    StyleConstants.setLeftIndent(style, Float.valueOf(left).floatValue());

    String right = rightField.getText();
    StyleConstants.setRightIndent(style, Float.valueOf(right).floatValue());

    String above = aboveField.getText();
    StyleConstants.setSpaceAbove(style, Float.valueOf(above).floatValue());

    String below = belowField.getText();
    StyleConstants.setSpaceBelow(style, Float.valueOf(below).floatValue());

    boolean bold = boldCheck.isSelected();
    StyleConstants.setBold(style, bold);

    boolean italic = italicCheck.isSelected();
    StyleConstants.setItalic(style, italic);
  }

  // Load the form from an existing Style.
  public void loadFromStyle(Style style) {
    nameField.setText(style.getName());
    nameField.setEditable(false); // don't allow name change

    String fam = StyleConstants.getFontFamily(style);
    fontCombo.setSelectedItem(fam);

    int size = StyleConstants.getFontSize(style);
    sizeCombo.setSelectedItem(Integer.toString(size));

    float left = StyleConstants.getLeftIndent(style);
    leftField.setText(Float.toString(left));

    float right = StyleConstants.getRightIndent(style);
    rightField.setText(Float.toString(right));

    float above = StyleConstants.getSpaceAbove(style);
    aboveField.setText(Float.toString(above));

    float below = StyleConstants.getSpaceBelow(style);
    belowField.setText(Float.toString(below));

    boolean bold = StyleConstants.isBold(style);
    boldCheck.setSelected(bold);

    boolean italic = StyleConstants.isItalic(style);
    italicCheck.setSelected(italic);
  }
}
