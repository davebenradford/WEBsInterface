package websinterface;

/**
 *
 * @author Dave Radford
 * @since May 2014
 * @version 0.09
 * 
 * Scenario Builder for WEBs Interface
 * 
 * This class executes the necessary code to build the SQLite database files
 * used by the STC project. The database files (.db3) are able to be manipulated
 * through Java using the Java Database Connectivity (JDBC) library.
 * 
 * Project Version History
 * 
 * v0.09: Creation of the ScenarioBuilder class.
 * v0.10: Migrated the BuildDB class into the ScenarioBuilder class.
 * 
 */

import java.io.File;
import java.io.IOException;
import static java.lang.Class.forName;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.System.arraycopy;
import java.sql.*;
import static java.sql.DriverManager.getConnection;
import static java.sql.DriverManager.getConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import static websinterface.WEBsInterface.calculateProgressBaseScenario;

public class ScenarioBuilder {
    private static final File spatial = new File("STC/Data/Spatial/spatial.db3");
    private static final File[] dbf_tbls = {new File("STC/Data/Spatial/small_dam.dbf"), new File("STC/Data/Spatial/cattle_yard.dbf"),
                                            new File("STC/Data/Spatial/grazing.dbf"), new File("STC/Data/Spatial/land2010_by_land_id.dbf"),
                                            new File("STC/Data/Spatial/farm2010.dbf")};
    protected static double progress;
    protected static boolean scenarioType;

    /**
     * @param scen: String containing the Scenario Name.
     * @param isBase: Boolean value indicating whether a scenario is a base
     *                scenario created by WEBs or a user-defined scenario.
     * @param type: Boolean value used to determine the type of scenario being
     *              created. A value of 'True' indicates an Historical scenario
     *              and a value of 'False' indicates a Conventional scenario.
     * @throws ClassNotFoundException: Missing the Library for SQLite version of JDBC.
     * @throws SQLException: Error with Queries made using SQLite.
     * @throws IOException: File I/O Error when verifying existence of a DBF table.
     */
    
    protected ScenarioBuilder(String scen, boolean isBase, boolean type) throws ClassNotFoundException, SQLException, IOException {
        String inDb;
        progress = 0.0;
        scenarioType = type;
        forName("org.sqlite.JDBC");
        
        if(isBase) {
            inDb = "base_" + scen + ".db3";
        }
        else {
            inDb = scen + ".db3";
        }
        
        Connection cInDb3 = getConnection("jdbc:sqlite:" + spatial.getAbsolutePath());
        cInDb3.setAutoCommit(false);
        progress = calculateProgressBaseScenario("\nOpened spatial.db3 database successfully", scenarioType, progress);

        Connection cOutput = getConnection("jdbc:sqlite:" + inDb);
        cOutput.setAutoCommit(false);
        progress = calculateProgressBaseScenario("\nConnection established to " + inDb + " database successfully", scenarioType, progress);
        
        try {
            Statement inStmtA = cInDb3.createStatement();
            Statement inStmtB = cInDb3.createStatement();
            Statement outStmt = cOutput.createStatement();
            
            createTables(outStmt, cOutput, inDb);
            
            String tblA = "yield_";
            if(type) {
                tblA += "historic";
            }
            else {
                tblA += "conventional";
            }
                    
            buildCropEconFields(inStmtA, outStmt, tblA, "crop_economic_fields", cOutput);
            
            buildCropEconFarms(inStmtA, inStmtB, outStmt, tblA, "field_farm", "crop_economic_farms", cOutput);
                       
            buildCropEconSubbasins(inStmtA, inStmtB, outStmt, tblA, "field_subbasin", "crop_economic_subbasins", cOutput);
            
            if(type) {
                buildHistoricScenario(inStmtA, inStmtB, outStmt, cOutput);
            }
        } catch(SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            cOutput.close();
            cInDb3.close();
        }
    }
    
    /**
     * 
     * @param out: Output Statement Call. This contains the Connection to the Output SQLite3 DB file.
     * @param c: Connection to the Output SQL Database.
     * @param scen: String 
     */
    
