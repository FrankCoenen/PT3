/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communications;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.List;
import shared.observer.RemotePropertyListener;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Merijn
 */
public class ChatBoxListener implements RemotePropertyListener {
    
    private List<ChatBericht> berichten;
            
    public ChatBoxListener()
    {
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException 
    {
        this.berichten = (List<ChatBericht>) evt.getNewValue();
    }
    
    public List<ChatBericht> getBerichten()
    {
        return this.berichten;
    }
}
