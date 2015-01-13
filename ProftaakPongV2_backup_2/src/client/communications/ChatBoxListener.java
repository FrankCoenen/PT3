/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.communications;

import client.components.Client;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import shared.observer.RemotePropertyListener;
import shared.observer.RemotePublisher;
import shared.serializable.ChatBericht;



/**
 *
 * @author Merijn
 */
public class ChatBoxListener extends UnicastRemoteObject implements RemotePropertyListener 
{
    
    private List<ChatBericht> berichten;
    private ObservableList<ChatBericht> observerList;
    private Client cient;
    private static final String PROPERTY = "berichten";
            
    public ChatBoxListener(Client client, RemotePublisher rp) throws RemoteException
    {
        berichten = new ArrayList();
        observerList = FXCollections.observableArrayList(berichten);     
        rp.addListener(this, PROPERTY);
        client.updateLobbyChatBox(observerList);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                observerList.setAll((List<ChatBericht>) evt.getNewValue());
            }
        });

    }
}
