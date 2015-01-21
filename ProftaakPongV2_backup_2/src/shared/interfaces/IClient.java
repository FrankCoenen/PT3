/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package shared.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import server.components.game.LineGoal;
import server.components.game.LineSide;
import server.components.game.Speelveld;

/**
 *
 * @author Merijn
 */
public interface IClient extends Remote
{
    /**
     * Met deze methode update je de spelerslijst. Dit wordt gedaan zodra een nieuwe speler inlogd.
     *
     * @param spelers De methode krijgt een lijst met spelers mee.
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel
     */
    public void updatePlayerList(List<String> spelers) throws RemoteException;
    /**
     * Deze methode update de lobbylijst voor alle mensen die in de hoofdlobby zit. Deze wordt ook
     * elke keer aangeroepen als er een nieuwe lobby bij komt.
     * @param gameLobbys Deze methode krijgt de lijst met gamelobbys mee.
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public void updateGameLobbyList(List<String> gameLobbys) throws RemoteException;
    /**
     * Deze methode update de gamelobby player lijst deze wordt elke keer aangeroepen als een
     * nieuwe speler een lobby joint. dan worden de nieuwe spelers erin gezet.
     * @param gameLobbySpelers Deze methode krijgt een string list mee van de naam gamelobbyspelers
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public void updateGameLobbyPlayers(List<String> gameLobbySpelers) throws RemoteException;
    /**
     * Met deze methode wordt de client gepinged vanuit de server als de ping aankomt wordt het true en dan 
     * weet de server dat die nog verbinding heeft.
     * @return -
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public boolean ping() throws RemoteException;
    /**
     * Met deze methode wordt de gameclient gestart hierdoor komt de speler in de gameclient terecht/
     * @param game De game wordt meegeven. DOordat ze deze krijgen kunnen ze de game starten
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public void startGameClient(IGame game) throws RemoteException;
    /**
     * Met deze methode  wordt het speelveld 25/sec doorgegeven. De positie van het batje en de bal wordt doorgegeven
     * dat doen we door dubbels door te geven. deze worden dan naar de server doorgestuurd. deze krijgt de 
     * informatie binnen en stuurt ze vervolgens weer door naar de andere clients.
     * @param speelveld Het speelveld wordt meegegeven zodat deze op de client wordt getekend en je kan spelen
     * @throws RemoteException een exception voor als er iets fout gaat met het remote deel.
     */
    public void updateSpeelveld(Speelveld speelveld) throws RemoteException;
    /**
     * Deze methode tekent de lijnen van het speelveld. Dit wordt gewoon op de client gedaan want 
     * bij iedereen is het veld hetzelfde. Hoe deze lijnen worden getekent is afhankelijk van hoe groot
     * iemands scherm is. Het tekenen van de lijnen wordt dus automatisch gedaan afhankelijk van hoe groot het
     * scherm van de speler is. Doordat we dit zo doen kan het op verschillende schermgroottes worden gespeeld.
     * @param lines Dit zijn de posities van de lijnen  en waar deze getekend moeten worden.Deze scalen ook automatisch mee
     * met de grootte van het scherm
     * @param goals Dit zijn de posities van de goals en waar deze getekend moeten worden. Deze scalen ook automatisch mee
     * met de grootte van het scherm
     * @throws RemoteException 
     */
    public void setLines(LineSide[] lines, LineGoal[] goals) throws RemoteException;
}
