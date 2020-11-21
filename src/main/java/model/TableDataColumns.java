package model;

import java.util.ArrayList;

public class TableDataColumns {

    private ArrayList<String> title;
    private ArrayList<TableData> data;

    public TableDataColumns(){
        title = new ArrayList<>();
        data = new ArrayList<>();
    }

    public TableDataColumns(ArrayList<TableData> data, ArrayList<String> titles){
        title = titles;
        this.data = data;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public ArrayList<TableData> getData() {
        return data;
    }
}
