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
 * @version 0.05
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
 */
public class newProjectDialog extends JFrame {
    
//<editor-fold defaultstate="collapsed" desc="Swing Component Declarations">
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
//</editor-fold>
    
    private final Font f = new Font("Sans_Serif", Font.BOLD, 12);
    private final File dataFile;
    private String source;
    
    protected newProjectDialog() throws IOException {
        // Initialize the Dialog Box
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(768, 200);
        setResizable(false);
        setIconImage(WEBsInterface.websIcon.getImage());
        setTitle("Create a New STC Project");
        setLocation(this.getWidth() / 4, this.getHeight());
        
        dataFile = new File(new File("").getAbsolutePath());
        source = dataFile.toString();
        
        // Watershed Components
        wsLbl = createLabel(new JLabel("Watershed:", SwingConstants.RIGHT));
        GridBagConstraints gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 0.3, 1.0);
        add(wsLbl, gbc);
        
        wsFld = createField(new JTextField("STC", 40), "Watershed Project Name");
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0, 2, 1, 0.7, 1.0);
        add(wsFld, gbc);
        
        // Project File Location Components
        projLbl = createLabel(new JLabel("Project Folder:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 1, 1, 1, 0.3, 1.0);
        add(projLbl, gbc);
        
        projFld = createField(new JTextField(source, 40), "Project File Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 1, 1, 1, 0.7, 1.0);
        add(projFld, gbc);
        
        projLocate = createButton(new JButton("..."), "Change Project Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 1, 1, 1, 0.05, 1.0);
        add(projLocate, gbc);
        
        // Spatial Data Locaton Components
        spatLbl = createLabel(new JLabel("Spatial Folder:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 2, 1, 1, 0.3, 1.0);
        add(spatLbl, gbc);
        
        spatFld = createField(new JTextField(source, 40), "Spatial Data Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 2, 1, 1, 0.7, 1.0);
        add(spatFld, gbc);
        
        spatLocate = createButton(new JButton("..."), "Change Spatial Data Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 2, 1, 1, 0.05, 1.0);
        add(spatLocate, gbc);
        
        // Swat Input File Location
        swatLbl = createLabel(new JLabel("SWAT Input:", SwingConstants.RIGHT));
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 3, 1, 1, 0.3, 1.0);
        add(swatLbl, gbc);
        
        swatFld = createField(new JTextField(source, 40), "SWAT Data Location");
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 3, 1, 1, 0.7, 1.0);
        add(swatFld, gbc);
        
        swatLocate = createButton(new JButton("..."), "Change SWAT Data Location", new directoryListener());
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 3, 1, 1, 0.05, 1.0);
        add(swatLocate, gbc);
        
        // Confirm and Cancel Buttons
        confirm = createButton(new JButton("OK"), "false", null);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 4, 1, 1, 0.85, 1.0);
        add(confirm, gbc);
        
        cancel = createButton(new JButton("Cancel"), "false", null);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 2, 4, 1, 1, 0.05, 1.0);
        add(cancel, gbc);
        
        pack();
        validate();
        setVisible(true);
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
    
    private File getDirectory(){
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
        }
    }
}
