package com.projectedespeses.controller;

import java.net.URL;
import java.nio.channels.NetworkChannel;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.CategoriaIngresDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.enumerats.EnumeratTipusDespesa;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador per a la finestra d'afegir ingressos.
 */
public class AfegirIngresController implements Initializable{

    @FXML
    private DatePicker dateMoviment;
    @FXML
    private ComboBox<CategoriaIngres> cbCategoriaI;
    @FXML
    private TextField txtimport;
    @FXML
    private ComboBox<EnumeratTipusDespesa> cbTipus;
    @FXML
    private TextArea txtDesc;

    
    private Stage stage;
    private Usuari usuariactual;
    private ObservableList<CategoriaIngres> olCategoriaIngres;
    private CategoriaIngresDAO categoriaIngresDAO;
    private MovimentDAO movimentDAO = new MovimentDAO();
    private IngressosDAO ingresDAO = new IngressosDAO();
    private Set<CategoriaIngres> categories;
    private Moviments pMovimentIngres;

    /**
     * Constructor que rep l'usuari actual.
     * @param usuari L'usuari actual.
     */
    public AfegirIngresController(Usuari usuari){
        this.usuariactual=usuari;
        categoriaIngresDAO = new CategoriaIngresDAO();
        categories = new HashSet(categoriaIngresDAO.selectAll());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        olCategoriaIngres = FXCollections.observableArrayList(categories);
        cbCategoriaI.setItems(olCategoriaIngres);
        cbTipus.getItems().addAll(EnumeratTipusDespesa.values());
    }

    /**
     * Mètode per afegir un moviment d'ingrés.
     * @param event L'esdeveniment que desencadena l'acció.
     */
    @FXML
    public void afegirMoviment(ActionEvent event){
        CategoriaIngres c = cbCategoriaI.getValue();
        EnumeratTipusDespesa enumerat = cbTipus.getValue();
        String data = String.valueOf(dateMoviment.getValue());
        String descripcio = txtDesc.getText();
        System.out.println("Fa Algo" +usuariactual.getId_usuari());
        if (c != null && enumerat != null && !data.isEmpty() && descripcio != null) {
            Moviments m = new Moviments();
            m.setIdUsuari(usuariactual.getId_usuari());
            m.setTipus(EnumDespesa.INGRES);
            System.out.println(m);

            movimentDAO.insert(m); //INSERT MOVIMENTS

            pMovimentIngres = movimentDAO.selectLastMoviment(); //NUMERO MOVIMENT CREAT

            Ingressos i = new Ingressos( //CREACIO OBJECTE INGRESSOS
                pMovimentIngres.getIdMoviment(), 
                usuariactual.getId_usuari(), 
                Date.valueOf(data) , 
                descripcio, 
                Integer.parseInt(txtimport.getText()),
                c.getId_categoria() , 
                enumerat);

            ingresDAO.insert(i);

            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Camps incorrectes, verifica que tots estiguin omplerts");
            alert.showAndWait();
        }
    }

    /**
     * Mètode per cancel·lar l'operació d'afegir un ingrés.
     * @param event L'esdeveniment que desencadena l'acció.
     */
    @FXML
    public void cancelarIngres(ActionEvent event){
        this.stage.close();
    }

    /**
     * Estableix l'escenari de la finestra.
     * @param stage L'escenari de la finestra.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }
}
