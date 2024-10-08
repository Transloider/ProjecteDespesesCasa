package com.projectedespeses.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.projectedespeses.DAO.UsuariDAO;
import com.projectedespeses.model.Usuari;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador per a la modificació d'un usuari.
 */
public class ModificarUsuariController implements Initializable {
    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtcontrasenya;

    private Stage stage;
    private Usuari usuariActual;
    private UsuariDAO usuariDAO = new UsuariDAO();

    /**
     * Constructor del controlador.
     * @param usuari L'usuari a modificar.
     */
    public ModificarUsuariController(Usuari usuari){
        this.usuariActual = usuari;
        System.out.println(usuariActual);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Modificar usuari pàgina");
    }

    /**
     * Maneja l'acció de modificar un usuari.
     */
    @FXML
    public void modificarUsuari(){
        String nom = txtnom.getText();
        String contrasenya = txtcontrasenya.getText();

        if (nom != null && contrasenya != null) {
            usuariActual.setNom(nom);
            usuariActual.setPassword(contrasenya);
            usuariDAO.update(usuariActual);
            this.stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Camps incorrectes, verifica que tots estiguin omplerts");
            alert.showAndWait();
        }
    }

    /**
     * Cancel·la la modificació de l'usuari.
     * @param event Event d'acció.
     */
    @FXML
    public void cancelarModificacio(ActionEvent event){
        this.stage.close();
    }

    /**
     * Defineix l'stage per a aquest controlador.
     * @param stage El stage de la finestra.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}
