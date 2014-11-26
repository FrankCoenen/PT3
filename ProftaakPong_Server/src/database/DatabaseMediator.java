/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import classes.Persoon;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

/**
 *
 * @author Michael
 */
public class DatabaseMediator 
{
    private Connection conn;
    
    public DatabaseMediator() 
    {
        try {
            this.verbindmetDatabase();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    private void verbindmetDatabase() throws ClassNotFoundException, InstantiationException, SQLException, IllegalAccessException
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
    
    private void verbindingverbrekenmetDatabase() 
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
    
    public void close()
    {
        this.verbindingverbrekenmetDatabase();
    }
    
    public ArrayList<Persoon> getPersonen()
    {
        ArrayList<Persoon> personen = new ArrayList<>();
        
        try 
        {
            this.verbindmetDatabase();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            Statement statement = conn.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM personen");
            
            while(res.next())
            {
                String gebruikersNaam = res.getString("gebruikersNaam");
                
                Persoon p = new Persoon(gebruikersNaam);
                
                personen.add(p);
            }
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
        
        return personen;
    }
    
    public boolean controleerPersoonsGegevens(String username, String password)
    {
        try 
        {
            this.verbindmetDatabase();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try
        {
            String query = "SELECT * FROM persoon WHERE GEBRUIKERSNAAM = ? AND WACHTWOORD = ?";
            PreparedStatement prest = conn.prepareStatement(query);
            prest.setString(1, password);
            prest.setString(2, username);
            
            prest.execute();
            
            ResultSet res = prest.getResultSet();
            
            while(res.next())
            {
                String wachtwoordResult = res.getString("wachtwoord");
                String gebruikersResult = res.getString("gebruikersNaam");
                
                if(wachtwoordResult.equals(""))
                {
                    return false;
                }
                if(gebruikersResult.equals(""))
                {
                    return false;
                }
                if(password.equals(wachtwoordResult))
                {
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
        
        return false;
    }
    
    public boolean registreerPersoonsGegevens(String username, String wachtwoord)
    {
        try 
        {
            this.verbindmetDatabase();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseMediator.class.getName()).log(Level.SEVERE, null, ex);
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
            
            ResultSet res = prest.getResultSet();
            
            prest.execute();
            
        }
        catch(SQLException e)
        {
            e.getMessage();
        }
        
        return false;
    }
    
    
}
