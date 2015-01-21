/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.data;


import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author Michael
 */
public class DatabaseConnector  
{
    /**
     * connectie variabelen
     */
    protected Connection conn;
    /**
     * constructor
     */
    protected DatabaseConnector() 
    {
        try {
            this.verbindmetDatabase();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       /**
        * hier maken wij een verbinding in de database
        * @throws ClassNotFoundException exeptions
        * @throws InstantiationException exeptions
        * @throws SQLException exeptions
        * @throws IllegalAccessException exeptions
        */
    protected void verbindmetDatabase() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException
    {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "game";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root"; 
        String password = "admin";
        
        Class.forName(driver).newInstance();
        conn = DriverManager.getConnection(url+dbName,userName,password);
        
        if (conn != null) {
		System.out.println("You made it, take control your database now!");
	} else {
		System.out.println("Failed to make connection!");
	}
    }
    /**
     * methode voor het verbreken van de database
     */
    protected void verbindingverbrekenmetDatabase() 
    {
        try 
        {
            conn.close();
            conn = null;
        } 
        catch (SQLException ex) 
        {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * sluiten van de DBconnectie
     */
    public void close()
    {
        this.verbindingverbrekenmetDatabase();
    }

    
    
}
