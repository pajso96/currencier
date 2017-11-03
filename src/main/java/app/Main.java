package app;

import com.sun.javafx.application.HostServicesDelegate;
import controllers.StartController;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import utils.StageStorage;
import utils.ViewLogger;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        Configuration.init();

        StageStorage.setStage(stage);

        Starter starter = new Starter();
        starter.manageStart(stage);
    }
}
