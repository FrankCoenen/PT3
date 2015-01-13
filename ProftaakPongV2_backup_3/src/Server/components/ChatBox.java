/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package server.components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.observer.BasicPublisher;
import shared.observer.RemotePropertyListener;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;

/**
 *
 * @author Michael
 */
public class ChatBox extends UnicastRemoteObject implements RemotePublisher
{
    private List<ChatBericht> berichten;
    private static final int MAXBERICHTEN = 100;
    private static final int DELETE = 10;
    private BasicPublisher publisher;
    private static final String[] PROPERTYS = new String[] {"berichten"};
    
    public ChatBox() throws RemoteException
    {
        publisher = new BasicPublisher(PROPERTYS);
        berichten = new ArrayList<ChatBericht>();  
    }
        
    public synchronized void addBericht(ChatBericht c)
    {
        if(berichten.size() > MAXBERICHTEN)
        {
           berichten.removeAll(berichten.subList(0, DELETE - 1));
        }
        berichten.add(c);
        
        publisher.inform(this, "berichten", null, berichten);
    }
    
    public int getListCount()
    {
        return berichten.size();
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException 
    {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException 
    {
        publisher.removeListener(listener, property);
    }
}
