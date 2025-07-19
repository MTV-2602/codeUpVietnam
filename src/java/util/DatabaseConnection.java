
package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
     public static Connection initializeDatabase() throws Exception {
        String jdbcURL = "jdbc:sqlserver://localhost:1433;databaseName=CodeUpVietnam";
        String jdbcUsername = "sa";
        String jdbcPassword = "12345";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
     
    public static void main(String[] args) {
        try {
            initializeDatabase();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }
    }
    
    
}
