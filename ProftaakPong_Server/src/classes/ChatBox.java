/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package classes;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 *
 * @author Michael
 */
public class ChatBox
{
    private ObservableList<ChatBericht> observableBerichten;
    private List<ChatBericht> berichten;
    private int maxberichten;
    
    public ChatBox()
    {
        maxberichten = 100;
        berichten = new ArrayList<ChatBericht>();
        observableBerichten = FXCollections.observableList(berichten);     
    }
    
    public ObservableList<ChatBericht> getBerichten()
    {
        return (ObservableList<ChatBericht>) FXCollections.unmodifiableObservableList(observableBerichten);
    }
        
    public void addBericht(ChatBericht c)
    {
        if(maxberichten == 0)
        {
            berichten.remove(10);
        }
        
        observableBerichten.add(c);
        System.out.println("Bericht geplaatst in lijst");
        System.out.println("Bericht: " + c.getTekst());
        maxberichten--;
    }
    
    public int getListCount()
    {
        return berichten.size();
    }
}
