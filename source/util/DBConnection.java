package source.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Utility class responsible for managing database connections.
 * Loads database configuration from the properties file and
 * provides methods to create and close database resources.
 */
public class DBConnection
{
    // Database URL
    private static String url;

    // Database username
    private static String user;

    // Database password
    private static String password;

    /**
     * Static initialization block that loads the JDBC driver
     * and reads database configuration from config.properties.
     */
    static
    {
        try
        {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Load database configuration properties
            Properties prop = new Properties();

            prop.load(new FileInputStream("config.properties"));

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
        }
        catch (Exception e)
        {
            ErrorUtil.printError("Load Database Configuration", e);
        }
    }

    /**
     * Creates and returns a new database connection.
     *
     * @return Active database connection
     * @throws SQLException If a database access error occurs
     */
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Closes the given Statement and Connection objects.
     *
     * @param connect Database connection to close
     * @param stmt SQL statement to close
     * @throws SQLException If an error occurs while closing resources
     */
    public static void closeConnection(Connection connect,
                                       Statement stmt) throws SQLException
    {
        if (stmt != null)
        {
            stmt.close();
        }

        if (connect != null)
        {
            connect.close();
        }
    }

    /**
     * Closes the given PreparedStatement and Connection objects.
     *
     * @param connect Database connection to close
     * @param pstmt Prepared statement to close
     * @throws SQLException If an error occurs while closing resources
     */
    public static void closeConnection(Connection connect,
                                       PreparedStatement pstmt) throws SQLException
    {
        if (pstmt != null)
        {
            pstmt.close();
        }

        if (connect != null)
        {
            connect.close();
        }
    }
}