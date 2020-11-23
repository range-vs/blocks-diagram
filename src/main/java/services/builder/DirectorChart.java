package services.builder;

import javafx.collections.ObservableList;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.TableColumn;
import model.TableData;

import java.util.ResourceBundle;

public class DirectorChart extends Director {

    public DirectorChart(StackedBarChart ch, ObservableList<TableColumn> titles, ObservableList<TableData> data, ResourceBundle resources){
        builder = new BuilderChart(ch, titles, data, resources);
    }

}
