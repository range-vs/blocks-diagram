package services.builder;

import javafx.beans.property.DoublePropertyBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import model.TableData;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

public class BuilderChart extends Builder {

    private Chart chart;
    private ObservableList<TableColumn> titles;
    private ObservableList<TableData> data;
    private ResourceBundle resources;

    public BuilderChart(StackedBarChart ch, ObservableList<TableColumn> titles, ObservableList<TableData> data, ResourceBundle resources){
        chart = new Chart(ch);
        this.titles = titles;
        this.data = data;
        this.resources = resources;
    }

    @Override
    public void buildPartA() {
        // create base properties for chart
        chart.getChart().getXAxis().setLabel(resources.getString("chart.titles"));
        chart.getChart().getYAxis().setLabel(resources.getString("chart.user.data"));
        chart.getChart().setAnimated(false);
    }

    @Override
    public void buildPartB() {
        // create all series for chart
        chart.getChart().getData().clear();
        for(var t: titles){
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(((TextField)(t.getGraphic())).getText());
            chart.getChart().getData().add(series);
        }
    }

    @Override
    public void buildPartC() {
        // create all data for all series for chart
        chart.getChart().setTitle(resources.getString("chart"));
        chart.getChart().setAnimated(true);
        double bigValue = 0.0;
        for(var arr: data){
            double tmp = arr.getData().stream().mapToDouble(DoublePropertyBase::get).sum();
            bigValue = Math.max(tmp, bigValue);
        }
        double smallValue = 0.0;
        for(var arr: data){
            double tmp = arr.getData().stream().mapToDouble(DoublePropertyBase::get).min().orElseThrow(NoSuchElementException::new);
            smallValue = Math.min(tmp, smallValue);
        }
        chart.getChart().getYAxis().setAutoRanging(false);
        ((NumberAxis)chart.getChart().getYAxis()).setLowerBound(Math.round(smallValue));
        ((NumberAxis)chart.getChart().getYAxis()).setUpperBound(Math.round(bigValue));
        ((NumberAxis)chart.getChart().getYAxis()).setTickUnit(Math.round(bigValue / 10.0));
        for(int i = 0; i < data.size(); ++i){
            for(int j = 0; j < data.get(i).getData().size(); ++j){
                double value = data.get(i).getData().get(j).doubleValue();
                ((XYChart.Series<String, Double>)chart.getChart().getData().get(j)).getData().add(new XYChart.Data<>(resources.getString("row") + (i + 1), value));
            }
        }
        ArrayList<String> xaxisTitles = new ArrayList<>();
        for(int i = 0; i < data.size(); ++i){
            xaxisTitles.add(resources.getString("row") + (i + 1));
        }
        ((CategoryAxis)chart.getChart().getXAxis()).getCategories().clear();
        ((CategoryAxis)chart.getChart().getXAxis()).setCategories(FXCollections.observableList(xaxisTitles));
    }

}
