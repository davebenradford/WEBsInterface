package websinterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Dave Radford
 * @since May 2014
 * @version 0.07
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
 * v0.07: Built the Main Panel Scenario Pane. Cleaned up code for scalability
 *        and efficiency.
 * 
 */

public class WEBsInterface extends JFrame implements Runnable {
    
// <editor-fold defaultstate="collapsed" desc="Dimension, Font, Border, and Image Declarations">
    private static final Dimension iconDim = new Dimension(32, 32);
    private static final Dimension frameDim = new Dimension(1024, 768);
    private static final Font f = new Font("Sans_Serif", Font.PLAIN, 12);
    
    public static final ImageIcon websIcon = new ImageIcon("src\\images\\icon_32x32.png");
    private static final ImageIcon newProjIcon = new ImageIcon("src\\images\\document_empty_32x32.png");
    private static final ImageIcon openProjIcon = new ImageIcon("src\\images\\folder_32x32.png");
    private static final ImageIcon smOpenProjIcon = new ImageIcon("src\\images\\folder_16x16.png");
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
    private static JPanel basic;
    private static JPanel spatial;
    private static JPanel swat;
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
    private static JButton scenButton;
    private static JButton spatLocBtn;
    private static JButton swatLocBtn;
    private static JSeparator vSepProj;
    private static JSeparator vSepScen;
    private static JSeparator vSepRun;
    
    public static JLabel spatLocation;
    public static JLabel projLocation;
    public static JLabel swatLocation;
//</editor-fold>
    
    private static void buildTopPanel() {
        // Initalize the Top Button Panel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        Border b = BorderFactory.createEmptyBorder();
        newProj = createButton(new JButton("New Project", newProjIcon), "New Project", b, true, new newProjListener());
        
        // Resize Top Panel
        Dimension topDim = new Dimension(frame.getMaximumSize().width, newProj.getMaximumSize().height);
        Dimension sepDim = new Dimension(frame.getPreferredSize().width, newProj.getHeight());
        topPanel.setSize(topDim);
        GridBagConstraints gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 0, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newProj, gbc);

        openProj = createButton(new JButton("Open Project", openProjIcon), "Open Project", b, true, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 1, 1, 1, 17, 0.028, 1.0);
        topPanel.add(openProj, gbc);
        
        saveProj = createButton(new JButton("Save Project", saveProjIcon), "Save Project", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 2, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveProj, gbc);
        
        vSepProj = createSeparator(new JSeparator(SwingConstants.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 3, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepProj, gbc);
        
        newScen = createButton(new JButton("New Scenario", newScenIcon), "New Scenario", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 4, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newScen, gbc);
        
        editScen = createButton(new JButton("Edit Scenario", editScenIcon), "Edit Scenario", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 5, 1, 1, 17, 0.028, 1.0);
        topPanel.add(editScen, gbc);
        
        saveScen = createButton(new JButton("Save Scenario", saveScenIcon), "Save Scenario", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 6, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveScen, gbc);

        delScen = createButton(new JButton("Delete Scen.", delScenIcon), "Delete Scenario", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 7, 1, 1, 17, 0.028, 1.0);
        topPanel.add(delScen, gbc);
        
        vSepScen = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 8, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepScen, gbc);
        
        btnDam = createButton(new JButton("Small Dam", damIcon), "Small Dam BMP", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 9, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnDam, gbc);
        
        btnPond = createButton(new JButton("Holding Pond", pondIcon), "Holding Pond BMP", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 10, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnPond, gbc);
        
        btnGraze = createButton(new JButton("Grazing", grazeIcon), "Grazing BMP", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 11, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnGraze, gbc);
        
        btnTill = createButton(new JButton("Tillage", tillIcon), "Tillage BMP", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 12, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnTill, gbc);
        
        btnForage = createButton(new JButton("Forage Cons.", forageIcon), "Forage Conservation BMP", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 13, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnForage, gbc);

