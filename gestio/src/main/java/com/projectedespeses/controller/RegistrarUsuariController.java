package com.projectedespeses.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.UsuariDAO;
import com.projectedespeses.model.Usuari;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador per a la finestra de registre d'usuaris.
 */
public class RegistrarUsuariController implements Initializable {

    @FXML
    private TextField txtContrasenya;
    @FXML
    private Button btnRegistrar;
    @FXML
    private TextField txtUsuari;
    private Set<Usuari> usuaris;
    private Usuari usuariLogin;
    private UsuariDAO usuariDAO = new UsuariDAO();
    private Stage stage;

    /**
     * Constructor del controlador.
     * 
     * @param usuarisanteriors Conjunt d'usuaris existents.
     */
    public RegistrarUsuariController(Set<Usuari> usuarisanteriors) {
        this.usuaris=usuarisanteriors;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO
    }

    @FXML
    private void registrar(ActionEvent event) throws IOException {
        if (!txtUsuari.getText().isEmpty() && !txtContrasenya.getText().isEmpty()) {
            usuariLogin = new Usuari(txtUsuari.getText(), txtContrasenya.getText());
            if (!usuaris.add(usuariLogin)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Aquest usuari ja existeix.");
                alert.showAndWait();
                txtUsuari.setText("");
                txtContrasenya.setText("");
            } else {
                usuaris.add(usuariLogin);
                usuariDAO.insert(usuariLogin);
                neteja();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("Usuari " +usuariLogin.getNom()  +" afegit correctament.");
                alert.showAndWait();
                stage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Usuari i/o contrasenya incorrectes...");
            alert.showAndWait();
        }
    }

    /**
     * Obté el conjunt d'usuaris.
     * 
     * @return Conjunt d'usuaris.
     */
    public Set<Usuari> getUsuaris() {
        return usuaris;
    }

    /**
     * Estableix l'escenari de la finestra.
     * 
     * @param stage Escenari de la finestra.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Neteja els camps de text de l'interfície gràfica.
     */
    private void neteja() {
        txtUsuari.setText("");
        txtContrasenya.setText("");
    }
}
