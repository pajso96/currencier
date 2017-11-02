package views;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartView extends AbstractView {

    public void show(Stage stage) {
        try {
            Parent parent = load("/start.fxml");

            stage.setTitle("Currencier");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
