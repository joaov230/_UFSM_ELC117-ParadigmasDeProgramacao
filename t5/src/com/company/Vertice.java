package com.company;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;


// 0 - Square
// 1 - Circle
public class Vertice {

    int index;
    public ArrayList<Vertice> adjacentes;
    public int vertexShape;
    public Rectangle rect;
    public Circle circle;


    public Vertice(int shape, Circle thisCircle, int id) {
        adjacentes = new ArrayList<Vertice>();
        vertexShape = shape;
        circle = thisCircle;
        rect = null;
        index = id;
    }

    public Vertice(int shape, Rectangle thisRectangle, int id) {
        adjacentes = new ArrayList<Vertice>();
        vertexShape = shape;
        rect = thisRectangle;
        circle = null;
        index = id;
    }

    //  Conecta esse vertice com outro vertice
    public void connect (Vertice vertex) {
        System.out.println(index + " conectou com " + vertex.getIndex());
        adjacentes.add(vertex);
    }

    public void printConnections () {
        System.out.print(index + " -> ");
        for (Vertice aux : adjacentes) {
            System.out.print(aux.getIndex() + " ");
        }
        System.out.println(" ");
    }

    public int getIndex () {
        return index;
    }

    public boolean isConnected (Vertice vertex) {
        for (Vertice aux : adjacentes) {
            if (vertex.getIndex() == aux.getIndex()) {
                return true;
            }
        }
        return false;
    }


    public int getConnectionsSize () {
        return adjacentes.size();
    }

    public void setVertexShape (int shape) {
        vertexShape = shape;
    }

    public int getVertexShape () {
        return vertexShape;
    }

    public Circle getCircle () {
        return circle;
    }

    public Rectangle getRect () {
        return rect;
    }

}
