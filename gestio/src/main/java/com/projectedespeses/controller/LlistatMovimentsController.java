package com.projectedespeses.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.CategoriaDespesaDAO;
import com.projectedespeses.DAO.DespesaDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.model.CategoriaDespesa;
import com.projectedespeses.model.Despeses;
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
 * Controlador per a la visualització del llistat de moviments.
 */
public class LlistatMovimentsController implements Initializable {
    private ObservableList<Moviments> olMoviments;
    private List<Moviments> moviments;
    private Usuari usuariActual;
    private MovimentDAO movimentDAO = new MovimentDAO();
    private Moviments moviment;
    private Stage stage;
    private ObservableList<CategoriaDespesa> olCategoriaDespesa;
    private Set<CategoriaDespesa> categories;
    private CategoriaDespesaDAO categoriaDespesaDAO;
    private DespesaDAO despesaDAO =  new DespesaDAO();

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
    private ChoiceBox<CategoriaDespesa> chCategories;

    /**
     * Constructor del controlador del llistat de moviments.
     * @param usuari Usuari actual que accedeix al llistat.
     */
    public LlistatMovimentsController(Usuari usuari){
        this.usuariActual=usuari;
        moviments = usuariActual.getMovimetnDespesa();
        categoriaDespesaDAO = new CategoriaDespesaDAO();
        categories = new HashSet<CategoriaDespesa>(categoriaDespesaDAO.selectAll());
    }

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
     * Mètode per omplir la informació del moviment seleccionat.
     * @param event Event de clic del ratolí.
     */
    @FXML
    private void omplirInformacio(MouseEvent event){
        try {
            moviment = llistaMoviments.getSelectionModel().getSelectedItem();
            moviment= movimentDAO.selectById(moviment.getIdMoviment());
    
            if (moviment.getTipus().equals(EnumDespesa.DESPESA)) {
                DespesaDAO d = new DespesaDAO();
                Despeses despesa = null;
                despesa=d.selectDespByMoviment(moviment.getIdMoviment());
    
                labDesc.setText(String.valueOf(despesa.getDescripcio()));
                labImport.setText(String.valueOf(despesa.getCost()));
                labData.setText(String.valueOf(despesa.getData()));
                labTipusDesp.setText(String.valueOf(despesa.getTipusdespesa()));
            } else if (moviment.getTipus().equals(EnumDespesa.INGRES)) {
                IngressosDAO i = new IngressosDAO();
                Ingressos ingres = null;
                ingres=i.selectIngresByMovment(moviment.getIdMoviment());
    
                labDesc.setText(String.valueOf(ingres.getDescripcio()));
                labImport.setText(String.valueOf(ingres.getCost()));
                labData.setText(String.valueOf(ingres.getData()));
                labTipusDesp.setText(String.valueOf(ingres.getTipusIngres()));
            } else {
                System.out.println("Enumerat buit");
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setContentText("Selecciona un camp que estigui complet");
            alert.showAndWait();
        }
    }

    /**
     * Mètode per aplicar un filtre als moviments per categoria de despesa.
     * @param event Event de clic del botó.
     */
    @FXML
    public void aplicarFiltre(ActionEvent event){
        CategoriaDespesa enumDespesa = chCategories.getValue();
        if (enumDespesa != null) {
            moviments=despesaDAO.selectDespesesByCategory(enumDespesa, usuariActual);
            olMoviments = FXCollections.observableArrayList(moviments);
            llistaMoviments.setItems(olMoviments);
        }
    }

    /**
     * Mètode per tancar la pàgina actual.
     */
    @FXML
    public void cancelarPagina(){
        this.stage.close();
    }

    /**
     * Estableix l'stage de la finestra actual.
     * @param stage Stage de la finestra actual.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}
