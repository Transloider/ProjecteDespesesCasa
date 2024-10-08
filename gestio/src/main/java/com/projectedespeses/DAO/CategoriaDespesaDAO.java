package com.projectedespeses.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projectedespeses.model.CategoriaDespesa;

/**
 * Classe per a l'accés a dades de la taula CategoriaDespesa a la base de dades.
 */
public class CategoriaDespesaDAO extends DBConnection implements DAO<CategoriaDespesa, Integer> {
    private final String INSERT = "INSERT INTO categoriaDespesa(nomCategoria) VALUES(?)";
    private final String UPDATE = "UPDATE categoriaDespesa SET nomCategoria = ? WHERE idCategoria=?";
    private final String DELETE = "DELETE FROM categoriaDespesa WHERE idCategoria=?";
    private final String SELECTALL = "SELECT * FROM categoriaDespesa";
    private final String SELECTBYNOM = "SELECT * FROM categoriaDespesa WHERE nomCategoria=?";
    private final String SELECTCATEGORIESNODISPONIBLES = "SELECT idCategoria FROM despesa WHERE idCategoria in (SELECT idCategoria FROM categoriaDespesa WHERE idCategoria = ?)";

    @Override
    public void insert(CategoriaDespesa t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNomCategoria());
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

    @Override
    public void update(CategoriaDespesa t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNomCategoria());
            ps.setInt(2, t.getIdCategoria());
            if (ps.executeUpdate() != 0) {
                System.out.println("Categoria modificada correctament");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(CategoriaDespesa t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getIdCategoria());
            if (ps.executeUpdate() != 0) {
                System.out.println("Categoria eliminada correctament");
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public CategoriaDespesa selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Mètode 'selectById' no implementat");
    }

    @Override
    public List<CategoriaDespesa> selectAll() {
        ArrayList<CategoriaDespesa> categories = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nomCategoria = rs.getString("nomCategoria");
                CategoriaDespesa categoria = new CategoriaDespesa(nomCategoria);
                categoria.setIdCategoria(idCategoria);
                categories.add(categoria);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories;
    }

    /**
     * Selecciona una categoria de despesa pel nom.
     * 
     * @param i Categoria de despesa a cercar.
     * @return Categoria de despesa trobada.
     */
    public CategoriaDespesa selectCategoriaByNom(CategoriaDespesa i) {
        CategoriaDespesa categoria = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYNOM);
            ps.setString(1, i.getNomCategoria());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nomCategoria = rs.getString("nomCategoria");
                categoria = new CategoriaDespesa(idCategoria, nomCategoria);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categoria;
    }

    /**
     * Verifica les categories de despesa que no estan disponibles per eliminar.
     * 
     * @param i Categoria de despesa a verificar.
     * @return Llista de categories de despesa no disponibles.
     */
    public ArrayList<CategoriaDespesa> verificarCategories(CategoriaDespesa i) {
        ArrayList<CategoriaDespesa> categories = new ArrayList<CategoriaDespesa>();
        CategoriaDespesa categoria = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTCATEGORIESNODISPONIBLES);
            ps.setInt(1, i.getIdCategoria());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                categoria = new CategoriaDespesa();
                categoria.setIdCategoria(idCategoria);
                categories.add(categoria);
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return categories;
    }
}
