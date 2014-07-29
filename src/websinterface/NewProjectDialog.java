package websinterface;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Dave Radford
 * @since May 2014
 * @version 0.09
 * 
 * WEBs Interface (Java): New Project Creation Dialog Box
 * 
 * This dialog box appears when the user decides to create a new project with
 * the WEBs interface.
 * 
 * Project Version History
 * 
 * v0.05: Built the New Project Dialog Box.
 * v0.06: Condensed the GridBagLayout weight setting values
 *        into one method for scalability.
 * v0.07: Cleaned up code for scalability and efficiency. Began implementing
 *        listener classes for the dialog menu.
 * v0.08: Dialog listeners mostly completed. Awaiting completion of Scenario
 *        creation class before finalizing listeners. Cleaning code in terms of
 *        function placement, component names, and overall comments.
 * v0.09: Build Scenario creation class.
 */

public class NewProjectDialog {
    
    //Swing Component Declarations
    
    private final JFrame frame;
    private final JTextField wsFld;
    private final JTextField projFld;
    private final JTextField spatFld;
    private final JTextField swatFld;
    private final JLabel wsLbl;
    private final JLabel projLbl;
    private final JLabel spatLbl;
    private final JLabel swatLbl;
    private final JButton confirm;
    private final JButton cancel;
    private final JButton projLocate;
    private final JButton spatLocate;
    private final JButton swatLocate;
    
    private final Font f = new Font("Sans_Serif", Font.BOLD, 12);
    private final File dataFile;
    private String source;
    
    protected NewProjectDialog() throws IOException {
        // Initialize the Dialog Box
        frame = new JFrame("Create a New STC Project");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(768, 200);
        frame.setResizable(false);
        frame.setIconImage(WEBsInterface.websIcon.getImage());
        frame.setLocation(frame.getWidth() / 4, frame.getHeight());
        
        dataFile = new File(new File("").getAbsolutePath());
        source = dataFile.toString();
        
        // Watershed Components
        wsLbl = createLabel(new JLabel("Watershed:", SwingConstants.RIGHT));
        GridBagConstraints gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 0.3, 1.0);
        frame.add(wsLbl, gbc);
        
