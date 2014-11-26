/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
// * and open the template in the editor.
 */

package classes;

/**
 *
 * @author Michael
 */
public class GameLobby 
{
    private String naam;
    private ChatBox chatbox;
    private GameEigenaar gameeigenaar;
    private Speler[] spelers;
    private AI[] ai;
    private Toeschouwer[] toeschouwers;
    //private Game game;
    
    public GameLobby(String naam, GameEigenaar gameeigenaar)
    {
        this.naam = naam;
        this.gameeigenaar = gameeigenaar;
        spelers = new Speler[3];
        ai = new AI[2];
    }
    
//    public Game getGame()
//    {
//        return this.game;
//    }
    
    public String getNaam()
    {
        return this.naam;
    }
    
    public void setNaam(String naam)
    {
        this.naam = naam;
    }   
    
    public Speler[] getSpelers()
    {
        return this.spelers;
    }
    
    public void setSpelers(Speler[] spelers)
    {
        this.spelers = spelers;
    }
    
    public Toeschouwer[] getToeschouwers()
    {
        return this.toeschouwers;
    }
    
    public void setToeschouwers(Toeschouwer[] toeschouwers)
    {
        this.toeschouwers = toeschouwers;
    }
    
    public ChatBox getChatbox()
    {
        return this.chatbox;
    }
    
    public void setChatbox(ChatBox chatbox)
    {
        this.chatbox = chatbox;
    }
    
    public void startGame()
    {
        spelers[0] = gameeigenaar;
        this.createAI1();
        this.createAI2();
        //game = new Game(this.spelers, this.ai);
    }
    
    public void createAI1()
    {
        Persoon p = new Persoon("AI1");
        Speler ps = p.omzettenSpeler();
        //AI psa = ps.omzettenAI(EMoeilijkheidsgraad.GEMIDDELD);
        //ai[0] = psa;
    }
    
    public void createAI2()
    {
        Persoon p = new Persoon("AI2");
        Speler ps = p.omzettenSpeler();
        //AI psa = ps.omzettenAI(EMoeilijkheidsgraad.GEMIDDELD);
        //ai[1] = psa;
    }
}
