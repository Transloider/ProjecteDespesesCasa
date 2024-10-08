package com.projectedespeses.model;

/**
 * Classe que representa una categoria d'ingrés.
 */
public class CategoriaIngres {
    private int id_categoria; // Identificador de la categoria
    private String nom; // Nom de la categoria

    /**
     * Constructor per defecte.
     */
    public CategoriaIngres() {
    }
    
    /**
     * Constructor amb nom de categoria.
     * 
     * @param nom Nom de la categoria.
     */
    public CategoriaIngres(String nom) {
        this.nom = nom;
    }

    /**
     * Constructor amb identificador i nom de categoria.
     * 
     * @param id_categoria  Identificador de la categoria.
     * @param nom Nom de la categoria.
     */
    public CategoriaIngres(int id_categoria, String nom) {
        this.id_categoria = id_categoria;
        this.nom = nom;
    }

    /**
     * Obté l'identificador de la categoria.
     * 
     * @return L'identificador de la categoria.
     */
    public int getId_categoria() {
        return id_categoria;
    }
    
    /**
     * Estableix l'identificador de la categoria.
     * 
     * @param id_categoria L'identificador de la categoria.
     */
    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    
    /**
     * Obté el nom de la categoria.
     * 
     * @return El nom de la categoria.
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Estableix el nom de la categoria.
     * 
     * @param nom El nom de la categoria.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retorna una representació en cadena de text del nom de la categoria.
     * 
     * @return El nom de la categoria.
     */
    @Override
    public String toString() {
        return  nom;
    }
}
