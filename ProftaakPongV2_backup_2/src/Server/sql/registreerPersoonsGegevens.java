/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Server.sql;

import Server.data.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class registreerPersoonsGegevens extends DatabaseConnector implements Runnable
{
    private final String username;
    private final String wachtwoord;
    
    public registreerPersoonsGegevens(String username, String wachtwoord)
    {
        super();
        this.username = username;
        this.wachtwoord = wachtwoord;
    }

    @Override
    public void run() 
    {
        try 
        {
            super.verbindmetDatabase();
        } 
        catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            String query = "INSERT INTO persoon (GEBRUIKERSNAAM, WACHTWOORD, RATING)" 
                    + " values (?,?,?)";
            String Invoerwachtwoord = wachtwoord;
            String Invoergebruikersnaam = username;
            int rating = 0;
            PreparedStatement prest = conn.prepareStatement(query);
            prest.setString(1, Invoergebruikersnaam);
            prest.setString(2, Invoerwachtwoord);
            prest.setInt(3, rating);
            
            prest.execute();
        }
        catch(SQLException e)
        {
            e.getMessage();
            System.out.println("db fout");
        }
        finally
        {
            super.verbindingverbrekenmetDatabase();
        }
    }   

}
