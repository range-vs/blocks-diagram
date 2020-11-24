package view;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import model.TableData;
import model.TableDataColumns;
import services.builder.Director;
import services.builder.DirectorChart;

import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class View {

    @FXML
    private CustomTableView tableData;

    @FXML
    private StackedBarChart chart;

    protected ResourceBundle resources;

    public void setModel(ObservableList<TableData> data){
        tableData.setData(data);
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
        tableData.setResources(this.resources);
    }

    public File openFileDialog(String title){
        var fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("open.file.mask"),
                        resources.getString("open.file.extension")));
        return fileChooser.showOpenDialog(null);
    }

    public File saveFileDialog(String title){
        var fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("open.file.mask"),
                        resources.getString("open.file.extension")));
        return fileChooser.showSaveDialog(null);
    }

    public void messageBox(String title, String header, String msg){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void createNewTableColumns(TableDataColumns tdc){
        tableData.getColumns().clear();
        for(var i = 0; i < tdc.getTitle().size(); ++i){
            tableData.getColumns().add(tableData.createColumn(tdc.getTitle().get(i), i));
        }
    }

    public void reInitTableData(){
        tableData.init();
    }

    public void buildChart(ObservableList<TableData> data) {
        if(data.isEmpty()){
            messageBox(resources.getString("file.error.read.title"),
                    resources.getString("app.error"),
                    resources.getString("app.error.table.empty"));
            return;
        }
        var director = new DirectorChart(chart, tableData.getColumns(), data, resources);
        director.build();
    }

    public void clearTable(){
        tableData.getColumns().clear();
    }

    public void clearChart(){
        chart.getData().clear();
    }

    public ArrayList<String> getTitles(){
        var titles = new ArrayList<String>();
        for(var t: tableData.getColumns()){
            var tableColumn = (TableColumn<String, Double>)t;
            titles.add(((TextField)tableColumn.getGraphic()).getText());
        }
        return titles;
    }

}
