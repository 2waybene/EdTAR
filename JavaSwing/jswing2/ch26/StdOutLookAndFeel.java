// StdOutLookAndFeel.java
// A *simple* L&F that puts our StdOutButton to work.  UIs for all the other
// components would be needed to make this L&F truly functional.
//
import javax.swing.*;
import javax.swing.plaf.*;

public class StdOutLookAndFeel extends LookAndFeel {

  // A few simple informational methods . . .

  public String getName() { return "Standard Output"; }
  public String getID() { return "StdOut"; }
  public String getDescription() { return "The Standard Output Look and Feel"; }
  public boolean isNativeLookAndFeel() { return false; }
  public boolean isSupportedLookAndFeel() { return true; }

  // Our only default is the UI delegate for buttons

  public UIDefaults getDefaults() {
    UIDefaults table = new UIDefaults();

    table.put("ButtonUI", "StdOutButtonUI");
    // In order to function, we'd also need lines here to define UI delegates
    // extending each of the following classes: CheckBoxUI, ComboBoxUI,
    // DesktopIconUI, FileChooserUI, InternalFrameUI, LabelUI,
    // PopupMenuSeparatorUI, ProgressBarUI, RadioButtonUI, ScrollBarUI,
    // ScrollPaneUI, SeparatorUI, SliderUI, SplitPaneUI, TabbedPaneUI,
    // TextFieldUI, ToggleButtonUI, ToolBarUI, ToolTipUI, TreeUI, RootPaneUI.

    return table;
  }
}
