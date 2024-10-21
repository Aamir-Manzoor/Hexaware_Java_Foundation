
/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (DBConnUtil)
 * Date: 21/10/2024
 */

package com.hexaware.cc.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.hexaware.cc.exception.DatabaseConnectionException;

public class DBConnUtil {
    private static Connection connection;

    public static Connection getConnection() throws DatabaseConnectionException {
        if (connection == null) {
            try {
                FileReader reader = new FileReader("resources/DBConfig.properties");
                Properties prop = new Properties();
                prop.load(reader);

                String driver = prop.getProperty("driver.classname");
                String url = prop.getProperty("url");
                String username = prop.getProperty("username");
                String password = prop.getProperty("password");

                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                throw new DatabaseConnectionException("Database driver not found: " + e.getMessage(), e);
            } catch (SQLException e) {
                throw new DatabaseConnectionException("Failed to establish database connection: " + e.getMessage(), e);
            } catch (IOException e) {
                throw new DatabaseConnectionException("Error reading database configuration file: " + e.getMessage(), e);
            }
        }
        return connection;
    }
}