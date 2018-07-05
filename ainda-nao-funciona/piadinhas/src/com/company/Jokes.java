package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Jokes extends Application {

    FileReader file;
    ArrayList<String> piadocas;

    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Label lblPiadas = new Label();

        Button btnGerarPiadas = new Button("Gerar piadas");
        btnGerarPiadas.setDisable(true);
        btnGerarPiadas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Random rnd = new Random();
                lblPiadas.setText(piadocas.get(0));
            }
        });

        VBox vb = new VBox();
        vb.setSpacing(50);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(lblPiadas, btnGerarPiadas);


        Button btnSelectFile = new Button("Escolher arquivo");
        btnSelectFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage2 = new Stage();

                Label label = new Label("Digite o nome do arquivo");
                TextField fileSelect = new TextField();
                Button btnOpenFile = new Button("Abrir arquivo");
                btnOpenFile.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        file = new FileReader(fileSelect.getCharacters().toString());
                        System.out.println(fileSelect.getCharacters().toString());
                        if (file.fileIsOpen()) {
                            piadocas = file.readWholeFile();
                            btnGerarPiadas.setDisable(false);
                        }
                        stage2.close();
                    }
                });

                HBox hb = new HBox();
                hb.setSpacing(5);
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().addAll(fileSelect, btnOpenFile);

                VBox vb = new VBox();
                vb.setAlignment(Pos.CENTER);
                vb.setSpacing(10);
                vb.getChildren().addAll(label, hb);

                stage2.setScene(new Scene(vb, 300, 100));
                stage2.show();
            }
        });

        ToolBar tb = new ToolBar();
        tb.setOrientation(Orientation.HORIZONTAL);
        tb.getItems().addAll(btnSelectFile);

        BorderPane bp = new BorderPane();
        bp.setTop(tb);
        bp.setCenter(vb);

        stage.setScene(new Scene(bp, 1200, 550));
        stage.show();
    }


}
