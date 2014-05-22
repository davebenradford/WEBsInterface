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

public class scenarioBuilder {
    
    public static void main( String args[] ) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch ( ClassNotFoundException | SQLException e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
