package com.projectedespeses.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.enumerats.EnumeratTipusDespesa;
import com.projectedespeses.model.CategoriaDespesa;
import com.projectedespeses.model.Despeses;
import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

import javafx.scene.control.Alert;
/**
 * Classe que implementa l'accés de dades per a les despeses.
 */

public class DespesaDAO extends DBConnection implements DAO<Despeses, Integer>{
    private final String INSERT = "INSERT INTO despesa(idMoviment,idCategoria,dataDespesa,descripcio,import,tipusDespesa) VALUES(?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE despesa SET descripcio = ? , import = ?, dataDespesa = ?, tipusDespesa = ?  WHERE idMoviment = ? ";
    private final String DELETE = "DELETE FROM ingressos WHERE idMoviment=?";
    private final String SELECTALL = "SELECT * FROM despesa WHERE idMoviment in (SELECT idMoviment FROM moviments WHERE idUsuari = ?)";
    private final String SELECTDESPESABYMOVIMENT = "SELECT dataDespesa, descripcio, import, tipusDespesa FROM despesa WHERE idMoviment=?";
    private final String SELECTMOVIMENDESPESABYCATEGORY = "SELECT * FROM moviments WHERE idUsuari=? and idMoviment in (SELECT idMoviment FROM despesa WHERE idCategoria=?)";
    private final String SELECTIMPORTBYDATA = "SELECT SUM(import) as import FROM despesa WHERE strftime('%m', datetime(dataDespesa / 1000, 'unixepoch')) = ? AND strftime('%Y', datetime(dataDespesa / 1000, 'unixepoch')) = ? AND idMoviment in (SELECT idMoviment FROM moviments WHERE idUsuari=?)";
                
    private MovimentDAO movimentDAO = new MovimentDAO();

    /**
     * Insereix una nova despesa a la base de dades.
     * @param t La despesa a inserir.
     */
    @Override
    public void insert(Despeses t) {
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(INSERT);
                ps.setInt(1, t.getIdMoviment());
                ps.setInt(2, t.getIdCategoriaDespesa());
                System.out.println(t.getData());
                ps.setDate(3, Date.valueOf(String.valueOf(t.getData())));
                ps.setString(4, t.getDescripcio());
                ps.setInt(5, t.getCost());
                ps.setString(6, String.valueOf(t.getTipusdespesa()));
                if (ps.executeUpdate() != 0) {
                    System.out.println("Despesa inserida correctament");
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
     * Actualitza una despesa a la base de dades.
     * @param t La despesa a actualitzar.
     */
    @Override
    public void update(Despeses t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getDescripcio());
            ps.setInt(2, t.getCost());
            ps.setDate(3,Date.valueOf(String.valueOf( t.getData())));
            ps.setString(4, String.valueOf(t.getTipusdespesa()));
            ps.setInt(5, t.getIdMoviment());
            if (ps.executeUpdate() != 0) {
                System.out.println("Ingres modificat correctament");
            }
        closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

     /**
     * Elimina una despesa de la base de dades.
     * @param t La despesa a eliminar.
     */
    @Override
    public void delete(Despeses t) {
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

    /**
     * No implementat.
     */
    @Override
    public Despeses selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    /**
     * Seleciona totes les despeses d'un usuari.
     * @param usuari L'usuari del qual es volen obtenir les despeses.
     * @return Una llista de totes les despeses de l'usuari.
     */
    public List<Despeses> selectAllByUser(Usuari usuari) {
        List<Despeses> despeses = new ArrayList<>();
        Despeses i= null;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTALL);
                ps.setInt(1, usuari.getId_usuari());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    // int idMoviment = rs.getInt("idMoviment");
                    Date datarecollida = rs.getDate("dataDespesa");
                    String desc = rs.getString("descripcio");
                    int cost = rs.getInt("import");
                    EnumeratTipusDespesa enumeratDespesa = EnumeratTipusDespesa.valueOf(rs.getString("tipusDespesa"));
                    i = new Despeses(datarecollida, desc, cost, enumeratDespesa);
                    despeses.add(i);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return despeses;
    }

    /**
     * Seleciona totes les despeses d'un usuari.
     * @param usuari L'usuari del qual es volen obtenir les despeses.
     * @return Una llista de totes les despeses de l'usuari.
     */
    public Despeses selectDespByMoviment(Integer id){
        Despeses d = null;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTDESPESABYMOVIMENT);
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Date data = rs.getDate("dataDespesa");
                    String descripcio = rs.getString("descripcio");
                    int cost = rs.getInt("import");
                    EnumeratTipusDespesa enumdesp = EnumeratTipusDespesa.valueOf(rs.getString("tipusDespesa"));
                    d = new Despeses(data, descripcio, cost, enumdesp);
                }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return d;
    }

    /**
     * Seleciona totes les despeses d'un usuari.
     * @param usuari L'usuari del qual es volen obtenir les despeses.
     * @return llistat de moviments
     */
    public ArrayList<Moviments> selectDespesesByCategory(CategoriaDespesa despesa, Usuari usuari){
        ArrayList<Moviments> moviments = new ArrayList<Moviments>();
        Moviments moviment;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTMOVIMENDESPESABYCATEGORY);
                ps.setInt(1, usuari.getId_usuari());
                ps.setInt(2, despesa.getIdCategoria());
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

    @Override
    public List<Despeses> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    /**
     * import total del mes
     * @param usuari Selecciona l'iport total del mes de l'usuari
     * @return retorna l'import total de cada mes
     */
    public Integer importTotalMes(Usuari usuari, String mes, String any){
        int costTotal = 0;
        try {
            connect();
                PreparedStatement ps = connection.prepareStatement(SELECTIMPORTBYDATA);
                ps.setString(1, mes);
                ps.setString(2, any);
                ps.setInt(3, usuari.getId_usuari());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    costTotal = rs.getInt("import");
                }
            closeConnection();
        } catch (SQLException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Data");
                alert.setContentText("Introduexi la data amb el format correcte mm i yyyy");
                alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return costTotal;
    }
}
