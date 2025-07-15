/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PC
 */
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
