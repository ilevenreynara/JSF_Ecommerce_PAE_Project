/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ileven
 */
public class MyConnection {
    static Connection con;
    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce", "root","");
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }
}
