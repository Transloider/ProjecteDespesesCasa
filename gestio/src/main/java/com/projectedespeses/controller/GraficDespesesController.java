package com.projectedespeses.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.projectedespeses.DAO.DespesaDAO;
import com.projectedespeses.DAO.IngressosDAO;
import com.projectedespeses.model.Despeses;
import com.projectedespeses.model.Ingressos;
import com.projectedespeses.model.Usuari;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Controlador per a la generació de gràfics de despeses.
 */
public class GraficDespesesController {
    @FXML
    private BarChart<String, Number> barChart;

    private DespesaDAO despesaDAO = new DespesaDAO();
    private IngressosDAO ingressosDAO = new IngressosDAO();
    private List<Despeses> despesesList;
    private List<Ingressos> ingressosList;
    private Stage stage;
    private Usuari usuari;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor de la classe.
     * @param usuari El usuari per al qual es generaran els gràfics de despeses.
     */
    public GraficDespesesController(Usuari usuari){
        this.usuari = usuari;
    }

    /**
     * Mètode d'inicialització del controlador.
     * Carrega les despeses i els ingressos de l'usuari i actualitza el gràfic.
     */
    public void initialize() {
        despesesList = despesaDAO.selectAllByUser(usuari);
        ingressosList = ingressosDAO.selectAllByUser(usuari);
        updateBarChart();
    }

    /**
     * Actualitza el gràfic de barres amb les despeses i ingressos mensuals.
     */
    private void updateBarChart() {
        if ((despesesList == null || despesesList.isEmpty()) && (ingressosList == null || ingressosList.isEmpty())) {
            return;
        }

        Map<YearMonth, Integer> monthlyExpenses = despesesList.stream()
                .collect(Collectors.groupingBy(
                        despesa -> parseDate(despesa.getData().toString()),
                        Collectors.summingInt(Despeses::getCost)
                ));

        Map<YearMonth, Integer> monthlyIncomes = ingressosList.stream()
                .collect(Collectors.groupingBy(
                        ingres -> parseDate(ingres.getData().toString()),
                        Collectors.summingInt(Ingressos::getCost)
                ));

        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        expenseSeries.setName("TOTAL DESPESES MENSUALS");

        for (Map.Entry<YearMonth, Integer> entry : monthlyExpenses.entrySet()) {
            if (entry.getKey() != null) {
                expenseSeries.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
        }

        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();
        incomeSeries.setName("TOTAL INGRESSOS MENSUALS");

        for (Map.Entry<YearMonth, Integer> entry : monthlyIncomes.entrySet()) {
            if (entry.getKey() != null) {
                incomeSeries.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
            }
        }

        barChart.getData().clear();
        barChart.getData().addAll(expenseSeries, incomeSeries);

        barChart.getXAxis().setLabel("Mes");
        barChart.getYAxis().setLabel("Cost");
        barChart.setTitle("Gràfic de Despeses i Ingressos Mensuals");
    }

    /**
     * Converteix una data de tipus String en un objecte YearMonth.
     * @param dateString La data en format de cadena de caràcters.
     * @return L'objecte YearMonth corresponent a la data.
     */
    private YearMonth parseDate(String dateString) {
        try {
            LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
            return YearMonth.from(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Cancel·la l'acció i tanca la finestra actual si n'hi ha una.
     */
    @FXML
    public void cancelarPagina(){
        if (this.stage != null) {
            this.stage.close();
        }
    }

    /**
     * Estableix l'escenari actual.
     * @param stage L'escenari actual.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
