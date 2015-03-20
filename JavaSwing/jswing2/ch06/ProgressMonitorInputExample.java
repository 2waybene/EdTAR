// ProgressMonitorInputExample.java
// Simpilar to the ProgressMonitorExample except that we can now use an
// actual input file to monitor rather than inducing progress manually.
// The file to load should be passed as a command line argument.
//
import java.io.*;
import java.awt.*;
import javax.swing.*;

public class ProgressMonitorInputExample {

    public ProgressMonitorInputExample(String filename) {
        ProgressMonitorInputStream monitor;
        try {
            monitor = new ProgressMonitorInputStream(
                null, "Loading "+filename, new FileInputStream(filename));
            while (monitor.available() > 0) {
                byte[] data = new byte[38];
                monitor.read(data);
                System.out.write(data);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to find file: "
                + filename, "Error", JOptionPane.ERROR_MESSAGE); 
        } catch (IOException e) {;}
    }

    public static void main(String args[]) {
         new ProgressMonitorInputExample(args[0]);
    }
}
