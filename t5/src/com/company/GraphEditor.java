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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;


public class GraphEditor extends Application {

    Grafo grafo;
    String strColor = "#FF0000";
    Color color = Color.RED;
    int shapeControler;
    //    0 when circle
    //    1 when square

    boolean click;
    Vertice vert;

    Pane pane;
    Label labelVert;
    Label labelAresta;
    Label labelSelect;
    Label labelIntersect;

    Rectangle rect;
    Circle c;
    Line line;


    @Override
    public void start(Stage stage) {

        click = false;

        grafo = new Grafo();

        ///////////////////////////////////////////
        // Botões das cores
        Button btnRed = new Button();
        Button btnGreen = new Button();
        Button btnBlue = new Button();

        btnRed.setMinSize(90, 30);
        btnGreen.setMinSize(90, 30);
        btnBlue.setMinSize(90, 30);

        btnRed.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        btnGreen.setBackground(new Background(new BackgroundFill(Color.LIMEGREEN, null, null)));
        btnBlue.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));

        btnRed.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                strColor = "#FF0000";
                color = Color.RED;
            }
        });
        btnGreen.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                strColor = "#32CD32";
                color = Color.LIMEGREEN;
            }
        });
        btnBlue.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                strColor = "#0000FF";
                color = Color.BLUE;
            }
        });
        ///////////////////////////////////////////

        ///////////////////////////////////////////
        // Botões das formas
        Button btnShapeCircle = new Button("Circulo");
        Button btnShapeSquare = new Button("Quadrado");

        btnShapeCircle.setMinSize(90, 30);
        btnShapeSquare.setMinSize(90, 30);

        btnShapeCircle.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                shapeControler = 0;
            }
        });
        btnShapeSquare.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                shapeControler = 1;
            }
        });
        ///////////////////////////////////////////

        labelVert = new Label(grafo.getSize() + " vertices");
        labelVert.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelVert.setMinSize(90, 30);
        labelVert.setAlignment(Pos.CENTER);

        labelAresta = new Label(grafo.getTotalConnections() + " arestas");
        labelAresta.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelAresta.setMinSize(90, 30);
        labelAresta.setAlignment(Pos.CENTER);

        labelSelect = new Label("Select");
        labelSelect.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelSelect.setMinSize(90, 30);
        labelSelect.setAlignment(Pos.CENTER);

        labelIntersect = new Label (grafo.verifyIntesection() + " interseções");
        labelIntersect.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        labelIntersect.setMinSize(90,30);
        labelIntersect.setAlignment(Pos.CENTER);

        ///////////////////////////////////////////
        // Botões das configs
        Button btnNew = new Button("New");
        Button btnSave = new Button("Save");
        Button btnExit = new Button("Exit");

        btnNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                start(stage);
            }
        });
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    SnapshotParameters parameters = new SnapshotParameters();
                    WritableImage wi = new WritableImage(800,600);
                    WritableImage snapshot = pane.snapshot(new SnapshotParameters(), null);

                    File output = new File ("C:/Users/Seven/Pictures/IDE/snapshot-" + new Date().getTime() + ".png");
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        vb.getChildren().addAll(labelVert, labelAresta, labelIntersect, labelSelect);

        ToolBar tbTop = new ToolBar();
        tbTop.setOrientation(Orientation.HORIZONTAL);
        tbTop.getItems().addAll(btnNew, btnSave, btnExit);

        ToolBar tbRight = new ToolBar();
        tbRight.setOrientation(Orientation.VERTICAL);
        tbRight.getItems().addAll(btnShapeCircle, btnShapeSquare,btnRed, btnGreen, btnBlue, vb);

        pane = new Pane();



//      Cria um shape e um vértice novo
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (shapeControler == 1) {
                    rect = new Rectangle(event.getX(),event.getY(),0,0);
                    rect.setFill(Paint.valueOf(strColor));
                    setRetangulo(rect);
                    grafo.addRectVertex(shapeControler, rect);
                    pane.getChildren().add(grafo.getLastRectVertex());
                } else if (shapeControler == 0) {
                    c = new Circle(event.getX(), event.getY(), 0, color);
                    setCirculo(c);
                    grafo.addCircleVertex(shapeControler, c);
                    pane.getChildren().add(grafo.getLastCircleVertex());
                }
            }
        });
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (shapeControler == 1) {
                    double dist = Math.abs(event.getX() - rect.getX());
                    if (dist > 60)
                        dist = 60;
                    rect.setWidth(dist);
                    rect.setHeight(dist);
                    rect.setArcHeight(10);
                    rect.setArcWidth(10);
                    labelVert.setText(grafo.getSize() + " vertices");
                } else if (shapeControler == 0){
                    double dist = Math.abs(Math.hypot(event.getX(), event.getY()) - Math.hypot(c.getCenterX(), c.getCenterY()));
                    if (dist >= 30)
                        dist = 30;
                    c.setRadius(dist);
                    labelVert.setText(grafo.getSize() + " vertices");
                }
            }
        });


        BorderPane bp = new BorderPane();
        bp.setCenter(pane);
        bp.setRight(tbRight);
        bp.setTop(tbTop);

        Scene scene = new Scene(bp, 800, 600);
        stage.setScene(scene);
        stage.show();
    }


