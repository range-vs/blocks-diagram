package view;

import javafx.beans.binding.Bindings;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextInputControl;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class TextFieldContextMenuCreator {

    public static ArrayList<MenuItem> createDefaultMenuItemsTextField(TextInputControl t, ResourceBundle resources) {
        var cut = new MenuItem(resources.getString("context.menu.cut"));
        cut.setOnAction(e -> t.cut());
        var copy = new MenuItem(resources.getString("context.menu.copy"));
        copy.setOnAction(e -> t.copy());
        var paste = new MenuItem(resources.getString("context.menu.paste"));
        paste.setOnAction(e -> t.paste());
        var delete = new MenuItem(resources.getString("context.menu.delete"));
        delete.setOnAction(e -> t.deleteText(t.getSelection()));
        var selectAll = new MenuItem(resources.getString("context.menu.select.all"));
        selectAll.setOnAction(e -> t.selectAll());

        var emptySelection = Bindings.createBooleanBinding(() ->
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


}
