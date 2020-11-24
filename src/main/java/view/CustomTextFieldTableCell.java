package view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

import java.util.ResourceBundle;

public class CustomTextFieldTableCell<S,T> extends TableCell<S,T> {

    public static <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
        return forTableColumn(new DefaultStringConverter());
    }

    public static <S,T> Callback<TableColumn<S,T>, TableCell<S,T>> forTableColumn(
            final StringConverter<T> converter) {
        return list -> new TextFieldTableCell<S,T>(converter);
    }

    private TextField textField;
    private ResourceBundle resources;

    public CustomTextFieldTableCell(StringConverter<T> converter, ResourceBundle resources) {
        this.getStyleClass().add("text-field-table-cell");
        setConverter(converter);
        this.resources = resources;
    }

    private ObjectProperty<StringConverter<T>> converter =
            new SimpleObjectProperty<StringConverter<T>>(this, "converter");


    public final ObjectProperty<StringConverter<T>> converterProperty() {
        return converter;
    }

    public final void setConverter(StringConverter<T> value) {
        converterProperty().set(value);
    }

    public final StringConverter<T> getConverter() {
        return converterProperty().get();
    }

    @Override public void startEdit() {
        if (! isEditable()
                || ! getTableView().isEditable()
                || ! getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (textField == null) {
                textField = CustomCellUtils.createTextField(this, getConverter());
                var contextMenu = new ContextMenu();
                var itemsMenu = TextFieldContextMenuCreator.createDefaultMenuItemsTextField(textField, resources);
                contextMenu.getItems().addAll(itemsMenu);
                textField.setContextMenu(contextMenu);
            }
            CustomCellUtils.startEdit(this, getConverter(), null, null, textField);
        }
    }

    @Override public void cancelEdit() {
        super.cancelEdit();
        CustomCellUtils.cancelEdit(this, getConverter(), null);
    }

    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        CustomCellUtils.updateItem(this, getConverter(), null, null, textField);
    }

}