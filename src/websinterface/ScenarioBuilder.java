package websinterface;

import java.sql.*;

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
 * 
 */

public class ScenarioBuilder {
    
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:spatial.db3");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM YIELD_HISTORIC;");
            while(rs.next()) {
                int field = rs.getInt("field");
                int year = rs.getInt("year");
                double yield = rs.getDouble("yield");
                double revenue = rs.getDouble("revenue");
                double cost = rs.getDouble("cost");
                double net = rs.getDouble("netreturn");
                System.out.println("FIELD = " + field);
                System.out.println("YEAR = " + year);
                System.out.println("YIELD = " + yield);
                System.out.println("REVENUE = " + revenue);
                System.out.println("COST = " + cost);
                System.out.println("NET RETURN = " + net);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
    }
}
