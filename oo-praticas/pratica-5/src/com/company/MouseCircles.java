package com.company;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

public class MouseCircles extends Application {

   private Circle c;
   private Line line;
   private boolean veredito = true;

   @Override
   public void start(Stage stage) {
       Pane pane = new Pane();

       Button btnC = new Button ("Circle");
       Button btnL = new Button ("Line");

       btnC.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent event) {
               veredito = true;
           }
       });

       btnL.setOnAction(new EventHandler<ActionEvent>() {
           public void handle(ActionEvent event) {
               veredito = false;
           }
       });


       pane.setOnMousePressed(new EventHandler<MouseEvent>() {
           public void handle(MouseEvent e) {
               if (veredito) {
                   c = new Circle(e.getX(), e.getY(), 0, Color.RED);
                   pane.getChildren().add(c);
               } else {
                   line = new Line(e.getX(), e.getY(), e.getX(), e.getY());
                   pane.getChildren().add(line);
               }
           }
       });

       pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
           public void handle(MouseEvent e) {
               if (veredito) {
                   double dist = Math.abs(Math.hypot(e.getX(), e.getY()) - Math.hypot(c.getCenterX(), c.getCenterY()));
                   c.setRadius(dist);
               } else {
                   line.setEndX(e.getX());
                   line.setEndY(e.getY());
               }
           }
       });


       HBox hb = new HBox();
       hb.setAlignment(Pos.CENTER);
       hb.getChildren().addAll(btnC,btnL);

       BorderPane bp = new BorderPane();
       bp.setCenter(pane);
       bp.setTop(hb);


       Scene scene = new Scene(bp, 600, 500);
       stage.setScene(scene);
       stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
