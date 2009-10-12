/*
 * O3DIDEApp.java
 */

package o3dide;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class O3DIDEApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new O3DIDEView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of O3DIDEApp
     */
    public static O3DIDEApp getApplication() {
        return Application.getInstance(O3DIDEApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(O3DIDEApp.class, args);
    }
}