        wsFld = createField(new JTextField("STC", 40), "Watershed Project Name");
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0, 2, 1, 0.7, 1.0);
        frame.add(wsFld, gbc);
        
        // Project File Location Components
        projLbl = createLabel(new JLabel("Project Folder:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 1, 1, 1, 0.3, 1.0);
        frame.add(projLbl, gbc);
        
        projFld = createField(new JTextField(source, 40), "Project File Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 1, 1, 1, 0.7, 1.0);
        frame.add(projFld, gbc);
        
        projLocate = createButton(new JButton("..."), "Change Project Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 1, 1, 1, 0.05, 1.0);
        frame.add(projLocate, gbc);
        
        // Spatial Data Locaton Components
        spatLbl = createLabel(new JLabel("Spatial Folder:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 2, 1, 1, 0.3, 1.0);
        frame.add(spatLbl, gbc);
        
        spatFld = createField(new JTextField(source, 40), "Spatial Data Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 2, 1, 1, 0.7, 1.0);
        frame.add(spatFld, gbc);
        
        spatLocate = createButton(new JButton("..."), "Change Spatial Data Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 2, 1, 1, 0.05, 1.0);
        frame.add(spatLocate, gbc);
        
        // Swat Input File Location
        swatLbl = createLabel(new JLabel("SWAT Input:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 3, 1, 1, 0.3, 1.0);
        frame.add(swatLbl, gbc);
        
        swatFld = createField(new JTextField(source, 40), "SWAT Data Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 3, 1, 1, 0.7, 1.0);
        frame.add(swatFld, gbc);
        
        swatLocate = createButton(new JButton("..."), "Change SWAT Data Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 3, 1, 1, 0.05, 1.0);
        frame.add(swatLocate, gbc);
        
        // Confirm and Cancel Buttons
        confirm = createButton(new JButton("OK"), "false", new confirmListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 4, 1, 1, 0.85, 1.0);
        frame.add(confirm, gbc);
        
        cancel = createButton(new JButton("Cancel"), "false", new cancelListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 2, 4, 1, 1, 0.05, 1.0);
        frame.add(cancel, gbc);
        
        frame.pack();
        frame.validate();
        frame.setVisible(true);
    }
    
    /**
     * 
     * @param lbl: The JLabel component being constructed.
     * @return: Returns the JLabel to be added to the Container for which it was created.
     */
    
    private JLabel createLabel(JLabel lbl) {
        lbl.setFont(f);
        return lbl;
    }
    
    /**
     * 
     * @param txt: The JTextField component being constructed.
     * @param s: The String for the JTextField's ToolTip Text.
     * @return: Returns the JTextField to be added to the Container for which it was created.
     */
    
    private JTextField createField(JTextField txt, String s) {
        txt.setFont(f);
        txt.setToolTipText(s);
        return txt;
    }
    
    /**
     * 
     * @param btn: The JButton component being constructed.
     * @param s: The String for the JButton's ToolTip Text.
     * @param a: The ActionListener used by the JButton.
     * @return: Returns the JButton to be added to the Container for which it was created.
     */
    
    private JButton createButton(JButton btn, String s, ActionListener a) {
        btn.setFont(f);
        if(!(s.equals("false"))) {
            btn.setToolTipText(s);
        }
        btn.addActionListener(a);
        return btn;
    }
    
    /**
     * 
     * @param i: Insets for the panel. Insets object.
     * @param fill: The fill property value for the component. Integer.
     * @param a: The anchor property value for the component. Integer.
     * @param xCoord: The X-Coordinate value for the component on the Frame grid. Integer
     * @param yCoord: The Y-Coordinate value for the component on the Frame grid. Integer
     * @param wide: The width value for the component across the layout grid. Integer.
     * @param high: The height value for the component across the layout grid. Integer.
     * @param weighX: The weight value for the width of the component when placed in the container. Integer.
     * @param weighY: The weight value for the height of the component when placed in the container. Integer.
     * @return 
     */
    
    private GridBagConstraints setGBC(Insets i, int fill, int a, int xCoord, int yCoord, int wide, int high, double weighX, double weighY) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = i;
        g.fill = fill;
        g.anchor = a;
        g.gridx = xCoord;
        g.gridy = yCoord;
        g.gridwidth = wide;
        g.gridheight = high;
        g.weightx = weighX;
        g.weighty = weighY;
        return g;
    }
    
    // File Chooser
    
    private File getDirectory() {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setSelectedFile(dataFile);
        selector.showOpenDialog(null);
        File openFile = selector.getSelectedFile();

        source = openFile.toString();
                
        return openFile;
    }
    
    // Listeners
    
    private class directoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae){
            getDirectory();
            if(ae.getSource().equals(projLocate)) {
                projFld.setText(source);
                spatFld.setText(source + "\\Data\\Spatial");
                swatFld.setText(source + "\\Data\\txtinout");
            }
            else if(ae.getSource().equals(spatLocate)) {
                spatFld.setText(source);
            }
            else if(ae.getSource().equals(swatLocate)) {
                swatFld.setText(source);
            }
        }
    }
    
    private class confirmListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            WEBsInterface.projLocation.setText(projFld.getText());
            WEBsInterface.spatLocation.setText(spatFld.getText());
            WEBsInterface.swatLocation.setText(swatFld.getText());
            WEBsInterface.basicProj.setVisible(true);
            WEBsInterface.spatial.setVisible(true);
            WEBsInterface.swat.setVisible(true);
            WEBsInterface.scenButton.setVisible(true);
            WEBsInterface.spatLocBtn.setVisible(true);
            WEBsInterface.swatLocBtn.setVisible(true);
            frame.dispose();
            WEBsInterface.frame.revalidate();
            WEBsInterface.projPanel.setVisible(true);
            WEBsInterface.projPanel.repaint();
        }
    }
    
    private class cancelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            frame.dispose();
        }
    }
}
