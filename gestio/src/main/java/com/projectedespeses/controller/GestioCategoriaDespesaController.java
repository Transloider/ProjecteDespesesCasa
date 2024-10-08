package com.projectedespeses.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.projectedespeses.DAO.CategoriaDespesaDAO;
import com.projectedespeses.model.CategoriaDespesa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador per a la gestió de categories de despesa.
 */
public class GestioCategoriaDespesaController implements Initializable {
    @FXML
    private ListView<CategoriaDespesa> llistaCategories;
    @FXML
    private TextField nomCategoria;

    private CategoriaDespesaDAO despesaDAO = new CategoriaDespesaDAO();
    private Stage stage;
    ObservableList<CategoriaDespesa> olCategoria;
    private List<CategoriaDespesa> categories;
    private CategoriaDespesa categoria;
    private CategoriaDespesa despesaActual;
    private CategoriaDespesa i;
    

    /**
     * Constructor per defecte.
     * Inicialitza la llista de categories amb les dades de la base de dades.
     */
    public GestioCategoriaDespesaController(){
        categories = despesaDAO.selectAll();
    }

    /**
     * Mètode d'inicialització del controlador.
     * Carrega les categories de despesa a la llista visual.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        olCategoria = FXCollections.observableArrayList(categories);
        if (llistaCategories != null) {
            llistaCategories.setItems(olCategoria);
        }
    }

    /**
     * Afegeix una nova categoria de despesa.
     * Si el nom de la categoria no està repetit, l'afegeix a la base de dades i actualitza la llista.
     * En cas contrari, mostra un missatge d'error.
     */
    @FXML
    void afegirCategoria(ActionEvent event) {
        String nom = nomCategoria.getText();
        categories = despesaDAO.selectAll();
        Boolean validador=false;
        for (CategoriaDespesa categoriaDespesa : categories) {
            if (categoriaDespesa.getNomCategoria().equals(nom)) {
                validador=true;
            }
        }
        if (validador==false) {
            if (nom != null) {
                i = new  CategoriaDespesa(nom);
                despesaDAO.insert(i);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("La categoria " +i.getNomCategoria() +" s'ha afegit correctament!");
                alert.showAndWait();
            }
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquest nom ja està registrat!");
            alert.showAndWait();
        }
    }

    /**
     * Elimina una categoria seleccionada de despesa.
     * Si la categoria no està associada a cap ingrés, l'elimina de la base de dades i actualitza la llista.
     * Si la categoria té associat almenys un ingrés, mostra un missatge d'error.
     */
    @FXML
    void eliminarCategoria(ActionEvent event) {
        if (despesaDAO.verificarCategories(despesaActual).size()==0) {
            despesaDAO.delete(despesaActual);
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquesta categoria ja està assignada a un ingrés");
            alert.showAndWait();
        }
    }

    /**
     * Modifica el nom d'una categoria seleccionada de despesa.
     * Si el nou nom no està repetit, actualitza el nom a la base de dades i refresca la llista.
     * Si el nom ja existeix, mostra un missatge d'error.
     */
    @FXML
    void modificarCategoria(ActionEvent event) {
        String nom = nomCategoria.getText();
        categories = despesaDAO.selectAll();
        Boolean validador=false;
        for (CategoriaDespesa categoriaIngres : categories) {
            if (categoriaIngres.getNomCategoria().equals(nom)) {
                validador=true;
            }
        }
        if (validador==false) {
            if (nom != null) {
                despesaActual.setNomCategoria(nom);
                despesaDAO.update(despesaActual);
            }
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquest nom ja està registrat!");
            alert.showAndWait();
        }
    }

    /**
     * Gestiona la selecció d'una categoria de despesa de la llista.
     * Quan es selecciona una categoria, mostra el seu nom al camp de text.
     */
    @FXML
    void seleccionarCategoria(MouseEvent event) {
        try {
            categoria = llistaCategories.getSelectionModel().getSelectedItem();
            nomCategoria.setText(categoria.getNomCategoria());
            despesaActual = categoria;
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setContentText("Selecciona un camp que estigui complet");
            alert.showAndWait();
        }
    }

    /**
     * Actualitza la llista de categories de despesa.
     * Carrega les dades de la base de dades i actualitza la llista visual.
     */
    public void update(){
        categories = despesaDAO.selectAll();
        olCategoria = FXCollections.observableArrayList(categories);
        if (llistaCategories != null) {
            llistaCategories.setItems(olCategoria);
        }
    }

    /**
     * Cancel·la l'acció i tanca la finestra actual.
     */
    @FXML
    public void cancelarPagina(){
        this.stage.close();
    }

    /**
     * Estableix l'escenari actual.
     * @param stage L'escenari actual.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}
