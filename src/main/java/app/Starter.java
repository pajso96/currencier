package app;

import database.Database;
import javafx.stage.Stage;
import model.Setting;
import utils.ViewLogger;
import views.AbstractView;
import views.StartView;

/**
 * Used for detect which view run for first
 */
public class Starter {

    public void manageStart(Stage stage) {
        Database.checkDriver();


        ViewLogger viewLogger = ViewLogger.getInstance();
        viewLogger.setStage(stage);

        if (!isSettingsInitialized()) {
            Setting.createNew();
            AbstractView view = new StartView();
            view.show(stage);
            viewLogger.showLog("This is first time you run application.\nNow you must set some preferences.");
            return;
        }



    }

    private boolean isSettingsInitialized(){
        Setting setting = Setting.getInstance();
        return setting != null;
    }

}
