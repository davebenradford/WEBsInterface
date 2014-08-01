package websinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import whitebox.cartographic.MapArea;
import whitebox.cartographic.MapInfo;

/**
 *
 * @author Dave Radford
 * @since May 2014
 * @version 0.09
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
 * v0.04: Establish Listeners for startup frame, called 'newProjectDialog'
 *        and 'newScenarioDialog.'
 * v0.05: Built the New Project Dialog Box.
 * v0.06: Condensed component creation into two methods and the GridBagLayout
 *        weight setting values into one method, both for scalability.
 * v0.07: Built the Main mapStatsPanel Scenario Pane. Cleaned up code for scalability
 *        and efficiency.
 * v0.08: Cleaning code, altering Component declarations for inter-package
 *        class calls. Completed listener functionality for main panel buttons.
 * v0.09: Creation of the ScenarioBuilder class.
 * v0.10: ScenarioBuilder Class completed for Base Scenarios.
 * v0.11: Scenario mapStatsPanel completed. Altering GUI elements and beginning to
 *        assemble Project File.
 * v0.12: Begin building Map & BMP UI mapStatsPanel.
 * 
 */

public class WEBsInterface extends JFrame implements Runnable {
    
    // Dimension, Font, Border, and Image Declarations
    
    private static final Dimension topBtnDim = new Dimension(80, 64);
    private static final Dimension pnlBtnDim = new Dimension(96, 64);
    private static final Dimension frameDim = new Dimension(1450, 950);
    private static final Font f = new Font("Sans_Serif", Font.PLAIN, 12);
    private static final ImageIcon newProjIcon = new ImageIcon("src\\images\\document_empty_32x32.png");
    private static final ImageIcon openProjIcon = new ImageIcon("src\\images\\folder_32x32.png");
    private static final ImageIcon smOpenProjIcon = new ImageIcon("src\\images\\folder_16x16.png");
    private static final ImageIcon saveProjIcon = new ImageIcon("src\\images\\save_as_32x32.png");
    private static final ImageIcon saveAsIcon = new ImageIcon("src\\images\\page_save.png");
    private static final ImageIcon resIcon = new ImageIcon("src\\images\\3d_glasses.png");
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
    public static final ImageIcon websIcon = new ImageIcon("src\\images\\icon_32x32.png");
    
    // Swing Component Declarations
    
    private static JPanel topPanel;
    private static JPanel projTreePanel;
    private static JPanel scenPanel;
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
    
    // Public Swing Components
    
    public static JFrame frame;
    public static JPanel projPanel;
    public static JPanel basicProj;
    public static JPanel basicScen;
    public static JPanel bmpSection;
    public static JPanel controlSection;
    public static JPanel spatial;
    public static JPanel swat;
    public static JPanel treePanel;
    public static JPanel whiteBoxPanel;
    public static JPanel mapViewPanel;
    public static JPanel mapStatsPanel;
    public static JPanel chartPanel;
    public static JPanel legendPanel;
    public static JPanel listPanel;
    public static JPanel infoPanel;
    public static JLabel spatLocation;
    public static JLabel projLocation;
    public static JLabel swatLocation;
    public static JLabel progressLbl; // PRIVATE?
    public static JButton scenButton;
    public static JButton spatLocBtn;
    public static JButton swatLocBtn;
    public static JButton btnDamSc;
    public static JButton btnPondSc;
    public static JButton btnGrazeSc;
    public static JButton btnTillSc;
    public static JButton btnForageSc;
    public static JButton btnSaveSc;
    public static JButton btnSaveAsSc;
    public static JButton btnEconSc;
    public static JButton btnSwatSc;
    public static JButton btnResultSc;
    public static JProgressBar websProgressBar; //PRIVATE?
    
    private static ArrayList<MapInfo> openMaps = new ArrayList<>();
    private static int activeMap = 0;
    private static MapRenderer mapViewArea = new MapRenderer();
    
