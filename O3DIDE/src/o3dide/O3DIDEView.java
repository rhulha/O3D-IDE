/*
 * O3DIDEView.java
 */
package o3dide;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * The application's main frame.
 */
public class O3DIDEView extends FrameView {

    JList jList = null;
    O3DJSParser o3dParser = new O3DJSParser();
    Actions actions;

    public O3DIDEView(SingleFrameApplication app) {
        super(app);

        initComponents();

        actions = new Actions(jTextAreaEditor1, jScrollPaneEditor1);

        jList = new JList();
        jList.setModel(new DefaultListModel());

        jPopupMenuSmartSense.add(new JScrollPane(jList));
        SmartSense smartSense = new SmartSense(jTextAreaEditor1, jList, o3dParser, jPopupMenuSmartSense);
        jTextAreaEditor1.addKeyListener(smartSense);

        jList.addKeyListener(smartSense);

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = O3DIDEApp.getApplication().getMainFrame();
            aboutBox = new O3DIDEAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        O3DIDEApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jTabbedPaneEditor = new javax.swing.JTabbedPane();
        jPanelEditor1 = new javax.swing.JPanel();
        jScrollPaneEditor1 = new javax.swing.JScrollPane();
        jTextAreaEditor1 = new javax.swing.JTextArea();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemTest = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenuTemplates = new javax.swing.JMenu();
        jMenuItemHTMLBase = new javax.swing.JMenuItem();
        jMenuItemRequire = new javax.swing.JMenuItem();
        jMenuItemInit = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jMenuItemDebugParsing = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPopupMenuSmartSense = new javax.swing.JPopupMenu();
        jToolBarMain = new javax.swing.JToolBar();
        jButtonLoadHTML = new javax.swing.JButton();
        jButtonParseO3D = new javax.swing.JButton();
        jButtonRepaint = new javax.swing.JButton();
        jButtonRun = new javax.swing.JButton();

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        mainPanel.setLayout(new java.awt.BorderLayout());

        jTabbedPaneEditor.setName("jTabbedPaneEditor"); // NOI18N

        jPanelEditor1.setName("jPanelEditor1"); // NOI18N
        jPanelEditor1.setLayout(new java.awt.BorderLayout());

        jScrollPaneEditor1.setName("jScrollPaneEditor1"); // NOI18N

        jTextAreaEditor1.setColumns(20);
        jTextAreaEditor1.setRows(5);
        jTextAreaEditor1.setMargin(new java.awt.Insets(2, 10, 2, 2));
        jTextAreaEditor1.setName("jTextAreaEditor1"); // NOI18N
        jScrollPaneEditor1.setViewportView(jTextAreaEditor1);

        jPanelEditor1.add(jScrollPaneEditor1, java.awt.BorderLayout.CENTER);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(o3dide.O3DIDEApp.class).getContext().getResourceMap(O3DIDEView.class);
        jTabbedPaneEditor.addTab(resourceMap.getString("jPanelEditor1.TabConstraints.tabTitle"), jPanelEditor1); // NOI18N

        mainPanel.add(jTabbedPaneEditor, java.awt.BorderLayout.CENTER);

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuItemOpen.setText(resourceMap.getString("jMenuItemOpen.text")); // NOI18N
        jMenuItemOpen.setName("jMenuItemOpen"); // NOI18N
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemOpen);

