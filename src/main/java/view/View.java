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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("open.file.mask"),
                        resources.getString("open.file.extension")));
        File selectedFile = fileChooser.showOpenDialog(null);
        return selectedFile;
    }

    public File saveFileDialog(String title){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(resources.getString("open.file.mask"),
                        resources.getString("open.file.extension")));
        File selectedFile = fileChooser.showSaveDialog(null);
        return selectedFile;
    }

    public void messageBox(String title, String header, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void createNewTableColumns(TableDataColumns tdc){
        tableData.getColumns().clear();
        for(Integer i = 0; i < tdc.getTitle().size(); ++i){
            tableData.getColumns().add(tableData.createColumn(tdc.getTitle().get(i), i));
        }
    }

    public void reinitTableData(){
        tableData.init();
    }

    public void buildChart(ObservableList<TableData> data) {
        if(data.isEmpty()){
            messageBox(resources.getString("file.error.read.title"),
                    resources.getString("app.error"),
                    resources.getString("app.error.table.empty"));
            return;
        }
        Director director = new Director(chart, tableData.getColumns(), data, resources);
        director.build();
    }

    public void clearTable(){
        tableData.getColumns().clear();
    }

    public void clearChart(){
        chart.getData().clear();
    }

    public ArrayList<String> getTitles(){
        ArrayList<String> titles = new ArrayList<>();
        for(var t: tableData.getColumns()){
            TableColumn<String, Double> tableColumn = (TableColumn<String, Double>)t;
            titles.add(((TextField)tableColumn.getGraphic()).getText());
        }
        return titles;
    }

}