    private static void createTables(Statement out, Connection c, String scen) {
        final String[] tbl_names = {"crop_economic_fields", "crop_economic_farms", "crop_economic_subbasins", "forage", "forage_hru",
                                    "tillage", "grazing_hrus", "grazing_economic", "grazing_economic_subbasins", "small_dams_economic",
                                    "holding_ponds_economic", "grazing", "holding_ponds", "small_dams", "tillage_hrus"};
        final String[] val_names = {"id", "year", "yield", "revenue", "cost", "net_return", "grazing_ha", "unit_cost", "hru",
                                    "cattle", "clay_liner", "plastic_ln", "wire_fence", "distance", "trenching", "pond_yrs",
                                    "annual_cost", "maintenance", "total_cost", "embankment", "life_time", "tillage"};
        final String[] val_types = {"int", "real"};
        try {
            int i = 1, t = 1;
            for (String tbl: tbl_names) {
                String sql = "CREATE TABLE IF NOT EXISTS " + tbl + "(";
                switch(i) {
                    case 1:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[1] + " " + val_types[0] + ", " + val_names[2] + " " + val_types[1] + ", "
                             + val_names[3] + " " + val_types[1] + ", " + val_names[4] + " " + val_types[1] + ", " + val_names[5] + " " + val_types[1] + ");";
                        t++;
                        if(t > 3) {
                            i++;
                            t = 1;
                        }
                        break;
                    case 2:
                        sql += val_names[0] + " " + val_types[0] + ");";
                        t++;
                        if(t > 4) {
                            i++;
                            t = 1;
                        }
                        break;
                    case 3:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[1] + " " + val_types[0] + ", " + val_names[4] + " " + val_types[1] + ");";
                        t++;
                        if(t > 4) {
                            i++;
                            t = 1;
                        }
                        break;
                    case 4:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[6] + " " + val_types[1] + ", " + val_names[7] + " " + val_types[1] + ", " + val_names[4] + " " + val_types[1] + ");";
                        i++;
                        break;
                    case 5:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[8] + " " + val_types[0] + ", " + val_names[9] + " " + val_types[1] + ", "
                            + val_names[10] + " " + val_types[1] + ", " + val_names[11] + " " + val_types[1] + ", " + val_names[12] + " " + val_types[1] + ", "
                            + val_names[13] + " " + val_types[1] + ", " + val_names[14] + " " + val_types[1] + ", " + val_names[15] + " " + val_types[1] + ", "
                             + val_names[4] + " " + val_types[1] + ", " + val_names[16] + " " + val_types[1] + ", " + val_names[17] + " " + val_types[1] + ", "
                            + val_names[18] + " " + val_types[1] + ");";
                        i++;
                        break;
                    case 6:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[19] + " " + val_types[1] + ", " + val_names[20] + " " + val_types[0] + ");";
                        i++;
                        break;
                    case 7:
                        sql += val_names[0] + " " + val_types[0] + ", " + val_names[21] + " " + val_types[0] + ");";
                        i++;
                        break;
                    default:
                        sql = "EoE";
                        i = 0;
                        break; 
                }
                if(sql.equalsIgnoreCase("EoE")) {
                    System.out.println("\nTABLES CREATED");
                }
                else {
                    out.executeUpdate(sql);
                }
            }
            c.commit();
            progress = calculateProgressBaseScenario("\n" + scen + " database created successfully", scenarioType, progress);
        } catch (SQLException ex) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param inA: Input Statement A used for calling tables from the Spatial.db3 file.
     * @param inB: Input Statement B used for calling tables from the Spatial.db3 file.
     * @param out: Output Statement Call. This contains the Connection to the Output SQLite3 DB file.
     * @param c: Connection to the Output SQL Database. 
     */
    
    private static void buildHistoricScenario(Statement inA, Statement inB, Statement out, Connection c) {
        try {
            ValueTypePair[][] src;
            src = loadDbfTableData(new Table(new File(dbf_tbls[0].getAbsolutePath())), "Existing", new String[]{"ID", "Embankment", "LifeTime"});
            buildDbfTables(src, out, c, "small_dams");
            buildDbfTables(loadSmallDamEconCosts(src), out, c, "small_dams_economic");
            
            src = loadDbfTableData(new Table(new File(dbf_tbls[1].getAbsolutePath())), "Existing", new String[]{"ID", "HRU",
                "Cattles", "ClayLiner", "PlasticLn", "WireFence", "Distance", "Trenching", "Pond_Yrs"});
            src = loadPondCosts(src);
            buildDbfTables(src, out, c, "holding_ponds");
            buildDbfTables(loadPondEcon(src), out, c, "holding_ponds_economic");
            
            src = loadDbfTableData(new Table(new File(dbf_tbls[2].getAbsolutePath())), "Existing", new String[]{"ID", "Grazing_Ha", "UnitCost"});
            src = loadGrazing(src);
            buildDbfTables(src, out, c, "grazing");
            buildDbfTables(loadGrazingEcon(src), out, c, "grazing_economic");
            
            src = loadGrazingSubbasinData(src, inA, inB, "subbasin_grazing", "grazing_area");
            buildDbfTables(src, out, c, "grazing_economic_subbasins");
            
            src = loadGrazingHruData(src, inA, out, "subbasin_grazing_hru", "grazing_economic_subbasins");
            buildDbfTables(src, out, c, "grazing_hrus");
        } catch (IOException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * 
     * @param in: Input Statement Call. This contains the Connection to the Input SQLite3 DB file.
     *            For all subsequent fillTableX functions, in refers to the Input connection. Any
     *            variables having the prefix 'in' are used in relation to values taken from the input tables
     *            via the input DB connection. 
     * @param out: Output Statement Call. This contains the Connection to the Output SQLite3 DB file.
     *             For all subsequent fillTableX functions, in refers to the Output connection. Any
     *             variables having the prefix 'out' are used in relation to values taken from/written to the output table
     *             via the output DB connection. 
     * @param inTbl: Name of the Input Table called yield_historic from the SQLite3 DB.
     * @param outTbl: crop_economic_fields Output SQL Table.
     * @param c: Connection to the Output SQL Database.
     */
    
    private static void buildCropEconFields(Statement in, Statement out, String inTbl, String outTbl, Connection c) {
        try {
            ResultSet inRs = in.executeQuery("SELECT * FROM " + inTbl + ";");
            ResultSet outRs = out.executeQuery("SELECT * FROM " + outTbl + ";");
            NameTypePair[] ntp = loadInputNamesAndTypes(inRs);
            String outColumnNames = loadOutputColumnNames(outRs);
            String sql = "INSERT INTO " + outTbl + "(" + outColumnNames + "VALUES(";
            while(inRs.next()) {
                out.executeUpdate(writeFieldOutputQuery(inRs, ntp, sql, 0));
            }
            c.commit();
            progress = calculateProgressBaseScenario("\n" + outTbl + " database created successfully", scenarioType, progress);
        } catch (SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e); 
        }
    }
    
    /**
     *
     * @param inFld: Input Statement for the yield_historic SQL Table. Referred
     *               to as Fld since it is used to build crop_Economic_Fields
     *               table. Additionally, each farm/subbasin is comprised of the
     *               fields from this table.
     * @param inBmp: Input Statement for the field_farm SQL Table.
     *               Referred to as inBMP to avoid duplication of the functions
     *               used to build the output SQL Tables. Farm and Subbasin are
     *               built using the same functions.
     * @param out: Output Statement Call.
     * @param inTblA: yield_historic SQL Table.
     * @param inTblB: field_farm SQL Table.
     * @param outTbl: crop_economic_farms Output SQL Table.
     * @param c: Connection to the Output SQL Database.
     */
    
    private static void buildCropEconFarms(Statement inFld, Statement inBmp, Statement out, String inTblA, String inTblB, String outTbl, Connection c){
        try {
            ResultSet inRsFld = inFld.executeQuery("SELECT * FROM " + inTblA + ";");
            ResultSet inRsFrm = inBmp.executeQuery("SELECT * FROM " + inTblB + " WHERE farm > 0 ORDER BY farm;");
            ResultSet outRs = out.executeQuery("SELECT * FROM " + outTbl + ";");
            NameTypePair[] ntp = loadInputNamesAndTypes(inRsFld);
            String outColumnNames = loadOutputColumnNames(outRs);
            String sql = "INSERT INTO " + outTbl + "(" + outColumnNames + "VALUES(";
            writeFarmSubbasinOutputQueries(inRsFrm, inFld, inTblA, ntp, sql, out, "farm");
            c.commit();
            progress = calculateProgressBaseScenario("\n" + outTbl +" database created successfully", scenarioType, progress);
        } catch(SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * 
     * @param inFld: Input Statement for the yield_historic SQL Table.
     * @param inBmp: Input Statement for the field_subbasin SQL Table.
     * @param out: Output Statement Call.
     * @param tblA: yield_historic SQL Table.
     * @param tblB: field_subbasin SQL Table.
     * @param outTbl: crop_economic_fields Output SQL Table.
     * @param c: Connection to the Output SQL Database.
     */
    
    private static void buildCropEconSubbasins(Statement inFld, Statement inBmp, Statement out, String tblA, String tblB, String outTbl, Connection c) {
        try {
            ResultSet inRsFld = inFld.executeQuery("SELECT * FROM " + tblA + ";");
            ResultSet inRsBsn = inBmp.executeQuery("SELECT * FROM " + tblB + " WHERE subbasin > 0 ORDER BY subbasin;");
            ResultSet outRs = out.executeQuery("SELECT * FROM " + outTbl + ";");
            NameTypePair[] ntp = loadInputNamesAndTypes(inRsFld);
            String outColumnNames = loadOutputColumnNames(outRs);
            String sql = "INSERT INTO " + outTbl + "(" + outColumnNames + "VALUES(";
            writeFarmSubbasinOutputQueries(inRsBsn, inFld, tblA, ntp, sql, out, "subbasin");
            c.commit();
            progress = calculateProgressBaseScenario("\n" + outTbl + " database created successfully", scenarioType, progress);
        } catch(SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
     
    /**
     * 
     * @param src: ValueTypePair Array containing the table data values.
     * @param out: Output Statement Call. This contains the Connection to the Output SQLite3 DB file.
     * @param c: Connection to the Output SQL Database.
     * @param tbl: String containing the name of the Output SQL Table.
     */
    
    private static void buildDbfTables(ValueTypePair[][] src, Statement out, Connection c, String tbl) {
        try {
            for(ValueTypePair[] s: src) {
                String sql = "INSERT INTO " + tbl + "(" + loadOutputColumnNames(out.executeQuery("SELECT * FROM " + tbl + ";")) + " VALUES(";
                out.executeUpdate(writeDbfOutputQueries(s, sql));
            }
            c.commit();
            progress = calculateProgressBaseScenario("\n" + tbl + " database created successfully", scenarioType, progress);
        } catch (SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * 
     * @param iRs: Input ResultSet containing the query result from the source
     *             table. All data will be written to the output Table using the
     *             out Connection Statement variable. The naming convention for
     *             the variables does not need the 'i' to represent input within
     *             the functions individually. This has been done to ensure that
     *             the documentation for the program can be clearly followed
     *             when troubleshooting bugs in the future. An 'i' prefix
     *             represents a ResultSet using a query with an Input Connection
     *             and an 'o' prefix represents a ResultSet using a query with
     *             an Output Connection.
     * @return ntp: The NameTypePair array compiled in the function. 
     */
    
    private static NameTypePair[] loadInputNamesAndTypes(ResultSet iRs) {
        NameTypePair[] ntp = null;
        try {
            ntp = new NameTypePair[iRs.getMetaData().getColumnCount()];
            for(int i = 1; i <= iRs.getMetaData().getColumnCount(); i++) {
                // 4 is the Java.Sql.Types code for Integers.
                if (iRs.getMetaData().getColumnType(i) == 4) {
                    ntp[i - 1] = new NameTypePair(iRs.getMetaData().getColumnName(i), 1);
                } else {
                    ntp[i - 1] = new NameTypePair(iRs.getMetaData().getColumnName(i), 0);
                }
            }
        } catch (SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        return ntp;
    }
    
    /**
     * 
     * @param oRs: Output ResultSet containing the query result from the target table.
     * @return outColumnNames: A formatted String containing all of the column
     *                         names in the table. This is done separately from
     *                         data retrieval in the setOutputValues function to
     *                         allow the columns names of the output tables to
     *                         work with the data from the source tables, based
     *                         on consistent naming standards. 
     * 
     */
    
    private static String loadOutputColumnNames(ResultSet oRs) {
        String outColumnNames = "";
        try {
            String[] outColumnNamesArray = new String[oRs.getMetaData().getColumnCount()];      
            for(int i = 1; i <= oRs.getMetaData().getColumnCount(); i++) {
                outColumnNamesArray[i - 1] = oRs.getMetaData().getColumnName(i);
                if(i == oRs.getMetaData().getColumnCount()) {
                    outColumnNames += outColumnNamesArray[i - 1] + ") ";
                }
                else {
                    outColumnNames += outColumnNamesArray[i - 1] + ", ";
                }
            }
        } catch (SQLException e){
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        return outColumnNames;
    }

    /**
     * @param tbl: Input DBF Formatted Table.
     * @param existing: The String used to mark the Existing Column. Used specifically
     *                  for the base scenarios. A modification will be needed for the
     *                  user-defined scenarios. Possibly a boolean call.
     * @param columns: Column names found within the input Table.
     * @return vals: A collection of the entries for each row in the input Table.
     *               Each row contains the data from the corresponding columns,
     *               as well as the type for the data value as a ValueTypePair.
     *               This is to accommodate potential issues with input data
     *               being read as an integer value instead of a double value.
     *               Pairing the data value with a static type value will prevent
     *               complications with writing the values to the SQLite databases.
     * @throws IOException: File I/O Error when verifying existence of a DBF table.
     */
    
    private static ValueTypePair[][] loadDbfTableData(Table tbl, String existing, String[] columns) throws IOException {
        ValueTypePair[][] vals = null, temp = null;
        int entries = 0;
        try {
            tbl.open(IfNonExistent.ERROR);
            temp = new ValueTypePair[tbl.getRecordCount()][columns.length];
            progress = calculateProgressBaseScenario("\nOpened " + tbl.getName() + " database successfully", scenarioType, progress);
            Iterator<Record> iter = tbl.recordIterator();
            for(int i = 0; i < tbl.getRecordCount(); i++) {
                Record rec = iter.next();
                Number exists = rec.getNumberValue(existing);
                if(exists.intValue() == 1) {
                    for(int j = 0; j < columns.length; j++) {
                        Number n = rec.getNumberValue(columns[j]);
                        if(n.doubleValue() % 1 > 0 || columns[j].equalsIgnoreCase("embankment") || columns[j].equalsIgnoreCase("distance")) {
                            temp[entries][j] = new ValueTypePair(n.doubleValue(), 0);
                        }
                        else {
                            temp[entries][j] = new ValueTypePair((double) n.intValue(), 1);
                        }
                    }
                    entries++;
                }
            }
            vals = new ValueTypePair[entries][columns.length];
            arraycopy(temp, 0, vals, 0, vals.length);
        } catch (CorruptedTableException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        finally {
            tbl.close();
        } 
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Small Dam data and type values.
     * @return vals: ValueTypePair Array containing the calculated costs for the 
     *               ponds, covering base, annual, maintenance, and total cost.
     */
    
    private static ValueTypePair[][] loadSmallDamEconCosts(ValueTypePair[][] vtp) {
        ValueTypePair[][] vals = new ValueTypePair[vtp.length * 20][3];
        for(int i = 0; i < vtp.length; i++) {
            int year = 1991;
            for(int j = 0; j < 20; j++) {
                vals[i * 20 + j][0] = vtp[i][0];
                vals[i * 20 + j][1] = new ValueTypePair(year , 1);
                vals[i * 20 + j][2] = new ValueTypePair(calculateSmallDamCost(vtp[i][1].getPairValueAsDouble(), vtp[i][2].getPairValueAsDouble()), 0);
                year++;
            }
        }
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Holding Pond data and type values.
     * @return vals: ValueTypePair Array containing the calculated cost for the
     *               economic figures for the dam as well as the year values.
     */
    
    private static ValueTypePair[][] loadPondCosts(ValueTypePair[][] vtp) {
        ValueTypePair[][] vals = new ValueTypePair[vtp.length][13];
        for(int i = 0; i < vtp.length; i++) {
            for(int j = 0; j < 9; j++) { 
                vals[i][j] = new ValueTypePair(vtp[i][j].getPairValueAsDouble(), vtp[i][j].getPairType());
            }
            vals[i][9] = new ValueTypePair(calculatePondBaseCost(
                    vals[i][2].getPairValueAsDouble(), vals[i][3].getPairValueAsDouble(),
                    vals[i][4].getPairValueAsDouble(), vals[i][5].getPairValueAsDouble(),
                    vals[i][6].getPairValueAsDouble(), vals[i][7].getPairValueAsDouble()), 0);
            vals[i][10] = new ValueTypePair(vals[i][9].getPairValueAsDouble() / vals[i][8].getPairValueAsDouble(), 0);
            vals[i][11] = new ValueTypePair(calculatePondMaintenanceCost(vals[i][2].getPairValueAsDouble(), vals[i][5].getPairValueAsDouble()), 0);
            vals[i][12] = new ValueTypePair(vals[i][10].getPairValueAsDouble() + vals[i][11].getPairValueAsDouble(), 0);
        }
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Holding Pond data and type values.
     * @return vals: ValueTypePair Array containing the calculated costs for the 
     *               ponds, covering base, annual, maintenance, and total cost.
     */
    
    private static ValueTypePair[][] loadPondEcon(ValueTypePair[][] vtp) {
        ValueTypePair[][] vals = new ValueTypePair[vtp.length * 20][3];
        for(int i = 0; i < vtp.length; i++) {
            int year = 1991;
            for(int j = 0; j < 20; j++) {
                vals[i * 20 + j][0] = vtp[i][0];
                vals[i * 20 + j][1] = new ValueTypePair(year, 1);
                vals[i * 20 + j][2] = vtp[i][12];
                year++;
            }
        }
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Grazing data and type values.
     * @return vals: ValueTypePair Array containing the economic data for the
     *               grazing areas.
     */
    
    private static ValueTypePair[][] loadGrazingEcon(ValueTypePair[][] vtp) {
        ValueTypePair[][] vals = new ValueTypePair[vtp.length * 20][3];
        for(int i = 0; i < vtp.length; i++) {
            int year = 1991;
            for(int j = 0; j < 20; j++) {
                vals[i * 20 + j][0] = new ValueTypePair(vtp[i][0].getPairValueAsDouble(), 1);
                vals[i * 20 + j][1] = new ValueTypePair(year, 1);
                vals[i * 20 + j][2] = new ValueTypePair(vtp[i][2].getPairValueAsDouble(), 0);
                year++;
            }
        }
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Grazing data and type values.
     * @return vals: ValueTypePair Array containing the calculated costs for the 
     *               grazing areas, as UnitCost * Grazing_Ha.
     */
    
    private static ValueTypePair[][] loadGrazing(ValueTypePair[][] vtp) {
        ValueTypePair[][] vals = new ValueTypePair[vtp.length][4];
        for(int i = 0; i < vtp.length; i++) {
            for(int j = 0; j < 3; j++) {
                vals[i][j] = new ValueTypePair(vtp[i][j].getPairValueAsDouble(), vtp[i][j].getPairType());
            }
            vals[i][3] = new ValueTypePair(vals[i][1].getPairValueAsDouble() * vals[i][2].getPairValueAsDouble(), 0);
        }
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Grazing Economic data and type values.
     * @param inA: Input Statement A used in calling the table in subGrz.
     * @param inB: Input Statement B used in calling the table in grzArea.
     * @param subGrz: Subbasin_grazing table name from Spatial.db3 as a String.
     * @param grzArea: grazing_area table name from Spatial.db3 as a String.
     * @return vals: ValueTypePair Array containing the calculated costs for the 
     *               grazing subbasin areas, calculated by the formula of 
     *               SUM(UnitCost * Area * Percentage) / SUM(Area * Percentage).
     *               Both of formulae are computed as the sums of these values.
     */
    
    private static ValueTypePair[][] loadGrazingSubbasinData(ValueTypePair[][] vtp, Statement inA, Statement inB, String subGrz, String grzArea) {
        ValueTypePair[][] vTemp = new ValueTypePair[vtp.length * 20][3];
        HashSet ids = new HashSet(), subs = new HashSet();
        int index = 0;
        for (ValueTypePair[] v : vtp) {
            ids.add(v[0].getPairValueAsInt());
        }
        try {
            ResultSet sRs = inA.executeQuery("SELECT * FROM " + subGrz + " ORDER BY grazing;");
            while(sRs.next()) {
                int subId = sRs.getInt(1);
                int grzId = sRs.getInt(2);
                if(ids.contains(grzId)) {
                    if(subs.contains(subId)) {
                    }
                    else {
                        subs.add(subId);
                        sRs = inA.executeQuery("SELECT * FROM " + subGrz + " WHERE subbasin = " + subId + ";");
                        int k = 0; 
                        IdValuePair[] iTempArea = new IdValuePair[vtp.length];
                        IdValuePair[] iTempPct = new IdValuePair[vtp.length];
                        while(sRs.next()) {
                            grzId = sRs.getInt(2);
                            //if(ids.contains(grzId)) {
                                ResultSet aRs = inB.executeQuery("SELECT * FROM " + grzArea + " WHERE id = " + grzId + ";");
                                iTempArea[k] = new IdValuePair(aRs.getInt(1), aRs.getDouble(2));
                                iTempPct[k] = new IdValuePair(sRs.getInt(2), sRs.getDouble(3));
                                k++;
                            //}
                            //else {
                           //     ResultSet aRs = inB.executeQuery("SELECT * FROM " + grzArea + " WHERE id = " + grzId + ";");
                           //     iTempArea[k] = new IdValuePair(aRs.getInt(1), aRs.getDouble(2));
                               //     iTempPct[k] = new IdValuePair(sRs.getInt(2), sRs.getDouble(3));
                           //     k++;
                            //}
                        }
                        IdValuePair[] grazeArea = new IdValuePair[k];
                        IdValuePair[] grazePct = new IdValuePair[k];
                        arraycopy(iTempArea, 0, grazeArea, 0, k);
                        arraycopy(iTempPct, 0, grazePct, 0, k);
                        double cost = calculateGrazingMeanCost(grazeArea, grazePct, vtp);
                        int year = 1991;
                        for(int i = 0; i < 20; i++) {
                            vTemp[index * 20 + i][0] = new ValueTypePair(subId, 1);
                            vTemp[index * 20 + i][1] = new ValueTypePair(year, 1);
                            vTemp[index * 20 + i][2] = new ValueTypePair(cost, 0);
                            year++;
                        }
                        index++;
                        sRs = inA.executeQuery("SELECT * FROM " + subGrz + " ORDER BY grazing;");
                    }
                }
            }
        } catch (SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        ValueTypePair[][] vals = new ValueTypePair[index * 20][3];
        arraycopy(vTemp, 0, vals, 0, vals.length);
        return vals;
    }
    
    /**
     * 
     * @param vtp: ValueTypePair Array containing the Grazing Economic data and type values.
     * @param in: Input Statement used in calling the table in hruGrz.
     * @param out: Output Statement used in calling the table in subGrz.
     * @param hruGrz: subbasin_grazing_hru table name from Spatial.db3 as a String.
     * @param subGrz: grazing_economic_subbasins table name from Output Database as a String.
     * @return vals: ValueTypePair Array containing the HRU indexes for the SWAT
     *               model for each grazing subbasin. The Array remains 2D for
     *               consistent use with the buildDbfTables method.
     */
    
    private static ValueTypePair[][] loadGrazingHruData(ValueTypePair[][] vtp, Statement in, Statement out, String hruGrz, String subGrz) {
        ValueTypePair[][] temp = new ValueTypePair[vtp.length][1];
        int length = 0;
        try {
            ResultSet sRs = out.executeQuery("SELECT DISTINCT id FROM " + subGrz + ";");
            int index = 0;
            while(sRs.next()) {
                ResultSet hRs = in.executeQuery("SELECT * FROM " + hruGrz + " WHERE subbasin = " + sRs.getInt(1) + ";");
                while(hRs.next()) {
                    temp[index][0] = new ValueTypePair(hRs.getInt(2), 1);
                    index++;
                }
            }
            length = index;
        } catch (SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        ValueTypePair[][] vals = new ValueTypePair[length][1];
        arraycopy(temp, 0, vals, 0, length);
        return vals;
    }
    
    /**
     * 
     * @param m: Embankment Value.
     * @param l: LifeTime Value.
     * @return Calculated Cost of Small Dam.
     */
    
    private static double calculateSmallDamCost(double m, double l) {
        return (4.87e-7 * pow(m, 3.0) - 4.24e-3 * pow(m, 2.0) + 1.28e1 * m + 6.71e3) / l;
    }
    
    /**
     * 
     * @param catl: Cattle value.
     * @param clay: Clay_Liner value.
     * @param trch: Trenching value.
     * @param dist: Distance value.
     * @param wire: Wire_Fence value.
     * @param plst: Plastic_Liner value.
     * @return Calculated Base Cost of Holding Pond.
     */
    
    private static double calculatePondBaseCost(double catl, double clay, double plst, double wire, double dist, double trch) {
        double sqrtC = sqrt(catl);
        double eqn1 = 2.232 * catl + 11.338 * sqrtC;
        double eqn2 = 3.72 * catl + trch * 7.94 * sqrtC + 0.844 * dist + clay * eqn1;

        double eqn3 = (0.5 * 9.5 + 7.47) * eqn1;

        double max = 1.38e-10 * pow(eqn2,2.0) - 5.027e-5 * eqn2 + 6.736 + clay * eqn3
        + plst / 0.7 * eqn3 + wire * (189.0 + sqrt(820.0 * catl)) + 10000.0;
        
        max *= 1.1483;

        eqn1 = 1.512 * catl + 9.332 * sqrtC;
        eqn2 = 2.52 * catl + trch * 6.54 * sqrtC + 0.844 * dist + clay * eqn1;
        eqn3 = (0.5 * 9.5 + 7.47) * eqn1;
        
        double min = 1.38e-10 * pow(eqn2, 2.0) - 5.027e-5 * eqn2 + 6.736 + clay * eqn3
        + plst / 0.7 * eqn3 + wire * (189.0 + sqrt(556.0 * catl)) + 10000.0;
        
        min *= 1.1483;

        return min / 2.0 + max / 2.0;
    }
    
    /**
     * 
     * @param catl: Cattle value.
     * @param wire: Wire_Fence value.
     * @return Calculated Maintenance Cost of Holding Pond.
     */
    
    private static double calculatePondMaintenanceCost(double catl, double wire) {
        double eqn = 0.03048 * pow(sqrt(1.68 * catl) - 6,2.0);

        double min = 1.38e-10 * pow(eqn, 2.0) - 5.027e-5 * eqn + 6.737
        + wire * (24.48 + 3.05 * sqrt(catl)) + 1.25 * catl;

        eqn = 0.03048 * pow(sqrt(2.48 * catl) - 6, 2.0);

        double max = 1.38e-10 * pow(eqn, 2.0) - 5.027e-5 * eqn + 6.737
        + wire * (24.48 + 3.71 * sqrt(catl)) + 1.85 * catl;

        return min / 2.0 + max / 2.0;
    }
    
    /**
     * 
     * @param ivpArea: IdValuePair Array containing the Grazing id and area values.
     * @param ivpPct: IdValuePair Array containing the Grazing id and Percent values.
     * @param vtp: ValueTypePair Array containing the Grazing Economic data and type values.
     * @return The Calculated value of the areaCostSum / areaSum. This formula is
     *         comprised of the Sum of the UnitCost of the Grazing area,
     *         times the Grazing area, times the Grazing area percentage, 
     *         over the Sum of the Grazing area times the Grazing area
     *         percentage.
     */
    
    private static double calculateGrazingMeanCost(IdValuePair[] ivpArea, IdValuePair[] ivpPct, ValueTypePair[][] vtp) {
        IdValuePair[] ivpCosts = new IdValuePair[ivpArea.length];
        double areaCostSum = 0.0, areaSum = 0.0;
        for(int i = 0; i < ivpArea.length; i++) {
            for (ValueTypePair[] v : vtp) {
                if (v[0].getPairValueAsInt() == ivpArea[i].getPairId()) {
                    ivpCosts[i] = new IdValuePair(ivpArea[i].getPairId(), v[2].getPairValueAsDouble());
                }
            }
            if(ivpCosts[i] == null) {
                ivpCosts[i] = new IdValuePair(ivpArea[i].getPairId(), 0.0);
            }
        }
        for(int i = 0; i < ivpCosts.length; i++) {
            areaCostSum += (ivpCosts[i].getPairValue() * ivpArea[i].getPairValue() * ivpPct[i].getPairValue());
            areaSum += ivpArea[i].getPairValue() * ivpPct[i].getPairValue();
        }
        return areaCostSum / areaSum;
    }
        
    /**
     * 
     * @param iRs: Input ResultSet containing the query result from the source table.
     * @param ntp: The NameTypePair array from getInputNamesAndTypes. 
     * @param s: String containing the SQL Query to be written to the output Table.
     * @param n: Starts building the Query String from the column n - 1 in ntp.
     * @return sql: The Query needed for the output statement.
     */
    
    private static String writeFieldOutputQuery(ResultSet iRs, NameTypePair[] ntp, String s, int n) {
        try {
            String sql = s;
            for(int i = n; i < iRs.getMetaData().getColumnCount(); i++) {
                if(ntp[i].getPairType() == 1) {
                    sql += iRs.getInt(ntp[i].getPairName());
                }
                else {
                    sql += iRs.getDouble(ntp[i].getPairName());
                }
                if(i == (iRs.getMetaData().getColumnCount() - 1)) {
                    sql += ");";
                }
                else {
                    sql += ", ";
                }
            }
            return sql;
        } catch(SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }
        
    /**
     * 
     * Optimize Function Calls for this Method later.
     * Break up loading Function and Writing Functions.
     * 
     * @param rsBmp: ResultSet for either the field_farm or field_subbasin
     *               SQL Table. Due to the same functions being needed for both
     *               the crop_economic_farms and crop_economic_subbasins SQL
     *               Tables, BMP is used as a common identifier in the function.
     * @param in: Input Statement Call.
     * @param tbl: yield_historic SQL Table.
     * @param ntp: The NameTypePair array from getInputNamesAndTypes.
     * @param s: String containing the SQL Query to be written to the output Table.
     * @param out: Output Statement Call.
     */
    
    private static void writeFarmSubbasinOutputQueries(ResultSet rsBmp, Statement in, String tbl, NameTypePair[] ntp, String s, Statement out, String bmp) {
        try {
            int multiField = 0;
            while(rsBmp.next()) {
                String sql = s;
                sql += rsBmp.getInt(2) + ", ";
                int fieldId = rsBmp.getInt(1);
                int bmpId = rsBmp.getInt(2);
                if(multiField == bmpId) {
                }
                else {
                    multiField = bmpId;
                    if(rsBmp.getDouble(3) == 1.0) {
                        ResultSet rsField = in.executeQuery("SELECT * FROM " + tbl + " WHERE field = " + fieldId + ";");
                        while(rsField.next()) {
                            out.executeUpdate(writeFieldOutputQuery(rsField, ntp, sql, 1));
                        }
                    }
                    else {
                        ResultSet rsField = in.executeQuery("SELECT * FROM " + rsBmp.getMetaData().getTableName(1) + " WHERE " + bmp + " = " + rsBmp.getInt(2) + ";");
                        IdValuePair[] ivp = null, temp = null;
                        int index = 0;
                        while(rsField.next()) {
                            if(ivp == null) {
                                ivp = new IdValuePair[1];
                                temp = new IdValuePair[1];
                                ivp[index] = new IdValuePair(rsField.getInt("field"), rsField.getDouble("percent"));
                                temp[index] = new IdValuePair();
                                index++;
                            }
                            else {
                                temp = new IdValuePair[ivp.length];
                                arraycopy(ivp, 0, temp, 0, temp.length);
                                ivp = new IdValuePair[temp.length + 1];
                                arraycopy(temp, 0, ivp, 0, temp.length);
                                ivp[index] = new IdValuePair(rsField.getInt("field"), rsField.getDouble("percent"));
                                index++;
                            }
                        }
                        double[][] bmpPercentTotals = new double[20][5];
                        for (IdValuePair ivp1 : ivp) {
                            int i = 0;
                            rsField = in.executeQuery("SELECT * FROM " + tbl + " WHERE field = " + ivp1.getPairId() + ";");
                            while(rsField.next()) {
                                try {
                                    for(int j = 1; j < rsField.getMetaData().getColumnCount(); j++) {
                                        if(j == 1) {
                                            bmpPercentTotals[i][j - 1] = rsField.getInt(ntp[j].getPairName());
                                        }
                                        else {
                                            bmpPercentTotals[i][j - 1] += rsField.getDouble(ntp[j].getPairName()) * ivp1.getPairValue();
                                        }

                                    }
                                } catch(SQLException e) {
                                    getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
                                }
                                i++;
                            }
                        }
                        for(int x = 0; x < 20; x++) {
                            String pctSql = sql;
                            for(int y = 0; y < 5; y++) {
                                if(y == 0) {
                                    int year = (int) bmpPercentTotals[x][y];
                                    pctSql += year + ", ";
                                }
                                else {
                                    if(y == 4) {
                                        pctSql += bmpPercentTotals[x][y] + ");";
                                    }
                                    else {
                                        pctSql += bmpPercentTotals[x][y] + ", ";
                                    }
                                }
                            }
                            out.executeUpdate(pctSql);
                        }
                    }
                }
            }
        } catch(SQLException e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    /**
     * 
     * @param src: Array containing the data values for the SQL query.
     * @param s: The partial SQL Query String.
     * @return sql: The complete SQL Query String.
     */
    
    private static String writeDbfOutputQueries(ValueTypePair[] src, String s) {
        String sql = s;
        try {
            for(int i = 0; i < src.length; i++) {
                if(i == src.length - 1) {
                    if(src[i].getPairValueAsInt() != -1) {
                        sql += src[i].getPairValueAsInt() + ")";
                    }
                    else {
                        sql += src[i].getPairValueAsDouble() + ")";
                    }
                }
                else {
                    if(src[i].getPairValueAsInt() != -1) {
                        sql += src[i].getPairValueAsInt() + ",";
                    }
                    else {
                        sql += src[i].getPairValueAsDouble() + ",";
                    }
                }
            }
            return sql;
        } catch(Exception  e) {
            getLogger(ScenarioBuilder.class.getName()).log(Level.SEVERE, null, e);
        }
        return "";
    }
    
}
