package com.company;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import javafx.scene.control.Alert.AlertType;


public class BusLine extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private HttpJSONService http = new HttpJSONService();

    private TableView<TableData> table = new TableView<TableData>();

    private final ObservableList<TableData> data =
            FXCollections.observableArrayList();

    private List<List> dataList;

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        final Label label = new Label("Onibus - RJ");
        label.setFont(new Font("Arial", 20));

        TableColumn<TableData, String> fstCol = new TableColumn<TableData, String>("Data e Hora");
        fstCol.setMinWidth(100);
        fstCol.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());

        TableColumn<TableData, String> sndCol = new TableColumn<TableData, String>("Ordem");
        sndCol.setMinWidth(90);
        sndCol.setCellValueFactory(cellData -> cellData.getValue().ordemProperty());

        TableColumn<TableData, String> trdCol = new TableColumn<TableData, String>("Linha");
        trdCol.setMinWidth(90);
        trdCol.setCellValueFactory(cellData -> cellData.getValue().linhaProperty());

        TableColumn<TableData, String> frthCol = new TableColumn<TableData, String>("Latitude");
        frthCol.setMinWidth(90);
        frthCol.setCellValueFactory(cellData -> cellData.getValue().latitudeProperty());

        TableColumn<TableData, String> fifthCol = new TableColumn<TableData, String>("Longitude");
        fifthCol.setMinWidth(90);
        fifthCol.setCellValueFactory(cellData -> cellData.getValue().longitudeProperty());

        TableColumn<TableData, String> sthCol = new TableColumn<TableData, String>("Velocidade");
        sthCol.setMinWidth(90);
        sthCol.setCellValueFactory(cellData -> cellData.getValue().velocidadeProperty());

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().add(fstCol);
        table.getColumns().add(sndCol);
        table.getColumns().add(trdCol);
        table.getColumns().add(frthCol);
        table.getColumns().add(fifthCol);
        table.getColumns().add(sthCol);

        table.setItems(data);

        Button btn = new Button("Adicione um cronograma de onibus");
        Button btnFilter = new Button("Filtrar");

        Label labelFilter = new Label("Digite a linha para filtrar a tabela:");
        TextField textField = new TextField ();

        btnFilter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String filtro;
                filtro = textField.getText();
                for (int i = 0; i < data.size(); i++) {
                    if (!data.get(i).getLinha().toString().equals(filtro)) {
                        data.remove(i);
                        i--;
                    }
                }
            }
        });


        VBox vb1 = new VBox();
        vb1.setAlignment(Pos.CENTER);
        vb1.getChildren().addAll(labelFilter, textField, btnFilter);

        HBox hb1 = new HBox();
        hb1.setSpacing(30);
        hb1.getChildren().addAll(btn, vb1);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, new Separator(), table, hb1);

        vbox.applyCss();
        vbox.layout();

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Map json = null;
                try {
                    json = http.sendGet("http://dadosabertos.rio.rj.gov.br/apiTransporte/apresentacao/rest/index.cfm/obterTodasPosicoes");
                } catch (Exception e) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Connection failed");
                    alert.setContentText("Please check your Internet connection!");
                    alert.showAndWait();
                }
                if (json != null) {
                    dataList = (List) json.get("DATA");
                    for (List lista : dataList) {
                        data.add(new TableData(lista));
                    }
                    BarChart<String, Number> bchart = createBarChart(data);
                    PieChart pchart = createPieChart(data);
                    VBox vb = new VBox();
                    vb.getChildren().addAll(pchart, bchart);
                    bp.setLeft(vb);
                }
            }
        });

        stage.setScene(new Scene(bp, 1200, 550));
        stage.show();
    }



    private PieChart createPieChart (ObservableList<TableData> dataa) {
        float parados = 0;
        float andando = 0;

        for (TableData entry : dataa) {
            if (Float.parseFloat(entry.getVelocidade()) == 0) {
                parados++;
            } else if (Float.parseFloat(entry.getVelocidade()) > 0) {
                andando++;
            }
        }

        ObservableList<PieChart.Data> pieChartDados =
                FXCollections.observableArrayList(
                        new PieChart.Data("Parados", parados),
                        new PieChart.Data("Andando", andando));
        final PieChart chart = new PieChart(pieChartDados);
        chart.setTitle("Veiculos em movimento");
        chart.setMaxSize(500,250);
        return chart;
    }



    private BarChart<String, Number> createBarChart(ObservableList<TableData> dataa) {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> bchart = new BarChart<String, Number>(xAxis, yAxis);

        xAxis.setLabel("Linha");
        yAxis.setLabel("Quantidade");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Onibus em movimento");

        Map<String,Long> quantidade =
                dataa.stream().filter(lamb -> Float.parseFloat(lamb.getVelocidade()) > 0 && !lamb.getLinha().isEmpty()
                ).collect(Collectors.groupingBy(TableData::getLinha, Collectors.counting()));

        for (TableData entry : dataa) {
            if (quantidade.get(entry.getLinha().toString()) != null) {
                series1.getData().add(new XYChart.Data(entry.getLinha().toString(), quantidade.get(entry.getLinha().toString())));
            }
        }

        bchart.getData().addAll(series1);
        bchart.setMaxSize(500,250);

        return bchart;
    }

}

