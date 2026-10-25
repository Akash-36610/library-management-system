package source.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileInputStream;
import java.util.Properties;

public class DBConnection
{   
    private static String url;
    private static String user;
    private static String password;

    static
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

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

    public static Connection getConnection() throws SQLException 
    {
        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection(Connection connect,Statement stmt) throws SQLException 
    {

        if( stmt != null) stmt.close();   
        if(connect != null) connect.close();

    }

    public static void closeConnection(Connection connect,PreparedStatement pstmt) throws SQLException 
    {

        if( pstmt != null) pstmt.close();   
        if(connect != null) connect.close();
        
    }

    public static void closeResultSet(ResultSet rs) throws SQLException
    {
        if(rs != null) rs.close();
    }
}
