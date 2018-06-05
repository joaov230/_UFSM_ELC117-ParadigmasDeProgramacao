package com.company;

import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.awt.geom.Line2D;
import java.util.ArrayList;



public class Grafo {

    private ArrayList<Vertice> vertices;
    private ArrayList<Adjacente> linhasAdjacentes;
    private int contVert;


//    Constructor
    public Grafo () {
        vertices = new ArrayList<Vertice>();
        linhasAdjacentes = new ArrayList<Adjacente>();
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

    public void connectVertex(Vertice vertex1, Vertice vertex2, Line line) {
        vertex1.connect(vertex1, vertex2, line);
        vertex2.connect(vertex2, vertex1, line);
        linhasAdjacentes.add(new Adjacente(vertex1, vertex2, line));
    }

    public int verifyIntesection () {
        int somador = 0;
        for (Adjacente l1 : linhasAdjacentes) {
            for (Adjacente l2 : linhasAdjacentes) {
                if ((l1.getIni().equals(l2.getIni())) || (l1.getFim().equals(l2.getFim())) ||
                        (l1.getIni().equals(l2.getFim())) || (l1.getFim().equals(l2.getIni())))
                    continue;

                if (l1.equals(l2))
                    continue;

                if (Line2D.linesIntersect(l1.getLine().getStartX(), l1.getLine().getStartY(), l1.getLine().getEndX(),
                        l1.getLine().getEndY(), l2.getLine().getStartX(), l2.getLine().getStartY(), l2.getLine().getEndX(),
                        l2.getLine().getEndY())) {
                    somador++;
                }
            }
        }

        return (somador/2);
    }

    public void clear () {
        vertices.clear();
        contVert = 0;
    }

    public void removeVertice (Vertice vert) {
        vertices.remove(vert);
    }


//    Deixar do jeito certo
    public int getSize () {
        int cont = 0;
        for (Vertice v : vertices) {
            if (v.getRect() != null) {
                if (v.getRect().getWidth() > 3) {
                    cont++;
                }
            }
            if (v.getCircle() != null) {
                if (v.getCircle().getRadius() > 3) {
                    cont++;
                }
            }
        }
        return cont;
    }


    public int getTotalConnections () {
        int somatorio = 0;
        for (Vertice vert : vertices) {
            somatorio += vert.getConnectionsSize();
        }
        return (somatorio/2);
    }

    public Circle getLastCircleVertex () {
        for (int i = vertices.size()-1; i >= 0; i--) {
            if (vertices.get(i).getRect() == null) {
                return vertices.get(i).getCircle();
            }
        }
        return null;
    }

    public Rectangle getLastRectVertex () {
        for (int i = vertices.size()-1; i >= 0; i--) {
            if (vertices.get(i).getCircle() == null) {
                return vertices.get(i).getRect();
            }
        }
        return null;
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

