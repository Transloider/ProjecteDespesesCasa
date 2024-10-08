package com.projectedespeses.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.model.Moviments;

/**
 * Classe que gestiona l'accés a dades relacionades amb els moviments.
 */
public class MovimentDAO extends DBConnection implements DAO<Moviments, Integer>{
    private final String INSERT = "INSERT INTO moviments(idUsuari,tipusMoviment) VALUES(?,?)";
    // private final String UPDATE = "UPDATE moviments SET idMoviment=?, idUsuari=? WHERE idUsuari=?";
    private final String DELETE = "DELETE FROM moviments WHERE idMoviment=?";
    private final String SELECTBYID = "SELECT idMoviment, tipusMoviment FROM moviments WHERE idUsuari=?";
    private final String SELECTMOVIMENTSBYUSER = "SELECT MAX(idMoviment) FROM moviments";
    private final String SELECTMOVIMENTSBYUSERLIST = "SELECT idMoviment,tipusMoviment FROM moviments WHERE idUsuari = ?";
    private final String SELECTALL = "SELECT * FROM moviments";
    private final String SELECTMOVIMENTBYID = "SELECT idMoviment,tipusMoviment FROM moviments WHERE idMoviment = ?";

    /**
     * Insereix un moviment a la base de dades.
     * 
     * @param t L'objecte Moviments a inserir.
     */
    @Override
    public void insert(Moviments t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setInt(1, t.getIdUsuari());
            ps.setString(2, String.valueOf(t.getTipus()));
            if (ps.executeUpdate() != 0) {
                System.out.println("Moviment inserit correctament");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Elimina un moviment de la base de dades.
     * 
     * @param t L'objecte Moviments a eliminar.
     */
    @Override
    public void update(Moviments t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * Seleciona tots els moviments d'un usuari basant-se en la seva ID.
     * 
     * @param id La ID de l'usuari.
     * @return Una llista d'objectes Moviments relacionats amb l'usuari.
     */
    @Override
    public void delete(Moviments t) {
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(DELETE);
                ps.setInt(1, t.getIdMoviment());
                ps.executeUpdate();
                System.out.println("Moviment eliminat correctament");
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Seleciona tots els moviments d'un usuari.
     * 
     * @param idUsuari La ID de l'usuari.
     * @return Una llista d'objectes Moviments relacionats amb l'usuari.
     */
    public List<Moviments> selectMovimentsById(Integer id) {
        ArrayList<Moviments> moviments = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idMoviment = rs.getInt("idMoviment");
                EnumDespesa enumDespesa = EnumDespesa.valueOf(rs.getString("tipusMoviment"));
                Moviments m = new Moviments(idMoviment,enumDespesa);
                System.out.println("Moviemnt" +m);
                moviments.add(m);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviments;
    }

    /**
     * Seleciona l'últim moviment d'un usuari.
     * 
     * @return L'objecte Moviments representant l'últim moviment de l'usuari.
     */
    public ArrayList<Moviments> selectMoviments(int idUsuari){
        ArrayList<Moviments> m = new ArrayList<Moviments>();
        Moviments moviment = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTMOVIMENTSBYUSERLIST);
            ps.setInt(1, idUsuari);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int movimentID = rs.getInt("idMoviment");
                EnumDespesa enumeratDespesa = EnumDespesa.valueOf(rs.getString("tipusMoviment"));
                moviment = new Moviments(movimentID, idUsuari ,enumeratDespesa);
                m.add(moviment);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    /**
     * Seleciona tots els moviments de la base de dades.
     * 
     * @return Una llista d'objectes Moviments.
     */
    // SELECCIONAR ULTIM MOVIMENT DE L'USUARI
    public Moviments selectLastMoviment() {
        Moviments moviment = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTMOVIMENTSBYUSER);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int movimentID = rs.getInt("MAX(idMoviment)"); 
                moviment = new Moviments(movimentID);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return moviment;
    }

    /**
     * Seleciona un moviment basant-se en la seva ID.
     * 
     * @param id La ID del moviment.
     * @return L'objecte Moviments relacionat amb la ID donada.
     */
    @Override
    public List<Moviments> selectAll() {
        ArrayList<Moviments> moviments = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_moviment = rs.getInt("idMoviment");
                int id_usuari = rs.getInt("idUsuari");
                String tipus = rs.getString("tipusMoviment");
                Moviments moviment = new Moviments(id_moviment, id_usuari, EnumDespesa.valueOf(tipus));
                moviments.add(moviment);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return moviments;
    }
    
    /**
     * Selecciona un moviment basant-se en la seva ID.
     * 
     * @param id La ID del moviment.
     * @return L'objecte Moviments relacionat amb la ID donada, o null si no es troba cap moviment amb aquesta ID.
     */
    @Override
    public Moviments selectById(Integer id) {
        Moviments moviment = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTMOVIMENTBYID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idMoviment = rs.getInt("idMoviment");
                String tipusMoviment = rs.getString("tipusMoviment");
                moviment = new Moviments(idMoviment,EnumDespesa.valueOf(tipusMoviment));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return moviment;
    }
}