    // Component Creation Methods
    
    /**
     *
     * Create Top mapStatsPanel Button Method
     * 
     * @param btn: The JButton component being constructed.
     * @param s: The String for the JButton's ToolTip Text.
     * @param brd: The Border for the JButton.
     * @param en: The Boolean value for buttons that are enabled at startup.
     * @param a: ActionListener associated with the JButton.
     * @param m: MouseListener associated with the JButton.
     * @return: Returns the JButton to be added to the Container for which it was created.
     */
    
    private static JButton createTopPanelButton(JButton btn, String s, Border brd, Boolean en, ActionListener a, MouseListener m) {
        btn.setFont(f);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setPreferredSize(topBtnDim);
        btn.setBorder(brd);
        btn.setContentAreaFilled(false);
        btn.setToolTipText(s);
        btn.setEnabled(en);
        btn.setFocusable(false);
        btn.addActionListener(a);
        btn.addMouseListener(m);
        return btn;
    }
    
    /**
     * 
     * Create Scenario mapStatsPanel Button Method
     * 
     * @param btn: The JButton component being constructed. 
     * @param s: The String for the JButton's ToolTip Text.
     * @param a: ActionListener associated with the JButton.
     * @param m: MouseListener associated with the JButton.
     * @return: Returns the JButton to be added to the Container for which it was created. 
     */
    
