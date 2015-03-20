// AudioAccessory.java
// An accessory for JFileChooser that lets you play music clips.  Only the
// simple .au, .aiff and .wav formats available through the Applet sound
// classes can be played.
//
import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.beans.*;
import java.io.*;
import java.applet.*;
import java.awt.event.*;

public class AudioAccessory extends JPanel implements PropertyChangeListener,
ActionListener {

  AudioClip currentClip;
  String currentName="";
  JLabel fileLabel;
  JButton playButton, stopButton;

  public AudioAccessory() {
    // Set up the accessory.  The file chooser will give us a reasonable size.
    setLayout(new BorderLayout());
    add(fileLabel = new JLabel("Clip Name"), BorderLayout.NORTH);
    JPanel p = new JPanel();
    playButton = new JButton("Play");
    stopButton = new JButton("Stop");
    playButton.setEnabled(false);
    stopButton.setEnabled(false);
    p.add(playButton);
    p.add(stopButton);
    add(p, BorderLayout.CENTER);

    playButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentClip != null) {
          currentClip.stop();
          currentClip.play();
        }
      }
    });
    stopButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (currentClip != null) {
          currentClip.stop();
        }
      }
    });
  }

  public void propertyChange(PropertyChangeEvent e) {
    String pname = e.getPropertyName();
    if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY.equals(pname)) {
      // Ok, the user selected a file in the chooser
      File f = (File)e.getNewValue();

      // Make reasonably sure it's an audio file
      if ((f != null) && 
          (f.getName().toLowerCase().endsWith(".au") ||
           f.getName().toLowerCase().endsWith(".wav") ||
           f.getName().toLowerCase().endsWith(".aif") ||
           f.getName().toLowerCase().endsWith(".aiff"))
         ) {
        setCurrentClip(f);
      }
      else {
        setCurrentClip(null);
      }
    }
  }

  public void setCurrentClip(File f) {
    if (currentClip != null) { currentClip.stop(); }
    // Make sure we have a real file, otherwise, disable the buttons
    if ((f == null) || (f.getName() == null)) {
      fileLabel.setText("no audio selected");
      playButton.setEnabled(false);
      stopButton.setEnabled(false);
      return;
    }

    // Ok, seems the audio file is real, so load it and enable the buttons
    String name = f.getName();
    if (name.equals(currentName)) {
      // Same clip they just loaded...make sure the player is enabled
      fileLabel.setText(name);
      playButton.setEnabled(true);
      stopButton.setEnabled(true);
      return;
    }
    currentName = name;
    try {
      URL u = new URL("file:///" + f.getAbsolutePath());
      currentClip = Applet.newAudioClip(u);
    }
    catch (Exception e) {
      e.printStackTrace();
      currentClip = null;
      fileLabel.setText("Error loading clip.");
    }
    fileLabel.setText(name);
    playButton.setEnabled(true);
    stopButton.setEnabled(true);
  }

  public void actionPerformed(ActionEvent ae) {
    // Be a little cavalier here...we're assuming the dialog was just
    // approved or cancelled so we should stop any playing clip
    if (currentClip != null) { currentClip.stop(); }
  }
}
