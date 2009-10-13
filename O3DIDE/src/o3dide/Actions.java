/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.awt.Desktop;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ray
 */
public class Actions {

    private final JTextArea jt;
    private final JScrollPane js;

    public Actions(JTextArea jt, JScrollPane js) {
        this.jt = jt;
        this.js = js;
    }

    public void insertTemplate(String file) {
        String base = Utils.readCompleteRelativeFile(file);
        jt.insert(base, jt.getCaretPosition());
        int cursor = jt.getText().indexOf("%CURSOR%");
        jt.setCaretPosition(cursor);
        jt.replaceRange("", cursor, cursor + "%CURSOR%".length());
        jt.requestFocus();
    }

    void loadHelloWorld() {
        //jTextArea1.replaceRange(new String(buf), 0, 0);
        jt.setText(Utils.readCompleteRelativeFile("templates/helloworld.html"));
        jt.setCaretPosition(0);
        jt.requestFocus();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                js.scrollRectToVisible(new Rectangle(0, 0, 0, 0));
                js.repaint();
            }
        });


    }

    void run() {
        try {
            FileWriter fw = new FileWriter(Utils.getFileRelativeToMainJar("run.html"));
            fw.write(jt.getText());
            fw.close();
            Desktop.getDesktop().open(Utils.getFileRelativeToMainJar("run.html"));
        } catch (IOException ex) {
            Logger.getLogger(Actions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadHelloCube() {
        //jTextArea1.replaceRange(new String(buf), 0, 0);
        jt.setText(Utils.readCompleteRelativeFile("templates/hellocube.html"));
        jt.setCaretPosition(0);
        jt.requestFocus();
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                js.scrollRectToVisible(new Rectangle(0, 0, 0, 0));
                js.repaint();
            }
        });
    }
}
