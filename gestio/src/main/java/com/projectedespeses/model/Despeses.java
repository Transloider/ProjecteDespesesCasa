package com.projectedespeses.model;

import java.time.LocalDate;
import java.util.Date;

import com.projectedespeses.enumerats.EnumeratTipusDespesa;

public class Despeses extends Moviments {
    private int idDespesa;
    private Date data;
    private String descripcio;
    private int cost;
    private int idCategoriaDespesa; //carregar objecte categoria despesa
    private EnumeratTipusDespesa tipusdespesa;
    private LocalDate datagrafic;

    /**
     * Constructor per defecte.
     */
    public Despeses() {
    }

    /**
     * Constructor amb data per a representació gràfica, descripció, cost i tipus de despesa.
     * 
     * @param datagrafic    Data per a representació gràfica.
     * @param descripcio    Descripció de la despesa.
     * @param cost          Cost de la despesa.
     * @param tipusdespesa  Tipus de despesa.
     */
    public Despeses( LocalDate datagrafic, String descripcio, int cost,
     EnumeratTipusDespesa tipusdespesa) {
        this.datagrafic = datagrafic;
        this.descripcio = descripcio;
        this.cost = cost;
        this.tipusdespesa = tipusdespesa;
    }

    /**
     * Constructor amb data, descripció, cost i tipus de despesa.
     * 
     * @param data          Data de la despesa.
     * @param descripcio    Descripció de la despesa.
     * @param cost          Cost de la despesa.
     * @param tipusdespesa  Tipus de despesa.
     */
    public Despeses( Date data, String descripcio, int cost,
     EnumeratTipusDespesa tipusdespesa) {
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.tipusdespesa = tipusdespesa;
    }

    /**
     * Constructor amb identificador de moviment, identificador d'usuari, data, descripció, cost,
     * identificador de categoria de despesa i tipus de despesa.
     * 
     * @param idMoviment            Identificador de moviment.
     * @param idUsuari              Identificador d'usuari.
     * @param data                  Data de la despesa.
     * @param descripcio            Descripció de la despesa.
     * @param cost                  Cost de la despesa.
     * @param idCategoriaDespesa    Identificador de la categoria de despesa.
     * @param tipusdespesa          Tipus de despesa.
     */
    public Despeses(int idMoviment, int idUsuari, Date data, String descripcio, int cost,
    int idCategoriaDespesa, EnumeratTipusDespesa tipusdespesa) {
        super(idMoviment, idUsuari);
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.idCategoriaDespesa = idCategoriaDespesa;
        this.tipusdespesa = tipusdespesa;
    }

    /**
     * Constructor amb identificador de moviment, identificador d'usuari, identificador de despesa,
     * data, descripció, cost, identificador de categoria de despesa i tipus de despesa.
     * 
     * @param idMoviment            Identificador de moviment.
     * @param idUsuari              Identificador d'usuari.
     * @param idDespesa             Identificador de la despesa.
     * @param data                  Data de la despesa.
     * @param descripcio            Descripció de la despesa.
     * @param cost                  Cost de la despesa.
     * @param idCategoriaDespesa    Identificador de la categoria de despesa.
     * @param tipusdespesa          Tipus de despesa.
     */
    public Despeses(int idMoviment, int idUsuari, int idDespesa, Date data, String descripcio, int cost,
            int idCategoriaDespesa, EnumeratTipusDespesa tipusdespesa) {
        super(idMoviment, idUsuari);
        this.idDespesa = idDespesa;
        this.data = data;
        this.descripcio = descripcio;
        this.cost = cost;
        this.idCategoriaDespesa = idCategoriaDespesa;
        this.tipusdespesa = tipusdespesa;
    }
    /**
     * Getters and setters
     * 
     */
    public int getIdDespesa() {
        return idDespesa;
    }
    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
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
    public int getIdCategoriaDespesa() {
        return idCategoriaDespesa;
    }
    public void setIdCategoriaDespesa(int idCategoriaDespesa) {
        this.idCategoriaDespesa = idCategoriaDespesa;
    }

    public EnumeratTipusDespesa getTipusdespesa() {
        return tipusdespesa;
    }

    public void setTipusdespesa(EnumeratTipusDespesa tipusdespesa) {
        this.tipusdespesa = tipusdespesa;
    }
    public LocalDate getDatagrafic() {
        return datagrafic;
    }
    public void setDatagrafic(LocalDate datagrafic) {
        this.datagrafic = datagrafic;
    }
    /**
     * Retorna una representació en cadena de text de la despesa.
     * 
     * @return Una cadena de text que representa la despesa.
     */
    @Override
    public String toString() {
        return "Despeses [idDespesa=" + idDespesa + ", data=" + data + ", descripcio=" + descripcio + ", cost=" + cost
                + ", idCategoriaDespesa=" + idCategoriaDespesa + ", tipus=" + tipusdespesa + "]";
    }
}
