package com.projectedespeses.controller;

import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.CategoriaDespesaDAO;
import com.projectedespeses.DAO.DespesaDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.enumerats.EnumeratTipusDespesa;
import com.projectedespeses.model.CategoriaDespesa;
import com.projectedespeses.model.CategoriaIngres;
import com.projectedespeses.model.Despeses;
import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador per a la finestra d'afegir despeses.
 */
public class AfegirDespesaController implements Initializable{
    @FXML
    private DatePicker dateMoviment;
    @FXML
    private ChoiceBox<CategoriaDespesa> cbCategoriaD;
    @FXML
    private TextField txtimport;
    @FXML
    private ChoiceBox<EnumeratTipusDespesa> cbTipus;
    @FXML
    private TextArea txtDesc;


    private Stage stage;
    private Usuari usuariActual;
    private ObservableList<CategoriaDespesa> olCategoriaDespesa;
    private Set<CategoriaDespesa> categories;
    private CategoriaDespesaDAO categoriaDespesaDAO;
    private MovimentDAO movimentDAO = new MovimentDAO();
    private DespesaDAO despesaDAO = new DespesaDAO();
    private Moviments pMovimentDespesa;



    /**
     * Constructor que rep l'usuari actual.
     * @param usuari L'usuari actual.
     */
    public AfegirDespesaController(Usuari usuari){
        this.usuariActual=usuari;
        categoriaDespesaDAO = new CategoriaDespesaDAO();
        categories = new HashSet<CategoriaDespesa>(categoriaDespesaDAO.selectAll());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       olCategoriaDespesa = FXCollections.observableArrayList(categories);
       cbCategoriaD.setItems(olCategoriaDespesa);
       cbTipus.getItems().addAll(EnumeratTipusDespesa.values());
    }

    /**
     * Mètode per afegir una despesa.
     * @param event L'esdeveniment que desencadena l'acció.
     */
    @FXML
    public void afegirDespesa(ActionEvent event){
        CategoriaDespesa c = cbCategoriaD.getValue();
        EnumeratTipusDespesa enumerat = cbTipus.getValue();
        String data = String.valueOf(dateMoviment.getValue());
        String descripcio = txtDesc.getText();

        if (c != null && enumerat != null && !data.isEmpty() && descripcio != null) {
            Moviments m = new Moviments();
            m.setIdUsuari(usuariActual.getId_usuari());
            m.setTipus(EnumDespesa.DESPESA);
            System.out.println(m);

            movimentDAO.insert(m); //INSERT MOVIMENTS

            pMovimentDespesa = movimentDAO.selectLastMoviment(); //NUMERO MOVIMENT CREAT

            Despeses d = new Despeses(
                pMovimentDespesa.getIdMoviment(),
                usuariActual.getId_usuari(),
                Date.valueOf(data),
                descripcio,
                Integer.parseInt(txtimport.getText()),
                c.getIdCategoria(),
                enumerat);
            
            despesaDAO.insert(d);

            this.stage.close();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Camps incorrectes, verifica que tots estiguin omplerts");
            alert.showAndWait();
        }
    }

    /**
     * Mètode per cancel·lar l'operació d'afegir una despesa.
     * @param event L'esdeveniment que desencadena l'acció.
     */
    @FXML
    public void cancelarDespesa(ActionEvent event){
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
