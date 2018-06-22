package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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

        final Label label = new Label("Bus");
        label.setFont(new Font("Arial", 20));

        TableColumn<TableData,String> fstCol = new TableColumn<TableData,String>("Data e Hora");
        fstCol.setMinWidth(100);
        fstCol.setCellValueFactory(cellData -> cellData.getValue().dataHoraProperty());

        TableColumn<TableData,String> sndCol = new TableColumn<TableData,String>("Ordem");
        sndCol.setMinWidth(90);
        sndCol.setCellValueFactory(cellData -> cellData.getValue().ordemProperty());

        TableColumn<TableData,String> trdCol = new TableColumn<TableData,String>("Linha");
        trdCol.setMinWidth(90);
        trdCol.setCellValueFactory(cellData -> cellData.getValue().linhaProperty());

        TableColumn<TableData,String> frthCol = new TableColumn<TableData,String>("Latitude");
        frthCol.setMinWidth(90);
        frthCol.setCellValueFactory(cellData -> cellData.getValue().latitudeProperty());

        TableColumn<TableData,String> fifthCol = new TableColumn<TableData,String>("Longitude");
        fifthCol.setMinWidth(90);
        fifthCol.setCellValueFactory(cellData -> cellData.getValue().longitudeProperty());

        TableColumn<TableData,String> sthCol = new TableColumn<TableData,String>("Velocidade");
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

        Button btn = new Button("Add a bus list");

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
                }
            }
        });


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, new Separator(), table, btn);

        vbox.applyCss();
        vbox.layout();

        ToolBar rightToolBar = new ToolBar();
        rightToolBar.setOrientation(Orientation.VERTICAL);
        rightToolBar.getItems().add(vbox);

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);

//        HBox hbox = new HBox();
//        hbox.setSpacing(30);
//        hbox.setAlignment(Pos.CENTER);
//        hbox.getChildren().addAll(vbox);

        stage.setScene(new Scene(bp, 1000, 500));
        stage.show();

    }

}

