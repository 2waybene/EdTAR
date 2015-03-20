// FSTest.java
// A quick test environment for the FSTree component.
//
 
import javax.swing.*;
import javax.swing.tree.*;

public class FSTest extends JFrame {

  public FSTest() {
    super("FSTree Component Test");
    setSize(300,300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    FSTree fst = new FSTree(new DefaultMutableTreeNode("Starter"));
    getContentPane().add(new JScrollPane(fst));
    setVisible(true);
  }

  public static void main(String args[]) {
    new FSTest();
  }
}
