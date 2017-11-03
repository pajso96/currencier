package utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ViewLogger {

    private static ViewLogger instance;
    private Stage stage;

    public static ViewLogger getInstance() {
        if (instance == null) {
            instance = new ViewLogger();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showModal(String text, final String type) {
        final Stage popupStage = new Stage();

        popupStage.initOwner(stage);
        popupStage.initModality(Modality.APPLICATION_MODAL);

        Button button = new Button("OK");
        button.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                popupStage.close();
                if (type.equals("Critical")) {
                    stage.close();
                }
            }
        });

        VBox pane = new VBox();
        pane.getChildren().add(new Text(text));
        pane.getChildren().add(button);
        pane.setPrefHeight(100);
        pane.setPrefWidth(300);
        pane.setSpacing(12);
        pane.setAlignment(Pos.CENTER);

        popupStage.setTitle(type);
        popupStage.setScene(new Scene(pane));

        if (type.equals("Critical")) {
            popupStage.showAndWait();
        } else {
            popupStage.show();
        }

    }

    public void showLog(String text) {
        showModal(text, "Information");
    }

    public void showCritical(String text) {
        showModal(text, "Critical");
    }
}

