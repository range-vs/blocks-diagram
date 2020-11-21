package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuItemPack {

    private EventHandler<ActionEvent> handler;
    private String title;

    MenuItemPack(EventHandler<ActionEvent> h, String t){
        handler = h;
        title = t;
    }

    public EventHandler<ActionEvent> getHandler() {
        return handler;
    }

    public String getTitle() {
        return title;
    }
}
