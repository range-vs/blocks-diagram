package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.TableData;
import model.TableDataColumns;
import services.LoadModelFromFile;
import services.SaveModelFromFile;
import view.View;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller extends View implements Initializable {

    private ObservableList<TableData> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        setResources(resources);
        reInitTableData();
        setModel(data);
    }

    public void openMenu() {
        File file = openFileDialog(resources.getString("open.file.msg"));
        if(file == null){
            return;
        }
        TableDataColumns loadedData = new LoadModelFromFile().loadData(file);
        if(loadedData == null){
            messageBox(resources.getString("file.error.read.title"),
                    resources.getString("file.error.read.header"),
                    resources.getString("file.error.read.msg") + "\"" + file.getAbsolutePath() + "\"");
            return;
        }
        reInitTableData();
        createNewTableColumns(loadedData);
        data = FXCollections.observableArrayList(loadedData.getData());
        setModel(data);
    }

    public void buildChart() {
        buildChart(data);
    }

    @Override
    public void clearTable() {
        data.clear();
        super.clearTable();
        reInitTableData();
    }

    @Override
    public void clearChart() {
        super.clearChart();
    }

    public void saveMenu() {
        if(data.isEmpty()){
            messageBox(resources.getString("file.error.read.title"),
                    resources.getString("app.error"),
                    resources.getString("app.error.table.empty"));
            return;
        }
        File file = saveFileDialog(resources.getString("save.file.msg"));
        if(file == null){
            return;
        }
        var titles = getTitles();
        TableDataColumns tableDataColumns = new TableDataColumns(new ArrayList<>(data), titles);
        if(!new SaveModelFromFile().saveData(file, tableDataColumns)){
            messageBox(resources.getString("file.error.read.title"),
                    resources.getString("file.error.read.header"),
                    resources.getString("file.error.write.msg") + "\"" + file.getAbsolutePath() + "\"");
        }
    }

}
