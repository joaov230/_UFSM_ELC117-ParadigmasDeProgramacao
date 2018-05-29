package com.company;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.awt.*;
import java.util.ArrayList;


// 0 - Square
// 1 - Circle
public class Vertice {

    private int index;

    public  ArrayList<Adjacente> adjacentes;

    private int vertexShape;
    private Rectangle rect;
    private Circle circle;


    public Vertice(int shape, Circle thisCircle, int id) {
        adjacentes = new ArrayList<Adjacente>();
        vertexShape = shape;
        circle = thisCircle;
        rect = null;
        index = id;
    }

    public Vertice(int shape, Rectangle thisRectangle, int id) {
        adjacentes = new ArrayList<Adjacente>();
        vertexShape = shape;
        rect = thisRectangle;
        circle = null;
        index = id;
    }

    public void connect (Vertice vertex1, Vertice vertex2, Line line) {
        adjacentes.add(new Adjacente(vertex1, vertex2, line));
    }

    public void printConnections () {
        System.out.print(index + " -> ");
        for (Adjacente aux : adjacentes) {
            System.out.print(aux.getFimIndex() + " ");
        }
        System.out.println(" ");
    }

    public boolean isConnected (Vertice vertex) {
        for (Adjacente aux : adjacentes) {
            if (vertex.getIndex() == aux.getFimIndex()) {
                return true;
            }
        }
        return false;
    }

    public void setVertexShape (int shape) {
        vertexShape = shape;
    }

    public int getVertexShape () {
        return vertexShape;
    }

    public int getIndex () {
        return index;
    }

    public Adjacente getAdj (Vertice vert1, Vertice vert2) {
        for (Adjacente adj : adjacentes) {
            if (adj.getIni().equals(vert1) && adj.getFim().equals(vert2)) {
                return adj;
            }
        }
        return null;
    }

    public int getConnectionsSize () {
        return adjacentes.size();
    }

    public Circle getCircle () {
        return circle;
    }

    public Rectangle getRect () {
        return rect;
    }

}
