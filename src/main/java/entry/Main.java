package entry;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var locale = new Locale("ru", "UK");
        var bundle = ResourceBundle.getBundle("strings", locale);
        stage.setTitle(bundle.getString("window.title"));
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../main_window.fxml"), bundle), 700, 520));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
