/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server.sql;

import server.data.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class HaalRatingOp extends DatabaseConnector implements Callable<Double> 
{
    private final String username;
    
    public HaalRatingOp(String username)       
    {
        super();
        this.username = username;
    }
    
    @Override
    public Double call()
    {
        try 
        {
            super.verbindmetDatabase();
        } 
        catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) 
        {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            String query = "SELECT score FROM rating WHERE GEBRUIKERSNAAM = ?";
            PreparedStatement prest = conn.prepareStatement(query);
            prest.setString(1, username);
            
            prest.execute();
            
            ResultSet res = prest.getResultSet();
            
                double result = res.getDouble("score");
                return result;
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
        finally
        {
            super.verbindingverbrekenmetDatabase();
            System.out.println("Verbinding verbroken met Database, Inlog");
        }

        return null;
    }
    
}
