/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communications;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import shared.observer.RemotePropertyListener;
import shared.observer.RemotePublisher;

/**
 *
 * @author Merijn
 */
public class ChatBoxListener implements RemotePropertyListener {
    
    public ChatBoxListener()
    {
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
