/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Domain.game;

import javafx.scene.paint.Color;


/**
 *
 * @author Merijn
 */
public class AI extends Speler {
    
    private final Bal bal;
    private boolean difrentMove;
    private int difrentCount;
    
    public AI(String gebruikersNaam, Bal bal)
    {
        super(gebruikersNaam);
        this.bal = bal;
        difrentMove = false;
        
    }

    //HIER GAAT SHIT FOUT 
    public void moveBatje() 
    {
        //HIER GAAT SHIT FOUT!!
        if(super.getBatje().getKleur() == Color.BLUE)
        {
            if (difrentMove == false && Math.random() < 0.01)
            {
                this.difrentMove = true;
                this.difrentCount = 15;
            }
            else
            {
                this.difrentCount--;
                if(this.difrentCount == 0)
                {
                    difrentMove = false;
                }
            }
            
            if(super.getBatje().getYPos() > bal.getYPos() )
            {
                System.out.println("BLUE -> Move Left");
                if(!difrentMove)
                {
                    super.getBatje().moveLeft();
                }
                else
                {
                    super.getBatje().moveRight();
                }
            }
            else
            {
                System.out.println("BLUE -> Move Right");
                if(!difrentMove)
                {
                    super.getBatje().moveRight();
                }
                else
                {
                    super.getBatje().moveLeft();
                }
            }
        }
        else
        {
            if (difrentMove == false && Math.random() < 0.01)
            {
                this.difrentMove = true;
                this.difrentCount = 15;
            }
            else
            {
                this.difrentCount--;
                if(this.difrentCount == 0)
                {
                    difrentMove = false;
                }
            }
            
            if(super.getBatje().getYPos() < bal.getYPos() )
            {
                System.out.println("BLUE -> Move Left");
                if(!difrentMove)
                {
                    super.getBatje().moveLeft();
                }
                else
                {
                    super.getBatje().moveRight();
                }
            }
            else
            {
                System.out.println("BLUE -> Move Right");
                if(!difrentMove)
                {
                    super.getBatje().moveRight();
                }
                else
                {
                    super.getBatje().moveLeft();
                }
            }
        }
    }
    
}
