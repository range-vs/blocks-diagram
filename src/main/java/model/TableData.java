package model;

import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;

public class TableData {

    private ArrayList<SimpleDoubleProperty> data;

    public TableData(ArrayList<Double> d){
        data = new ArrayList<>();
        for(var elem: d){
            data.add(new SimpleDoubleProperty(elem));
        }
    }

    public TableData(){
        data = new ArrayList<>();
    }

    public ArrayList<SimpleDoubleProperty> getData() {
        return data;
    }
}
