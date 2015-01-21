/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.sql;

import server.data.DatabaseConnector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class BerekenRatingOp extends DatabaseConnector implements Callable<Boolean> {

    /**
     * variabelen met een logische naam
     */
    private final String username;
    private final int newRonde;

    /**
     * constructor
     * @param username de naam van de gebruiker
     * @param NewRonde de nieuwste ronde die wordt ingevoerd in de DB
     */
    public BerekenRatingOp(String username, int NewRonde) {
        super();
        this.username = username;
        this.newRonde = NewRonde;
    }

    @Override
    public Boolean call() {
        try {
            super.verbindmetDatabase();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            
            String query = "SELECT * FROM score WHERE GEBRUIKERSNAAM = ?";
            PreparedStatement prest = conn.prepareStatement(query);
            prest.setString(1, username);

            prest.execute();

            ResultSet res = prest.getResultSet();

            double scoreResult = 0.0;
            int ronde2Result = 0;
            int ronde3Result = 0;
            int ronde4Result = 0;
            int ronde5Result = 0;

            while (res.next()) {
                DecimalFormat dec = new DecimalFormat("#.#");
                scoreResult = 0.0;
                ronde2Result = res.getInt("round1");
                ronde3Result = res.getInt("round2");
                ronde4Result = res.getInt("round3");
                ronde5Result = res.getInt("round4");

                scoreResult = ((5 * newRonde) + (4 * ronde2Result) + (3 * ronde3Result) + (2 * ronde4Result) + (1 * ronde5Result)) / 15;
                dec.format(scoreResult);
            }

            String query2 = "UPDATE score SET score = ?,round1 = ?, round2 = ?, round3 = ?, round4 = ?, round5 = ? WHERE GEBRUIKERSNAAM = ?";
            PreparedStatement prest2 = conn.prepareStatement(query2);
            prest2.setDouble(1,scoreResult);
            prest2.setDouble(2, newRonde);
            prest2.setInt(3, ronde2Result);
            prest2.setInt(4, ronde3Result);
            prest2.setInt(5, ronde4Result);
            prest2.setInt(6, ronde5Result);
            prest2.setString(7, username);
            
            prest2.execute();

        } catch (SQLException e) {
            e.getMessage();
        } finally {
            super.verbindingverbrekenmetDatabase();
            System.out.println("Verbinding verbroken met Database, Inlog");
        }

        return false;
    }

}
