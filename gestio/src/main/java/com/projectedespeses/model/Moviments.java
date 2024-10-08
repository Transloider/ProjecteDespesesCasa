package com.projectedespeses.model;

import com.projectedespeses.enumerats.EnumDespesa;

/**
 * Classe que representa els moviments en un sistema de despeses.
 */
public class Moviments {
    private int idMoviment;
    private int idUsuari;
    private EnumDespesa tipus;

    /**
     * Constructor sense paràmetres de la classe Moviments.
     */
    public Moviments() {
    }
    
    /**
     * Constructor de la classe Moviments amb l'identificador de moviment.
     * @param idMoviment L'identificador del moviment.
     */
    public Moviments(int idMoviment) {
        this.idMoviment = idMoviment;
    }
    
    /**
     * Constructor de la classe Moviments amb l'identificador de moviment i l'identificador d'usuari.
     * @param idMoviment L'identificador del moviment.
     * @param idUsuari L'identificador de l'usuari associat al moviment.
     */
    public Moviments(int idMoviment, int idUsuari) {
        this.idMoviment = idMoviment;
        this.idUsuari = idUsuari;
    }

    /**
     * Constructor de la classe Moviments amb l'identificador de moviment i el tipus de despesa.
     * @param idMoviment L'identificador del moviment.
     * @param tipus El tipus de despesa associat al moviment.
     */
    public Moviments(int idMoviment, EnumDespesa tipus) {
        this.idMoviment = idMoviment;
        this.tipus = tipus;
    }

    /**
     * Constructor de la classe Moviments amb l'identificador de moviment, l'identificador d'usuari i el tipus de despesa.
     * @param idMoviment L'identificador del moviment.
     * @param idUsuari L'identificador de l'usuari associat al moviment.
     * @param tipus El tipus de despesa associat al moviment.
     */
    public Moviments(int idMoviment, int idUsuari, EnumDespesa tipus) {
        this.idMoviment = idMoviment;
        this.idUsuari = idUsuari;
        this.tipus = tipus;
    }

    /**
     * Obté l'identificador del moviment.
     * @return L'identificador del moviment.
     */
    public int getIdMoviment() {
        return idMoviment;
    }

    /**
     * Estableix l'identificador del moviment.
     * @param idMoviment L'identificador del moviment.
     */
    public void setIdMoviment(int idMoviment) {
        this.idMoviment = idMoviment;
    }

    /**
     * Obté l'identificador de l'usuari associat al moviment.
     * @return L'identificador de l'usuari.
     */
    public int getIdUsuari() {
        return idUsuari;
    }
    
    /**
     * Estableix l'identificador de l'usuari associat al moviment.
     * @param idUsuari L'identificador de l'usuari.
     */
    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    /**
     * Estableix el tipus de despesa associat al moviment.
     * @param tipus El tipus de despesa.
     */
    public void setTipus(EnumDespesa tipus) {
        this.tipus = tipus;
    }

    /**
     * Obté el tipus de despesa associat al moviment.
     * @return El tipus de despesa.
     */
    public EnumDespesa getTipus() {
        return tipus;
    }

    /**
     * Retorna una representació en format de cadena del moviment.
     * @return Una cadena que representa el moviment.
     */
    @Override
    public String toString() {
        return "Moviment " + idMoviment +"  "+tipus;
    }
}
