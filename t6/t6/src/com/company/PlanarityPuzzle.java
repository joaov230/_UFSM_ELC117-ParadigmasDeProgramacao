package com.company;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.*;


public class PlanarityPuzzle extends Application {

    Grafo grafo;
    int nivel = 1;
    String strColor = "#FF0000";
    Color color = Color.RED;

    Pane pane;
    Label labelVert;
    Label labelAresta;
    Label labelIntersect;

    Circle c;
    Line line;


    @Override
    public void start(Stage stage) {

        grafo = new Grafo();


        ///////////////////////////////////////////
        // Botões das configs
        Button btnNew = new Button("New");
        Button btnExit = new Button("Exit");
        Button btnVerifyNextLevel = new Button("Done");
        Button btnNextLevel = new Button("Force Next Level");

        btnNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(stage);
            }
        });
        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        btnVerifyNextLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (grafo.verifyIntesection() == 0) {
                    resetGame();
                } else {
                    newWindow();
                }
            }
        });
        btnNextLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetGame();
            }
        });
        ///////////////////////////////////////////

        pane = new Pane();

        createRandomVertexes();

        ////////////////////////////////////////////////
        // Label

        labelVert = new Label(grafo.getSize() + " vertices");
        labelVert.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelVert.setMinSize(90, 30);
        labelVert.setAlignment(Pos.CENTER);

        labelAresta = new Label(grafo.getTotalConnections() + " arestas");
        labelAresta.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelAresta.setMinSize(90, 30);
        labelAresta.setAlignment(Pos.CENTER);

        labelIntersect = new Label (grafo.verifyIntesection() + " interseções");
        labelIntersect.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelIntersect.setMinSize(90,30);
        labelIntersect.setAlignment(Pos.CENTER);

        ////////////////////////////////////////////////

        VBox vb = new VBox();
        vb.setSpacing(2);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(labelVert, labelAresta, labelIntersect);

        ToolBar tbTop = new ToolBar();
        tbTop.setOrientation(Orientation.HORIZONTAL);
        tbTop.getItems().addAll(btnNew, btnExit, btnVerifyNextLevel, btnNextLevel);

        ToolBar tbRight = new ToolBar();
        tbRight.setOrientation(Orientation.VERTICAL);
        tbRight.getItems().addAll(vb);

        /////////////////////////////////////////////////


        BorderPane bp = new BorderPane();
        bp.setCenter(pane);
        bp.setRight(tbRight);
        bp.setTop(tbTop);

        stage.setTitle("t6-jvbeltrame");
        Scene scene = new Scene(bp, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    // Cria uma nova janela avisando que o jogador não acabou ainda o jogo
    private void newWindow () {
        Stage primaryStage = new Stage();
        Label notDoneYet = new Label("Você não completou o nível ainda!");
        Button btnOk = new Button("Ok");
        Button btnNext = new Button("Mas eu quero passar de nível!");

        btnNext.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetGame();
                primaryStage.close();
            }
        });
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(10);
        hb.getChildren().addAll(btnNext, btnOk);

        VBox vb = new VBox();
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(20);
        vb.getChildren().addAll(notDoneYet, hb);

        Scene scene = new Scene(vb, 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //  Define se vai desenhar a linha ou não
    private void setCirculo (Circle circulo) {

        circulo.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Vertice vertAux = grafo.getVertexByShape(circulo);
                Line linhaFinal = grafo.getLineByFinalVertex(vertAux);

                double x = event.getX();
                double y = event.getY();
                if (x < 0 || x > 700)
                    x = circulo.getCenterX();
                if (y < 0 || y > 565)
                    y = circulo.getCenterY();

                if (linhaFinal != null) {
                    linhaFinal.setEndX(x);
                    linhaFinal.setEndY(y);
                }

                Line linhaInicial = grafo.getLineByInitialVertex(vertAux);
                if (linhaInicial != null) {
                    linhaInicial.setStartX(x);
                    linhaInicial.setStartY(y);
                }


                circulo.setCenterX(x);
                circulo.setCenterY(y);

                labelIntersect.setText(grafo.verifyIntesection() + " interseções");
            }
        });
    }


    public void createRandomVertexes () {
        Random gerador = new Random();
        Vertice v1 = null;
        Vertice v2;

        for (int i = 0; (i < (nivel+2)*3) || (grafo.verifyIntesection() < 2); i++) {
            double x = gerador.nextInt(650);
            if (x < 20)
                x += 20;
            double y = gerador.nextInt(550);
            if (y < 20)
                y += 20;
            c = new Circle(x, y,20, color);
            c.setStrokeWidth(2);
            c.setStroke(Color.BLACK);
            grafo.addCircleVertex(c);
            setCirculo(grafo.getLastCircleVertex());
            pane.getChildren().add(grafo.getLastCircleVertex());

            // se for a primeira iteração, v1 é o vertice inicial e continua
            if (i == 0) {
                v1 = grafo.getVertexByShape(c);
                continue;
            }

            // v2 passa a ser o vertice final antes de conectar
            v2 = grafo.getVertexByShape(c);

            if (v1 == null) {
                continue;
            }

            Circle c1 = v1.getCircle();
            Circle c2 = v2.getCircle();

            line = new Line (c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
            line.setStroke(Paint.valueOf(strColor));
            line.setStrokeWidth(3);

            grafo.connectVertex(v1, v2, line);
            pane.getChildren().add(line);

            // v1 passa a ser o novo vertice inicial após conectar
            v1 = grafo.getVertexByShape(c);
        }

        // faz a conexão final
        v1 = grafo.getLastVertex();
        v2 = grafo.getVertex(0);
        Circle c1 = v1.getCircle();
        Circle c2 = v2.getCircle();

        line = new Line (c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        line.setStroke(Paint.valueOf(strColor));
        line.setStrokeWidth(3);

        grafo.connectVertex(v1, v2, line);
        pane.getChildren().add(line);

    }


    public void resetGame () {
        grafo.clear();
        pane.getChildren().clear();
        nivel++;
        createRandomVertexes();
        labelIntersect.setText(grafo.verifyIntesection() + " interseções");
        labelAresta.setText(grafo.getTotalConnections() + " arestas");
        labelAresta.setText(grafo.getSize() + " vertices");
    }


    public static void main (String[] args) { launch(args); }
}
