package com.projectedespeses.model;

import java.util.Date;

import com.projectedespeses.enumerats.EnumeratTipusDespesa;
/**
 * Classe que representa un ingrés.
 */
public class Ingressos extends Moviments {
    private int idIngres;
    private Date data;
    private String descripcio;
    private int cost;
    private int idCategoriaIngres;
    private EnumeratTipusDespesa tipusIngres;

    /**
     * Constructor amb data i descripció.
     * 
     * @param data        Data de l'ingrés.
     * @param descripcio  Descripció de l'ingrés.
     */
    public Ingressos( Date data, String descripcio) {
        this.data = data;
        this.descripcio = descripcio;
    }

    /**
     * Constructor per defecte.
     */
    public Ingressos() {
    }

    /**
     * Constructor amb data, descripció, cost i tipus d'ingrés.
     * 
     * @param data          Data de l'ingrés.
     * @param descripcio    Descripció de l'ingrés.
     * @param cost          Cost de l'ingrés.
     * @param tipusIngres   Tipus d'ingrés.
     */
    public Ingressos( Date data, String descripcio, int cost, EnumeratTipusDespesa tipusIngres) {
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.tipusIngres = tipusIngres;
    }

    /**
     * Constructor amb identificador de moviment, identificador d'usuari, data, descripció, cost,
     * identificador de categoria d'ingrés i tipus d'ingrés.
     * 
     * @param idMoviment            Identificador de moviment.
     * @param idUsuari              Identificador d'usuari.
     * @param data                  Data de l'ingrés.
     * @param descripcio            Descripció de l'ingrés.
     * @param cost                  Cost de l'ingrés.
     * @param idCategoriaIngres     Identificador de la categoria d'ingrés.
     * @param tipusIngres           Tipus d'ingrés.
     */
    public Ingressos(int idMoviment, int idUsuari, Date data, String descripcio, int cost,
    int idCategoriaIngres, EnumeratTipusDespesa tipusIngres) {
        super(idMoviment, idUsuari);
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.idCategoriaIngres = idCategoriaIngres;
        this.tipusIngres = tipusIngres;
    }

    /**
     * Constructor amb identificador de moviment, identificador d'usuari, identificador d'ingrés,
     * data, descripció, cost, identificador de categoria d'ingrés i tipus d'ingrés.
     * 
     * @param idMoviment            Identificador de moviment.
     * @param idUsuari              Identificador d'usuari.
     * @param idIngres              Identificador de l'ingrés.
     * @param data                  Data de l'ingrés.
     * @param descripcio            Descripció de l'ingrés.
     * @param cost                  Cost de l'ingrés.
     * @param idCategoriaIngres     Identificador de la categoria d'ingrés.
     * @param tipusIngres           Tipus d'ingrés.
     */
    public Ingressos(int idMoviment, int idUsuari, int idIngres, Date data, String descripcio, int cost,
            int idCategoriaIngres, EnumeratTipusDespesa tipusIngres) {
        super(idMoviment, idUsuari);
        this.idIngres = idIngres;
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.idCategoriaIngres = idCategoriaIngres;
        this.tipusIngres = tipusIngres;
    }
    /**
     * Getters and setters
     * 
     */
    public int getIdIngres() {
        return idIngres;
    }

    public void setIdIngres(int idIngres) {
        this.idIngres = idIngres;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getIdCategoriaIngres() {
        return idCategoriaIngres;
    }

    public void setIdCategoriaIngres(int idCategoriaIngres) {
        this.idCategoriaIngres = idCategoriaIngres;
    }

    public EnumeratTipusDespesa getTipusIngres() {
        return tipusIngres;
    }

    public void setTipusIngres(EnumeratTipusDespesa tipusIngres) {
        this.tipusIngres = tipusIngres;
    }
    
    /**
     * Retorna una representació en cadena de text de l'ingrés.
     * 
     * @return Una cadena de text que representa l'ingrés.
     */
    @Override
    public String toString() {
        return "Ingressos [idIngres=" + idIngres + ", data=" + data + ", descripcio=" + descripcio + ", cost=" + cost
                + ", idCategoriaIngres=" + idCategoriaIngres + ", tipus=" + tipusIngres + "]";
    }
}
