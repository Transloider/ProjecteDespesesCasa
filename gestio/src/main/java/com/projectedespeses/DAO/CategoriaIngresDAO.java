package com.projectedespeses.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.projectedespeses.model.CategoriaIngres;

/**
 * Classe per a l'accés a dades de la taula CategoriaIngressos a la base de dades.
 */
public class CategoriaIngresDAO extends DBConnection implements DAO<CategoriaIngres, Integer>{
    private final String INSERT = "INSERT INTO categoriaIngressos(nomCategoria) VALUES(?)";
    private final String UPDATE = "UPDATE categoriaIngressos SET nomCategoria = ? WHERE idCategoria=?";
    private final String DELETE = "DELETE FROM categoriaIngressos WHERE idCategoria=?";
    private final String SELECTALL = "SELECT * FROM categoriaIngressos";
    private final String SELECTBYNOM = "SELECT * FROM categoriaIngressos WHERE nomCategoria=?";
    private final String SELECTCATEGORIESNODISPONIBLES = "SELECT idCategoria FROM ingressos WHERE idCategoria in (SELECT idCategoria FROM categoriaIngressos WHERE idCategoria = ?)";

    @Override
    public void insert(CategoriaIngres t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setString(1, t.getNom());
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
    public void update(CategoriaIngres t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, t.getNom());
            ps.setInt(2, t.getId_categoria());
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
    public void delete(CategoriaIngres t) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(DELETE);
            ps.setInt(1, t.getId_categoria());
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
    public CategoriaIngres selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Mètode 'selectById' no implementat");
    }

    @Override
    public List<CategoriaIngres> selectAll() {
        ArrayList<CategoriaIngres> categories = new ArrayList<>();
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement(SELECTALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nomCategoria = rs.getString("nomCategoria");
                CategoriaIngres categoria = new CategoriaIngres(nomCategoria);
                categoria.setId_categoria(idCategoria);
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
     * Selecciona una categoria d'ingrés pel nom.
     * 
     * @param i Categoria d'ingrés a cercar.
     * @return Categoria d'ingrés trobada.
     */
    public CategoriaIngres selectCategoriaByNom(CategoriaIngres i){
        CategoriaIngres categoria=null;
        try {
        connect();
            PreparedStatement ps = connection.prepareStatement(SELECTBYNOM);
            ps.setString(1,i.getNom());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                String nomCategoria = rs.getString("nomCategoria");
                categoria = new CategoriaIngres(idCategoria, nomCategoria);
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
     * Verifica les categories d'ingrés que no estan disponibles per eliminar.
     * 
     * @param i Categoria d'ingrés a verificar.
     * @return Llista de categories d'ingrés no disponibles.
     */
    public ArrayList<CategoriaIngres> verificarCategories(CategoriaIngres i){
        ArrayList<CategoriaIngres> categories = new ArrayList<CategoriaIngres>();
        CategoriaIngres categoria=null;
        try {
        connect();
            PreparedStatement ps = connection.prepareStatement(SELECTCATEGORIESNODISPONIBLES);
            ps.setInt(1,i.getId_categoria());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int idCategoria = rs.getInt("idCategoria");
                categoria = new CategoriaIngres();
                categoria.setId_categoria(idCategoria);
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