    private static JButton createScenarioPanelButton(JButton btn, String s, ActionListener a, MouseListener m) {
        btn.setFont(f);
        btn.setVerticalTextPosition(SwingConstants.BOTTOM);
        btn.setHorizontalTextPosition(SwingConstants.CENTER);
        btn.setPreferredSize(pnlBtnDim);
        btn.setToolTipText(s);
        btn.setEnabled(true);
        btn.setFocusable(false);
        btn.addActionListener(a);
        btn.addMouseListener(m);
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
     * Create Panel Method
     * 
     * @param pnl: The JPanel component being constructed.
     * @param s: The String containing the title for the JPanel.
     * @param w: The Width Integer value of the JPanel.
     * @param h: The Height Integer value of the JPanel.
     * @param v: The Boolean value used for the JPanel's visibility value.
     * @return: Returns the JPanel to be added to the Container for which it was created. 
     */
    
    private static JPanel createPanel(JPanel pnl, String s, Boolean v) {
        pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), s, TitledBorder.LEFT, TitledBorder.TOP, f));
        pnl.setLayout(new GridBagLayout());
        pnl.setVisible(v);
        return pnl;
    }
    
    private static void createMapArea() {
        LoadMapRenderer lmrt = new LoadMapRenderer();
    }
    
    /**
     * 
     * Set GridBagConstraints (GBC) Method
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
     * @return: Returns the GridBagConstraints for the component to be added.
     */
    
    private static GridBagConstraints setGbc(Insets  i, int fill, int a, int xCoord, int yCoord, int wide, int high, double weighX, double weighY) {
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
    
    /**
     * 
     * Calculating Progress Bar Value Method
     * 
     * @param t: The boolean value used to determine the progress for historic
     *           or conventional progress counter.
     * @param p: The current value of the progress bar.
     * @return The value to be added to the progress bar total.
     */        
    
    public static double calculateProgressBaseScenario(String txt, boolean type, double progress) {
        progressLbl.setText(txt);
        progressLbl.revalidate();
        progressLbl.repaint();
        if(type) {
            progress += 5.8823529411;
        }
        else {
            progress += 16.666666667;
        }
        websProgressBar.setValue((int) Math.round(progress));
        websProgressBar.revalidate();
        websProgressBar.repaint();
        return progress;
    }
    
    // UI Builder Methods
    
    private static void buildTopPanel() {
        // Initalize the Top Button mapStatsPanel
        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());
        Border b = BorderFactory.createEmptyBorder();
        newProj = createTopPanelButton(new JButton("New Project", newProjIcon), "New Project", b, true, new newProjListener(), new buttonMouseListener());
        
        // Resize Top Panel
        Dimension topDim = new Dimension(frame.getMaximumSize().width, newProj.getMaximumSize().height);
        Dimension sepDim = new Dimension(frame.getPreferredSize().width, newProj.getHeight());
        topPanel.setSize(topDim);
        GridBagConstraints gbc = setGbc(new Insets(2, 4, 2, 8), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 0, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newProj, gbc);

        openProj = createTopPanelButton(new JButton("Open Project", openProjIcon), "Open Project", b, true, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 2, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 1, 1, 1, 17, 0.028, 1.0);
        topPanel.add(openProj, gbc);
        
        saveProj = createTopPanelButton(new JButton("Save Project", saveProjIcon), "Save Project", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 2, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveProj, gbc);
        
        vSepProj = createSeparator(new JSeparator(SwingConstants.VERTICAL), sepDim, Color.GRAY);
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 3, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepProj, gbc);
        
        newScen = createTopPanelButton(new JButton("New Scenario", newScenIcon), "New Scenario", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 4, 1, 1, 17, 0.028, 1.0);
        topPanel.add(newScen, gbc);
        
        editScen = createTopPanelButton(new JButton("Edit Scenario", editScenIcon), "Edit Scenario", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 5, 1, 1, 17, 0.028, 1.0);
        topPanel.add(editScen, gbc);
        
        saveScen = createTopPanelButton(new JButton("Save Scenario", saveScenIcon), "Save Scenario", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 6, 1, 1, 17, 0.028, 1.0);
        topPanel.add(saveScen, gbc);

        delScen = createTopPanelButton(new JButton("Delete Scen.", delScenIcon), "Delete Scenario", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 7, 1, 1, 17, 0.028, 1.0);
        topPanel.add(delScen, gbc);
        
        vSepScen = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 8, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepScen, gbc);
        
        btnDam = createTopPanelButton(new JButton("Small Dam", damIcon), "Small Dam BMP", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 9, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnDam, gbc);
        
        btnPond = createTopPanelButton(new JButton("Holding Pond", pondIcon), "Holding Pond BMP", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 10, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnPond, gbc);
        
        btnGraze = createTopPanelButton(new JButton("Grazing", grazeIcon), "Grazing BMP", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 11, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnGraze, gbc);
        
        btnTill = createTopPanelButton(new JButton("Tillage", tillIcon), "Tillage BMP", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 12, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnTill, gbc);
        
        btnForage = createTopPanelButton(new JButton("Forage", forageIcon), "Foraging BMP", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.FIRST_LINE_START, 13, 1, 1, 17, 0.028, 1.0);
        topPanel.add(btnForage, gbc);

        vSepRun = createSeparator(new JSeparator(JSeparator.VERTICAL), sepDim, Color.GRAY);
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.VERTICAL, GridBagConstraints.FIRST_LINE_START, 14, 1, 1, 17, 0.028, 1.0);
        topPanel.add(vSepRun, gbc);
        
        runEcon = createTopPanelButton(new JButton("Run Economic", econIcon), "Run Economic Model", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.HORIZONTAL, GridBagConstraints.FIRST_LINE_START, 15, 1, 1, 17, 0.028, 1.0);
        topPanel.add(runEcon, gbc);
        
        runSwat = createTopPanelButton(new JButton("Run SWAT", swatIcon),"Run Soil and Watershed Assessment Tool (SWAT) Model", b, false, null, new buttonMouseListener());
        gbc = setGbc(new Insets(2, 4, 2, 4), GridBagConstraints.NONE, GridBagConstraints.WEST, 16, 1, 1, 17, 1.0, 1.0);
        topPanel.add(runSwat, gbc);
    }
    
    private static void buildProjectTreePanel() {
        projTreePanel = new JPanel(new GridBagLayout());
        projTreePanel.setBorder(BorderFactory.createLoweredBevelBorder());
        projTreePanel.setBackground(Color.WHITE);
        projTreePanel.setToolTipText("This is the Project Tree Panel.");
        
        DefaultMutableTreeNode projNode = new DefaultMutableTreeNode("Project");
        DefaultMutableTreeNode scenNode = new DefaultMutableTreeNode("Base Scenario"); // Generic Node without having an Object Name? May be necessary.
        JTree tree = new JTree(projNode);
        scenNode.add(new DefaultMutableTreeNode("Small Dams"));
        scenNode.add(new DefaultMutableTreeNode("Holding Ponds"));
        scenNode.add(new DefaultMutableTreeNode("Grazing Areas"));
        scenNode.add(new DefaultMutableTreeNode("Tillage Areas"));
        scenNode.add(new DefaultMutableTreeNode("Foraging Areas"));
        scenNode.add(new DefaultMutableTreeNode("Results"));
        projNode.add(scenNode);
        
        treePanel = new JPanel(new GridBagLayout());
        treePanel.setPreferredSize(new Dimension(projTreePanel.getWidth(), projTreePanel.getHeight()));
        treePanel.setBackground(Color.WHITE);
        treePanel.setVisible(true);
        GridBagConstraints gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 0.2, 0.0);
        treePanel.add(tree, gbc);
        
        JLabel filler = new JLabel();
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.BOTH, GridBagConstraints.WEST, 1, 0, 2, 2, 0.8, 0.9);
        treePanel.add(filler, gbc);
        projTreePanel.add(treePanel, gbc);
        projTreePanel.setVisible(true);
    }
    
    private static void buildNewProjectPanel() {
        projPanel = new JPanel(new GridBagLayout());
        projPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        Border b = BorderFactory.createRaisedBevelBorder();
        basicProj = createPanel(new JPanel(), "Basic Information", false);
        
        JLabel wsNameLbl = new JLabel("Watershed Name", SwingConstants.RIGHT);
        GridBagConstraints gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.0, 0.0);
        basicProj.add(wsNameLbl, gbc);
        
        JTextField wsNameFld = new JTextField("STC", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 0.75, 0.0);
        basicProj.add(wsNameFld, gbc);
        
        JLabel projLocLbl = new JLabel("Project File", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 2, 1, 1, 0.05, 0.0);
        basicProj.add(projLocLbl, gbc);
        
        projLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 2, 1, 1, 0.75, 0.0);
        basicProj.add(projLocation, gbc);
        
        gbc = setGbc(new Insets(0, 32, 0, 32), GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 0, 1, 1, 0.75, 0.0);
        projPanel.add(basicProj, gbc);
        
        spatial = createPanel(new JPanel(), "Spatial Data", false);
        
        JLabel spatLocLbl = new JLabel("Spatial Data Folder", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.05, 0.0);
        spatial.add(spatLocLbl, gbc);
        
        spatLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 0.75, 0.0);
        spatial.add(spatLocation, gbc);
        
        spatLocBtn = createTopPanelButton(new JButton(smOpenProjIcon), "Show the Spatial Data Folder in Explorer.", b, true, new openLocationListener(), null);
        spatLocBtn.setVisible(false);
        spatLocBtn.setPreferredSize(new Dimension(32, 32));
        gbc = setGbc(new Insets(0, 0, 0, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 3, 1, 1, 3, 0.0, 0.0);
        spatial.add(spatLocBtn, gbc);
        
        JLabel smDamLbl = new JLabel("Small Dam", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 2, 1, 1, 0.05, 0.0);
        spatial.add(smDamLbl, gbc);
        
        JTextField smDamFld = new JTextField("small_dam", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 2, 1, 1, 0.75, 0.0);
        spatial.add(smDamFld, gbc);
        
        JLabel pondLbl = new JLabel("Holding Pond", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 3, 1, 1, 0.05, 0.0);
        spatial.add(pondLbl, gbc);
        
        JTextField pondFld = new JTextField("cattle_yard", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 3, 1, 1, 0.75, 0.0);
        spatial.add(pondFld, gbc);
        
        JLabel grazeLbl = new JLabel("Grazing", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 4, 1, 1, 0.05, 0.0);
        spatial.add(grazeLbl, gbc);
        
        JTextField grazeFld = new JTextField("grazing", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 4, 1, 1, 0.75, 0.0);
        spatial.add(grazeFld, gbc);
        
        JLabel fieldLbl = new JLabel("Field", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 5, 1, 1, 0.05, 0.0);
        spatial.add(fieldLbl, gbc);
        
        JTextField fieldFld = new JTextField("land2010_by_land_id", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 5, 1, 1, 0.75, 0.0);
        spatial.add(fieldFld, gbc);

        JLabel farmLbl = new JLabel("Farm", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 24, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 6, 1, 1, 0.05, 0.0);
        spatial.add(farmLbl, gbc);
        
        JTextField farmFld = new JTextField("farm2010", 40);
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 6, 1, 1, 0.75, 1.0);
        spatial.add(farmFld, gbc);
        
        gbc = setGbc(new Insets(0, 32, 0, 32), GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 1, 1, 1, 0.75, 0.0);
        projPanel.add(spatial, gbc);
        
        swat = createPanel(new JPanel(), "SWAT Data", false);
        
        JLabel swatLocLbl = new JLabel("SWAT Data Folder", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(16, 16, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.05, 0.0);
        swat.add(swatLocLbl, gbc);
        
        swatLocation = new JLabel("C:\\", SwingConstants.LEFT);
        gbc = setGbc(new Insets(16, 16, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 0.75, 0.0);
        swat.add(swatLocation, gbc);
        
        swatLocBtn = createTopPanelButton(new JButton(smOpenProjIcon), "Show the SWAT Data Folder in Explorer.", b, true, new openLocationListener(), null);
        swatLocBtn.setVisible(false);
        swatLocBtn.setPreferredSize(new Dimension(32, 32));
        gbc = setGbc(new Insets(0, 0, 0, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 3, 1, 1, 1, 0.0, 0.0);
        swat.add(swatLocBtn, gbc);
        
        gbc = setGbc(new Insets(0, 32, 0, 32), GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER, 1, 2, 1, 1, 0.75, 0.0);
        projPanel.add(swat, gbc);
        
        scenButton = createScenarioPanelButton(new JButton(), "Builds the Base Historical and Conventional Scenarios.", new baseScenarioListener(), null);
        scenButton.setLayout(new BorderLayout());
        scenButton.add(BorderLayout.CENTER, new JLabel("Create Base", SwingConstants.CENTER));
        scenButton.add(BorderLayout.SOUTH, new JLabel(" Scenarios ", SwingConstants.CENTER));
        
        gbc = setGbc(new Insets(16, 32, 16, 16), GridBagConstraints.NONE, GridBagConstraints.NORTHWEST, 0, 3, 0, 0, 0.0, 0.25);
        scenButton.setContentAreaFilled(true);
        scenButton.setVisible(false);
        projPanel.add(scenButton, gbc);
    }
    
    private static void buildScenarioPanel() {
        scenPanel = new JPanel(new GridBagLayout());
        scenPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        basicScen = createPanel(new JPanel(), "Basic Information", false);
        
        JLabel scenNameLbl = new JLabel("Scenario Name", SwingConstants.RIGHT);
        GridBagConstraints gbc = setGbc(new Insets(16, 70, 16, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 1, 1, 1, 0.2, 0.0);
        basicScen.add(scenNameLbl, gbc);
        
        JTextField scenNameFld = new JTextField("PLACEHOLDER", 82);
        gbc = setGbc(new Insets(0, 4, 0, 4), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 1, 1, 1, 0.8, 0.0);
        basicScen.add(scenNameFld, gbc);
        
        JLabel filler = new JLabel();
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.BOTH, GridBagConstraints.WEST, 2, 1, 1, 2, 1.0, 0.0);
        basicScen.add(filler, gbc);
        
        JLabel scenDescLbl = new JLabel("Scenario Description", SwingConstants.RIGHT);
        gbc = setGbc(new Insets(0, 46, 16, 0), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 2, 1, 1, 0.2, 0.0);
        basicScen.add(scenDescLbl, gbc);
        
        JTextArea scenDescFld = new JTextArea("Description PLACEHOLDER", 3, 60);
        scenDescFld.setFont(f);
        scenDescFld.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc = setGbc(new Insets(0, 4, 4, 4), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 2, 1, 1, 0.8, 0.0);
        basicScen.add(scenDescFld, gbc);
        
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 1.0, 0.0);
        scenPanel.add(basicScen, gbc);
        
        bmpSection = createPanel(new JPanel(), "BMPs", false);
        
        btnDamSc = createScenarioPanelButton(new JButton("Small Dam", damIcon), "Select Small Dams", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, 1, 1, 0.0, 0.0);
        bmpSection.add(btnDamSc, gbc);
        
        btnPondSc = createScenarioPanelButton(new JButton("Holding Pond", pondIcon), "Select Holding Ponds", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 0, 1, 1, 0.0, 0.0);
        bmpSection.add(btnPondSc, gbc);
        
        btnGrazeSc = createScenarioPanelButton(new JButton("Grazing", grazeIcon), "Select Grazing Areas", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 2, 0, 1, 1, 0.0, 0.0);
        bmpSection.add(btnGrazeSc, gbc);

        btnGrazeSc = createScenarioPanelButton(new JButton("Tillage", tillIcon), "Select Tillage Areas", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 3, 0, 1, 1, 0.0, 0.0);
        bmpSection.add(btnGrazeSc, gbc);

        btnGrazeSc = createScenarioPanelButton(new JButton("Forage", forageIcon), "Select Foraging Areas", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 4, 0, 1, 1, 1.0, 0.0);
        bmpSection.add(btnGrazeSc, gbc);
        
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 1, 1, 1, 1.0, 0.0);
        scenPanel.add(bmpSection, gbc);
        
        controlSection = createPanel(new JPanel(), "Controls", false);
        
        btnSaveSc = createScenarioPanelButton(new JButton("Save", saveScenIcon), "Save Scenario", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, 1, 1, 0.0, 0.0);
        controlSection.add(btnSaveSc , gbc);
        
        btnSaveAsSc = createScenarioPanelButton(new JButton("Save As", saveAsIcon), "Save Scenario As", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 1, 0, 1, 1, 0.0, 0.0);
        controlSection.add(btnSaveAsSc , gbc);
        
        btnEconSc = createScenarioPanelButton(new JButton("Economic", econIcon), "Run Economic Model", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 2, 0, 1, 1, 0.0, 0.0);
        controlSection.add(btnEconSc , gbc);
        
        btnSwatSc = createScenarioPanelButton(new JButton("SWAT", swatIcon), "Run SWAT Model", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 3, 0, 1, 1, 0.0, 0.0);
        controlSection.add(btnSwatSc , gbc);
        
        btnResultSc = createScenarioPanelButton(new JButton("Results", resIcon), "View Scenario Results", null, null);
        gbc = setGbc(new Insets(4, 16, 4, 16), GridBagConstraints.NONE, GridBagConstraints.WEST, 4, 0, 1, 1, 1.0, 0.0);
        controlSection.add(btnResultSc, gbc);
        
        gbc = setGbc(new Insets(0, 0, 0, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 2, 1, 1, 1.0, 0.25);
        scenPanel.add(controlSection, gbc);
        scenPanel.validate();
    }
    
    private static void buildWhiteBoxPanel() {
        whiteBoxPanel = new JPanel(new GridBagLayout());
        
        legendPanel = new JPanel();
        legendPanel.setBorder(BorderFactory.createLoweredBevelBorder());        
        legendPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = setGbc(new Insets(0, 2, 2, 0), GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 0, 1, 2, 0.2, 0.7);
        whiteBoxPanel.add(legendPanel, gbc);
        
        mapViewPanel = new JPanel();
        mapViewPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mapViewPanel.setBackground(Color.WHITE);
        mapViewPanel.add(mapViewArea);
        
        gbc = setGbc(new Insets(0, 2, 2, 0), GridBagConstraints.BOTH, GridBagConstraints.EAST, 1, 0, 1, 2, 0.8, 0.7);
        whiteBoxPanel.add(mapViewPanel, gbc);
        
        chartPanel = new JPanel();
        chartPanel.setBorder(BorderFactory.createLoweredBevelBorder());        
        chartPanel.setBackground(Color.WHITE);
            
        gbc = setGbc(new Insets(2, 2, 0, 0), GridBagConstraints.BOTH, GridBagConstraints.EAST, 0, 2, 2, 1, 1.0, 0.3);
        whiteBoxPanel.add(chartPanel, gbc);
        
        mapStatsPanel = new JPanel(new GridBagLayout());
        mapStatsPanel.setBorder(BorderFactory.createEmptyBorder());
        
        gbc = setGbc(new Insets(0, 0, 2, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 0, 0, 1, 1, 1.0, 0.0);
        mapStatsPanel.add(new JButton(" Select All "), gbc);
        
        gbc = setGbc(new Insets(0, 0, 2, 0), GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST, 1, 0, 1, 1, 1.0, 0.0);
        mapStatsPanel.add(new JButton("Deselect All"), gbc);
        
        listPanel = new JPanel();
        listPanel.setBackground(Color.WHITE);
        listPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        gbc = setGbc(new Insets(2, 0, 2, 0), GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 0, 1, 2, 3, 1.0, 0.8);
        mapStatsPanel.add(listPanel, gbc);
        
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        
        gbc = setGbc(new Insets(2, 0, 0, 0), GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST, 0, 4, 2, 1, 1.0, 0.2);
        mapStatsPanel.add(infoPanel, gbc);
    }
    
    private static void buildProgressBar() {
        websProgressBar = new JProgressBar(0, 100);
        websProgressBar.setString(null);
        websProgressBar.setStringPainted(false);
        progressLbl = new JLabel("", SwingConstants.RIGHT);
    }
    
    // Listener Classes
    
    private static class newProjListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                NewProjectDialog npd = new NewProjectDialog();
            } catch (IOException e) {
                Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    private static class openLocationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            if(ae.getSource().equals(spatLocBtn)) {
                try {
                    Process pb = new ProcessBuilder("explorer.exe", "/select," + spatLocation.getText() + "\\spatial.db3").start();
                } catch (IOException e) {
                    Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            else if(ae.getSource().equals(swatLocBtn)) {
                try {
                    Process pb = new ProcessBuilder("explorer.exe", "/select," + swatLocation.getText() + "\\conventional").start();
                } catch (IOException e) {
                    Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    private static class baseScenarioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                websProgressBar.setStringPainted(true);
                frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                frame.getGlassPane().setVisible(true);
                ScenarioBuilder scH = new ScenarioBuilder("historic", true, true);
                ScenarioBuilder scC = new ScenarioBuilder("conventional", true, false);
                websProgressBar.setStringPainted(false);
                frame.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                frame.getGlassPane().setVisible(true);
                progressLbl.setText("Completed");
                websProgressBar.setValue(websProgressBar.getMinimum());
                projPanel.setVisible(false);
                scenPanel.setVisible(true);
                scenPanel.requestFocusInWindow();
                basicScen.setVisible(true);
                bmpSection.setVisible(true);
                controlSection.setVisible(true);
                frame.validate();
            } catch (ClassNotFoundException | SQLException | IOException e) {
                Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
    
    private static class buttonMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent me) {
            
        }

        @Override
        public void mousePressed(MouseEvent me) {
            
        }

        @Override
        public void mouseReleased(MouseEvent me) {
            
        }

        @Override
        public void mouseEntered(MouseEvent me) {
            JButton src = (JButton) me.getSource();
            if(src.isEnabled()) {
                src.setContentAreaFilled(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent me) {
            JButton src = (JButton) me.getSource();
            if(src.isEnabled()) {
                src.setContentAreaFilled(false);
            }
        }
    }
    
    /**
     * 
     * Main Method
     * 
     * @param args: Default Parameter.
     */
    
    public static void main(String[] args) {
        // Set the Look and Feel of the Frame
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Logger.getLogger(WEBsInterface.class.getName()).log(Level.SEVERE, null, e);
        }
        // Initialize and Create the Main Frame (Container)
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frame.getWidth(), frame.getHeight());
        frame.setMinimumSize(frameDim);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.setIconImage(websIcon.getImage());
        frame.setTitle("WEBsInterface v0.12");
        
        // Build the Top Toolbar
        buildTopPanel();
        GridBagConstraints gbc = setGbc(new Insets(4, 4, 4, 4), GridBagConstraints.NONE, GridBagConstraints.WEST, 0, 0, 4, 1, 1.0, 0.0);
        frame.add(topPanel, gbc);
        
        // Build the Database/Project File Structure mapStatsPanel
        buildProjectTreePanel();
        gbc = setGbc(new Insets(4, 4, 22, 2), GridBagConstraints.BOTH, GridBagConstraints.WEST, 0, 1, 1, 2, 0.2, 1.0);
        frame.add(projTreePanel, gbc);
        
        // Build the Main mapStatsPanel
        buildNewProjectPanel();
        gbc = setGbc(new Insets(4, 2, 22, 4), GridBagConstraints.BOTH, GridBagConstraints.WEST, 1, 1, 3, 2, 0.7, 1.0);
        projPanel.requestFocusInWindow();
        frame.add(projPanel, gbc);
        
        // Build the Progress Bar & Label
        buildProgressBar();
        gbc = setGbc(new Insets(0, 2, 4, 154), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 3, 2, 1, 1, 0.15, 0.0);
        frame.add(progressLbl, gbc);
        gbc = setGbc(new Insets(0, 2, 4, 4), GridBagConstraints.NONE, GridBagConstraints.SOUTHEAST, 3, 2, 1, 1, 0.85, 0.0);
        frame.add(websProgressBar, gbc);
        
        // Build the Scenario Panel
        buildScenarioPanel();
        gbc = setGbc(new Insets(4, 2, 22, 4), GridBagConstraints.BOTH, GridBagConstraints.EAST, 1, 1, 3, 2, 0.7, 1.0);
        frame.add(scenPanel, gbc);
        
        // Build the WhiteBox Panel
        buildWhiteBoxPanel();
        gbc = setGbc(new Insets(4, 2, 22, 4), GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1, 1, 2, 2, 0.7, 1.0);
        frame.add(whiteBoxPanel, gbc);
        
        // Add MapStats Panel
        gbc = setGbc(new Insets(4, 2, 22, 4), GridBagConstraints.BOTH, GridBagConstraints.EAST, 3, 1, 1, 2, 1.0, 1.0);
        frame.add(mapStatsPanel, gbc);
        
        // Pack and Display the Frame
        frame.pack();
        frame.validate();
        frame.setVisible(true);
        projPanel.setVisible(false);
        scenPanel.setVisible(false);
        whiteBoxPanel.setVisible(true);
        mapStatsPanel.setVisible(false);
    }
    
    @Override
    public void run() {
        WEBsInterface webs = new WEBsInterface();
    }
}
