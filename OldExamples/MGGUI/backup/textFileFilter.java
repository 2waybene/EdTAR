import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class textFileFilter extends FileFilter {
    
    // Accept all directories and all gif, jpg, or tiff files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
	if (extension != null) {
            if (extension.equals(Utils.txt) ||
                extension.equals(Utils.bat) ||
                extension.equals(Utils.dat))
                {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "Just Text Related File";
    }
}
