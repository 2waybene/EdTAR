// OpaqueExample.java
// Creates two JPanels (opaque), one containing another opaque JPanel, and
// the other containing a non-opaque JPanel.
//
import javax.swing.*;
import java.awt.*;

public class OpaqueExample extends JFrame {

  public OpaqueExample() {
    super("Opaque JPanel Demo");
    setSize(400, 200);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel opaque = createNested(true);
    JPanel notOpaque = createNested(false);

    // Throw it all together
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(opaque);
    getContentPane().add(notOpaque);
  }

  public static void main(String[] args) {
    OpaqueExample oe = new OpaqueExample();
    oe.setVisible(true);
  }

  // Create a JPanel containing another JPanel. The inner JPanel's opacity
  // is set according to the parameter. A JButton is placed inside the inner
  // JPanel to give it some content.
  public JPanel createNested(boolean opaque) {
    JPanel outer = new JPanel(new FlowLayout());
    JPanel inner = new JPanel(new FlowLayout());
    outer.setBackground(Color.white);
    inner.setBackground(Color.black);

    inner.setOpaque(opaque);
    inner.setBorder(BorderFactory.createLineBorder(Color.gray));

    inner.add(new JButton("Button"));
    outer.add(inner);

    return outer;
  }
}
