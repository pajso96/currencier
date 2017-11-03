package utils;

import javafx.stage.Stage;

public class StageStorage {

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StageStorage.stage = stage;
    }
}
