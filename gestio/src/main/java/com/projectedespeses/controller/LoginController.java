package com.projectedespeses.controller;

import java.io.IOException;

import java.util.HashSet;
import java.util.Set;

import com.projectedespeses.DAO.UsuariDAO;
import com.projectedespeses.model.Usuari;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controlador per a la finestra de login.
 */
public class LoginController {
    @FXML
    private TextField txtUsuari;
    @FXML
    private PasswordField txtContrasenya;

    private Set<Usuari> usuaris;
    private Usuari usuariLogin;
    private UsuariDAO usuariDAO;
    private Stage stage;

    /**
     * Constructor del controlador.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public LoginController() {
        usuariDAO = new UsuariDAO();
        usuaris = new HashSet(usuariDAO.selectAll());
        for (Usuari usuari : usuaris) {
            System.out.println(usuari);
        }
    }

    /**
     * Estableix el stage actual.
     * 
     * @param stage El stage actual.
     */
    public void setStage(@SuppressWarnings("exports") Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Acció quan es prem el botó d'acceptar.
     * 
     * @throws IOException Si hi ha un error en carregar la següent pàgina.
     */
    @FXML
    private void acceptar() throws IOException {
        if (!txtUsuari.getText().isEmpty() && !txtContrasenya.getText().isEmpty()) {
            usuariLogin = new Usuari(txtUsuari.getText(), txtContrasenya.getText());
            if (validarUsuari(usuariLogin)) {

                usuariLogin=usuariDAO.selectUsuariByNomContra(usuariLogin);
                System.out.println(usuariLogin);
                carregarPaginaInici();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenció");
                alert.setContentText("L'usuari que estàs intentant inserir no està registrat");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Usuari o contrasenya incorrecte");
            alert.showAndWait();
        }
    }

    /**
     * Carrega la pàgina inicial de l'aplicació.
     */
    private void carregarPaginaInici() {
       try {
            System.out.println("CARREGAR APP PRINCIPAL");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/paginaInici.fxml"));
            loader.setControllerFactory(t -> new PaginaIniciController(usuariLogin));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Programa despeses");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

            this.stage.close();
       } catch (Exception e) {
            System.out.println("error");
       }
    }

    /**
     * Valida si l'usuari és vàlid.
     * 
     * @param usuari L'usuari a validar.
     * @return True si l'usuari és vàlid, False altrament.
     */
    private boolean validarUsuari(Usuari usuari) {
        return usuaris.contains(usuari);
    }

    /**
     * Acció quan es prem el botó de registrar usuari.
     * 
     * @throws IOException Si hi ha un error en carregar la finestra de registre d'usuari.
     */
    @FXML
    private void registrarUsauri() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/registrarUsuari.fxml"));
            loader.setControllerFactory(t -> new RegistrarUsuariController(usuaris));
            Parent root = loader.load();

            Scene scene = new Scene(root, 290, 240);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Registre");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            
            ((RegistrarUsuariController) loader.getController()).setStage(stage);
        } catch (IOException ex) {
            //
        }
    }
}
