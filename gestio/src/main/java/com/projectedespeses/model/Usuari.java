package com.projectedespeses.model;

import java.util.ArrayList;
import java.util.Objects;

import com.projectedespeses.enumerats.EnumDespesa;

/**
 * Classe que representa un usuari en el sistema de gestió de despeses.
 */
public class Usuari {
    private String nom;
    private String password;
    private int id_usuari;
    private ArrayList<Moviments> moviment = new ArrayList<>();

    /**
     * Constructor sense paràmetres de la classe Usuari.
     */
    public Usuari() {
    }
    
    /**
     * Constructor de la classe Usuari amb nom i contrasenya.
     * @param nom El nom de l'usuari.
     * @param password La contrasenya de l'usuari.
     */
    public Usuari(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    /**
     * Constructor de la classe Usuari amb nom, contrasenya i identificador d'usuari.
     * @param nom El nom de l'usuari.
     * @param password La contrasenya de l'usuari.
     * @param id_usuari L'identificador de l'usuari.
     */
    public Usuari(String nom, String password, int id_usuari) {
        this.nom = nom;
        this.password = password;
        this.id_usuari = id_usuari;
    }

    /**
     * Constructor de la classe Usuari amb nom, contrasenya, identificador d'usuari i llista de moviments.
     * @param nom El nom de l'usuari.
     * @param password La contrasenya de l'usuari.
     * @param id_usuari L'identificador de l'usuari.
     * @param moviment La llista de moviments associada a l'usuari.
     */
    public Usuari(String nom, String password, int id_usuari, ArrayList<Moviments> moviment) {
        this.nom = nom;
        this.password = password;
        this.id_usuari = id_usuari;
        this.moviment = moviment;
    }

    /**
     * Obté el nom de l'usuari.
     * @return El nom de l'usuari.
     */
    public String getNom() {
        return nom;
    }
    
    /**
     * Estableix el nom de l'usuari.
     * @param nom El nom de l'usuari.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la contrasenya de l'usuari.
     * @return La contrasenya de l'usuari.
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Estableix la contrasenya de l'usuari.
     * @param password La contrasenya de l'usuari.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obté l'identificador de l'usuari.
     * @return L'identificador de l'usuari.
     */
    public int getId_usuari() {
        return id_usuari;
    }

    /**
     * Estableix l'identificador de l'usuari.
     * @param id_usuari L'identificador de l'usuari.
     */
    public void setId_usuari(int id_usuari) {
        this.id_usuari = id_usuari;
    }

    /**
     * Estableix la llista de moviments associada a l'usuari.
     * @param moviment La llista de moviments.
     */
    public void setMoviment(ArrayList<Moviments> moviment) {
        this.moviment = moviment;
    }

    /**
     * Obté la llista de moviments associada a l'usuari.
     * @return La llista de moviments.
     */
    public ArrayList<Moviments> getMoviment() {
        return moviment;
    }

    /**
     * Obté una llista de moviments de tipus despesa associats a l'usuari.
     * @return La llista de moviments de despesa.
     */
    public ArrayList<Moviments> getMovimetnDespesa(){
        ArrayList<Moviments> despeses = new ArrayList<>();
        for (Moviments moviment : moviment) {
            if (moviment.getTipus().equals(EnumDespesa.DESPESA)) {
                despeses.add(moviment);
            }
        }
        return despeses;
    }

    /**
     * Obté una llista de moviments de tipus ingrés associats a l'usuari.
     * @return La llista de moviments d'ingrés.
     */
    public ArrayList<Moviments> getMovimetnIngres(){
        ArrayList<Moviments> ingres = new ArrayList<>();
        for (Moviments moviment : moviment) {
            if (moviment.getTipus().equals(EnumDespesa.INGRES)) {
                ingres.add(moviment);
            }
        }
        return ingres;
    }

    /**
     * Calcula el codi de hash de l'usuari.
     * @return El codi de hash.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.nom) + Objects.hashCode(this.password);
        return hash;
    }
    
    /**
     * Comprova si dos usuaris són iguals.
     * @param obj L'objecte amb el qual es compara.
     * @return Cert si són iguals, fals altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuari other = (Usuari) obj;
        if (!Objects.equals(this.nom, other.nom) && !Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    /**
     * Retorna una representació en format de cadena de l'usuari.
     * @return Una cadena que representa l'usuari.
     */
    @Override
    public String toString() {
        return "Usuari [nom=" + nom + ", password=" + password + ", id_usuari=" + id_usuari + ", moviment=" + moviment
                + "]";
    }

}
