package view;

import utils.BooleanHelper;
import converters.CustomDoubleStringConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import model.TableData;

import java.util.*;

public class CustomTableView extends TableView {

    private TableColumn currentColumn;
    private ObservableList<TableData> data;
    private HashMap<ContextMenuItems, MenuItem> contextMenuItems;
    private ContextMenu contextMenuColumns;
    private ResourceBundle resources;

    public CustomTableView(){
        super();
    }

    public void init(){
        currentColumn = null;
        getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        initContextMenu();
        setContextMenu(createContextMenuRows());
        contextMenuColumns = createContextMenuColumns();
    }

    private void initContextMenu(){
        HashMap<ContextMenuItems, MenuItemPack> items = new HashMap<>(){{
           put(ContextMenuItems.ADD_NEW_COLUMN, new MenuItemPack(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   TableData td = new TableData(new ArrayList<>(){{
                       add(0.0);
                   }});
                   data.clear();
                   data.add(td);
                   getColumns().add(createColumn(resources.getString("context.menu.new.column"), 0));
                   showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
               }
           }, resources.getString("context.menu.new.column")));
            put(ContextMenuItems.ADD_BEFORE_ROW, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = getSelectionModel().getSelectedIndex();
                    ArrayList<Double> values = new ArrayList<>();
                    for(int i = 0; i < data.get(0).getData().size(); ++i){
                        values.add(0.0);
                    }
                    data.add(index, new TableData(values));
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));}
            }, resources.getString("context.menu.new.row.before")));
            put(ContextMenuItems.ADD_AFTER_ROW, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = getSelectionModel().getSelectedIndex();
                    ArrayList<Double> values = new ArrayList<>();
                    for(int i = 0; i < data.get(0).getData().size(); ++i){
                        values.add(0.0);
                    }
                    data.add(index + 1, new TableData(values));
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.new.row.after")));
            put(ContextMenuItems.REMOVE_ROW, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int index = getSelectionModel().getSelectedIndex();
                    if(index == -1)
                        return;
                    data.remove(index);
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.remove.row")));
            put(ContextMenuItems.ADD_NEW_ROW, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ArrayList<Double> values = new ArrayList<>();
                    for(int i = 0; i < getColumns().size(); ++i){
                        values.add(0.0);
                    }
                    data.add(new TableData(values));
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.new.row")));
            // -------------- //
            put(ContextMenuItems.ADD_BEFORE_COLUMN, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int ind = getColumns().indexOf(currentColumn);
                    getColumns().add(ind, createColumn(resources.getString("context.menu.new.column"), data.size() - 1));
                    for(var elem: data){
                        elem.getData().add(ind, new SimpleDoubleProperty(0.0));
                    }
                    for(int i = 0; i < getColumns().size(); ++i){ // recalculate index model
                        TableColumn<TableData, Double> tc = (TableColumn<TableData, Double>) getColumns().get(i);
                        final int indexProp = i;
                        tc.setCellValueFactory(cellData -> cellData.getValue().getData().get(indexProp).asObject());
                    }
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.new.column.before")));
            put(ContextMenuItems.ADD_AFTER_COLUMN, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int ind = getColumns().indexOf(currentColumn) + 1;
                    getColumns().add(ind, createColumn(resources.getString("context.menu.new.column"), data.size() - 1));
                    for(var elem: data){
                        elem.getData().add(ind, new SimpleDoubleProperty(0.0));
                    }
                    for(int i = 0; i < getColumns().size(); ++i){ // recalculate index model
                        TableColumn<TableData, Double> tc = (TableColumn<TableData, Double>) getColumns().get(i);
                        final int indexProp = i;
                        tc.setCellValueFactory(cellData -> cellData.getValue().getData().get(indexProp).asObject());
                    }
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.new.column.after")));
            put(ContextMenuItems.REMOVE_COLUMN, new MenuItemPack(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    int ind = getColumns().indexOf(currentColumn);
                    for(var elem: data){
                        elem.getData().remove(ind);
                    }
                    if(data.get(0).getData().isEmpty()){
                        data.clear();
                    }
                    getColumns().remove(currentColumn);
                    currentColumn = null;
                    for(int i = 0; i < getColumns().size(); ++i){
                        TableColumn<TableData, Double> tc = (TableColumn<TableData, Double>) getColumns().get(i);
                        final int indexProp = i;
                        tc.setCellValueFactory(cellData -> cellData.getValue().getData().get(indexProp).asObject());
                    }
                    showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size()));
                }
            }, resources.getString("context.menu.remove.column")));
        }};

        contextMenuItems = new HashMap<>();
        for(var pair: items.entrySet()){
            MenuItem item = new MenuItem(pair.getValue().getTitle());
            contextMenuItems.put(pair.getKey(), item);
            item.setOnAction(pair.getValue().getHandler());
        }

    }

    private ContextMenu createContextMenuRows(){
        List<ContextMenuItems> keys =
                Arrays.asList(ContextMenuItems.ADD_NEW_COLUMN,
                        ContextMenuItems.ADD_BEFORE_ROW,
                        ContextMenuItems.ADD_AFTER_ROW,
                        ContextMenuItems.REMOVE_ROW,
                        ContextMenuItems.ADD_NEW_ROW);
        ContextMenu contextMenu = new ContextMenu();
        for(var k: keys){
            contextMenu.getItems().add(contextMenuItems.get(k));
        }
        showMenuItems(data != null ? BooleanHelper.toBoolean(data.size()) : false, BooleanHelper.toBoolean(getColumns().size()));
        return contextMenu;
    }

    private ContextMenu createContextMenuColumns(){
        List<ContextMenuItems> keys =
                Arrays.asList(ContextMenuItems.ADD_BEFORE_COLUMN,
                        ContextMenuItems.ADD_AFTER_COLUMN,
                        ContextMenuItems.REMOVE_COLUMN);
        ContextMenu contextMenu = new ContextMenu();
        for(var k: keys){
            contextMenu.getItems().add(contextMenuItems.get(k));
        }
        contextMenu.getItems().add(new SeparatorMenuItem());
        return contextMenu;
    }

    public TableColumn<TableData, Double> createColumn(String title, Integer index){
        // create column and register click on column
        TableColumn<TableData, Double> tableColumn = new TableColumn<>();
        tableColumn.setPrefWidth(150.0);
        tableColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        tableColumn.setCellValueFactory(cellData -> cellData.getValue().getData().get(index).asObject());
        TextField titleHeaderColumn = new TextField(title);
        titleHeaderColumn.setUserData(tableColumn);
        titleHeaderColumn.setOnMouseClicked(
                e -> currentColumn = (TableColumn)((TextField)e.getSource()).getUserData()
        );

        titleHeaderColumn.setContextMenu(contextMenuColumns);
        var defaultContextMenu = createDefaultMenuItems(titleHeaderColumn);
        titleHeaderColumn.getContextMenu().setOnShowing(event -> titleHeaderColumn.getContextMenu().getItems().addAll(defaultContextMenu));
        titleHeaderColumn.getContextMenu().setOnHiding(event -> titleHeaderColumn.getContextMenu().getItems().remove(4, titleHeaderColumn.getContextMenu().getItems().size()));

        tableColumn.setGraphic(titleHeaderColumn);
        return tableColumn;
    }

    private ArrayList<MenuItem> createDefaultMenuItems(TextInputControl t) {
        MenuItem cut = new MenuItem(resources.getString("context.menu.cut"));
        cut.setOnAction(e -> t.cut());
        MenuItem copy = new MenuItem(resources.getString("context.menu.copy"));
        copy.setOnAction(e -> t.copy());
        MenuItem paste = new MenuItem(resources.getString("context.menu.paste"));
        paste.setOnAction(e -> t.paste());
        MenuItem delete = new MenuItem(resources.getString("context.menu.delete"));
        delete.setOnAction(e -> t.deleteText(t.getSelection()));
        MenuItem selectAll = new MenuItem(resources.getString("context.menu.select.all"));
        selectAll.setOnAction(e -> t.selectAll());

        BooleanBinding emptySelection = Bindings.createBooleanBinding(() ->
                        t.getSelection().getLength() == 0,
                t.selectionProperty());

        cut.disableProperty().bind(emptySelection);
        copy.disableProperty().bind(emptySelection);
        delete.disableProperty().bind(emptySelection);

        return new ArrayList<>(){{
            add(cut);
            add(copy);
            add(paste);
            add(delete);
            add(new SeparatorMenuItem());
            add(selectAll);
        }};
    }

    public void setData(ObservableList<TableData> data) {
        this.data = data;
        setItems(this.data);
        showMenuItems(BooleanHelper.toBoolean(data.size()), BooleanHelper.toBoolean(getColumns().size())); // только если не пустая коллекция
    }

    private void safeSetVisible(MenuItem m, boolean vis){
        if(m == null){
            return;
        }
        m.setVisible(vis);
    }

    private void showMenuItems(Boolean isRows, Boolean isColumns){
        if(!isRows && !isColumns){
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_COLUMN), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_COLUMN), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_COLUMN), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_ROW), false);
        }else if(!isRows && isColumns){
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_COLUMN), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_ROW), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_ROW), false);
        }else if(isRows && isColumns){
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_COLUMN), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_NEW_ROW), false);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_AFTER_ROW), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.ADD_BEFORE_ROW), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_COLUMN), true);
            safeSetVisible(contextMenuItems.get(ContextMenuItems.REMOVE_ROW), true);
        }
//        else if(isRows && !isColumns){
//            contextMenuItems.get(ContextMenuItems.ADD_NEW_COLUMN).setVisible(false);
//            contextMenuItems.get(ContextMenuItems.ADD_NEW_ROW).setVisible(false);
//            contextMenuItems.get(ContextMenuItems.ADD_AFTER_COLUMN).setVisible(true);
//            contextMenuItems.get(ContextMenuItems.ADD_AFTER_ROW).setVisible(true);
//            contextMenuItems.get(ContextMenuItems.ADD_BEFORE_COLUMN).setVisible(true);
//            contextMenuItems.get(ContextMenuItems.ADD_BEFORE_ROW).setVisible(true);
//            contextMenuItems.get(ContextMenuItems.REMOVE_COLUMN).setVisible(true);
//            contextMenuItems.get(ContextMenuItems.REMOVE_ROW).setVisible(true);
//        }
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }
}
