/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.serializable;

import java.io.Serializable;
import java.util.Calendar;
import server.components.Persoon;

/**
 *
 * @author Michael
 */
public class ChatBericht implements Serializable
{
    private String tekst;
    private Calendar datum;
    private transient Persoon persoon;
    private String name;

    public ChatBericht(String tekst, Persoon persoon) {
        this.tekst = tekst;
        this.persoon = persoon;
        this.datum = Calendar.getInstance();
        this.name = persoon.getGebruikersnaam();
    }

    public String getTekst() {
        return this.tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public Calendar getDatum() {
        return this.datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }

    public Persoon getPersoon() {
        return this.persoon;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(this.name).append(">").append(this.tekst);
                
        return sb.toString();
    }
}