        vSepRun = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 14, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepRun, gbc);
        
        runEcon = createButton(new JButton("Run Economic", econIcon), "Run Economic Model", b, false, null);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 15, 1, 1, 17, 0.028, 1.0);
        topPanel.add(runEcon, gbc);
        
        runSwat = createButton(new JButton("Run SWAT", swatIcon),"Run Soil and Watershed Assessment Tool (SWAT) Model", b, false, null);
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
    
    private static JButton createButton(JButton btn, String s, Border brd, Boolean en, ActionListener a) {
        btn.setFont(f);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setSize(iconDim);
        btn.setBorder(brd);
        btn.setContentAreaFilled(false);
        btn.setToolTipText(s);
        btn.setEnabled(en);
        btn.setBounds(50, 50, 50, 50);
        btn.addActionListener(a);
        return btn;
    }
    
    /**
     * Create Separator Method
     * 
     * @param sep: The JSeparator component being constructed.
     * @param d: The Dimension value used for resizing the JSeparator.
     * @param c: The Foreground color of the JSeparator.
     * @return: Returns the JSeparator to be added to the Container for which it was created.
     */
    
    private static JSeparator createSeparator(JSeparator sep, Dimension d, Color c){
        sep.setSize(d);
        sep.setForeground(c);
        return sep;
    }
    
    /**
     * 
     * @param pnl: The JPanel component being constructed.
     * @param s: The String containing the title for the JPanel.
     * @param w: The Width Integer value of the JPanel.
     * @param h: The Height Integer value of the JPanel.
     * @param v: The Boolean value used for the JPanel's visibility value.
     * @return: Returns the JPanel to be added to the Container for which it was created. 
     */
    
    private static JPanel createPanel(JPanel pnl, String s, int w, int h, Boolean v) {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), s, TitledBorder.LEFT, TitledBorder.TOP, f));
        pnl.setLayout(new GridBagLayout());
        pnl.setSize(w, h);
        pnl.setVisible(v);
        return pnl;
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
    
    private static void buildDBPanel() {
        dbPanel = new JPanel();
        dbPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        dbPanel.setBackground(Color.WHITE);
        dbPanel.setToolTipText("This is the Database/File Structure Panel.");
        dbPanel.setVisible(true);
    }
    
    private static void buildMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainPanel.setToolTipText("This is the Main Program Panel.");
        
        Border b = BorderFactory.createRaisedBevelBorder();
        basic = createPanel(new JPanel(), "Basic Information", mainPanel.getWidth(), mainPanel.getHeight(), false);
        
        JLabel wsNameLbl = new JLabel("Watershed Name", SwingConstants.RIGHT);
        GridBagConstraints gbc = setGBC(new Insets(16, 16, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.0, 0.0);
        basic.add(wsNameLbl, gbc);
        
        JTextField wsNameFld = new JTextField("STC", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 2.0, 0.0);
        basic.add(wsNameFld, gbc);
        
        JLabel filler = new JLabel();
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 2, 1, 2, 1, 1.0, 0.0);
        basic.add(filler, gbc);
        
        JLabel projLocLbl = new JLabel("Project File", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 46, 16, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 2, 1, 1, 0.2, 0.0);
        basic.add(projLocLbl, gbc);
        
        projLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 2, 1, 1, 1.0, 0.0);
        basic.add(projLocation, gbc);
        
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 1.0, 0.0);
        mainPanel.add(basic, gbc);
        
        spatial = createPanel(new JPanel(), "Spatial Data", mainPanel.getWidth(), mainPanel.getHeight(), false);
        
        JLabel spatLocLbl = new JLabel("Spatial Data Folder", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 6, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.05, 0.0);
        spatial.add(spatLocLbl, gbc);
        
        spatLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGBC(new Insets(0, 8, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 0.9, 0.0);
        spatial.add(spatLocation, gbc);
        
        spatLocBtn = createButton(new JButton(smOpenProjIcon), "Show the Spatial Data Folder in Explorer.", b, true, new openLocationListener());
        spatLocBtn.setVisible(false);
        gbc = setGBC(new Insets(0, 0, 0, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 2, 1, 1, 3, 0.0, 0.0);
        spatial.add(spatLocBtn, gbc);
        
        JLabel smDamLbl = new JLabel("Small Dam", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 48, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 2, 1, 1, 0.05, 0.0);
        spatial.add(smDamLbl, gbc);
        
        JTextField smDamFld = new JTextField("small_dam", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 2, 1, 1, 0.95, 0.0);
        spatial.add(smDamFld, gbc);
        
        JLabel pondLbl = new JLabel("Holding Pond", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 34, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 3, 1, 1, 0.05, 0.0);
        spatial.add(pondLbl, gbc);
        
        JTextField pondFld = new JTextField("holding_pond", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 3, 1, 1, 0.95, 0.0);
        spatial.add(pondFld, gbc);
        
        JLabel grazeLbl = new JLabel("Grazing", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 60, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 4, 1, 1, 0.05, 0.0);
        spatial.add(grazeLbl, gbc);
        
        JTextField grazeFld = new JTextField("grazing", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 4, 1, 1, 0.95, 0.0);
        spatial.add(grazeFld, gbc);
        
        JLabel fieldLbl = new JLabel("Field", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 74, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 5, 1, 1, 0.05, 0.0);
        spatial.add(fieldLbl, gbc);
        
        JTextField fieldFld = new JTextField("land2010_by_land_id", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 5, 1, 1, 0.95, 0.0);
        spatial.add(fieldFld, gbc);

        JLabel farmLbl = new JLabel("Farm", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 74, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 6, 1, 1, 0.05, 0.0);
        spatial.add(farmLbl, gbc);
        
        JTextField farmFld = new JTextField("farm2010", 40);
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 6, 1, 1, 0.95, 1.0);
        spatial.add(farmFld, gbc);
        
        // 116
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 1, 1, 1, 1.0, 0.0);
        mainPanel.add(spatial, gbc);
        
        swat = createPanel(new JPanel(), "SWAT Data", mainPanel.getWidth(), mainPanel.getHeight(), false);
        
        JLabel swatLocLbl = new JLabel("SWAT Data Folder", SwingConstants.RIGHT);
        gbc = setGBC(new Insets(16, 10, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.05, 0.0);
        swat.add(swatLocLbl, gbc);
        
        swatLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGBC(new Insets(16, 0, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 1.0, 0.0);
        swat.add(swatLocation, gbc);
        
        swatLocBtn = createButton(new JButton(smOpenProjIcon), "Show the SWAT Data Folder in Explorer.", b, true, new openLocationListener());
        swatLocBtn.setVisible(false);
        gbc = setGBC(new Insets(0, 0, 0, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 2, 1, 1, 1, 0.0, 0.0);
        swat.add(swatLocBtn, gbc);
        
        // -114
        gbc = setGBC(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST, 0, 2, 1, 1, 1.0, 0.0);
        mainPanel.add(swat, gbc);
        
        scenButton = createButton(new JButton("Create Base Scenarios"), "Builds the Base Historical and Conventional Scenarios.", b, true, null);
        gbc = setGBC(new Insets(16, 16, 16, 16), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 3, 0, 0, 0.25, 0.25);
        scenButton.setContentAreaFilled(true);
        scenButton.setVisible(false);
        mainPanel.add(scenButton, gbc);
        mainPanel.setVisible(true);
    }
    
    // Listener Classes
    
    private static class newProjListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                newProjectDialog npd = new newProjectDialog();
                basic.setVisible(true);
                spatial.setVisible(true);
                swat.setVisible(true);
                scenButton.setVisible(true);
                spatLocBtn.setVisible(true);
                swatLocBtn.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static class openLocationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource().equals(spatLocBtn)) {
                try {
                    System.out.println(spatLocation.getText());
                    Process pb = new ProcessBuilder("explorer.exe", "/select," + spatLocation.getText() + "\\spatial.db3").start();
                } catch (IOException ex) {
                    Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(ae.getSource().equals(swatLocBtn)) {
                try {
                    System.out.println(swatLocation.getText());
                    Process pb = new ProcessBuilder("explorer.exe", "/select," + swatLocation.getText() + "\\conventional").start();
                } catch (IOException ex) {
                    Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
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
        frame.setTitle("WEBsInterface v0.07");
        
        // Build the Top Toolbar
        buildTopPanel();
        GridBagConstraints gbc = setGBC(new Insets(4, 4, 4, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 2, 1, 1, 0.3);
        frame.add(topPanel, gbc);
        
        // Build the Database/Project File Structure Panel
        buildDBPanel();
        gbc = setGBC(new Insets((topPanel.getPreferredSize().height + 8), 4, 8, 2), GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 0, 1, 2, 0.15, 0.7);
        frame.add(dbPanel, gbc);
        
        // Build the Main Panel
        buildMainPanel();
        gbc = setGBC(new Insets((topPanel.getPreferredSize().height + 8), 2, 8, 4), GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 0, 1, 2, 0.85, 0.7);
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