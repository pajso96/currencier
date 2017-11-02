package controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import views.AbstractView;
import views.StartView;

public class StartController {

    private AbstractView view;
    private Stage stage;

    public StartController(Stage stage) {
        this.stage = stage;
        view = new StartView();
        view.show(stage);
    }

    @FXML
    public void initialize(){


    }


}
