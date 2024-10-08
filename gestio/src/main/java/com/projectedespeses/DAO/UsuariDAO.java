package com.projectedespeses.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

public class UsuariDAO extends DBConnection implements DAO<Usuari, Integer> {
    private final String INSERT = "INSERT INTO usuaris(nom,contrasenya) VALUES(?,?)";
    private final String UPDATE = "UPDATE usuaris SET nom=?, contrasenya=? WHERE idUsuari=?";
    // private final String DELETE = "DELETE FROM USUARI WHERE id=?";
    // private final String SELECTBYID = "SELECT * FROM usuaris WHERE idUsuari=?";
    private final String SELECTALL = "SELECT * FROM usuaris";
    private final String SELECTUSUARI = "SELECT * FROM usuaris WHERE nom=? and contrasenya=?";
    private MovimentDAO movimentDAO = new MovimentDAO();

    /**
     * Insereix un usuari a la base de dades.
     * 
     * @param t Usuari a inserir.
     */
    @Override
    public void insert(Usuari t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPassword());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuari inserit correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Actualitza les dades d'un usuari a la base de dades.
     * 
     * @param t Usuari a actualitzar.
     */
    @Override
    public void update(Usuari t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNom());
            ps.setString(2, t.getPassword());
            ps.setInt(3, t.getId_usuari());
            if (ps.executeUpdate() != 0) {
                System.out.println("Usuari modificat correctament en BBDD.");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Elimina un usuari de la base de dades.
     * 
     * @param t Usuari a eliminar.
     */
    @Override
    public void delete(Usuari t) {
        // Mètode no implementat.
    }

    /**
     * Selecciona un usuari de la base de dades per nom i contrasenya.
     * 
     * @param usuari Usuari amb les dades de nom i contrasenya per a la cerca.
     * @return Usuari trobat a la base de dades, si existeix.
     */
    public Usuari selectUsuariByNomContra(Usuari usuari) {
        Usuari newUser = null;
        ArrayList<Moviments> m = new ArrayList<Moviments>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTUSUARI);
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUsuari = rs.getInt("idUsuari");
                String nom = rs.getString("nom");
                String passowrd = rs.getString("contrasenya");
                newUser = new Usuari(nom, passowrd);
                newUser.setId_usuari(idUsuari);
            }
            m = movimentDAO.selectMoviments(newUser.getId_usuari());
            newUser.setMoviment(m);
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newUser;
    }

    /**
     * Selecciona tots els usuaris de la base de dades.
     * 
     * @return Llista de tots els usuaris de la base de dades.
     */
    @Override
    public List<Usuari> selectAll() {
        ArrayList<Usuari> usuaris = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idUsuari = rs.getInt("idUsuari");
                String correu = rs.getString("nom");
                String passowrd = rs.getString("contrasenya");
                Usuari usuari = new Usuari(correu, passowrd);
                // DAOmOVIMENT SELECT ALLBYID
                usuari.setId_usuari(idUsuari);
                usuaris.add(usuari);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return usuaris;
    }

    /**
     * Selecciona un usuari de la base de dades per identificador.
     * 
     * @param id Identificador de l'usuari.
     * @return Usuari trobat a la base de dades, si existeix.
     */
    @Override
    public Usuari selectById(Integer id) {
        // Mètode no implementat.
        throw new UnsupportedOperationException("Mètode 'selectById' no implementat");
    }
}
