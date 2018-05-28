package com.company;

import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;



public class Grafo {

    public ArrayList<Vertice> vertices;
    int contVert;


//    Constructor
    public Grafo () {
        vertices = new ArrayList<Vertice>();
        contVert = 0;
    }

    public void addCircleVertex (int shape, Circle circle) {
        vertices.add(new Vertice(shape, circle, contVert));
        contVert++;
    }

    public void addRectVertex (int shape, Rectangle rectangle) {
        vertices.add(new Vertice(shape, rectangle, contVert));
        contVert++;
    }

    public int getGraphSize () {
        return vertices.size();
    }

    public Circle getLastCircleVertex () {
        Vertice v = vertices.get(vertices.size()-1);
        return v.getCircle();
    }

    public Rectangle getLastRectVertex () {
        Vertice v = vertices.get(vertices.size()-1);
        return v.getRect();
    }

    public int getVertexId (Vertice v) {
        for (int i = 0; i < vertices.size()-1; i++) {
            Vertice auxV = vertices.get(i);
            if (v == auxV) {
                return i;
            }
        }
        return -1;
    }

    public Vertice getVertex (int id) {
        return vertices.get(id);
    }

    public Vertice getLastVertex () {
        return vertices.get(vertices.size()-1);
    }


    public Vertice getVertexByShape (Circle c) {
        for (int i = 0; i < vertices.size()-1; i++) {
            Vertice v = vertices.get(i);
            if (v.getCircle() == c) {
                return v;
            }
        }
        return null;
    }

    public Vertice getVertexByShape (Rectangle r) {
        for (int i = 0; i < vertices.size()-1; i++) {
            Vertice v = vertices.get(i);
            if (v.getRect() == r) {
                return v;
            }
        }
        return null;
    }
}

