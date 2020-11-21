package services.builder;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.TableColumn;
import model.TableData;
import view.CustomTableView;

import java.util.ResourceBundle;


public class Director {

    private Builder builder;

    public Director(StackedBarChart ch, ObservableList<TableColumn> titles, ObservableList<TableData> data, ResourceBundle resources){
        builder = new BuilderRelease(ch, titles, data, resources);
    }

    public void build(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }

}
