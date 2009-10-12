/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package o3dide;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ray
 */
public class SmartSense implements KeyListener {

    private JList jList;
    private O3DJSParser o3dParser;
    private JPopupMenu jPopupMenu;
    private JTextArea jt;

    public SmartSense(JTextArea jt, JList jList, O3DJSParser o3dParser, JPopupMenu jPopupMenu) {
        this.jList = jList;
        this.o3dParser = o3dParser;
        this.jPopupMenu = jPopupMenu;
        this.jt = jt;
    }

    public void keyTyped(KeyEvent e) {
        if( e.getSource() == jList)
        {
            if( e.getKeyChar() == KeyEvent.VK_ENTER)
            {
                String s = (String)jList.getSelectedValue();
                int cp = jt.getCaretPosition();
                jt.insert(s, cp);
                jPopupMenu.setVisible(false);
            }
        }
        if (' ' == e.getKeyChar() && e.getModifiers() == KeyEvent.CTRL_MASK) {
            JTextArea source = (JTextArea) e.getSource();
            Point cp = source.getCaret().getMagicCaretPosition();
            try {
                String text = "";
                if( source.getCaretPosition()>0)
                    text = source.getText(source.getCaretPosition() - 10, 10);
                int last = text.lastIndexOf(' ');
                if (last > 0) {
                    text = text.substring(last);
                }
                //jMenuItem1.setText(text);
                System.out.println(text);
            } catch (Exception ex) {
                Logger.getLogger(O3DIDEView.class.getName()).log(Level.SEVERE, null, ex);
            }

            DefaultListModel dlm = (DefaultListModel) jList.getModel();
            dlm.clear();
            Set<String> keySet = o3dParser.getJsObject().getChildren().keySet();
            for (String key : keySet) {
                dlm.addElement(key);
            }
            jPopupMenu.show(source, cp.x, cp.y + 16);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    jList.requestFocus();
                }
            });
        }

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
