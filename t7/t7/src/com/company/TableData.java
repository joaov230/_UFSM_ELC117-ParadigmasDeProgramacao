package com.company;

import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class TableData {
    private final SimpleStringProperty dataHora;
    private final SimpleStringProperty ordem;
    private final SimpleStringProperty linha;
    private final SimpleStringProperty latitude;
    private final SimpleStringProperty longitude;
    private final SimpleStringProperty velocidade;

    public TableData(List lis) {
        this.dataHora = new SimpleStringProperty(lis.get(0).toString());
        this.ordem = new SimpleStringProperty(lis.get(1).toString());
        this.linha = new SimpleStringProperty(String.valueOf(lis.get(2)));
        this.latitude = new SimpleStringProperty(String.valueOf(lis.get(3)));
        this.longitude = new SimpleStringProperty(String.valueOf(lis.get(4)));
        this.velocidade = new SimpleStringProperty(String.valueOf(lis.get(5)));
    }

    public SimpleStringProperty dataHoraProperty() {
        return dataHora;
    }
    public String getDataHora() {
        return dataHora.get();
    }
    public void setDataHora(String dh) {
        this.dataHora.set(dh);
    }

    public SimpleStringProperty ordemProperty() {
        return ordem;
    }
    public String getOrdem() {
        return ordem.get();
    }
    public void setOrdem(String ord) {
        this.ordem.set(ord);
    }

    public SimpleStringProperty linhaProperty() {
        return linha;
    }
    public String getLinha() {
        return linha.get();
    }
    public void setLinha(String lin) {
        this.linha.set(lin);
    }

    public SimpleStringProperty latitudeProperty() {
        return latitude;
    }
    public String getLatitude() {
        return latitude.get();
    }
    public void setLatitude(String lat) {
        this.latitude.set(lat);
    }

    public SimpleStringProperty longitudeProperty() {
        return longitude;
    }
    public String getLongitude() {
        return longitude.get();
    }
    public void setLongitude(String longi) {
        this.longitude.set(longi);
    }

    public SimpleStringProperty velocidadeProperty() {
        return velocidade;
    }
    public String getVelocidade() {
        return velocidade.get();
    }
    public void setVelocidade(String vel) {
        this.velocidade.set(vel);
    }
}