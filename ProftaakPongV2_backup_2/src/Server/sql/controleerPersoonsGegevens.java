/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.sql;

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
public class controleerPersoonsGegevens extends DatabaseConnector implements Callable<Boolean> 
{
    private final String username;
    private final String wachtwoord;
    
    public controleerPersoonsGegevens(String username, String wachtwoord)       
    {
        super();
        this.username = username;
        this.wachtwoord = wachtwoord;
    }
    
    @Override
    public Boolean call()
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
            String query = "SELECT * FROM persoon WHERE GEBRUIKERSNAAM = ? AND WACHTWOORD = ?";
            PreparedStatement prest = conn.prepareStatement(query);
            prest.setString(1, wachtwoord);
            prest.setString(2, username);
            
            prest.execute();
            
            ResultSet res = prest.getResultSet();
            
            while(res.next())
            {
                String wachtwoordResult = res.getString("wachtwoord");
                String gebruikersResult = res.getString("gebruikersNaam");
                
                System.out.println("User: " + gebruikersResult + " logged in!");
                
                if(wachtwoordResult.equals(""))
                {
                    return false;
                }
                if(gebruikersResult.equals(""))
                {
                    return false;
                }
                if(wachtwoord.equals(wachtwoordResult))
                {
                    System.out.println("Inlog Succesvol");
                    return true;
                }
                else
                {
                    return false;
                }
            }
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

        return false;
    }
    
}