//  Define se vai desenhar a linha ou não
    private void setCirculo (Circle circulo) {
        circulo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (click == false) {
                    click = true;
                    vert = grafo.getVertexByShape(circulo);

                    labelSelect.setText("Selected");
                    labelSelect.setTextFill(Paint.valueOf("#FF0000"));
                    labelSelect.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                } else if (click == true) {
                    Vertice vertiLocal = grafo.getVertexByShape(circulo);
                    if (!(vert == vertiLocal || vert.isConnected(vertiLocal))) {
                        click = false;
                        drawLine(vert, vertiLocal);
                        grafo.connectVertex(vert, vertiLocal, line);
                        pane.getChildren().add(vert.getAdj(vert, vertiLocal).getLine());
                        labelAresta.setText(grafo.getTotalConnections() + " arestas");

                        labelIntersect.setText(grafo.verifyIntesection() + " interceções");

                        labelSelect.setText("Select");
                        labelSelect.setTextFill(Paint.valueOf("#000000"));
                        labelSelect.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    }
                }
            }
        });
    }

//  Define se vai desenhar a linha ou não
    public void setRetangulo (Rectangle retangulo) {
        retangulo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (click == false) {
                    click = true;
                    vert = grafo.getVertexByShape(retangulo);
                    labelSelect.setText("Selected");
                    labelSelect.setTextFill(Paint.valueOf("#FF0000"));
                    labelSelect.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                } else {
                    Vertice vertiLocal = grafo.getVertexByShape(retangulo);
                    if (!(vert == vertiLocal || vert.isConnected(vertiLocal))) {
                        click = false;
                        drawLine(vert, vertiLocal);
                        grafo.connectVertex(vert, vertiLocal, line);
                        pane.getChildren().add(vert.getAdj(vert, vertiLocal).getLine());
                        labelAresta.setText(grafo.getTotalConnections() + " arestas");
                        labelSelect.setText("Select");
                        labelSelect.setTextFill(Paint.valueOf("#000000"));
                        labelSelect.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                    }
                }
            }
        });
    }

    public void drawLine (Vertice vert1, Vertice vert2) {
        int shape1 = vert1.getVertexShape();
        int shape2 = vert2.getVertexShape();

        if (shape1 == 0 && shape2 == 0) {
            Circle c1 = vert1.getCircle();
            Circle c2 = vert2.getCircle();

            line = new Line (c1.getCenterX(), c1.getCenterY(), c2.getCenterX(), c2.getCenterY());
            line.setStroke(Paint.valueOf(strColor));
            line.setStrokeWidth(3);
        }
        if (shape1 == 0 && shape2 == 1) {
            Circle c1 = vert1.getCircle();
            Rectangle r2 = vert2.getRect();

            line = new Line (c1.getCenterX(), c1.getCenterY(), r2.getX()+(r2.getWidth()/2), r2.getY()+(r2.getHeight()/2));
            line.setStroke(Paint.valueOf(strColor));
            line.setStrokeWidth(3);
        }
        if (shape1 == 1 && shape2 == 0) {
            Rectangle r1 = vert1.getRect();
            Circle c2 = vert2.getCircle();

            line = new Line (r1.getX()+(r1.getWidth()/2), r1.getY()+(r1.getHeight()/2), c2.getCenterX(), c2.getCenterY());
            line.setStroke(Paint.valueOf(strColor));
            line.setStrokeWidth(3);
        }
        if (shape1 == 1 && shape2 == 1) {
            Rectangle r1 = vert1.getRect();
            Rectangle r2 = vert2.getRect();

            line = new Line (r1.getX() + (r1.getWidth()/2), r1.getY()+(r1.getHeight()/2), r2.getX()+(r2.getWidth()/2), r2.getY()+(r2.getHeight()/2));
            line.setStroke(Paint.valueOf(strColor));
            line.setStrokeWidth(3);
        }
    }

    public static void main (String[] args) { launch(args); }
}

