package task14.hexaware.bs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3310/hmbank"; 
    private static final String USER = "root";
    private static final String PASSWORD = "22647";

    public static Connection getDBConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return conn;
    }
} 

