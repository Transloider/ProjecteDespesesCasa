package com.projectedespeses.model;

/**
 * Classe que representa una categoria de despesa.
 */
public class CategoriaDespesa {
    private int idCategoria; // Identificador de la categoria
    private String nomCategoria; // Nom de la categoria

    /**
     * Constructor per defecte.
     */
    public CategoriaDespesa() {
    }

    /**
     * Constructor amb nom de categoria.
     * 
     * @param nomCategoria Nom de la categoria.
     */
    public CategoriaDespesa(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }
    
    /**
     * Constructor amb identificador i nom de categoria.
     * 
     * @param idCategoria  Identificador de la categoria.
     * @param nomCategoria Nom de la categoria.
     */
    public CategoriaDespesa(int idCategoria, String nomCategoria) {
        this.idCategoria = idCategoria;
        this.nomCategoria = nomCategoria;
    }

    /**
     * Obté l'identificador de la categoria.
     * 
     * @return L'identificador de la categoria.
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Estableix l'identificador de la categoria.
     * 
     * @param idCategoria L'identificador de la categoria.
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obté el nom de la categoria.
     * 
     * @return El nom de la categoria.
     */
    public String getNomCategoria() {
        return nomCategoria;
    }

    /**
     * Estableix el nom de la categoria.
     * 
     * @param nomCategoria El nom de la categoria.
     */
    public void setNomCategoria(String nomCategoria) {
        this.nomCategoria = nomCategoria;
    }

    /**
     * Retorna una representació en cadena de text del nom de la categoria.
     * 
     * @return El nom de la categoria.
     */
    @Override
    public String toString() {
        return nomCategoria;
    }
}
