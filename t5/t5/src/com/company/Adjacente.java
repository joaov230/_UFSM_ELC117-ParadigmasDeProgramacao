package com.company;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Adjacente {

    private Vertice ini;
    private Vertice fim;
    private Line line;


    public Adjacente(Vertice thisIni, Vertice thisFim, Line thisLine) {
        ini = thisIni;
        fim = thisFim;
        line = thisLine;
    }


    public Vertice getIni () {
        return ini;
    }

    public Vertice getFim () {
        return fim;
    }

    public Line getLine () {
        return line;
    }

    public int getFimIndex () {
        return fim.getIndex();
    }

    public int getIniIndex () {
        return ini.getIndex();
    }

}
