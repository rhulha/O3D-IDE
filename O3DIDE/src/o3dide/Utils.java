/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ray
 */
public class Utils {

    public static URL getJarLocation() {
        return Utils.class.getProtectionDomain().getCodeSource().getLocation();
    }

    public static String getUserDirLocation() {
        return System.getProperty("user.dir");
    }

    public static File getBestJarLocation() {
        URL jarLocation = getJarLocation();
        if (jarLocation.toString().endsWith("jar")) {
            try {
                return new File(jarLocation.toURI()).getParentFile();
            } catch (URISyntaxException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return new File(getUserDirLocation());

    }

    public static File getFileRelativeToMainJar(String relativePath) {
        return new File(Utils.getBestJarLocation(), relativePath);
    }

    public static String readCompleteRelativeFile( String relativePath)
    {
        return readCompleteFile(getFileRelativeToMainJar(relativePath));
    }

    public static String readCompleteFile( File f)
    {
        byte buf[] = new byte[(int) f.length()];
        try {
            FileInputStream fis = new FileInputStream(f);
            fis.read(buf);
            fis.close();
        } catch ( IOException ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return new String(buf);

    }
}
