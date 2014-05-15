package websinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
/**
 *
 * @author Dave Radford
 * @since May 2014
 * @version 0.05
 * 
 * WEBs Interface (Java)
 * 
 * This project contains the modules for the Java Version of the CAN-SWAT UI.
 * This class serves as the main/primary/core??? class for the entire project.
 * 
 * Project Version History
 * 
 * v0.01: Basic Frame established. Icon image added. Frame startup settings
 *        initialized.
 * v0.02: Begin adding components to startup frame. 
 * v0.03: Align and adjust look and feel of startup frame.
 * v0.04: Establish Listeners for startup frame.
 *          newProjectDialog
 *          newScenarioDialog
 * v0.05: Built the New Project Dialog Box.
 * v0.06: Condensed component creation into two methods and the GridBagLayout
 *        weight setting values into one method, both for scalability.
 * 
 * 
 */
public class WEBsInterface extends JFrame implements Runnable {
    
// <editor-fold defaultstate="collapsed" desc="Dimension, Font, Border, and Image Declarations">
    private static final Dimension iconDim = new Dimension(32, 32);
    private static final Dimension frameDim = new Dimension(1024, 768);
    private static final Font f = new Font("Sans_Serif", Font.PLAIN, 12);
    private static final Border b = BorderFactory.createLineBorder(Color.BLACK);
    
    public static final ImageIcon websIcon = new ImageIcon("src\\images\\icon_32x32.png");
    private static final ImageIcon newProjIcon = new ImageIcon("src\\images\\document_empty_32x32.png");
    private static final ImageIcon openProjIcon = new ImageIcon("src\\images\\folder_32x32.png");
    private static final ImageIcon saveProjIcon = new ImageIcon("src\\images\\save_as_32x32.png");
    private static final ImageIcon newScenIcon = new ImageIcon("src\\images\\add.png");
    private static final ImageIcon editScenIcon = new ImageIcon("src\\images\\information.png");
    private static final ImageIcon saveScenIcon = new ImageIcon("src\\images\\picture_save.png");
    private static final ImageIcon delScenIcon = new ImageIcon("src\\images\\delete.png");
    private static final ImageIcon damIcon = new ImageIcon("src\\images\\draw_polygon_curves.png");
    private static final ImageIcon pondIcon = new ImageIcon("src\\images\\button.png");
    private static final ImageIcon grazeIcon = new ImageIcon("src\\images\\cow_head_32.png");
    private static final ImageIcon tillIcon = new ImageIcon("src\\images\\tractor.png");
    private static final ImageIcon forageIcon = new ImageIcon("src\\images\\grass.png");
    private static final ImageIcon econIcon = new ImageIcon("src\\images\\cash_stack.png");
    private static final ImageIcon swatIcon = new ImageIcon("src\\images\\weather_snow.png");
//</editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Swing Component Declarations">
    private static JFrame frame;
    private static JPanel topPanel;
    private static JPanel dbPanel;
    private static JPanel mainPanel;
    private static JButton newProj;
    private static JButton openProj;
    private static JButton saveProj;
    private static JButton newScen;
    private static JButton editScen;
    private static JButton saveScen;
    private static JButton delScen;
    private static JButton btnDam;
    private static JButton btnPond;
    private static JButton btnGraze;
    private static JButton btnTill;
    private static JButton btnForage;
    private static JButton runEcon;
    private static JButton runSwat;
    private static JSeparator vSepProj;
    private static JSeparator vSepScen;
    private static JSeparator vSepRun;
//</editor-fold>
    
    private static void buildTopPanel() {
        // Initalize the Top Button Panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        newProj = createButton(new JButton(" New Project ", newProjIcon), "New Project", true, new newProjListener());
        
        // Resize Top Panel
        Dimension topDim = new Dimension(frame.getMaximumSize().width, newProj.getMaximumSize().height);
        Dimension sepDim = new Dimension(frame.getPreferredSize().width, newProj.getHeight());
        topPanel.setSize(topDim);
        GridBagConstraints gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newProj, gbc);

        openProj = createButton(new JButton("Open Project ", openProjIcon), "Open Project", true, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 1, 1, 1, 17, 0.028, 1.0);
        topPanel.add(openProj, gbc);
        
        saveProj = createButton(new JButton("Save Project ", saveProjIcon), "Save Project", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 2, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveProj, gbc);
        
