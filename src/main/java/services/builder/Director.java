package services.builder;

import javafx.collections.ObservableList;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.TableColumn;
import model.TableData;

import java.util.ResourceBundle;


public abstract class Director {

    protected Builder builder;

    public void build(){
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
    }

}
