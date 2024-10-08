package com.projectedespeses.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.projectedespeses.DAO.CategoriaIngresDAO;
import com.projectedespeses.model.CategoriaIngres;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador per a la gestió de categories.
 */
public class GestioCategoriaController implements Initializable{
    
    @FXML
    private ListView<CategoriaIngres> llistaCategories;
    @FXML
    private TextField nomCategoria;
    @FXML
    private Button tancarCategoria;

    private CategoriaIngresDAO ingresDAO = new CategoriaIngresDAO();
    private CategoriaIngres i;
    private Stage stage;
    private ObservableList<CategoriaIngres> olCategoria;
    private List<CategoriaIngres> categories;
    private CategoriaIngres categoria;
    private CategoriaIngres categoriaActual;

    /**
     * Constructor per defecte.
     * Inicialitza la llista de categories amb les dades de la base de dades.
     */
    public GestioCategoriaController(){
        categories = ingresDAO.selectAll();
    }

    /**
     * Mètode d'inicialització del controlador.
     * Carrega les categories a la llista visual.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        olCategoria = FXCollections.observableArrayList(categories);
        if (llistaCategories != null) {
            llistaCategories.setItems(olCategoria);
        }
    }

    /**
     * Afegeix una nova categoria.
     * Si el nom de la categoria no està repetit, l'afegeix a la base de dades i actualitza la llista.
     * En cas contrari, mostra un missatge d'error.
     */
    @FXML
    public void afegirCategoria(ActionEvent event) {
        String nom = nomCategoria.getText();
        categories = ingresDAO.selectAll();
        Boolean validador=false;
        for (CategoriaIngres categoriaIngres : categories) {
            if (categoriaIngres.getNom().equals(nom)) {
                validador=true;
            }
        }
        if (validador==false) {
            if (nom != null) {
                i = new  CategoriaIngres(nom);
                ingresDAO.insert(i);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setContentText("La categoria " +i.getNom() +" s'ha afegit correctament!");
                alert.showAndWait();
            }
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquest nom ja esta registrat!");
            alert.showAndWait();
        }
    }

    /**
     * Gestiona la selecció d'una categoria de la llista.
     * Quan es selecciona una categoria, mostra el seu nom al camp de text.
     */
    @FXML
    public void seleccionarCategoria(MouseEvent event){

        try {
            categoria = llistaCategories.getSelectionModel().getSelectedItem();
            nomCategoria.setText(categoria.getNom());
            categoriaActual = categoria;
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setContentText("Selecciona un camp que estigui complet");
            alert.showAndWait();
        }
    }

    /**
     * Modifica el nom d'una categoria seleccionada.
     * Si el nou nom no està repetit, actualitza el nom a la base de dades i refresca la llista.
     * Si el nom ja existeix, mostra un missatge d'error.
     */
    @FXML
    public void modificarCategoria(ActionEvent event) {
        String nom = nomCategoria.getText();
        categories = ingresDAO.selectAll();
        Boolean validador=false;
        for (CategoriaIngres categoriaIngres : categories) {
            if (categoriaIngres.getNom().equals(nom)) {
                validador=true;
            }
        }
        if (validador==false) {
            if (nom != null) {
                categoriaActual.setNom(nom);
                ingresDAO.update(categoriaActual);
            }
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquest nom ja esta registrat!");
            alert.showAndWait();
        }
    }

    /**
     * Elimina una categoria seleccionada.
     * Si la categoria no està associada a cap ingrés, l'elimina de la base de dades i actualitza la llista.
     * Si la categoria té associat almenys un ingrés, mostra un missatge d'error.
     */
    @FXML
    public void eliminarCategoria(ActionEvent event){
        if (ingresDAO.verificarCategories(categoriaActual).size()==0) {
            ingresDAO.delete(categoriaActual);
            update();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Avís");
            alert.setContentText("Aquesta categoria ja està assignada a un ingrés");
            alert.showAndWait();
        }
    }

    /**
     * Actualitza la llista de categories.
     * Carrega les dades de la base de dades i actualitza la llista visual.
     */
    private void update() {
        categories = ingresDAO.selectAll();
        olCategoria = FXCollections.observableArrayList(categories);
        if (llistaCategories != null) {
            llistaCategories.setItems(olCategoria);
        }
    }

    /**
     * Tanca la pàgina actual.
     */
    @FXML
    public void cancelarPagina(){
        this.stage.close();
    }

    /**
     * Defineix l'escenari.
     * @param stage L'escenari actual.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}
