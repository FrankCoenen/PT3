/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.sql;

import server.data.DatabaseConnector;
import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michael
 */
public class LeaderBoardOphalen extends DatabaseConnector implements Callable<ArrayList<String[]>> {

    private final String username;
    ArrayList<String[]> mainArray;
    String[] swagg = new String[2];

    public LeaderBoardOphalen(String username) {
        super();
        this.username = username;
    }

    @Override
    public ArrayList<String[]> call() {
        try {
            super.verbindmetDatabase();
        } catch (ClassNotFoundException | InstantiationException | SQLException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            String query = "SELECT gebruikersnaam, score FROM rating ORDER BY score DESC";
            PreparedStatement prest = conn.prepareStatement(query);

            prest.execute();

            ResultSet res = prest.getResultSet();

            while (res.next()) {
                mainArray.add(new String[]{res.getString("gebruikersnaam"), Double.toString(res.getDouble("score"))});
            }
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            super.verbindingverbrekenmetDatabase();
            System.out.println("Verbinding verbroken met Database, Inlog");
        }

        return mainArray;
    }

}
