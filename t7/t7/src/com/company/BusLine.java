package com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
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
import java.util.Map;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


//  Mostra piadas do Chuck Norris em TableView, usando a API deste site:
//  https://api.chucknorris.io/
//  Exemplifica também:
//    - CellFactory customizada para quebra de linha na segunda coluna
//    - Dialogs em JavaFX (ver mais em: http://code.makery.ch/blog/javafx-dialogs-official/)
//    - Uso de HttpURLConnection para comunicação com servidor web
//    - Uso de ScriptEngine para tratar JSON retornado pelo servidor web
//    - Bug na customização da quebra de linha :-) (primeira linha adicionada não se ajusta)

public class BusLine extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private HttpJSONService http = new HttpJSONService();

    private TableView<TableData> table = new TableView<TableData>();

    private final ObservableList<TableData> data =
            FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new Group());

        final Label label = new Label("Bus");
        label.setFont(new Font("Arial", 20));

        TableColumn<TableData,String> fstCol = new TableColumn<TableData,String>("Data e Hora");
        fstCol.setCellValueFactory(cellData -> cellData.getValue().listaProperty());

//        TableColumn<TableData,String> sndCol = new TableColumn<TableData,String>("Ordem");
//        sndCol.setCellValueFactory(cellData -> cellData.getValue().ordemProperty());
//
//        TableColumn<TableData,String> trdCol = new TableColumn<TableData,String>("Linha");
//        sndCol.setCellValueFactory(cellData -> cellData.getValue().linhaProperty());
//
//        TableColumn<TableData,String> frthCol = new TableColumn<TableData,String>("Latitude");
//        sndCol.setCellValueFactory(cellData -> cellData.getValue().latitudeProperty());
//
//        TableColumn<TableData,String> fifthCol = new TableColumn<TableData,String>("Longitude");
//        sndCol.setCellValueFactory(cellData -> cellData.getValue().longitudeProperty());
//
//        TableColumn<TableData,String> sthCol = new TableColumn<TableData,String>("Velocidade");
//        sndCol.setCellValueFactory(cellData -> cellData.getValue().ordemProperty());

        // Corrige a quebra de linha
//        fstCol.setCellFactory(column -> {
//            return new TableCell<TableData, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item == null || empty) {
//                        setText(null);
//                        setStyle("");
//                    } else {
//                        Text text = new Text(item.toString());
//
//                        text.wrappingWidthProperty().bind(widthProperty());
//                        text.textProperty().bind(itemProperty());
//                        this.setWrapText(true);
//                        setGraphic(text);
//                    }
//                }
//            };
//        });


        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().add(fstCol);
//        table.getColumns().add(sndCol);
//        table.getColumns().add(trdCol);
//        table.getColumns().add(frthCol);
//        table.getColumns().add(fifthCol);
//        table.getColumns().add(sthCol);

        table.setItems(data);

        Button btn = new Button("Add a bus");

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
                if (json != null)
                    data.add(new TableData((String)json.get("DATA")));
            }
        });

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        vbox.getChildren().addAll(label, table, btn);

        vbox.applyCss();
        vbox.layout();

        stage.setScene(new Scene(vbox, 800, 500));
        stage.show();

    }

    public class TableData {
        private final SimpleStringProperty lista;
//        private final SimpleStringProperty ordem;
//        private final SimpleStringProperty linha;
//        private final SimpleStringProperty latitude;
//        private final SimpleStringProperty longitude;
//        private final SimpleStringProperty velocidade;

        private TableData(String lis) {
            this.lista = new SimpleStringProperty(lis);
//            this.ordem = new SimpleStringProperty(ord);
//            this.linha = new SimpleStringProperty(lin);
//            this.latitude = new SimpleStringProperty(lat);
//            this.longitude = new SimpleStringProperty(longi);
//            this.velocidade = new SimpleStringProperty(vel);

        }
        public SimpleStringProperty listaProperty() {
            return lista;
        }
        public String getLista() {
            return lista.get();
        }
        public void setLista(String dh) {
            this.lista.set(dh);
        }
//
//        public SimpleStringProperty ordemProperty() {
//            return ordem;
//        }
//        public String getOrdem() {
//            return ordem.get();
//        }
//        public void setOrdem(String ord) {
//            this.ordem.set(ord);
//        }
//
//        public SimpleStringProperty linhaProperty() {
//            return linha;
//        }
//        public String getLinha() {
//            return linha.get();
//        }
//        public void setLinha(String lin) {
//            this.linha.set(lin);
//        }
//
//        public SimpleStringProperty latitudeProperty() {
//            return latitude;
//        }
//        public String getLatitude() {
//            return latitude.get();
//        }
//        public void setLatitude(String lat) {
//            this.latitude.set(lat);
//        }
//
//        public SimpleStringProperty longitudeProperty() {
//            return longitude;
//        }
//        public String getLongitude() {
//            return longitude.get();
//        }
//        public void setLongitude(String longi) {
//            this.longitude.set(longi);
//        }
//
//        public SimpleStringProperty velocidadeProperty() {
//            return velocidade;
//        }
//        public String getVelocidade() {
//            return velocidade.get();
//        }
//        public void setVelocidade(String vel) {
//            this.velocidade.set(vel);
//        }
    }
}

