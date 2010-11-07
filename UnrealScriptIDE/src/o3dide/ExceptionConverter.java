/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

/**
 *
 * @author Ray
 */
public class ExceptionConverter {

    public void run() throws Exception {
    }

    public void start() throws RuntimeException {
        try {
            run();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
