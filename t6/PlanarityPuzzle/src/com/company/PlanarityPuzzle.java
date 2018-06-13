package com.company;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sun.awt.Symbol;
import sun.security.provider.certpath.Vertex;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class PlanarityPuzzle extends Application {

    Grafo grafo;
    int nivel = 1;
    String strColor = "#FF0000";
    Color color = Color.RED;

    boolean first_vert = true;
    Vertice vert;

    Pane pane;
    Label labelVert;
    Label labelAresta;
    Label labelIntersect;

    Rectangle rect;
    Circle c;
    Line line;


    @Override
    public void start(Stage stage) {

        grafo = new Grafo();


        ///////////////////////////////////////////

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

        ///////////////////////////////////////////
        // Botões das configs
        Button btnNew = new Button("New");
        Button btnExit = new Button("Exit");

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
        ///////////////////////////////////////////


        VBox vb = new VBox();
        vb.setSpacing(2);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(labelVert, labelAresta, labelIntersect);

        ToolBar tbTop = new ToolBar();
        tbTop.setOrientation(Orientation.HORIZONTAL);
        tbTop.getItems().addAll(btnNew, btnExit);

        ToolBar tbRight = new ToolBar();
        tbRight.setOrientation(Orientation.VERTICAL);
        tbRight.getItems().addAll(vb);

        pane = new Pane();


        Random gerador = new Random();
        Vertice v1 = null;
        Vertice v2;

        for (int i = 0; i < (nivel+2)*3; i++) {
            double x = gerador.nextInt(650);
            if (x < 20)
                x += 20;
            double y = gerador.nextInt(550);
            if (y < 20)
                y += 20;
            c = new Circle(x, y,20, color);
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
        v1 = grafo.getVertex(0);
        v2 = grafo.getLastVertex();
        Circle c1 = v1.getCircle();
        Circle c2 = v2.getCircle();

        Line lineAux = new Line (c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
        lineAux.setStroke(Paint.valueOf(strColor));
        lineAux.setStrokeWidth(3);

        grafo.connectVertex(v1, v2, lineAux);
        pane.getChildren().add(lineAux);





        BorderPane bp = new BorderPane();
        bp.setCenter(pane);
        bp.setRight(tbRight);
        bp.setTop(tbTop);

        stage.setTitle("t6-jvbeltrame");
        Scene scene = new Scene(bp, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


    double difCentroX;
    double difCentroY;

    //  Define se vai desenhar a linha ou não
    private void setCirculo (Circle circulo) {

        circulo.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Vertice vertAux = grafo.getVertexByShape(circulo);
                Line linhaFinal = grafo.getLineByFinalVertex(vertAux);
                if (linhaFinal != null) {
                    linhaFinal.setEndX(event.getX());
                    linhaFinal.setEndY(event.getY());
                }

                Line linhaInicial = grafo.getLineByInitialVertex(vertAux);
                if (linhaInicial != null) {
                    linhaInicial.setStartX(event.getX());
                    linhaInicial.setStartY(event.getY());
                }

                circulo.setCenterX(event.getX());
                circulo.setCenterY(event.getY());
            }
        });



    }


    public static void main (String[] args) { launch(args); }
}
