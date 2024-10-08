package com.projectedespeses.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.projectedespeses.DAO.DespesaDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.DAO.MovimentDAO;
import com.projectedespeses.enumerats.EnumDespesa;
import com.projectedespeses.model.Despeses;
import com.projectedespeses.model.Ingressos;
import com.projectedespeses.model.Moviments;
import com.projectedespeses.model.Usuari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class PaginaIniciController implements Initializable {
    @FXML
    private ListView<Moviments> llistaMoviments;

    private ObservableList<Moviments> olMoviments;
    private Usuari usuariactual;
    private MovimentDAO movimentDAO;
    private List<Moviments> moviments;
    private Moviments moviment;
    private DespesaDAO despesaDAO = new DespesaDAO();
    @FXML
    private TextField labtext;
    @FXML
    private TextField labimp;
    @FXML
    private TextField labdat;
    @FXML
    private TextField labtip;
    @FXML
    private Label txtusuari;
    @FXML
    private TextField txtmes;
    @FXML
    private TextField txtany;
    @FXML
    private Label labImport;


    // public void setUsuari(Usuari usuari){
    //     this.usuari= usuari;
    // }

    /**
     * Constructor de la classe.
     * 
     * @param usuari El usuari actual de l'aplicació.
     */
    public PaginaIniciController(Usuari usuari) { //CARREGAR PÀGINA INICI
        movimentDAO = new MovimentDAO();
        System.out.println("ID USUARI " +usuari);
        System.out.println(usuari.getId_usuari());
        moviments = usuari.getMoviment();
        for (Moviments moviments2 : moviments) {
            System.out.println("Prova " +moviments2);
        }
        usuariactual=usuari;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) { //CÀRREGA INACIALITZADORA
        olMoviments = FXCollections.observableArrayList(moviments);
        if (llistaMoviments!=null) {
            llistaMoviments.setItems(olMoviments);
        }
        txtusuari.setText(String.valueOf(usuariactual.getNom()));
    }
    /**
     * Afegir ingres
     * 
     */
    @FXML
    private void afegirIngres(ActionEvent event) throws IOException { 
        System.out.println("Afegir Ingrés");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/AfegirIngres.fxml"));
        loader.setControllerFactory(t -> new AfegirIngresController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Afegir Ingrés");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((AfegirIngresController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }

    /**
     * Llistar Moviments
     * 
     */
    @FXML
    private void llistarMovimetns(ActionEvent event) throws IOException {
        System.out.println("Llistar Moviment");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/llistatMoviments.fxml"));
        loader.setControllerFactory(t -> new LlistatMovimentsController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((LlistatMovimentsController) loader.getController()).setStage(stage);

        stage.showAndWait();
    }
    /**
     * Llistar Moviments ingres
     * 
     */
    @FXML
    private void llistarMovimetnsIngres(ActionEvent event) throws IOException { 
        System.out.println("Llistar Moviment");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/llistarMovimentsIngres.fxml"));
        loader.setControllerFactory(t -> new LlistarMovimentsIngresController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((LlistarMovimentsIngresController) loader.getController()).setStage(stage);

        stage.showAndWait();
    }

    /**
     * Afegir Despesa
     * 
     */
    @FXML
    private void afegirDespesa(ActionEvent event) throws IOException { //AFEGIR INGRÉS
        System.out.println("Afegir Despesa");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/AfegirDespesa.fxml"));
        loader.setControllerFactory(t -> new AfegirDespesaController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Afegir Despesa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((AfegirDespesaController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }

    /**
     * Gestio Categoria
     * 
     */
    @FXML
    private void gestioCategories(ActionEvent event) throws IOException { 
        System.out.println("Gestio Categories");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/gestioCategories.fxml"));
        loader.setControllerFactory(t -> new GestioCategoriaController());
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Afegir Despesa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((GestioCategoriaController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }

    /**
     * Gestio Categoria
     * 
     */
    @FXML
    private void gestioCategoriesDespesa(ActionEvent event) throws IOException { 
        System.out.println("Gestio Categories");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/gestioCategoriesDespesa.fxml"));
        loader.setControllerFactory(t -> new GestioCategoriaDespesaController());
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setTitle("Afegir Despesa");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((GestioCategoriaDespesaController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }

    /**
     * Modificar usuari
     * 
     */
    @FXML
    private void modificarUsuari(ActionEvent event) throws IOException { //AFEGIR INGRÉS
        System.out.println("Modificar Usuari");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/modificarUsuari.fxml"));
        loader.setControllerFactory(t -> new ModificarUsuariController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Modificar Usuari");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((ModificarUsuariController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }

    /**
     * Redireccio grafic
     * 
     */
    @FXML
    private void grafic(ActionEvent event) throws IOException { 
        System.out.println("Gràfics Moviments");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/graficsMoviments.fxml"));
        loader.setControllerFactory(t -> new GraficDespesesController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Gràfics Moviments");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((GraficDespesesController) loader.getController()).setStage(stage);

        stage.showAndWait();

        updateControls();
    }
    /**
     * Omplir info data
     * 
     */
    @FXML
    private void rellenarClient(MouseEvent event) {
        try {
            moviment = llistaMoviments.getSelectionModel().getSelectedItem();

            moviment=movimentDAO.selectById(moviment.getIdMoviment());
            if (moviment.getTipus().equals(EnumDespesa.DESPESA)) {
                DespesaDAO d = new DespesaDAO();
                Despeses despesa = null;
                despesa=d.selectDespByMoviment(moviment.getIdMoviment());
    
                labtext.setText(String.valueOf(despesa.getDescripcio()));
                labimp.setText(String.valueOf(despesa.getCost()));
                labdat.setText(String.valueOf(despesa.getData()));
                labtip.setText(String.valueOf(despesa.getTipusdespesa()));
                System.out.println("DESPESA");
            } else if (moviment.getTipus().equals(EnumDespesa.INGRES)) {
                IngressosDAO i = new IngressosDAO();
                Ingressos ingres = null;
                ingres=i.selectIngresByMovment(moviment.getIdMoviment());
    
                labtext.setText(String.valueOf(ingres.getDescripcio()));
                labimp.setText(String.valueOf(ingres.getCost()));
                labdat.setText(String.valueOf(ingres.getData()));
                labtip.setText(String.valueOf(ingres.getTipusIngres()));
                System.out.println("INGRES");
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
     * Modificar Moviment
     * 
     */
    
    @FXML
    private void modificarMoviemnt(ActionEvent event) throws IOException { //AFEGIR INGRÉS
        System.out.println("Modificar Moviment");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projectedespeses/view/modificarMoviment.fxml"));
        loader.setControllerFactory(t -> new ModificarMovimentController(usuariactual));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Modificar Moviment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setScene(scene);

        ((ModificarMovimentController) loader.getController()).setStage(stage);

        stage.showAndWait();
        updateControls();
    }

    private void updateControls() { //ACTUALITZAR CONTROLADORS OBSERVABLE LIST
        ArrayList<Moviments> m = new ArrayList<Moviments>();
        m=movimentDAO.selectMoviments(usuariactual.getId_usuari());
        usuariactual.setMoviment(m);
        moviments = usuariactual.getMoviment();
        olMoviments = FXCollections.observableArrayList(moviments);
        llistaMoviments.setItems(olMoviments);
    }
    /**
     * Calcul Despesa
     * 
     */
    @FXML
    private void calculDespesaTotal(){
        String datames = txtmes.getText();
        String dataany = txtany.getText();
        if (dataany != null && datames != null) {
            int total = despesaDAO.importTotalMes(usuariactual, datames, dataany);
            labImport.setText(String.valueOf(total));
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Data");
            alert.setContentText("Introduexi la data amb el format correcte mm i yyyy");
            alert.showAndWait();
        }
    }
}
