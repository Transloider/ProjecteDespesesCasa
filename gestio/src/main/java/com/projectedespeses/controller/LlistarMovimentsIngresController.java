package com.projectedespeses.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.CategoriaIngresDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.model.CategoriaIngres;
import com.projectedespeses.model.Ingressos;
import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controlador per a la visualització i gestió dels moviments d'ingressos de l'usuari.
 */
public class LlistarMovimentsIngresController implements Initializable{
    private ObservableList<Moviments> olMoviments;
    private List<Moviments> moviments;
    private Usuari usuariActual;
    private MovimentDAO movimentDAO = new MovimentDAO();
    private Moviments moviment;
    private Stage stage;
    private ObservableList<CategoriaIngres> olCategoriaDespesa;
    private Set<CategoriaIngres> categories;
    private CategoriaIngresDAO categoriaIngresDAO;
    private IngressosDAO ingressosDAO = new IngressosDAO();

    @FXML
    private ListView<Moviments> llistaMoviments;
    @FXML
    private Label labData;
    @FXML
    private Label labDesc;
    @FXML
    private Label labImport;
    @FXML
    private Label labTipusDesp;
    @FXML
    private ChoiceBox<CategoriaIngres> chCategories;

    /**
     * Constructor de la classe.
     * @param usuari L'usuari actual per al qual es mostraran els moviments d'ingressos.
     */
    public LlistarMovimentsIngresController(Usuari usuari){
        this.usuariActual=usuari;
        moviments = usuariActual.getMovimetnIngres();
        categoriaIngresDAO = new CategoriaIngresDAO();
        categories = new HashSet<CategoriaIngres>(categoriaIngresDAO.selectAll());
    }

    /**
     * Mètode d'inicialització del controlador.
     * Carrega els moviments d'ingressos de l'usuari i les categories disponibles.
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        olMoviments = FXCollections.observableArrayList(moviments);
        if (llistaMoviments != null) {
            llistaMoviments.setItems(olMoviments);
        }
        olCategoriaDespesa = FXCollections.observableArrayList(categories);
        chCategories.setItems(olCategoriaDespesa);
    }

    /**
     * Omple les etiquetes d'informació amb els detalls del moviment d'ingrés seleccionat.
     */
    @FXML
    private void omplirInformacio(MouseEvent event){
        try {
            moviment = llistaMoviments.getSelectionModel().getSelectedItem();
            moviment= movimentDAO.selectById(moviment.getIdMoviment());
    
            if (moviment.getTipus().equals(EnumDespesa.INGRES)) {
                IngressosDAO i = new IngressosDAO();
                Ingressos ingres;
                ingres=i.selectIngByMoviment(moviment.getIdMoviment());
                labDesc.setText(String.valueOf(ingres.getDescripcio()));
                labImport.setText(String.valueOf(ingres.getCost()));
                labData.setText(String.valueOf(ingres.getData()));
                labTipusDesp.setText(String.valueOf(ingres.getTipusIngres()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setContentText("Selecciona un camp que estigui complet");
            alert.showAndWait();
        }
    }

    /**
     * Aplica un filtre als moviments d'ingressos segons la categoria seleccionada.
     */
    @FXML
    public void aplicarFiltre(ActionEvent event){
        CategoriaIngres categoriaIngres = chCategories.getValue();
        if (categoriaIngres != null) {
            moviments=ingressosDAO.selectIngressosByCategory(categoriaIngres, usuariActual);
            olMoviments = FXCollections.observableArrayList(moviments);
            llistaMoviments.setItems(olMoviments);
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