        vSepProj = createSeparator(new JSeparator(SwingConstants.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 3, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepProj, gbc);
        
        newScen = createButton(new JButton("New Scenario ", newScenIcon), "New Scenario", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 4, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newScen, gbc);
        
        editScen = createButton(new JButton("Edit Scenario", editScenIcon), "Edit Scenario", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 5, 1, 1, 17, 0.028, 1.0);
        topPanel.add(editScen, gbc);
        
        saveScen = createButton(new JButton("Save Scenario", saveScenIcon), "Save Scenario", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 6, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveScen, gbc);

        delScen = createButton(new JButton("Delete Scen. ", delScenIcon), "Delete Scenario", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 7, 1, 1, 17, 0.028, 1.0);
        topPanel.add(delScen, gbc);
        
        vSepScen = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 8, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepScen, gbc);
        
        btnDam = createButton(new JButton("  Small Dam ", damIcon), "Small Dam BMP", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 9, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnDam, gbc);
        
        btnPond = createButton(new JButton("Holding Pond", pondIcon), "Holding Pond BMP", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnPond, gbc);
        
        btnGraze = createButton(new JButton("   Grazing  ", grazeIcon), "Grazing BMP", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 11, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnGraze, gbc);
        
        btnTill = createButton(new JButton("   Tillage  ", tillIcon), "Tillage BMP", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 12, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnTill, gbc);
        
        btnForage = createButton(new JButton("Forage Cons.", forageIcon), "Forage Conservation BMP", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 13, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnForage, gbc);

        vSepRun = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 14, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepRun, gbc);
        
        runEcon = createButton(new JButton("Run Economic", econIcon), "Run Economic Model", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 15, 1, 1, 17, 0.028, 1.0);
        topPanel.add(runEcon, gbc);
        
        runSwat = createButton(new JButton("  Run SWAT  ", swatIcon),"Run Soil and Watershed Assessment Tool (SWAT) Model", false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 16, 1, 1, 17, 1.0, 1.0);
        topPanel.add(runSwat, gbc);
    }
    
    /**
     * Create Button Method
     * 
     * @param btn: The JButton component being constructed.
     * @param s: The String for the JButton's ToolTip Text.
     * @param en: The Boolean value for buttons that are enabled at startup.
     * @return: Returns the JButton to be added to the Container for which it was created.
     */
    
    private static JButton createButton(JButton btn, String s, Boolean en, ActionListener a) {
        btn.setFont(f);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setSize(iconDim);
        btn.setBorder(b);
        btn.setContentAreaFilled(false);
        btn.setToolTipText(s);
        btn.setEnabled(en);
        btn.addActionListener(a);
        return btn;
    }
    
    /**
     * Create Separator Method
     * 
     * @param sep: The JSeparator component being constructed.
     * @param d: The Dimension value used for resizing the JSeparator.
     * @param c: The Foreground color of the JSeparator.
     * @return: Returns the JSeparator to be added to the JFrame for which it was created.
     */
    
    private static JSeparator createSeparator(JSeparator sep, Dimension d, Color c){
        sep.setSize(d);
        sep.setForeground(c);
        return sep;
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
     * @return: Returns the JSeparator to be added to the Container for which it was created.
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
    
    // Listener Classes
    
    private static class newProjListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            newProjectDialog npd = new newProjectDialog();
        }
    }
    
    private static void buildDBPanel() {
        dbPanel = new JPanel();
        dbPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        dbPanel.setBackground(Color.WHITE);
        dbPanel.setToolTipText("This is the Database/File Structure Panel.");
        dbPanel.setVisible(true);
    }
    
    private static void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setToolTipText("This is the Main Program Panel.");
        mainPanel.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Set the Look and Feel of the Frame
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Initialize and Create the Main Frame (Container)
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frame.getWidth(), frame.getHeight());
        frame.setPreferredSize(frameDim);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setIconImage(websIcon.getImage());
        frame.setTitle("WEBsInterface v0.06");
        
        // Build the Top Toolbar
        buildTopPanel();
        GridBagConstraints gbc = setGBC(new Insets(4, 4, 4, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 2, 1, 1, 0.2);
        frame.add(topPanel, gbc);
        
        // Build the Database/Project File Structure Panel
        buildDBPanel();
        gbc = setGBC(new Insets((topPanel.getPreferredSize().height + 8), 4, 8, 2), GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 0, 1, 2, 0.15, 0.8);
        frame.add(dbPanel, gbc);
        
        // Build the Main Panel
        buildMainPanel();
        gbc = setGBC(new Insets((topPanel.getPreferredSize().height + 8), 2, 8, 4), GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 0, 1, 2, 0.85, 0.8);
        mainPanel.requestFocusInWindow();
        frame.add(mainPanel, gbc);
        
        // Pack and Display the Frame
        frame.pack();
        frame.validate();
        frame.setVisible(true);
    }
    
    @Override
    public void run() {
        WEBsInterface webs = new WEBsInterface();
    }
}