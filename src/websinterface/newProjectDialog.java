package websinterface;

import java.awt.Color;
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
 */
public class newProjectDialog extends JFrame {
    
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
    
    protected newProjectDialog() {
        // Initialize the Dialog Box
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(768, 200);
        setResizable(false);
        setIconImage(WEBsInterface.websIcon.getImage());
        setTitle("Create a New STC Project");
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(8, 8, 16, 8);
        gbc.gridwidth = 3;
        gbc.gridheight = 5;

        // Component Initialization
        
        // Watershed Components
        wsLbl = new JLabel("Watershed     ");
        wsLbl.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        wsLbl.setBackground(Color.WHITE);
        wsLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 0;       
        gbc.weightx = 0.3;
        add(wsLbl, gbc);
        
        wsFld = new JTextField("STC", 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        add(wsFld, gbc);
        
        // Project File Location Components
        projLbl = new JLabel("Project Folder");
        projLbl.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        projLbl.setBackground(Color.WHITE);
        projLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        add(projLbl, gbc);
        
        projFld = new JTextField("C:\\", 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        add(projFld, gbc);
        
        projLocate = new JButton("...");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.05;
        add(projLocate, gbc);
        
        // Spatial Library Locaton Components
        spatLbl = new JLabel("Spatial Folder");
        spatLbl.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        spatLbl.setBackground(Color.WHITE);
        spatLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        add(spatLbl, gbc);
        
        spatFld = new JTextField("C:\\", 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.7;
        add(spatFld, gbc);
        
        spatLocate = new JButton("...");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.05;
        add(spatLocate, gbc);
        
        // Swat Input File Location
        swatLbl = new JLabel("SWAT Input    ");
        swatLbl.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        swatLbl.setBackground(Color.WHITE);
        swatLbl.setHorizontalTextPosition(SwingConstants.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 3;      
        gbc.weightx = 0.3;
        add(swatLbl, gbc);
        
        swatFld = new JTextField("C:\\", 40);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.65;
        add(swatFld, gbc);
        
        swatLocate = new JButton("...");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        add(swatLocate, gbc);
        
        // Confirm and Cancel Buttons
        confirm = new JButton("  OK  ");
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.85;
        add(confirm, gbc);
        
        cancel = new JButton("Cancel");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.05;
        add(cancel, gbc);
        
        pack();
        validate();
        setVisible(true);
    }
}
