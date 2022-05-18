package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Handy utilities for dealing with databases
 */
public class DatabaseUtils
{
    //JDBC driver class
    public static String DB_DRIVER;
    //database URL
    public static String DB_URL;
    //username to access database
    public static String USER;
    //password to access database
    public static String PASSWORD;

    //read a .properties file to load database credentials
    public static void loadDatabaseProperties() throws IOException
    {
        Properties dbProps = new Properties();
        FileInputStream in = new FileInputStream("database.properties");
        dbProps.load(in);

        DB_DRIVER = (String) dbProps.get("driver");
        DB_URL = (String) dbProps.get("url");
        USER = (String) dbProps.get("user");
        PASSWORD = (String) dbProps.get("password");
    }

    //open a new connection using DriverManager
    public static Connection createConnection()
            throws ClassNotFoundException, SQLException
    {
        //register JDBC driver
        Class.forName(DB_DRIVER);
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    /**
     * close a connection to the database
     * to avoid memory leaks
     * @param connection the Connection to clean up
     */
    public static void close(Connection connection)
    {
        try
        {
            if (connection != null)
                connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * close a Statement to avoid memory leaks
     * @param st the Statement to clean up
     */
    public static void close(Statement st)
    {
        try
        {
            if (st != null)
                st.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * close a ResultSet to avoid memory leaks
     * @param rs the ResultSet to clean up
     */
    public static void close(ResultSet rs)
    {
        try
        {
            if (rs != null)
                rs.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}