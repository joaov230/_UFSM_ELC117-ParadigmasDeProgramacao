package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;


public class HelloImage extends Application {
    public int imgcontroler = 0;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        Image[] imagem = new Image[6];
        imagem[0] = new Image("acacia.png");
        imagem[1] = new Image("carvalho.png");
        imagem[2] = new Image("carvalhoescuro.png");
        imagem[3] = new Image("eucalipto.png");
        imagem[4] = new Image("pinheiro.png");
        imagem[5] = new Image("selva.png");
        ImageView imageView = new ImageView(imagem[imgcontroler]);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(150);


        Button bleft = new Button("<");
        bleft.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (imgcontroler > 0) {
                    imgcontroler--;
                } else {
                    imgcontroler = imagem.length-1;
                }
                imageView.setImage(imagem[imgcontroler]);
            }
        });

        Button bright = new Button(">");
        bright.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (imgcontroler < imagem.length-1) {
                    imgcontroler++;
                } else {
                    imgcontroler = 0;
                }
                imageView.setImage(imagem[imgcontroler]);
            }
        });

        HBox hb = new HBox();
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        hb.getChildren().addAll(bleft,imageView,bright);

        stage.setScene(new Scene(hb, 300, 250));
        stage.show();
    }
}
