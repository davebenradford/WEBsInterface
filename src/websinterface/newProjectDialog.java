package websinterface;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    
    private static final Font f = new Font("Sans_Serif", Font.BOLD, 12);
    
    protected newProjectDialog() {
        // Initialize the Dialog Box
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(768, 200);
        setResizable(false);
        setIconImage(WEBsInterface.websIcon.getImage());
        setTitle("Create a New STC Project");
        setLocation(this.getWidth() / 4, this.getHeight());
        GridBagConstraints gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 0.3, 1.0);

        // Component Initialization
        
        // Watershed Components
        wsLbl = new JLabel("      Watershed:");
        wsLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        wsLbl.setFont(f);
        add(wsLbl, gbc);
        
        wsFld = new JTextField("STC", 40);
        wsFld.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0, 2, 1, 0.7, 1.0);
        add(wsFld, gbc);
        
        // Project File Location Components
        projLbl = new JLabel(" Project Folder:");
        projLbl.setFont(f);
        projLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 1, 2, 1, 0.3, 1.0);
        add(projLbl, gbc);
        
        projFld = new JTextField("C:\\", 40);
        projFld.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 1, 1, 1, 0.7, 1.0);
        add(projFld, gbc);
        
        projLocate = new JButton("...");
        projLocate.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 1, 1, 1, 0.05, 1.0);
        add(projLocate, gbc);
        
        // Spatial Library Locaton Components
        spatLbl = new JLabel("  Spatial Folder:");
        spatLbl.setFont(f);
        spatLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 2, 1, 1, 0.3, 1.0);
        add(spatLbl, gbc);
        
        spatFld = new JTextField("C:\\", 40);
        spatFld.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 2, 1, 1, 0.7, 1.0);
        add(spatFld, gbc);
        
        spatLocate = new JButton("...");
        spatLocate.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 2, 1, 1, 0.05, 1.0);
        add(spatLocate, gbc);
        
        // Swat Input File Location
        swatLbl = new JLabel("      SWAT Input:");
        swatLbl.setFont(f);
        swatLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 3, 1, 1, 0.3, 1.0);
        add(swatLbl, gbc);
        
        swatFld = new JTextField("C:\\", 40);
        swatFld.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, -24), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 3, 1, 1, 0.7, 1.0);
        add(swatFld, gbc);
        
        swatLocate = new JButton("...");
        swatLocate.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_END, 2, 3, 1, 1, 0.05, 1.0);
        add(swatLocate, gbc);
        
        // Confirm and Cancel Buttons
        confirm = new JButton("  OK  ");
        confirm.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 1, 4, 1, 1, 0.85, 1.0);
        add(confirm, gbc);
        
        cancel = new JButton("Cancel");
        cancel.setFont(f);
        gbc = setGBC(new Insets(8, 8, 8, 8), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 2, 4, 1, 1, 0.05, 1.0);
        add(cancel, gbc);
        
        pack();
        validate();
        setVisible(true);
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
    
    private static GridBagConstraints setGBC(Insets i, int fill, int a, int xCoord, int yCoord, int wide, int high, double weighX, double weighY) {
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
}
