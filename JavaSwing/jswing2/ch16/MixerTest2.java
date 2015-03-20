// MixerTest2.java
// A test of the MixerModel and sliders for rendering volume values.
// This version also includes adjustable sliders for editing the
// volume values.
//
import java.awt.*;
import javax.swing.*;

public class MixerTest2 extends JFrame {

  public MixerTest2() {
    super("Customer Editor Test");
    setSize(600,160);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    MixerModel test = new MixerModel();
    test.dump();
    JTable jt = new JTable(test);
    jt.setDefaultRenderer(Volume.class, new VolumeRenderer());
    jt.setDefaultEditor(Volume.class, new VolumeEditor());
    JScrollPane jsp = new JScrollPane(jt);
    getContentPane().add(jsp, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    MixerTest2 mt = new MixerTest2();
    mt.setVisible(true);
  }
}
