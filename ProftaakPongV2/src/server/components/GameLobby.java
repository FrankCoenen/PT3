/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Merijn
 */
public class GameLobby {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Persoon> getSpelers() {
        return spelers;
    }

    public void addSpelers(Persoon spelers) {
        this.spelers.add(spelers);
    }

    public int getSpotsLeft() {
        return spotsLeft;
    }

    public void setSpotsLeft(int spotsLeft) {
        this.spotsLeft = spotsLeft;
    }

    public Boolean isHasStarted() {
        return hasStarted;
    }

    public void setHasStarted(Boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

    public List<Persoon> getSpectators() {
        return spectators;
    }

    public void addSpectators(Persoon spectators) {
        this.spectators.add(spectators);
    }
    private List<Persoon> spelers;
    private int spotsLeft;
    private Boolean hasStarted;
    private List<Persoon> spectators;
    
    public GameLobby(Persoon creator)
    {
        this.name = creator.getGebruikersnaam() + "'s Game";
        this.spectators = new ArrayList();
        this.spelers = new ArrayList();
        this.spotsLeft = 2;
        this.hasStarted=false;
        this.spelers.add(creator);
    }
}
