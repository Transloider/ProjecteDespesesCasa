package com.projectedespeses.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe per a gestionar la connexió amb la base de dades.
 */
public class DBConnection {
    public Connection connection;
    private String dataBase = "DespesesCasa.db";

    /**
     * Constructor de la classe DBConnection.
     */
    public DBConnection() {        
    }

    /**
     * Estableix la connexió amb la base de dades.
     */
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            //DriverManager.registerDriver(new org.sqlite.JDBC()); // posem uno o l'altre
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataBase);
            System.out.println("Connexió correcta");
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut connectar. " + ex.toString());
        } catch (ClassNotFoundException ex){
            System.out.println("No s'ha pogut carregar el driver" + ex.toString());
        }
    }

    /**
     * Tanca la connexió amb la base de dades.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("No s'ha pogut tancar la connexió. " + ex.toString());
        }
    }
}
