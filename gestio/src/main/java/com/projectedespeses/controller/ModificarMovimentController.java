package com.projectedespeses.controller;

import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.projectedespeses.DAO.DespesaDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.enumerats.EnumeratTipusDespesa;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ModificarMovimentController implements Initializable{
    @FXML
    private TextField txtDesc;
    @FXML
    private TextField txtImport;
    @FXML
    private TextField txtData;
    @FXML
    private TextField txtTipus;
    @FXML
    private ListView<Moviments> llistaMoviments;

    private Stage stage;
    private ObservableList<Moviments> olMoviments;
    private Set<Moviments> moviments;
    private Moviments movimentActual;
    private MovimentDAO movimentDAO;
    private IngressosDAO ingressosDAO = new IngressosDAO();
    private DespesaDAO despesaDAO = new DespesaDAO();
    private Usuari usuariactual;

    /**
     * Constructor del controlador ModificarMovimentController.
     * @param usuari L'usuari que està fent la modificació.
     */
    public ModificarMovimentController(Usuari usuari){
        this.usuariactual = usuari;
        movimentDAO = new MovimentDAO();
        System.out.println("ID USUARI " +usuari);
        System.out.println(usuari.getId_usuari());
        moviments = new HashSet(movimentDAO.selectMovimentsById(usuari.getId_usuari()));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        olMoviments = FXCollections.observableArrayList(moviments);
        if (llistaMoviments!=null) {
            llistaMoviments.setItems(olMoviments);
        }
        System.out.println("funca");
    }

    /**
     * Mètode per a omplir els camps de moviment seleccionat.
     * @param event L'esdeveniment del ratolí.
     */
    @FXML
    private void rellenarClient(MouseEvent event) {
        try {
            movimentActual = llistaMoviments.getSelectionModel().getSelectedItem();

            movimentActual=movimentDAO.selectById(movimentActual.getIdMoviment());
            
            
            if (movimentActual.getTipus().equals(EnumDespesa.DESPESA)) {
                DespesaDAO d = new DespesaDAO();
                Despeses despesa = null;
                despesa=d.selectDespByMoviment(movimentActual.getIdMoviment());
    
                txtDesc.setText(String.valueOf(despesa.getDescripcio()));
                txtImport.setText(String.valueOf(despesa.getCost()));
                txtData.setText(String.valueOf(despesa.getData()));
                txtTipus.setText(String.valueOf(despesa.getTipusdespesa()));
                System.out.println("DESPESA");
                
            } else if (movimentActual.getTipus().equals(EnumDespesa.INGRES)) {
                IngressosDAO i = new IngressosDAO();
                Ingressos ingres = null;
                ingres=i.selectIngresByMovment(movimentActual.getIdMoviment());
    
                txtDesc.setText(String.valueOf(ingres.getDescripcio()));
                txtImport.setText(String.valueOf(ingres.getCost()));
                txtData.setText(String.valueOf(ingres.getData()));
                txtTipus.setText(String.valueOf(ingres.getTipusIngres()));
    
                System.out.println(movimentActual);
                System.out.println(movimentActual.getIdMoviment());
                
            } else {
                System.out.println("Enumerat buit");
                this.stage.close();
            }
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Alerta");
            alert.setContentText("Selecciona un camp que estigui complet");
            alert.showAndWait();
        }
    }
    /**
     * Mètode per a modificar el moviment seleccionat.
     * @param event L'esdeveniment del botó modificar.
     */
    @FXML
    private void modificar(ActionEvent event){
        String desc = txtDesc.getText();
        int cost = Integer.parseInt(txtImport.getText());
        Date data = Date.valueOf(txtData.getText());
        EnumeratTipusDespesa enumeratDespesa = EnumeratTipusDespesa.valueOf(txtTipus.getText());
        if (desc != null && cost != 0 && data != null && enumeratDespesa != null) {
            if (movimentActual.getTipus().equals(EnumDespesa.INGRES)) {
                Ingressos i = new Ingressos(data, desc, cost, enumeratDespesa);
                i.setIdMoviment(movimentActual.getIdMoviment());
                
                ingressosDAO.update(i);
                
                System.out.println("Modificat correctament");
            } else if (movimentActual.getTipus().equals(EnumDespesa.DESPESA)) {
                Despeses d = new Despeses(data, desc, cost, enumeratDespesa);
                d.setIdMoviment(movimentActual.getIdMoviment());
                
                despesaDAO.update(d);
                
            } else {
                System.out.println("ERROR -> NO HA TROBAT CAM TIPUS MOVIMENT");
                this.stage.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Camps incorrectes, verifica que tots estiguin omplerts");
            alert.showAndWait();
        }
        update();
    }

    /**
     * Mètode per a eliminar el moviment seleccionat.
     * @param event L'esdeveniment del botó eliminar.
     */
    @FXML
    private void eliminar(ActionEvent event){
        if (movimentActual.getTipus().equals(EnumDespesa.INGRES)) {
            Ingressos i = new Ingressos();
            i.setIdMoviment(movimentActual.getIdMoviment());
            
            ingressosDAO.delete(i);
            System.out.println("Eliminat Correctament");
        } else if (movimentActual.getTipus().equals(EnumDespesa.DESPESA)) {
            Despeses d = new Despeses();
            d.setIdMoviment(movimentActual.getIdMoviment());
            
            despesaDAO.delete(d);
            System.out.println("Eliminat Correctament");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No hi ha cap tipus de moviment");
            alert.showAndWait();
        }
        update();
    }

    /**
     * Mètode per a actualitzar.
     */
    public void update(){
        moviments = new HashSet(movimentDAO.selectMovimentsById(usuariactual.getId_usuari()));
        olMoviments = FXCollections.observableArrayList(moviments);
        if (llistaMoviments!=null) {
            llistaMoviments.setItems(olMoviments);
        }
    }

    @FXML
    public void cancelarMoviment(ActionEvent event){
        this.stage.close();
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
