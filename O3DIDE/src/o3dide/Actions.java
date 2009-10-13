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
    private File openFile = null;
    private boolean editorDirty = false;

    public File getOpenFile() {
        return openFile;
    }

    public void setOpenFile(File openFile) {
        this.openFile = openFile;
    }

    public boolean isEditorDirty() {
        return editorDirty;
    }

    public void setEditorDirty(boolean isEditorDirty) {
        this.editorDirty = isEditorDirty;
    }

    public Actions(JTextArea jt, JScrollPane js) {
        this.jt = jt;
        this.js = js;
    }

    public void parseO3DJSFiles(O3DJSParser o3dParser) {
        try {
            o3dParser.parse(new File(Utils.getBestJarLocation(), "o3djs/base.js"));
            o3dParser.parse(new File(Utils.getBestJarLocation(), "o3djs/util.js"));
            o3dParser.parse(new File(Utils.getBestJarLocation(), "o3djs/math.js"));
            o3dParser.parse(new File(Utils.getBestJarLocation(), "o3djs/rendergraph.js"));
        } catch (IOException ex) {
            Logger.getLogger(O3DIDEView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Utils.writeCompleteFile(jt.getText(), Utils.getFileRelativeToMainJar("run.html"));

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

    void save() {
        Utils.writeCompleteFile(jt.getText(), openFile);
    }
}
