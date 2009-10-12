/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.io.File;
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
}
