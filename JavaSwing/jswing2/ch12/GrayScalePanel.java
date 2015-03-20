// GrayScalePanel.java
// A simple implementation of the AbstractColorChooserPanel class. This class
// provides a slider and a textfield for picking out a shade of gray.
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

public class GrayScalePanel extends AbstractColorChooserPanel 
                            implements ChangeListener, ActionListener {

  JSlider scale;
  JTextField percentField;

  // Set up our list of grays. We'll assume we have all 256 possible shades,
  // and we'll do it when the class is loaded.
  static Color[] grays = new Color[256];
  static {
    for (int i=0; i<256; i++) { grays[i] = new Color(i, i, i); }
  }

  public GrayScalePanel() {
    setLayout(new GridLayout(0, 1));

    // create the slider and attach us as a listener
    scale = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
    scale.addChangeListener(this);

    // Set up our display for the chooser
    add(new JLabel("Pick your shade of gray:", JLabel.CENTER));
    JPanel jp = new JPanel();
    jp.add(new JLabel("Black"));
    jp.add(scale);
    jp.add(new JLabel("White"));
    add(jp);

    JPanel jp2 = new JPanel();
    percentField = new JTextField(3);
    percentField.setHorizontalAlignment(SwingConstants.RIGHT);
    percentField.addActionListener(this);
    jp2.add(percentField);
    jp2.add(new JLabel("%"));
    add(jp2);
  }

  // We did this work in the constructor so we can skip it here.
  protected void buildChooser() { }

  // Make sure the slider is in sync with the other panels.
  public void updateChooser() { 
    Color c = getColorSelectionModel().getSelectedColor();
    scale.setValue(toGray(c));
  }

  protected int toGray(Color c) {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    // Grab the luminance the same way GIMP does...
    return (int)Math.round(0.3 * r + 0.59 * g + 0.11 * b );
  }

  // Pick a name for our tab in the chooser
  public String getDisplayName() { return "Gray Scale"; }

  // No need for an icon.
  public Icon getSmallDisplayIcon() { return null; }
  public Icon getLargeDisplayIcon() { return null; }
  // And lastly, update the selection model as our slider changes.
  public void stateChanged(ChangeEvent ce) {
    getColorSelectionModel().setSelectedColor(grays[scale.getValue()]);
    percentField.setText("" + (100-(int)Math.round(scale.getValue() / 2.55)));
  }

  public void actionPerformed(ActionEvent ae) {
    int val = 100 - Integer.parseInt(ae.getActionCommand());
    getColorSelectionModel().setSelectedColor(grays[(int)(val * 2.55)]);
  }
}
