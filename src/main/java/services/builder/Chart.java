package services.builder;

import javafx.scene.chart.StackedBarChart;

public class Chart {

    private StackedBarChart chart;

    public Chart(StackedBarChart ch){
        chart = ch;
    }

    public StackedBarChart getChart() {
        return chart;
    }
}
