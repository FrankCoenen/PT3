/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain.game;

/**
 *
 * @author Michael
 */
public class Persoon 
{
    protected String wachtwoord;
    protected String gebruikersNaam;
    protected int rating;
    
    public Persoon(String gebruikersNaam)
    {
        this.gebruikersNaam = gebruikersNaam;
    }
    
    public Persoon(String gebruikersNaam, String wachtwoord)
    {
        this.gebruikersNaam = gebruikersNaam;
        this.wachtwoord = wachtwoord;
    }
    
    public String getWachtwoord()
    {
        return this.wachtwoord;
    }   
    
    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }   
    
    public String getGebruikersnaam()
    {
        return this.gebruikersNaam;
    }
    
    public void setGebruikersnaam(String gebruikersNaam)
    {
        this.gebruikersNaam = gebruikersNaam;
    }
    
    public int getRating()
    {
        return this.rating;
    }
    
    public void setRating(int rating)
    {
        this.rating = rating;
    }
    
    public Toeschouwer omzettenToeschouwer()
    {
        Toeschouwer nieuw = new Toeschouwer(this.gebruikersNaam);
        return nieuw;
    }
    
    public Speler omzettenSpeler()
    {
        Speler nieuw = new Speler(this.gebruikersNaam);
        return nieuw;
    }
    
    
}
