package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractView {

    public abstract void show(Stage stage);

    protected Parent load(String path) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views" + path));
        return loader.load();
    }

}
