package com.projectedespeses.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.enumerats.EnumeratTipusDespesa;
import com.projectedespeses.model.CategoriaIngres;
import com.projectedespeses.model.Ingressos;
import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

/**
 * Classe que implementa l'accés de dades per als ingressos.
 */
public class IngressosDAO extends DBConnection implements DAO<Ingressos, Integer>{
    private final String INSERT = "INSERT INTO ingressos(idMoviment,idCategoria,dataIngres,descripcio,import,tipusDespesa) VALUES(?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE ingressos SET descripcio = ? , import = ?, dataIngres = ?, tipusDespesa = ?  WHERE idMoviment = ? ";
    private final String DELETE = "DELETE FROM ingressos WHERE idMoviment=?";
    // private final String SELECTBYID = "SELECT idMoviment FROM ingressos WHERE idUsuari=?";
    // private final String SELECTMOVIMENTSBYUSER = "SELECT MAX(idMoviment) FROM ingressos";
    private final String SELECTALL = "SELECT * FROM ingressos WHERE idMoviment in (SELECT idMoviment FROM moviments WHERE idUsuari = ?)";
    private final String SELECTINGRESBYMOVMENT= "SELECT dataIngres, descripcio, import, tipusDespesa FROM ingressos WHERE idMoviment=?";
    private final String SELECTMOVIMENINGRESBYCATEGORY = "SELECT * FROM moviments WHERE idUsuari=? and idMoviment in (SELECT idMoviment FROM ingressos WHERE idCategoria=?)";
    private final String SELECTINGRESBYMOVIMENT = "SELECT dataIngres, descripcio, import, tipusDespesa FROM ingressos WHERE idMoviment=?";

    private MovimentDAO movimentDAO = new MovimentDAO();

    /**
     * Insereix un nou ingrés a la base de dades.
     * @param t L'ingrés a inserir.
     */
    @Override
    public void insert(Ingressos t) {
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(INSERT);
                ps.setInt(1, t.getIdMoviment());
                ps.setInt(2, t.getIdCategoriaIngres());
                System.out.println(t.getData());
                ps.setDate(3, Date.valueOf(String.valueOf(t.getData())));
                ps.setString(4, t.getDescripcio());
                ps.setInt(5, t.getCost());
                ps.setString(6, String.valueOf(t.getTipusIngres()));
                if (ps.executeUpdate() != 0) {
                    System.out.println("Ingres inserit correctament");
                }
                closeConnection();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Actualitza un ingrés a la base de dades.
     * @param t L'ingrés a actualitzar.
     */
    @Override
    public void update(Ingressos t) {
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(UPDATE);
                ps.setString(1, t.getDescripcio());
                ps.setInt(2, t.getCost());
                ps.setDate(3,Date.valueOf(String.valueOf( t.getData())));
                ps.setString(4, String.valueOf(t.getTipusIngres()));
                ps.setInt(5, t.getIdMoviment());
                if (ps.executeUpdate() != 0) {
                    System.out.println("Ingres modificat correctament");
                }
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Elimina un ingrés de la base de dades.
     * @param t L'ingrés a eliminar.
     */
    @Override
    public void delete(Ingressos t) {
        Moviments m = new Moviments();
        m.setIdMoviment(t.getIdMoviment());
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(DELETE);
                ps.setInt(1, t.getIdMoviment());
                ps.executeUpdate();
                System.out.println("Usuari eliminat correctametn ");
                movimentDAO.delete(m);
                System.out.println("Moviment eliminat correctament");
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Ingressos selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public List<Ingressos> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    /**
     * Seleciona un ingrés segons el seu moviment.
     * @param idMoviment L'identificador del moviment associat a l'ingrés.
     * @return L'ingrés associat al moviment.
     */
    public Ingressos selectIngresByMovment(Integer idMoviment){
        Ingressos i = null;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTINGRESBYMOVMENT);
                ps.setInt(1, idMoviment);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Date data = rs.getDate("dataIngres");
                    String desc = rs.getString("descripcio");
                    int cost = rs.getInt("import");
                    EnumeratTipusDespesa enumeratDespesa = EnumeratTipusDespesa.valueOf(rs.getString("tipusDespesa"));
                    i = new Ingressos(data, desc, cost, enumeratDespesa);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return i;
    }

    /**
     * Selecciona tots els ingressos d'una categoria per a un usuari.
     * @param igres La categoria d'ingressos a seleccionar.
     * @param usuari L'usuari associat als ingressos.
     * @return Una llista de moviments corresponents als ingressos de la categoria per a l'usuari.
     */
    public ArrayList<Moviments> selectIngressosByCategory(CategoriaIngres igres, Usuari usuari){
        ArrayList<Moviments> moviments = new ArrayList<Moviments>();
        Moviments moviment;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTMOVIMENINGRESBYCATEGORY);
                ps.setInt(1, usuari.getId_usuari());
                ps.setInt(2, igres.getId_categoria());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    int idMoviment = rs.getInt("idMoviment");
                    EnumDespesa enumDesp = EnumDespesa.valueOf(rs.getString("tipusMoviment"));
                    moviment = new Moviments(idMoviment, enumDesp);
                    moviments.add(moviment);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return moviments;
    }

    /**
     * Selecciona un ingrés segons el seu moviment.
     * @param idMoviment L'identificador del moviment associat a l'ingrés.
     * @return L'ingrés associat al moviment.
     */
    public Ingressos selectIngByMoviment(int idMoviment) {
        Ingressos i = null;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTINGRESBYMOVIMENT);
                ps.setInt(1, idMoviment);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Date data = rs.getDate("dataIngres");
                    String descripcio = rs.getString("descripcio");
                    int cost = rs.getInt("import");
                    EnumeratTipusDespesa enumdesp = EnumeratTipusDespesa.valueOf(rs.getString("tipusDespesa"));
                    i = new Ingressos(data, descripcio, cost, enumdesp);
                    System.out.println("Restultat consulta " + i);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return i;
    }

    /**
     * Selecciona tots els ingressos d'un usuari.
     * @param usuari L'usuari del qual es volen obtenir els ingressos.
     * @return Una llista de tots els ingressos de l'usuari.
     */
    public List<Ingressos> selectAllByUser(Usuari usuari) {
        List<Ingressos> ingres = new ArrayList<>();
        Ingressos i= null;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTALL);
                ps.setInt(1, usuari.getId_usuari());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // int idMoviment = rs.getInt("idMoviment");
                    Date datarecollida = rs.getDate("dataIngres");
                    String desc = rs.getString("descripcio");
                    int cost = rs.getInt("import");
                    EnumeratTipusDespesa enumeratDespesa = EnumeratTipusDespesa.valueOf(rs.getString("tipusDespesa"));
                    i = new Ingressos(datarecollida, desc, cost, enumeratDespesa);
                    ingres.add(i);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ingres;
    }
}
