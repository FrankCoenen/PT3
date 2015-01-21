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
    /**
     * de variabelen met een logische naam
     */
    private List<ChatBericht> berichten;
    private ObservableList<ChatBericht> observerList;
    private Client cient;
    private static final String PROPERTY = "berichten";
    private String type;
            
    /**
     * constructor
     * @param client de client van de gebruiker
     * @param rp remotepublisher
     * @param type type chatboxlistener
     * @throws RemoteException 
     */
    public ChatBoxListener(Client client, RemotePublisher rp, String type) throws RemoteException
    {
        berichten = new ArrayList();
        observerList = FXCollections.observableArrayList(berichten);     
        rp.addListener(this, PROPERTY);
        if(type.equals("lobby"))
        {
            client.updateLobbyChatBox(observerList);
        }
        else
        {
            client.updateGameChatBox(observerList);
        }
        
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