        jMenuItemTest.setText(resourceMap.getString("jMenuItemTest.text")); // NOI18N
        jMenuItemTest.setName("jMenuItemTest"); // NOI18N
        jMenuItemTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemTestActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItemTest);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(o3dide.O3DIDEApp.class).getContext().getActionMap(O3DIDEView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenuTemplates.setText(resourceMap.getString("jMenuTemplates.text")); // NOI18N
        jMenuTemplates.setName("jMenuTemplates"); // NOI18N

        jMenuItemHTMLBase.setText(resourceMap.getString("jMenuItemHTMLBase.text")); // NOI18N
        jMenuItemHTMLBase.setName("jMenuItemHTMLBase"); // NOI18N
        jMenuItemHTMLBase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHTMLBaseActionPerformed(evt);
            }
        });
        jMenuTemplates.add(jMenuItemHTMLBase);

        jMenuItemRequire.setText(resourceMap.getString("jMenuItemRequire.text")); // NOI18N
        jMenuItemRequire.setName("jMenuItemRequire"); // NOI18N
        jMenuItemRequire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRequireActionPerformed(evt);
            }
        });
        jMenuTemplates.add(jMenuItemRequire);

        jMenuItemInit.setText(resourceMap.getString("jMenuItemInit.text")); // NOI18N
        jMenuItemInit.setName("jMenuItemInit"); // NOI18N
        jMenuItemInit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemInitActionPerformed(evt);
            }
        });
        jMenuTemplates.add(jMenuItemInit);

        menuBar.add(jMenuTemplates);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        jMenuItemDebugParsing.setText(resourceMap.getString("jMenuItemDebugParsing.text")); // NOI18N
        jMenuItemDebugParsing.setName("jMenuItemDebugParsing"); // NOI18N
        jMenuItemDebugParsing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDebugParsingActionPerformed(evt);
            }
        });
        helpMenu.add(jMenuItemDebugParsing);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPopupMenuSmartSense.setName("jPopupMenuSmartSense"); // NOI18N

        jToolBarMain.setRollover(true);
        jToolBarMain.setName("jToolBarMain"); // NOI18N

        jButtonLoadHTML.setIcon(resourceMap.getIcon("jButtonLoadHTML.icon")); // NOI18N
        jButtonLoadHTML.setText(resourceMap.getString("jButtonLoadHTML.text")); // NOI18N
        jButtonLoadHTML.setFocusable(false);
        jButtonLoadHTML.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonLoadHTML.setName("jButtonLoadHTML"); // NOI18N
        jButtonLoadHTML.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonLoadHTML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadHTMLActionPerformed(evt);
            }
        });
        jToolBarMain.add(jButtonLoadHTML);

        jButtonParseO3D.setText(resourceMap.getString("jButtonParseO3D.text")); // NOI18N
        jButtonParseO3D.setFocusable(false);
        jButtonParseO3D.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonParseO3D.setName("jButtonParseO3D"); // NOI18N
        jButtonParseO3D.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonParseO3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonParseO3DActionPerformed(evt);
            }
        });
        jToolBarMain.add(jButtonParseO3D);

        jButtonRepaint.setText(resourceMap.getString("jButtonRepaint.text")); // NOI18N
        jButtonRepaint.setFocusable(false);
        jButtonRepaint.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRepaint.setName("jButtonRepaint"); // NOI18N
        jButtonRepaint.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRepaint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRepaintActionPerformed(evt);
            }
        });
        jToolBarMain.add(jButtonRepaint);

        jButtonRun.setText(resourceMap.getString("jButtonRun.text")); // NOI18N
        jButtonRun.setFocusable(false);
        jButtonRun.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRun.setName("jButtonRun"); // NOI18N
        jButtonRun.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });
        jToolBarMain.add(jButtonRun);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(jToolBarMain);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenActionPerformed
        // TODO add your handling code here:
        JFileChooser fc = new JFileChooser();
        int opt = fc.showOpenDialog(this.getFrame());
        if (opt == JFileChooser.APPROVE_OPTION) {
            File sf = fc.getSelectedFile();
            byte buf[] = new byte[(int) sf.length()];
            try {
                FileInputStream fis = new FileInputStream(sf);
                fis.read(buf);
                fis.close();
                jTextAreaEditor1.setText(new String(buf));
            } catch (IOException ex) {
                Logger.getLogger(O3DIDEView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemTestActionPerformed
        // TODO add your handling code here:
        System.out.println(Utils.getJarLocation());
        System.out.println(Utils.getUserDirLocation());

    }//GEN-LAST:event_jMenuItemTestActionPerformed

    private void jButtonLoadHTMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadHTMLActionPerformed
        // TODO add your handling code here:
        actions.loadHelloCube();
    }//GEN-LAST:event_jButtonLoadHTMLActionPerformed

    private void jButtonParseO3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonParseO3DActionPerformed
        // TODO add your handling code here:
        try {
            o3dParser.parse( new File(Utils.getBestJarLocation(), "o3djs/base.js"));
            o3dParser.parse( new File(Utils.getBestJarLocation(), "o3djs/util.js"));
            o3dParser.parse( new File(Utils.getBestJarLocation(), "o3djs/math.js"));
            o3dParser.parse( new File(Utils.getBestJarLocation(), "o3djs/rendergraph.js"));
        } catch (IOException ex) {
            Logger.getLogger(O3DIDEView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonParseO3DActionPerformed

    private void jButtonRepaintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRepaintActionPerformed
        // TODO add your handling code here:
        jScrollPaneEditor1.repaint();
    }//GEN-LAST:event_jButtonRepaintActionPerformed

    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        // TODO add your handling code here:
        actions.run();
        
    }//GEN-LAST:event_jButtonRunActionPerformed

    private void jMenuItemHTMLBaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHTMLBaseActionPerformed
        // TODO add your handling code here:
        actions.insertTemplate("templates/HTMLBase.txt");
    }//GEN-LAST:event_jMenuItemHTMLBaseActionPerformed

    private void jMenuItemRequireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRequireActionPerformed
        // TODO add your handling code here:
        actions.insertTemplate("templates/require.txt");
    }//GEN-LAST:event_jMenuItemRequireActionPerformed

    private void jMenuItemInitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemInitActionPerformed
        // TODO add your handling code here:
        actions.insertTemplate("templates/init.txt");
    }//GEN-LAST:event_jMenuItemInitActionPerformed

    private void jMenuItemDebugParsingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDebugParsingActionPerformed
        // TODO add your handling code here:
        jTextAreaEditor1.setText(o3dParser.getJsObject().toString());
    }//GEN-LAST:event_jMenuItemDebugParsingActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLoadHTML;
    private javax.swing.JButton jButtonParseO3D;
    private javax.swing.JButton jButtonRepaint;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JMenuItem jMenuItemDebugParsing;
    private javax.swing.JMenuItem jMenuItemHTMLBase;
    private javax.swing.JMenuItem jMenuItemInit;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemRequire;
    private javax.swing.JMenuItem jMenuItemTest;
    private javax.swing.JMenu jMenuTemplates;
    private javax.swing.JPanel jPanelEditor1;
    private javax.swing.JPopupMenu jPopupMenuSmartSense;
    private javax.swing.JScrollPane jScrollPaneEditor1;
    private javax.swing.JTabbedPane jTabbedPaneEditor;
    private javax.swing.JTextArea jTextAreaEditor1;
    private javax.swing.JToolBar jToolBarMain;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}
